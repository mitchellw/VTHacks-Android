package com.vt.vthacks;

import android.support.v7.app.ActionBarActivity;
import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.os.Bundle;


// -------------------------------------------------------------------------
/**
 * Not quite sure what this page will do yet
 *
 * @author Brandon Potts
 * @version Mar 10, 2014
 */
public class MainMenuActivity
    extends ActionBarActivity
{

    // Creates ImageView variables that will hold the imageViews on the page
    private View mChatImage;
    private View mAnnounceImage;
    private View mScheduleImage;
    private View mMapImage;
    private View mAwardsImage;
    private View mContactsImage;
    private View mSocialImage;
    private android.app.ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);


//        actionBar = getSupportActionBar();
//        actionBar.hide();


        //gets the chat button and sets the on click listener
        mChatImage = findViewById(R.id.chat_button);
        mChatImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
               startActivity(new Intent(MainMenuActivity.this , ChatActivity.class));

            }
        });


        //initializes the Announce button and sets the on click listener
        mAnnounceImage = findViewById(R.id.announcement_button);
        mAnnounceImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this , AnnouncementsActivity.class));

            }
        });

        //initializes the awards button and sets the on click listener
        mAwardsImage = findViewById(R.id.awards_button);
        mAwardsImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, AwardsActivity.class));

            }
        });


        //initializes the Contacts button and sets the on click listener
        mContactsImage = findViewById(R.id.contacts_button);
        mContactsImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, ContactsActivity.class));

            }
        });



        //initializes the Map button and sets the on click listener
        mMapImage = findViewById(R.id.map_button);
        mMapImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, MapActivity.class));

            }
        });



        //initializes the Schedule button and sets the on click listener
        mScheduleImage = findViewById(R.id.schedule_button);
        mScheduleImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, ScheduleActivity.class));

            }
        });


        //initializes the social button and sets the on click listener
        mSocialImage = findViewById(R.id.social_button);
        mSocialImage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v)
            {
                startActivity(new Intent(MainMenuActivity.this, SocialActivity.class));

            }
        });



    }



}
