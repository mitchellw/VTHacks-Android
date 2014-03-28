package com.vt.vthacks.model.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import twitter4j.MediaEntity;
import twitter4j.Status;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.vt.vthacks.model.IPhotoStreamItem;

public class TwitterPhotoStreamItem implements IPhotoStreamItem {
	private String text;
	private Bitmap bitmap;

	public TwitterPhotoStreamItem(Status status) {
		text = "@" + status.getUser().getScreenName() + ":" + status.getText();
		MediaEntity[] mediaEntities = status.getMediaEntities();
		for (int i = 0; i < mediaEntities.length; i++) {
			try {
				URL mediaEntity = new URL(mediaEntities[i].getMediaURL());
				InputStream is = mediaEntity.openStream();
				bitmap = BitmapFactory.decodeStream(is);
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
		return bitmap;
	}

	@Override
	public String toString() {
		return text;
	}
}
