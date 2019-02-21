package com.kovacnet.waterlevel;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import cz.msebera.android.httpclient.client.methods.HttpPost;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.util.EntityUtils;
import lecho.lib.hellocharts.gesture.ContainerScrollType;
import lecho.lib.hellocharts.gesture.ZoomType;
import lecho.lib.hellocharts.model.Axis;
import lecho.lib.hellocharts.model.AxisValue;
import lecho.lib.hellocharts.model.ChartData;
import lecho.lib.hellocharts.model.ColumnChartData;
import lecho.lib.hellocharts.model.Line;
import lecho.lib.hellocharts.model.LineChartData;
import lecho.lib.hellocharts.model.PointValue;
import lecho.lib.hellocharts.view.Chart;
import lecho.lib.hellocharts.view.LineChartView;

public class StatsActivity extends AppCompatActivity {
    private LineChartView chart;
    private ArrayList<Measurement> measurements;
    private ArrayList<Stats> stats;
    private TextView maxWT,minWT,maxBT,minBT,maxW,minW,maxB,minB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
        maxWT =(TextView)findViewById(R.id.textView4);
        minWT =(TextView)findViewById(R.id.textView5);
        maxBT =(TextView)findViewById(R.id.textView6);
        minBT =(TextView)findViewById(R.id.textView7);
        maxW =(TextView)findViewById(R.id.textView8);
        minW =(TextView)findViewById(R.id.textView9);
        maxB =(TextView)findViewById(R.id.textView10);
        minB =(TextView)findViewById(R.id.textView11);

        chart = (LineChartView)findViewById(R.id.chart);
        measurements = (ArrayList<Measurement>)getIntent().getSerializableExtra("measurements");
        populateChart(measurements);
        Add add = new Add();
        add.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menustats, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_all:
                openAllMeasurements();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateChart(ArrayList<Measurement> measurements)
    {
        chart.setInteractive(true);
        chart.setZoomType(ZoomType.HORIZONTAL);
        chart.setContainerScrollEnabled(true, ContainerScrollType.HORIZONTAL);


        List<PointValue> values = new ArrayList<PointValue>();
        List<AxisValue> valuesAxis = new ArrayList<AxisValue>();
        int i = 0;
        for(Measurement m:measurements)
        {
            Date time=new Date((long)Double.parseDouble(m.getDatetime())*1000);
            Calendar calendar = GregorianCalendar.getInstance(); // creates a new calendar instance
            calendar.setTime(time);   // assigns calendar to given date
            Log.i("TAG", String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)));
            values.add(new PointValue(calendar.get(Calendar.HOUR_OF_DAY), Float.parseFloat(m.getWaterLevel())));

            AxisValue val = new AxisValue(calendar.get(Calendar.HOUR_OF_DAY));
            valuesAxis.add(i++,val);
        }


        //In most cased you can call data model methods in builder-pattern-like manner.
        Line line = new Line(values).setColor(Color.parseColor("#00B0FF")).setCubic(true);
        List<Line> lines = new ArrayList<Line>();
        lines.add(line);

        LineChartData data = new LineChartData();
        data.setLines(lines);


        Axis axisX = new Axis();
        axisX.setValues(valuesAxis);
        axisX.setName("Hour");
        data.setAxisXBottom(axisX);

        Axis axisY = new Axis();
        axisY.setHasLines(true);
        axisY.setName("");
        data.setAxisYRight(axisY);

        ColumnChartData columnChartData = new ColumnChartData();
        columnChartData.setStacked(true);
        line.setStrokeWidth(1);
        chart.setLineChartData(data);
    }

    private void openAllMeasurements() {
        Intent intent = new Intent(StatsActivity.this, AllMeasurementsActivity.class);
        intent.putExtra("measurements",measurements);
        startActivity(intent);
    }


    private void fillStats()
    {
        for(Stats data:stats)
        {
            maxWT.setText("Highest water level: "+data.getMaxWT()+"cm");
            minWT.setText("Lowest water level: "+data.getMinWT()+"cm");
            maxBT.setText("Highest battery voltage: "+data.getMaxBT()+"V");
            minBT.setText("Lowest battery voltage: "+data.getMinBT()+"V");
            maxW.setText("Highest water level: "+data.getMaxW()+"cm");
            minW.setText("Lowest water level: "+data.getMinW()+"cm");
            maxB.setText("Highest battery voltage: "+data.getMaxB()+"V");
            minB.setText("Lowest battery voltage: "+data.getMinB()+"V");
        }


    }
    class Motor{  
    	
    	motor1(){System.out.println("emtying and filling motors");}  
    Scanner reader = new Scanner(System.in);  // Reading from System.in
    motor1 b=new motor1();
    System.out.println("please enter the maximum water level");
    int n = reader.nextInt();
if (data.getMaxWT>n ) {
	motor1 true;
}
else if(getMaxWT<n) {
	motor1 false;
}

motor2 b=new motor2();
System.out.println("please enter the minimum water level");
int m = reader.nextInt();
if (data.getMinWT()>m ) {
motor2 true;
}
else if(data.getMinWT()T<m) {
motor2 false;
}



    private class Add extends AsyncTask<Void, Void, String> {
        private String SERVICE_URL = "http://reservoirlevel.azurewebsites.net/getStats.php";

        @Override
        protected String doInBackground(Void... params) {
            DefaultHttpClient hc = new DefaultHttpClient();
            String webResult ="";

            try {
                Log.v("TAG",SERVICE_URL);
                HttpPost request = new HttpPost(SERVICE_URL);
                request.setHeader("Accept", "application/json");
                request.setHeader("Authorization", "anzekovac:kovac");
                webResult = EntityUtils.toString(hc.execute(request).getEntity());
                Log.v("TAG", webResult);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return webResult;
        }

        @Override
        protected void onPostExecute(String result)
        {
            Gson gson = new Gson();
            JsonParser parser = new JsonParser();
            JsonArray jArray = (JsonArray) parser.parse(result).getAsJsonObject().get("Stats");
            ArrayList<Stats> lcs = new ArrayList<Stats>();

            for(JsonElement obj : jArray )
            {
                Stats cse = gson.fromJson( obj , Stats.class);
                lcs.add(cse);
            }
            stats=lcs;
            fillStats();
        }
    }

}
