package com.kovacnet.waterlevel;

import android.content.Context;
import android.util.Log;

import com.activeandroid.query.Delete;
import com.activeandroid.query.Select;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.NameValuePair;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.message.BasicNameValuePair;

public class RestClient {

    /*
    Variables
     */

    private static final String BASE_URL = "http://reservoirlevel.azurewebsites.net/";
    private static final String ENDPOINT_LOGIN = "login.php"; // username:password in POST
    private static final String ENDPOINT_GET_MEASUREMENTS = "getNew.php"; //range:today in POST for todays measurements
    private static final String ENDPOINT_GET_STATS = "getStats.php"; //Auth in POST

    private static RestClient ourInstance = new RestClient();

    private AsyncHttpClient asyncHttpClient;
    private Gson gson;

    /*
    Singleton
     */

    public static RestClient getInstance() {
        return ourInstance;
    }

    /*
    Constructor
     */

    private RestClient() {
        asyncHttpClient = new AsyncHttpClient();
        gson = new Gson();
    }

    /*
    Interfaces
     */

    public interface RequestListener{
        void onSuccess();
        void onFailure();
    }

    public interface MeasurementListener{
        void onSuccess(ArrayList<Measurement> measurementsList);
        void onFailure();
    }

    public interface StatsListener{
        void onSuccess(ArrayList<Stats> StatsList);
        void onFailure();
    }

    /*
    Methods
     */

    public void loginUser(final Context context, final String username, final String password, final RequestListener responseHandler) {
        asyncHttpClient.removeAllHeaders();

        RequestParams params = new RequestParams();
        params.add("username",username);
        params.add("password",password);

        asyncHttpClient.post(BASE_URL + ENDPOINT_LOGIN,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (responseHandler != null)
                    responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                if (responseHandler != null) {
                    if (responseString.equals("true")) {
                        responseHandler.onSuccess();
                    } else {
                        responseHandler.onFailure();
                    }
                }
            }
        });
    }



    public void getRecentMeasurements(Context context, final MeasurementListener responseHandler) {
        asyncHttpClient.removeAllHeaders();
        RequestParams params = new RequestParams();
        params.add("range", "today");
        params.add("type", "android");

        asyncHttpClient.post(BASE_URL + ENDPOINT_GET_MEASUREMENTS,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (responseHandler != null)
                    responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Log.w("TAG", responseString);
                ArrayList<Measurement> messageList = jsonToMessages(responseString,false);

                if (responseHandler != null)
                    responseHandler.onSuccess(messageList);
            }
        });
    }

    public void getAllMeasurements(Context context, final MeasurementListener responseHandler) {
        Select select = new Select();
        List<Measurement> measurements = select.all().from(Measurement.class).execute();
        int elemcount=0;
        if(measurements==null)
            elemcount=0;
        else
            elemcount=measurements.size();

        asyncHttpClient.removeAllHeaders();
        RequestParams params = new RequestParams();
        params.add("type", "android");
        params.add("ctr",String.valueOf(elemcount));
        asyncHttpClient.post(BASE_URL + ENDPOINT_GET_MEASUREMENTS, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (responseHandler != null)
                    responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //Log.w("TAG", responseString);
                ArrayList<Measurement> messageList = jsonToMessages(responseString, true);
                if (responseHandler != null)
                    responseHandler.onSuccess(messageList);
            }
        });
    }

    public void getStats(Context context, final StatsListener responseHandler) {
        asyncHttpClient.removeAllHeaders();
        RequestParams params = new RequestParams();
        params.add("type", "android");
        asyncHttpClient.post(BASE_URL + ENDPOINT_GET_STATS,params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                if (responseHandler != null)
                    responseHandler.onFailure();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Log.w("TAG", responseString);
                ArrayList<Stats> StatsList = jsonToStats(responseString);

                if (responseHandler != null)
                    responseHandler.onSuccess(StatsList);
            }
        });
    }

    public ArrayList<Measurement> jsonToMessages(String json,boolean db )
    {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jArray = (JsonArray) parser.parse(json).getAsJsonObject().get("Measurements");

        ArrayList<Measurement> lcs = new ArrayList<Measurement>();

        if(db && jArray.size()!=0)
        {
            Delete delete = new Delete();
            delete.from(Measurement.class).execute();
        }

        for(JsonElement obj : jArray )
        {
            Measurement cse = gson.fromJson( obj , Measurement.class);
            lcs.add(cse);
            if(db)
            {
                cse.save();
            }
        }
        Collections.reverse(lcs);
        return lcs;
    }

    public ArrayList<Stats> jsonToStats(String json)
    {
        Gson gson = new Gson();
        JsonParser parser = new JsonParser();
        JsonArray jArray = (JsonArray) parser.parse(json).getAsJsonObject().get("Stats");
        ArrayList<Stats> lcs = new ArrayList<Stats>();

        for(JsonElement obj : jArray )
        {
            Stats cse = gson.fromJson( obj , Stats.class);
            lcs.add(cse);
        }
        return lcs;



    }
}
