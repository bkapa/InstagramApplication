package com.walmartlabs.bkapa.instagramapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PhotosActivity extends AppCompatActivity {
    public static final String CLIENT_ID = "d51ab31a28f44fe1802c34dcd3288637";
    private ArrayList<InstagramPhoto> photos;
    private InstagramPhotosAdapter photoAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photos);
        photos = new ArrayList<InstagramPhoto>();

        //Bind the data.
        photoAdapter = new InstagramPhotosAdapter(this,photos);

        //List View

        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);

        lvPhotos.setAdapter(photoAdapter);

        //Send API request for  Photos.
        fetchPopularPhotos();


    }

    private void fetchPopularPhotos() {
      /*
      Client Id : d51ab31a28f44fe1802c34dcd3288637
       URL for POPULAR PHOTOS: https://api.instagram.com/v1/media/popular?access_token=ACCESS-TOKEN
        data => type: image/vedio,
                caption: => text
        images : standard_resolution => url
        user => username
        */

        AsyncHttpClient client = new AsyncHttpClient();
        String url = "https://api.instagram.com/v1/media/popular?client_id="+CLIENT_ID;
        client.get(url, null, new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                JSONArray photosJson = null;
                try{
                    photosJson = response.getJSONArray("data");
                    for(int i = 0; i< photosJson.length(); i++){

                        JSONObject photoJson = photosJson.getJSONObject(i);
                        InstagramPhoto photo = new InstagramPhoto();
                        photo.setUsername(photoJson.getJSONObject("user").getString("username"));
                        photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                        photo.setImageUrl(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        photos.add(photo);

                    }

                }catch (JSONException exp){
                    exp.printStackTrace();
                }

                photoAdapter.notifyDataSetChanged();

            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_photos, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
