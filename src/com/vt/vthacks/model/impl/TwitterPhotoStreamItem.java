package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import twitter4j.MediaEntity;
import twitter4j.Status;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Log;

import com.vt.vthacks.model.IPhotoStreamItem;

public class TwitterPhotoStreamItem implements IPhotoStreamItem {
	private static final String TAG = "TwitterPhotoStreamItem";

	private static LruCache<String, Bitmap> cache =
			new LruCache<String, Bitmap>((int)Runtime.getRuntime().maxMemory() / 1024 / 3) {
		@Override
		protected int sizeOf(String key, Bitmap value) {
			if (value == null) {
				return 0;
			}
			return value.getRowBytes() * value.getHeight() / 1024;
		}
		
		protected void entryRemoved(boolean evicted, String key, Bitmap oldValue, Bitmap newValue) {
			super.entryRemoved(evicted, key, oldValue, newValue);
			oldValue.recycle();
		}
	};

	private String username;
	private URL userImageURL;
	private String text;
	private URL imageURL;

	public TwitterPhotoStreamItem(Status status) {
		this.username = "@" + status.getUser().getScreenName();

		try {
			userImageURL = new URL(status.getUser().getBiggerProfileImageURL());
		} catch (IOException e) {
		}

		this.text = status.getText();

		MediaEntity[] mediaEntities = status.getMediaEntities();
		for (int i = 0; i < mediaEntities.length; i++) {
			try {
				imageURL = new URL(mediaEntities[i].getMediaURL());
				break;
			} catch (IOException e) {
			}
		}
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Bitmap getImage() {
		if (imageURL == null) {
			return null;
		}

		Bitmap bitmap = null;
		synchronized (cache) {
			bitmap = cache.get(imageURL.toString());

			if (bitmap == null) {
				bitmap = getBitmapFromURL(imageURL);
				if (bitmap != null) {
					cache.put(imageURL.toString(), bitmap);
				}
			}
		}

		return bitmap;
	}

	@Override
	public String getUser() {
		return username;
	}

	@Override
	public Bitmap getUserImage() {
		if (userImageURL == null) {
			return null;
		}

		Bitmap bitmap = null;
		synchronized (cache) {
			bitmap = cache.get(userImageURL.toString());

			if (bitmap == null) {
				bitmap = getBitmapFromURL(userImageURL);
				if (bitmap != null) {
					cache.put(userImageURL.toString(), bitmap);
				}
			}
		}

		return bitmap;
	}

	@Override
	public String toString() {
		return text;
	}
	
	public static void clearCache() {
		cache.evictAll();
	}

	private Bitmap getBitmapFromURL(URL url) {
		Bitmap bitmap = null;
		try {
			InputStream is = url.openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		}
		catch (OutOfMemoryError e) {
			Log.d(TAG, "oom");
		}

		return bitmap;
	}
}
