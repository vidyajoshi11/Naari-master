package com.dan.naari;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class DeveloperByActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer_by);
    }



    public void Ary(View v){
        Intent implicit = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/vidyajoshi11"));
        startActivity(implicit);

    }


}
