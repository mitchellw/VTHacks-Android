package com.vt.vthacks;

import java.util.List;

import com.vt.vthacks.model.IPhotoStreamItem;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class PhotoStreamAdapter extends ArrayAdapter<IPhotoStreamItem> {
	
	private Context context;
	private LayoutInflater mInflater;

	public PhotoStreamAdapter(Context context, List<IPhotoStreamItem> listItems) {
		super(context, 0, listItems);
		this.context = context;
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
        
        photoStreamViewHolder.imageView.setImageBitmap(item.getImage());
        photoStreamViewHolder.textView.setText(item.getText());
        
		return photoStreamViewHolder.rootView;
//        final ListItem item = getItem(position);
//
//        // Don't use instanceof because it actually could be a slight performance hit here.
//        if (item.getClass() == FriendListItem.class) {
//            final FriendListItem friendItem = (FriendListItem)item;
//            final FriendViewHolder friendViewHolder;
//            if (convertView == null) {
//                View view;
//                if (friendItem.isFriend()) {
//                    view = mInflater.inflate(R.layout.friend_list_item, parent, false);
//                }
//                else {
//                    view = mInflater.inflate(R.layout.added_you_list_item, parent, false);
//                }
//                friendViewHolder = FriendViewHolder.create((RelativeLayout) view);
//                view.setTag(friendViewHolder);
//            }
//            else {
//                friendViewHolder = (FriendViewHolder)convertView.getTag();
//            }
//
//            friendViewHolder.nameTextView.setText(friendItem.getName());
//            if (!friendItem.isFriend()) {
//                friendViewHolder.addAsFriendButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        addFriendListener.onListItemButtonClicked(friendItem);
//                    }
//                });
//            }
//
//            return friendViewHolder.rootView;
//        }
//        else {
//            return null;
//        }
    }

    private static class PhotoStreamItemViewHolder {
        public final RelativeLayout rootView;
        public final TextView textView;
        public final ImageView imageView;

        private PhotoStreamItemViewHolder(RelativeLayout rootView, TextView textView, ImageView imageView) {
            this.rootView = rootView;
            this.textView = textView;
            this.imageView = imageView;
        }

        public static PhotoStreamItemViewHolder create(RelativeLayout rootView) {
            TextView textView = (TextView)rootView.findViewById(R.id.textView);
            ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView);
            return new PhotoStreamItemViewHolder(rootView, textView, imageView);
        }
    }
}
