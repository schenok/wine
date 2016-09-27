package com.nkpb.wine;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class ViewSelectedMenuActivity extends AppCompatActivity {

    public static final String SELECTED_IMAGE = "SELECTED_IMAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_selected_menu);

        //get selected image and display
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra(SELECTED_IMAGE);
        if (imagePath != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
            if (bitmap != null) {
                ImageView imageView = (ImageView) findViewById(R.id.menu_image_view);
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
