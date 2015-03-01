package edu.washington.sraden.quizdroid;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;


/**
 * Created by Steven on 2/25/15.
 */
public class NetworkUtil {
    //private QuizApp app;
    private RequestQueue queue;
    private Context context;

    private String urlAddress = "http://tednewardsandbox.site44.com/questions.json"; //default

    public NetworkUtil(Context context) {
        this.queue = Volley.newRequestQueue(context);
        //this.app = app;
        this.context = context;
    }

    public RequestQueue getRequestQueue() {
        if (queue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            queue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return queue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void setUrlAddress(String url) {
        this.urlAddress = url;
    }

    public void makeRequest() {

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(urlAddress, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                writeToFile(response.toString()); //Write JSON object to a file
                //alertDownloadFailed(context);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("exception", "Response Error: " + error.toString());
                //alertDownloadFailed(context);
            }
        });
        addToRequestQueue(jsArrayRequest);
    }

    private void writeToFile(String data) {
        Log.i("info", "Writing to file...");

        try {
            File filePath = new File(context.getFilesDir().getAbsolutePath() + "/quizdata.json");
            FileOutputStream fos = new FileOutputStream(filePath);
            fos.write(data.getBytes());
            fos.close();

            Log.i("info", "Wrote to file");
        } catch (IOException e) {
            Log.e("exception", "File write failed: " + e.toString());
        }
    }

    //Not exactly working
    public void alertDownloadFailed(final Context context) {
        new AlertDialog.Builder(context)
                .setTitle("Download failed")
                .setMessage("Download of Quiz failed. Do you want to retry now or later")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

}
