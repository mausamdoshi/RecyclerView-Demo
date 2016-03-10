package demo.app.com.recyclerviewdemo.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import demo.app.com.recyclerviewdemo.Listners.RecyclerItemClickListener;
import demo.app.com.recyclerviewdemo.R;
import demo.app.com.recyclerviewdemo.adapters.RecycleViewAdapter;
import demo.app.com.recyclerviewdemo.data.ArticleData;


/**
 * @author mausam
 * This is demo app for implementing RecyclerView and CardView (Component of material design).
 * Fethches the data from server and display a list in RecyclerView.
 */

public class ArticleListActivity extends AppCompatActivity {

    private final String feedURL = "http://javatechig.com/?json=get_recent_posts&count=60";
    private RecyclerView recyclerView = null;
    private ProgressBar progressBar = null;

    private ArrayList<ArticleData> feedsList = null;
    private RecycleViewAdapter adapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);


        setContentView(R.layout.article_list);

        //find the views to populate data
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        if(isNetworkAvailable()){
            //performs the network request
            new AsyncServerTask().execute(feedURL);
        }else{
            Toast.makeText(ArticleListActivity.this,"Internet Connection is not avaialble.",Toast.LENGTH_LONG).show();
        }



        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {

                Log.e("position", "" + position);

                Intent intent = new Intent(ArticleListActivity.this, ArticleDetailActivity.class);
                intent.putExtra("articleData", feedsList.get(position));
                startActivity(intent);
            }
        }));

    }


    //Performs the network request to fetch feed Data and load them into list.
    public class AsyncServerTask extends AsyncTask<String, Void, Integer> {


        public AsyncServerTask() {
            super();
        }

        //Performs Network operation
        @Override
        protected Integer doInBackground(String... params) {

            Integer result = 0;
            HttpURLConnection urlConnection;
            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();
                int statusCode = urlConnection.getResponseCode();

                // 200 represents HTTP OK
                if (statusCode == 200) {
                    BufferedReader r = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = r.readLine()) != null) {
                        response.append(line);
                    }
                    parseResult(response.toString());
                    result = 1; // Success
                } else {
                    result = 0; //"Failed to get data.";
                }
            } catch (Exception e) {
                Log.d("exception", e.getLocalizedMessage());
            }
            return result;


        }


        //parses JSON response to Article objects and prepares the list of Article objects.
        private void parseResult(String result) {
            try {
                JSONObject response = new JSONObject(result);
                JSONArray articles = response.optJSONArray("posts");
                feedsList = new ArrayList<ArticleData>();

                for (int i = 0; i < articles.length(); i++) {
                    JSONObject article = articles.optJSONObject(i);
                    ArticleData item = new ArticleData();
                    item.setTitle(article.getString("title"));
                    item.setThumbnail(article.getString("thumbnail"));
                    item.setFullImageUrl(article.getJSONObject("thumbnail_images").getJSONObject("full").getString("url"));
                    item.setDescription(article.getString("excerpt"));

                    feedsList.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected void onPreExecute() {

            setProgressBarIndeterminateVisibility(true);
        }

        //This method calls after the request is processed
        @Override
        protected void onPostExecute(Integer result) {
            progressBar.setVisibility(View.GONE);

            if (result == 1) {
                //Set the adapter of Recyclerview with article data.
                adapter = new RecycleViewAdapter(ArticleListActivity.this, feedsList);
                recyclerView.setAdapter(adapter);
            } else {
                Toast.makeText(ArticleListActivity.this, "Error has occurred. Failed to get data from server.", Toast.LENGTH_SHORT).show();
            }

        }

        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
