package com.emrekose.pinchzoomdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.emrekose.pinchzoom.Touch;

public class MainActivity extends AppCompatActivity {

    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView)findViewById(R.id.imageView);


        //imageView.setOnTouchListener(new Touch());

        //if you change default min and max zoom values
        imageView.setOnTouchListener(new Touch(2f,6f));
    }
}
