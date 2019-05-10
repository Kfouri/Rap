package com.kfouri.rappitest.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.kfouri.rappitest.R;

public class VideoDataActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_data);

        Bundle data = this.getIntent().getExtras();
        assert data != null;
        int id = data.getInt("id", 0);
        boolean isMovie = data.getBoolean("movie", false);

        Toast.makeText(this, "Peli: " + id, Toast.LENGTH_LONG).show();

    }
}
