package com.nkpb.wine;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Spinner;

import com.nkpb.wine.db.VarietalContract;
import com.nkpb.wine.db.WineDbHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SaveWineToProfileActivity extends AppCompatActivity {

    private List<String> redVarietals;
    private List<String> whiteVarietals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_wine_to_profile);
        prePopulateVarietals();

        Spinner varietalSpinner = (Spinner) findViewById(R.id.saveWine_varietals);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, redVarietals);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        varietalSpinner.setAdapter(spinnerArrayAdapter);
        Log.d("SaveWine", "Setting default varietal to red");

        // setup listeners
        Spinner classificationSpinner = (Spinner) findViewById(R.id.saveWine_classifications);
        classificationSpinner.setOnItemSelectedListener(new WineClassificationSelectedListener());
    }

    public void saveWineToProfile(View v) {
        Spinner spinner = (Spinner) findViewById(R.id.saveWine_classifications);
        String classification = (String)spinner.getSelectedItem();
        spinner = (Spinner) findViewById(R.id.saveWine_varietals);
        String varietal = (String)spinner.getSelectedItem();
        DatePicker datePicker = (DatePicker) findViewById(R.id.saveWine_date);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(new Date(datePicker.getYear()-1900, datePicker.getMonth(), datePicker.getDayOfMonth()));
        RatingBar ratingBar = (RatingBar) findViewById(R.id.saveWine_stars);
        float stars = ratingBar.getRating();
        Log.d("SaveWine", "Selected values: " + classification + "," + varietal + "," + dateString + "," + stars);
        finish();
    }

    public class WineClassificationSelectedListener implements OnItemSelectedListener {

        public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
            String selectedClassification = parent.getItemAtPosition(pos).toString();
            Spinner varietalSpinner = (Spinner) findViewById(R.id.saveWine_varietals);
            List<String> newVarietals;
            Log.d("SaveWine", "selectedClassification is " + selectedClassification);
            if ("Red".equalsIgnoreCase(selectedClassification)) {
                newVarietals = redVarietals;
            } else {
                newVarietals = whiteVarietals;
            }
            Log.d("SaveWine", "varietals are " + newVarietals.toString());
            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, newVarietals);
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            varietalSpinner.setAdapter(spinnerArrayAdapter);
        }

        public void onNothingSelected(AdapterView parent) {
            // Do nothing.
        }
    }

    private void prePopulateVarietals() {
        Log.d("SaveWine", "prePopulateVarietals");
        WineDbHelper dbHelper = new WineDbHelper(this);
        // TODO: get distinct varietals.classification -> classificationSpinner?
        // get red/white varietals
        // specifies which columns from the database
        redVarietals = dbHelper.getVarietalsFromDb("Red");
        whiteVarietals = dbHelper.getVarietalsFromDb("White");
    }
}
