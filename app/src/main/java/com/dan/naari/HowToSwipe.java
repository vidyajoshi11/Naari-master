package com.dan.naari;

import android.graphics.Color;
import android.os.Bundle;

import com.hololo.tutorial.library.Step;
import com.hololo.tutorial.library.TutorialActivity;

public class HowToSwipe extends TutorialActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_how_to_swipe);

        addFragment(new Step.Builder()
                .setTitle("How to use this Naari app??")
                .setContent("First-Just press the volume up/down button police siren and girls helpline call started both happened in same time.")
                .setSummary("Step 2")
                .setBackgroundColor(Color.parseColor("#CC0059"))
                .setDrawable(R.drawable.s5).build());

        addFragment(new Step.Builder()
                .setTitle("About Naari Rakshak")
                .setContent("In order to ask for help, you need to add your family and friends mobile number.and then press volume up/down button message and location send the entered mobile number.").setSummary("Step 1")
                .setBackgroundColor(Color.parseColor("#CC0059"))
                .setDrawable(R.drawable.s1).build());

        addFragment(new Step.Builder()
                .setTitle("How to use in trouble?")
                .setContent("Just press the volume up/down button for 5 seconds.")
                .setSummary("Step 2")
                .setBackgroundColor(Color.parseColor("#CC0059"))
                .setDrawable(R.drawable.s2).build());

        addFragment(new Step.Builder()
                .setTitle("You Just Swap your Mobile then what happens??")
                .setContent("Automatically sending a call to police..")
                .setSummary("Step 3")
                .setSummary("This is summary")
                .setBackgroundColor(Color.parseColor("#CC0059"))
                .setDrawable(R.drawable.s4).build());

        addFragment(new Step.Builder()
                .setTitle("All Background voice recorded")
                .setContent("In your critical condition you start a recording all your voice recorded and video save in your gallery.")
                .setSummary("Step 4")
                .setSummary("This is summary")
                .setBackgroundColor(Color.parseColor("#CC0059"))
                .setDrawable(R.drawable.s6).build());
    }

    @Override
    public void currentFragmentPosition(int position) {

    }
}
