package edu.washington.sraden.quizdroid;

import android.content.Context;
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


/**
 * Created by Steven on 2/25/15.
 */
public class NetworkUtil {
    private QuizApp app;
    private RequestQueue queue;
    private Context context;

    private String urlAddress = "http://tednewardsandbox.site44.com/questions.json";

    public NetworkUtil(Context context, QuizApp app) {
        this.queue = Volley.newRequestQueue(context);
        this.app = app;
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

    public void makeRequest() {

        JsonArrayRequest jsArrayRequest = new JsonArrayRequest(urlAddress, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.i("hello", response.toString());
                try {
                    Log.i("title", response.getJSONObject(0).getString("title"));
                    Toast.makeText(context, response.getJSONObject(0).getString("title"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("hello", error.toString());
            }
        });

        addToRequestQueue(jsArrayRequest);
    }



}
