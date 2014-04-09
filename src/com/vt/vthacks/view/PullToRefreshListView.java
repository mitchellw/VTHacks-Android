package com.vt.vthacks.view;

import com.vt.vthacks.R;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Based on https://github.com/johannilsson/android-pulltorefresh
 * @author wilsonmitchell
 *
 */
public class PullToRefreshListView extends ListView implements AbsListView.OnScrollListener {

	private static enum RefreshState {
		TAP_TO_REFRESH, PULL_TO_REFRESH, RELEASE_TO_REFRESH, REFRESHING
	}

	private static enum ScrollToBottomState {
		SCROLL_TO_ACTIVATE, ACTIVATED
	}

	private static final String TAG = "PullToRefreshListView";

	private OnRefreshListener mOnRefreshListener;
	private OnScrollToBottomListener onScrollToBottomListener;

	/**
	 * Listener that will receive notifications every time the list scrolls.
	 */
	private OnScrollListener mOnScrollListener;

	private LayoutInflater mInflater;
	private RelativeLayout mRefreshView;
	private ImageView mRefreshViewImage;
	private TextView mRefreshViewLastUpdated;
	private TextView mRefreshViewLabel;
	private AnimationDrawable refreshAnimationDrawable;

	private int mCurrentScrollState;
	private RefreshState mRefreshState;
	private ScrollToBottomState scrollToBottomState;

	private int mRefreshOriginalTopPadding;

	private boolean mBounceHack;

	public PullToRefreshListView(Context context) {
		super(context);
		init(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	public PullToRefreshListView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	private void init(Context context) {
		// Load all of the animations we need in code rather than through XML
		mInflater = (LayoutInflater) context.getSystemService(
				Context.LAYOUT_INFLATER_SERVICE);

		mRefreshView = (RelativeLayout) mInflater.inflate(
				R.layout.pull_to_refresh_header, this, false);
		mRefreshViewImage =
				(ImageView) mRefreshView.findViewById(R.id.pull_to_refresh_image);
		mRefreshViewLastUpdated =
				(TextView) mRefreshView.findViewById(R.id.pull_to_refresh_updated_at);
		mRefreshViewLabel =
				(TextView) mRefreshView.findViewById(R.id.pull_to_refresh_label);

		mRefreshViewImage.setImageResource(R.drawable.list_refresh_anim);
		refreshAnimationDrawable = (AnimationDrawable) mRefreshViewImage.getDrawable();

		mRefreshView.setOnClickListener(new OnClickRefreshListener());
		mRefreshOriginalTopPadding = mRefreshView.getPaddingTop();

		setRefreshState(RefreshState.TAP_TO_REFRESH);
		scrollToBottomState = ScrollToBottomState.SCROLL_TO_ACTIVATE;

		addHeaderView(mRefreshView);

		super.setOnScrollListener(this);
	}

	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		setSelection(1);
	}

	@Override
	public void setAdapter(ListAdapter adapter) {
		super.setAdapter(adapter);
		setSelection(1);
	}

	/**
	 * Set the listener that will receive notifications every time the list
	 * scrolls.
	 *
	 * @param l The scroll listener.
	 */
	@Override
	public void setOnScrollListener(AbsListView.OnScrollListener l) {
		mOnScrollListener = l;
	}

	/**
	 * Register a callback to be invoked when this list should be refreshed.
	 *
	 * @param onRefreshListener The callback to run.
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		mOnRefreshListener = onRefreshListener;
	}

	public void setOnScrollToBottomListener(OnScrollToBottomListener onScrollToBottomListener) {
		this.onScrollToBottomListener = onScrollToBottomListener;
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * @param lastUpdated Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			mRefreshViewLastUpdated.setVisibility(View.VISIBLE);
			mRefreshViewLastUpdated.setText(lastUpdated);
		} else {
			mRefreshViewLastUpdated.setVisibility(View.GONE);
		}
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		mBounceHack = false;

		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			if (!isVerticalScrollBarEnabled()) {
				setVerticalScrollBarEnabled(true);
			}
			if (getFirstVisiblePosition() == 0 && mRefreshState != RefreshState.REFRESHING) {
				if ((mRefreshView.getTop() >= 0)
						&& mRefreshState == RefreshState.RELEASE_TO_REFRESH) {
					// Initiate the refresh
					setRefreshState(RefreshState.REFRESHING);
					onRefresh();
				} else if (mRefreshView.getTop() <= 0) {
					// Abort refresh and scroll down below the refresh view
					resetHeader();
					setSelection(1);
				}
			}
			break;
		case MotionEvent.ACTION_DOWN:
			break;
		case MotionEvent.ACTION_MOVE:
			if (mRefreshState == RefreshState.RELEASE_TO_REFRESH) {
				if (isVerticalFadingEdgeEnabled()) {
					setVerticalScrollBarEnabled(false);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * Sets the header padding back to original size.
	 */
	private void resetHeaderPadding() {
		mRefreshView.setPadding(
				mRefreshView.getPaddingLeft(),
				mRefreshOriginalTopPadding,
				mRefreshView.getPaddingRight(),
				mRefreshView.getPaddingBottom());
	}

	/**
	 * Resets the header to the original state.
	 */
	private void resetHeader() {
		if (mRefreshState != RefreshState.TAP_TO_REFRESH) {
			setRefreshState(RefreshState.TAP_TO_REFRESH);

			resetHeaderPadding();
		}

		refreshAnimationDrawable.stop();
	}
	
	private void setRefreshState(RefreshState state) {
		mRefreshState = state;

		switch (state) {
		case PULL_TO_REFRESH:
			mRefreshViewLabel.setText(R.string.pull_to_refresh);
			break;
		case REFRESHING:
			mRefreshViewLabel.setText(R.string.refreshing);
			break;
		case RELEASE_TO_REFRESH:
			mRefreshViewLabel.setText(R.string.release_to_refresh);
			break;
		case TAP_TO_REFRESH:
			mRefreshViewLabel.setText(R.string.tap_to_refresh);
			break;
		default:
			break;
		}
	}

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// When the refresh view is completely visible, change the text to say
		// "Release to refresh..." and flip the arrow drawable.
		if (mCurrentScrollState == SCROLL_STATE_TOUCH_SCROLL
				&& mRefreshState != RefreshState.REFRESHING) {
			if (firstVisibleItem == 0) {
				if ((mRefreshView.getTop() >= 0)
						&& mRefreshState != RefreshState.RELEASE_TO_REFRESH) {
					refreshAnimationDrawable.start();
					setRefreshState(RefreshState.RELEASE_TO_REFRESH);
				} else if (mRefreshView.getTop() < 0
						&& mRefreshState != RefreshState.PULL_TO_REFRESH) {
					if (mRefreshState != RefreshState.TAP_TO_REFRESH) {
						refreshAnimationDrawable.stop();
					}
					setRefreshState(RefreshState.PULL_TO_REFRESH);
				}
			} else {
				resetHeader();
			}
		} else if (mCurrentScrollState == SCROLL_STATE_FLING
				&& firstVisibleItem == 0
				&& mRefreshState != RefreshState.REFRESHING) {
			setSelection(1);
			mBounceHack = true;
		} else if (mBounceHack && mCurrentScrollState == SCROLL_STATE_FLING) {
			setSelection(1);
		}

		if (scrollToBottomState != ScrollToBottomState.ACTIVATED
				&& firstVisibleItem + visibleItemCount == totalItemCount
				&& onScrollToBottomListener != null) {

			scrollToBottomState = ScrollToBottomState.ACTIVATED;
			onScrollToBottomListener.onScrollToBottom();
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScroll(view, firstVisibleItem,
					visibleItemCount, totalItemCount);
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		mCurrentScrollState = scrollState;

		if (mCurrentScrollState == SCROLL_STATE_IDLE) {
			mBounceHack = false;
		}

		if (mOnScrollListener != null) {
			mOnScrollListener.onScrollStateChanged(view, scrollState);
		}
	}

	private void prepareForRefresh() {
		resetHeaderPadding();

		refreshAnimationDrawable.start();

		setRefreshState(RefreshState.REFRESHING);
	}

	public void onRefresh() {
		prepareForRefresh();

		if (mOnRefreshListener != null) {
			mOnRefreshListener.onRefresh();
		}
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * @param lastUpdated Last updated at.
	 */
	public void onRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onRefreshComplete();
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 */
	public void onRefreshComplete() {
		Log.d(TAG, "onRefreshComplete");

		resetHeader();

		// If refresh view is visible when loading completes, scroll down to
		// the next item.
		if (getFirstVisiblePosition() == 0) {
			invalidateViews();
			setSelection(1);
		}
	}

	public void onScrollToBottomComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onScrollToBottomComplete();
	}

	public void onScrollToBottomComplete() {
		Log.d(TAG, "onScrollToBottomComplete");
		
		scrollToBottomState = ScrollToBottomState.SCROLL_TO_ACTIVATE;
	}

	/**
	 * Invoked when the refresh view is clicked on. This is mainly used when
	 * there's only a few items in the list and it's not possible to drag the
	 * list.
	 */
	private class OnClickRefreshListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			if (mRefreshState != RefreshState.REFRESHING) {
				onRefresh();
			}
		}

	}

	/**
	 * Interface definition for a callback to be invoked when list should be
	 * refreshed.
	 */
	public interface OnRefreshListener {
		/**
		 * Called when the list should be refreshed.
		 * <p>
		 * A call to {@link PullToRefreshListView #onRefreshComplete()} is
		 * expected to indicate that the refresh has completed.
		 */
		public void onRefresh();
	}

	/**
	 * Interface definition for a callback to be invoked when list has been
	 * scrolled to the bottom.
	 * @author wilsonmitchell
	 */
	public interface OnScrollToBottomListener {
		/**
		 * Called when the list has been scrolled to the bottom.
		 * <p>
		 * A call to {@link PullToRefreshListView #onBottomScrollComplete()} is
		 * expected to indicate that the action has been completed.
		 */
		public void onScrollToBottom();
	}
}
