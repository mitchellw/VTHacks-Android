package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import twitter4j.MediaEntity;
import twitter4j.Status;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.vt.vthacks.model.IPhotoStreamItem;

public class TwitterPhotoStreamItem implements IPhotoStreamItem {
	private String text;
	private URL imageURL;
	private ConcurrentHashMap<String, Bitmap> cache;

	public TwitterPhotoStreamItem(Status status) {
		this.text = "@" + status.getUser().getScreenName() + ": " + status.getText();
		this.cache = new ConcurrentHashMap<String, Bitmap>();

		MediaEntity[] mediaEntities = status.getMediaEntities();
		for (int i = 0; i < mediaEntities.length; i++) {
			try {
				imageURL = new URL(mediaEntities[i].getMediaURL());
				break;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public String getText() {
		return text;
	}

	@Override
	public Bitmap getImage() {
		Bitmap bitmap = cache.get(imageURL.toString());

		if (bitmap == null) {
			Log.d("stuff", "stuff");
			bitmap = getBitmapFromURL();
			cache.put(imageURL.toString(), bitmap);
		}

		return bitmap;
	}

	private Bitmap getBitmapFromURL() {
		Bitmap bitmap = null;
		try {
			InputStream is = imageURL.openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		}

		return bitmap;
	}

	@Override
	public String toString() {
		return text;
	}
}
