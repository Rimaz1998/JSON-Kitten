package com.rinzler.kittenjson;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.rinzler.kittenjson.Model.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ExampleAdapter.onItemClickListener {
    public static final String EXTRA_URL = "imageURL";
    public static final String EXTRA_CREATOR = "creatorName";
    public static final String EXTRA_LIKES = "likeCount";
    private RecyclerView mRecylerView;
    private ExampleAdapter mAdapter;
    private ArrayList<Item> mItemList;
    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecylerView = findViewById(R.id.recyclerView);
        mRecylerView.setHasFixedSize(true);
        mRecylerView.setLayoutManager(new LinearLayoutManager(this));

        mItemList = new ArrayList<>();

       mRequestQueue = MySingleton.getInstance(this.getApplicationContext()).getRequestQueue();
      //  mRequestQueue = Volley.newRequestQueue(this);
        parseJson();
    }

    private void parseJson(){
        String url = "https://pixabay.com/api/?key=14624203-c12c7a712306fe66cf299db09&q=kittens&image_type=photo&pretty=true";

        //todo we are getting items from an array which is stored inside an object
        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //the arrays name which is inside the object is "hits"
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i =0; i < jsonArray.length(); i++){
                        //get each object from the array
                        JSONObject hit = jsonArray.getJSONObject(i);

                        String creatorName = hit.getString("user");
                        int likes = hit.getInt("likes");
                        String imageUrl = hit.getString("webformatURL");

                       // Item item = mItemList.get(i);
                        mItemList.add(new Item(imageUrl,creatorName,likes));
                    }

                    //todo : set the recyclerview adapter inside the json
                    mAdapter = new ExampleAdapter(MainActivity.this , mItemList);
                    mRecylerView.setAdapter(mAdapter);
                    //use the method we created
                    mAdapter.setOnItemClickListener(MainActivity.this);



                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error Retrieving Items, " + error, Toast.LENGTH_SHORT).show();
            }
        });
        //add the reques to the requestqueue
        mRequestQueue.add(objectRequest);
    }

    @Override
    public void onItemClick(int position) {
        //here we handle all the click events
        //get the position of the adapter clicked
        Intent detailIntent = new Intent(this,DetailActivity.class);
        Item itemInPosition = mItemList.get(position);
        detailIntent.putExtra(EXTRA_URL,itemInPosition.getmImageUrl());
        detailIntent.putExtra(EXTRA_CREATOR,itemInPosition.getmCreator());
        detailIntent.putExtra(EXTRA_LIKES, itemInPosition.getmLikes());

        startActivity(detailIntent);
    }
}