package com.nkpb.wine;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;

import org.apache.http.client.*;

/**
 * Created by Nicole on 9/26/2016.
 */
public class FileUploadHelper extends AsyncTask<Void, Void, String> {
    private ProgressDialog pd;

    public FileUploadHelper(Context context) {
        pd = new ProgressDialog(context);
    }

    @Override
    protected String doInBackground(Void... params) {
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("LINK TO SERVER");
        MultipartEntity mpEntity = new MultipartEntity(HttpMultipartMode.BROWSER_COMPATIBLE);
        if (filePath != null) {
            File file = new File(filePath);
            Log.d("EDIT USER PROFILE", "UPLOAD: file length = " + file.length());
            Log.d("EDIT USER PROFILE", "UPLOAD: file exist = " + file.exists());
            mpEntity.addPart("avatar", new FileBody(file, "application/octet"));
        }
        httppost.setEntity(mpEntity);
        HttpResponse response = httpclient.execute(httppost);
        return "done";
    }

    protected void onPreExecute() {
        super.onPreExecute();
        pd.setMessage("Image uploading.  Please wait.");
        pd.show();
    }
}
