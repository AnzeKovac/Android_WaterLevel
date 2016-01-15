package com.kovacnet.waterlevel;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.activeandroid.query.Select;

import java.util.ArrayList;

public class AllMeasurementsActivity extends AppCompatActivity {
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

  private void initRecyclerView() {
      showLoading();

      RestClient.getInstance().getAllMeasurements(
              getApplicationContext(),
              new RestClient.MeasurementListener() {
                  @Override
                  public void onSuccess(ArrayList<Measurement> messageList) {
                      if (messageList != null) {
                          Select select = new Select();
                          measurements = new ArrayList(select.all().from(Measurement.class).orderBy("datetime DESC") .execute());
                          MeasurementRecyclerAdapter adapter = new MeasurementRecyclerAdapter(getApplicationContext(), measurements);
                          recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                          recyclerView.setAdapter(adapter);
                      }
                      hideLoading();
                  }

                  @Override
                  public void onFailure() {
                      Toast.makeText(getApplicationContext(), "Oops, something went wrnog. Please try again.", Toast.LENGTH_SHORT).show();
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

}

