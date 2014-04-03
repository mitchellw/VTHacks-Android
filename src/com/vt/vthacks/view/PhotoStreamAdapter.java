package com.vt.vthacks.view;

import java.lang.ref.WeakReference;
import java.util.List;

import com.vt.vthacks.R;
import com.vt.vthacks.model.IPhotoStreamItem;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class PhotoStreamAdapter extends ArrayAdapter<IPhotoStreamItem> {

	private Context context;
	private LayoutInflater mInflater;
	private OnImageClickListener clickListener;

	public PhotoStreamAdapter(Context context, List<IPhotoStreamItem> listItems, OnImageClickListener clickListener) {
		super(context, 0, listItems);
		this.context = context;
		this.clickListener = clickListener;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		final IPhotoStreamItem item = getItem(position);

		final PhotoStreamItemViewHolder photoStreamViewHolder;
		if (convertView == null) {
			RelativeLayout rootView = (RelativeLayout)mInflater.inflate(R.layout.photo_stream_row, parent, false);
			photoStreamViewHolder = PhotoStreamItemViewHolder.create(rootView);
			rootView.setTag(photoStreamViewHolder);
		}
		else {
			photoStreamViewHolder = (PhotoStreamItemViewHolder)convertView.getTag();
		}
		
		LoadBitmapTask task = new LoadBitmapTask(photoStreamViewHolder, item);
		if (photoStreamViewHolder.getTask() != null) {
			photoStreamViewHolder.getTask().cancel(true);
		}
		photoStreamViewHolder.setTask(task);
		task.execute();
		photoStreamViewHolder.textView.setText(item.getText());
		
		photoStreamViewHolder.rootView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				clickListener.onImageClicked(item.getImage());
			}
		});

		return photoStreamViewHolder.rootView;
	}

	private static class PhotoStreamItemViewHolder {
		public final RelativeLayout rootView;
		public final TextView textView;
		public final ImageView imageView;
		public final ProgressBar progressBar;

		private LoadBitmapTask task;

		private PhotoStreamItemViewHolder(RelativeLayout rootView, TextView textView, ImageView imageView, ProgressBar progressBar) {
			this.rootView = rootView;
			this.textView = textView;
			this.imageView = imageView;
			this.progressBar = progressBar;
		}
		
		public LoadBitmapTask getTask() {
			return task;
		}
		
		public void setTask(LoadBitmapTask task) {
			this.task = task;
		}

		public static PhotoStreamItemViewHolder create(RelativeLayout rootView) {
			TextView textView = (TextView)rootView.findViewById(R.id.textView);
			ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
			ProgressBar progressBar = (ProgressBar)rootView.findViewById(R.id.progressBar);
			return new PhotoStreamItemViewHolder(rootView, textView, imageView, progressBar);
		}
	}

	private class LoadBitmapTask extends AsyncTask<Void, Void, Bitmap> {

		private WeakReference<PhotoStreamItemViewHolder> viewHolderRef;
		private IPhotoStreamItem item;

		public LoadBitmapTask(PhotoStreamItemViewHolder viewHolder, IPhotoStreamItem item) {
			this.viewHolderRef = new WeakReference<PhotoStreamItemViewHolder>(viewHolder);
			this.item = item;
		}
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
			if (viewHolderRef != null) {
				PhotoStreamItemViewHolder viewHolder = viewHolderRef.get();
				if (viewHolder != null) {
					viewHolder.progressBar.setVisibility(View.VISIBLE);
					viewHolder.imageView.setImageBitmap(null);
					LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams.addRule(RelativeLayout.BELOW, viewHolder.progressBar.getId());
					viewHolder.textView.setLayoutParams(layoutParams);
				}
			}
		}

		@Override
		protected Bitmap doInBackground(Void... params) {
			if (item == null) {
				return null;
			}

			return item.getImage();
		}

		protected void onPostExecute(Bitmap bitmap) {
			super.onPostExecute(bitmap);

			if (viewHolderRef != null && !isCancelled()) {
				PhotoStreamItemViewHolder viewHolder = viewHolderRef.get();
				if (viewHolder != null) {
					viewHolder.progressBar.setVisibility(View.GONE);
					viewHolder.imageView.setImageBitmap(bitmap);
					LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
					layoutParams.addRule(RelativeLayout.BELOW, viewHolder.imageView.getId());
					viewHolder.textView.setLayoutParams(layoutParams);
				}
			}
		}
	}
}
