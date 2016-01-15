package com.kovacnet.waterlevel;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;

public class MeasurementsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ArrayList<Measurement> measurements;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_measurements);

        recyclerView = (RecyclerView)findViewById(R.id.newMeasurementsRecycler);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
        initRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_refresh:
                initRecyclerView();
                return true;
            case R.id.stats:
                openStat();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void initRecyclerView() {
        showLoading();

        RestClient.getInstance().getRecentMeasurements(
                getApplicationContext(),
                new RestClient.MeasurementListener() {
                    @Override
                    public void onSuccess(ArrayList<Measurement> measurementsList) {
                        if (measurementsList != null) {
                            measurements = measurementsList;
                            MeasurementRecyclerAdapter adapter = new MeasurementRecyclerAdapter(getApplicationContext(), measurements);
                            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                            recyclerView.setAdapter(adapter);
                        }
                        hideLoading();
                    }

                    @Override
                    public void onFailure() {
                        Toast.makeText(getApplicationContext(), "Oops, something went wrong. Please try again.", Toast.LENGTH_SHORT).show();
                        hideLoading();
                    }
                }
        );
    }

    private void showLoading() {
        recyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideLoading() {
        recyclerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private void openStat()
    {
        Intent intent = new Intent(MeasurementsActivity.this, StatsActivity.class);
        intent.putExtra("measurements",measurements);
        startActivity(intent);
    }
}
