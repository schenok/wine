package com.nkpb.wine;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    private static final int GALLERY_IMAGE_RESULT = 0;
    private static final int CAMERA_IMAGE_RESULT = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showCameraMenuPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.setOnMenuItemClickListener(this);
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.photo_menu, popup.getMenu());
        popup.show();
    }

    public void saveWineToProfile(View v) {
        Intent intent = new Intent(this, SaveWineToProfileActivity.class);
        startActivity(intent);
    }

    public void showProfile(View v) {
        Intent intent = new Intent(this, ViewProfileActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.from_camera:
                getPictureFromCamera(item);
                return true;
            case R.id.from_gallery:
                getPictureFromGallery(item);
                return true;
            default:
                return false;
        }
    }

    private void getPictureFromCamera(MenuItem item) {
        // create an intent to redirect control to the camera
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_IMAGE_RESULT);
    }

    private void getPictureFromGallery(MenuItem item) {
        // if we don't have permission to read from storage don't continue
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            // create an intent to redirect control to the gallery
            Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            galleryIntent.setType("image/*");
            startActivityForResult(galleryIntent, GALLERY_IMAGE_RESULT);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            if (data == null) {
                //Display an error, redirect back to main.... something
                return;
            }
        }
        if (requestCode == GALLERY_IMAGE_RESULT) {
            getSelectedImageAndRedirect(data);
        } else if (requestCode == CAMERA_IMAGE_RESULT) {
            getSelectedImageAndRedirect(data);
        }
    }

    private void getSelectedImageAndRedirect(Intent data) {
        Uri selectedImage = data.getData();
        String[] filePathColumn = { MediaStore.Images.Media.DATA };

        Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        // until we do anything else with the image... display in viewer activity
        if (picturePath != null) {
            Intent intent = new Intent(this, ViewSelectedMenuActivity.class);
            intent.putExtra(ViewSelectedMenuActivity.SELECTED_IMAGE, picturePath);
            startActivity(intent);
        }
    }
}
