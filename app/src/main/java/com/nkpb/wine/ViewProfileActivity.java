package com.nkpb.wine;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.util.Log;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;
import java.util.HashMap;
import java.util.Map;

public class ViewProfileActivity extends AppCompatActivity {

    private static final int[] COLORS = { Color.BLUE, Color.MAGENTA, Color.GREEN, Color.CYAN,
            Color.RED, Color.YELLOW, Color.WHITE, Color.BLACK,
            Color.GRAY, Color.LTGRAY };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        getProfileBalance();
        getProfileRed();
        getProfileWhite();
    }

    private void getProfileBalance() {
        // balance: 34% white, 66% red
        Map<String, Double> retrievedData = new HashMap<>();
        retrievedData.put("White", 34d);
        retrievedData.put("Red", 66d);
        CategorySeries balanceDataset = new CategorySeries("Balance");
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);
        int i=0;
        for (Map.Entry<String, Double> entry : retrievedData.entrySet()) {
            balanceDataset.add(entry.getKey(), entry.getValue());
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(COLORS[i]);
            renderer.addSeriesRenderer(seriesRenderer);
            i++;
        }
        GraphicalView balancePie = ChartFactory.getPieChartView(this, balanceDataset, renderer);
        LinearLayout balanceProfile = (LinearLayout) findViewById(R.id.profile_balance);
        if (balanceProfile == null) {
            Log.d("GreateBalancePie", "Have no profile element!!!!!");
        } else {
            balanceProfile.removeAllViews();
            balanceProfile.addView(balancePie);
        }
    }

    private void getProfileRed() {
        // red: cabernet 20%, sangiovese 18%, franc 10%, zinfandel 13%, merlot 8%, pinot noir 23%, shiraz 8%
        Map<String, Double> retrievedData = new HashMap<>();
        retrievedData.put("Cabernet", 20d);
        retrievedData.put("Sangiovese", 18d);
        retrievedData.put("Franc", 10d);
        retrievedData.put("Zinfandel", 13d);
        retrievedData.put("Merlot", 8d);
        retrievedData.put("Pinot Noir", 23d);
        retrievedData.put("Shiraz", 8d);
        CategorySeries redDataset = new CategorySeries("Red");
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);
        int i=0;
        for (Map.Entry<String, Double> entry : retrievedData.entrySet()) {
            redDataset.add(entry.getKey(), entry.getValue());
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(COLORS[i]);
            renderer.addSeriesRenderer(seriesRenderer);
            i++;
        }
        GraphicalView redPie = ChartFactory.getPieChartView(this, redDataset, renderer);
        LinearLayout redProfile = (LinearLayout) findViewById(R.id.profile_red);
        if (redProfile == null) {
            Log.d("GreateRedPie", "Have no profile element!!!!!");
        } else {
            redProfile.removeAllViews();
            redProfile.addView(redPie);
        }
    }

    private void getProfileWhite() {
        // white: chardonnay 40%, vermentino 30%, verdelho 15%, semillion 5%, riesling 10%
        Map<String, Double> retrievedData = new HashMap<>();
        retrievedData.put("Chardonnay", 40d);
        retrievedData.put("Vermentino", 30d);
        retrievedData.put("Verdelho", 15d);
        retrievedData.put("Semillion", 5d);
        retrievedData.put("Riesling", 10d);
        CategorySeries whiteDataset = new CategorySeries("White");
        DefaultRenderer renderer = new DefaultRenderer();
        renderer.setZoomEnabled(false);
        renderer.setPanEnabled(false);
        int i=0;
        for (Map.Entry<String, Double> entry : retrievedData.entrySet()) {
            whiteDataset.add(entry.getKey(), entry.getValue());
            SimpleSeriesRenderer seriesRenderer = new SimpleSeriesRenderer();
            seriesRenderer.setColor(COLORS[i]);
            renderer.addSeriesRenderer(seriesRenderer);
            i++;
        }
        GraphicalView whitePie = ChartFactory.getPieChartView(this, whiteDataset, renderer);
        LinearLayout whiteProfile = (LinearLayout) findViewById(R.id.profile_white);
        if (whiteProfile == null) {
            Log.d("CreateWhitePie", "Have no profile element!!!!!");
        } else {
            whiteProfile.removeAllViews();
            whiteProfile.addView(whitePie);
        }
    }
}
