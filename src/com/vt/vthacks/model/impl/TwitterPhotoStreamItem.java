package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.concurrent.ConcurrentHashMap;

import twitter4j.MediaEntity;
import twitter4j.Status;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vt.vthacks.model.IPhotoStreamItem;

public class TwitterPhotoStreamItem implements IPhotoStreamItem {
	private String username;
	private URL userImageURL;
	private String text;
	private URL imageURL;
	private ConcurrentHashMap<String, Bitmap> cache;

	public TwitterPhotoStreamItem(Status status) {
		this.cache = new ConcurrentHashMap<String, Bitmap>();
		
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

		Bitmap bitmap = cache.get(imageURL.toString());

		if (bitmap == null) {
			bitmap = getBitmapFromURL(imageURL);
			cache.put(imageURL.toString(), bitmap);
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

		Bitmap bitmap = cache.get(userImageURL.toString());

		if (bitmap == null) {
			bitmap = getBitmapFromURL(userImageURL);
			cache.put(userImageURL.toString(), bitmap);
		}

		return bitmap;
	}

	@Override
	public String toString() {
		return text;
	}

	private Bitmap getBitmapFromURL(URL url) {
		Bitmap bitmap = null;
		try {
			InputStream is = url.openStream();
			bitmap = BitmapFactory.decodeStream(is);
		} catch (IOException e) {
		}

		return bitmap;
	}
}
