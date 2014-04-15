package com.vt.vthacks;

import com.vt.vthacks.GroupFinderFragment.GroupFinderListener;
import com.vt.vthacks.model.INavigationItem;
import com.vt.vthacks.model.impl.NavigationItem;
import com.vt.vthacks.view.NavigationAdapter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity implements GroupFinderListener {
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private CharSequence mDrawerTitle;
	private CharSequence mTitle;
	private INavigationItem[] navigationItems;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);

		navigationItems = new INavigationItem[]
				{
				new NavigationItem(R.drawable.announcements_res,
						R.string.label_announcements),
						new NavigationItem(R.drawable.calendar_res,
								R.string.label_schedule),
								new NavigationItem(R.drawable.map_res,
										R.string.label_map),
										new NavigationItem(R.drawable.awards_res,
												R.string.label_awards),
												new NavigationItem(R.drawable.contacts_res,
														R.string.label_contacts),
														new NavigationItem(R.drawable.group_res,
																R.string.label_groups),
																new NavigationItem(R.drawable.images_res,
																		R.string.label_photos)
				};
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);

		// Set the adapter for the list view
		mDrawerList.setAdapter(new NavigationAdapter(this, navigationItems));
		// Set the list's click listener
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

		mTitle = mDrawerTitle = getTitle();
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

			/** Called when a drawer has settled in a completely closed state. */
			public void onDrawerClosed(View view) {
				super.onDrawerClosed(view);
				getSupportActionBar().setTitle(mTitle);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}

			/** Called when a drawer has settled in a completely open state. */
			public void onDrawerOpened(View drawerView) {
				super.onDrawerOpened(drawerView);
				getSupportActionBar().setTitle(mDrawerTitle);
				ActivityCompat.invalidateOptionsMenu(MainActivity.this);
			}
		};

		// Set the drawer toggle as the DrawerListener
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setHomeButtonEnabled(true);

		// Set the first item to be visible
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, new AnnouncementsFragment())
		.commit();
		mDrawerList.setSelection(0);
		setTitle(navigationItems[0].getTitleRes());
	}

	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectItem(position);
		}
	}

	/** Swaps fragments in the main content view */
	private void selectItem(int position) {
		INavigationItem item = navigationItems[position];

		Fragment fragment = null;
		switch (position) {
		case 0:
			fragment = new AnnouncementsFragment();
			break;
		case 1:
			fragment = new ScheduleFragment();
			break;
		case 2:
			fragment = new MapFragment();
			break;
		case 3:
			fragment = new AwardsFragment();
			break;
		case 4:
			fragment = new ContactsFragment();
			break;
		case 5:
			fragment = new GroupFinderFragment();
			break;
		case 6:
			fragment = new SocialFragment();
			break;
		}

		// Insert the fragment by replacing any existing fragment
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction()
		.replace(R.id.content_frame, fragment)
		.commit();

		// Highlight the selected item, update the title, and close the drawer
		mDrawerList.setSelection(position);
		setTitle(item.getTitleRes());
		mDrawerLayout.closeDrawer(mDrawerList);
	}

	@Override
	public void setTitle(CharSequence title) {
		mTitle = title;
		getSupportActionBar().setTitle(mTitle);
	}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

	/* Called whenever we call invalidateOptionsMenu() */
	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		// If the nav drawer is open, hide action items related to the content view
		//		boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
		//		menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
		return super.onPrepareOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Pass the event to ActionBarDrawerToggle, if it returns
		// true, then it has handled the app icon touch event
		if (mDrawerToggle.onOptionsItemSelected(item)) {
			return true;
		}
		// Handle your other action bar items...

		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onAddGroupClicked() {
		FragmentManager fragmentManager = getSupportFragmentManager();
		fragmentManager.beginTransaction().addToBackStack(null)
		.replace(R.id.content_frame, new AddGroupFragment())
		.commit();
	}
}
