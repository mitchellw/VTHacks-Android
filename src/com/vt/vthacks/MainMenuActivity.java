package com.vt.vthacks;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

// -------------------------------------------------------------------------
/**
 * Not quite sure what this page will do yet
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class MainMenuActivity
    extends Activity
{

    // Creates ImageView variables that will hold the imageViews on the page
    private ImageView mChatImage;
    private ImageView mAnnounceImage;
    private ImageView mScheduleImage;
    private ImageView mMapImage;
    private ImageView mAwardsImage;
    private ImageView mContactsImage;
    private ImageView mSocialImage;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);

        //gets the chat button and sets the on click listener
        mChatImage = (ImageView) findViewById(R.id.chat_button);
        mChatImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
               startActivity(new Intent(MainMenuActivity.this , ChatActivity.class));

            }
        });


        //initializes the Announce button and sets the on click listener
        mAnnounceImage = (ImageView) findViewById(R.id.announcement_button);
        mAnnounceImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this , AnnouncementsActivity.class));

            }
        });

        //initializes the awards button and sets the on click listener
        mAwardsImage = (ImageView) findViewById(R.id.awards_button);
        mAwardsImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, AwardsActivity.class));

            }
        });


        //initializes the Contacts button and sets the on click listener
        mContactsImage = (ImageView) findViewById(R.id.contacts_button);
        mContactsImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, ContactsActivity.class));

            }
        });



        //initializes the Map button and sets the on click listener
        mMapImage = (ImageView) findViewById(R.id.map_button);
        mMapImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, MapActivity.class));

            }
        });



        //initializes the Schedule button and sets the on click listener
        mScheduleImage = (ImageView) findViewById(R.id.schedule_button);
        mScheduleImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, ScheduleActivity.class));

            }
        });


        //initializes the social button and sets the on click listener
        mSocialImage = (ImageView) findViewById(R.id.social_button);
        mSocialImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, SocialActivity.class));

            }
        });








    }

    // TODO Will this be needed?
    /**
     * @Override public boolean onCreateOptionsMenu(Menu menu) { // Inflate the
     *           menu; this adds items to the action bar if it is present.
     *           getMenuInflater().inflate(R.menu.main, menu); return true; }
     */

}
