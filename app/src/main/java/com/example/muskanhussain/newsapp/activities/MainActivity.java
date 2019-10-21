package com.example.muskanhussain.newsapp.activities;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.animation.Animation;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.muskanhussain.newsapp.R;
import com.example.muskanhussain.newsapp.adapter.VerticalPagerAdapter;
import com.example.muskanhussain.newsapp.customview.VerticalViewPager;
import com.example.muskanhussain.newsapp.fragment.ParentFragment;
import com.example.muskanhussain.newsapp.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, ParentFragment.ToggleVerticalScrolling {
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private VerticalViewPager newsView;
    private VerticalPagerAdapter adapter;
    private String baseUrl, key;
    private RequestQueue queue;
    private List<News> newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpUI();
        populateHeadlines("none");
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: Add Search Dialog Box
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });


    }

    private void populateHeadlines(String category) {
        String url;
        if(category == "none") {
            url = baseUrl + "top-headlines?country=in&pageSize=10&apiKey=" + key;
        } else {
            url = baseUrl + "top-headlines?country=in&category=" + category +"&pageSize=10&apiKey=" + key;
        }

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    newsList.clear();
                    JSONArray articles = response.getJSONArray("articles");
                    Log.d("MHARR", articles.toString());
                    for(int i = 0; i < articles.length(); i++) {
                        News item = new News();
                        Log.d("MHLEN", String.valueOf(articles.length()));
                        JSONObject ob = articles.getJSONObject(i);
                        Log.d("MHOBJ", ob.toString());
                        item.setTitle(ob.getString("title"));
                        item.setImageUrl(ob.getString("urlToImage"));
                        item.setUrl(ob.getString("url"));
                        item.setDescription(ob.getString("description"));
                        item.setPublishedAt(ob.getString("publishedAt"));
                        newsList.add(item);
                        Log.d("MHNEWSINSIDELOOP", item.getTitle());
                    }
                    Log.d("MHNEWSLEN", String.valueOf(newsList.size()));
                    for(int i = 0; i < newsList.size(); i++) {
                        Log.d("MHSizeInsideLoop", String.valueOf(newsList.size()));
                        Log.d("MHMAIN", newsList.get(i).getTitle());
                    }
                    adapter = new VerticalPagerAdapter(getSupportFragmentManager(), newsList);
                    newsView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Log.d("MHJSON", e.toString());
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("MHVOLL", error.toString());

            }
        });
        queue.add(objectRequest);
    }

    private void setUpUI() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = findViewById(R.id.search);
        drawer = findViewById(R.id.drawer_layout);
        newsView = findViewById(R.id.news_view);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        baseUrl = getResources().getString(R.string.base_url);
        key = getResources().getString(R.string.api_key);
        queue = Volley.newRequestQueue(this);
        newsList = new ArrayList<>();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if(id == R.id.top_headlines) {
            populateHeadlines("none");
        } else if(id == R.id.nav_business) {
            populateHeadlines("business");
        } else if(id == R.id.nav_entertainment) {
            populateHeadlines("entertainment");
        } else if(id == R.id.nav_general) {
            populateHeadlines("general");
        } else if(id == R.id.nav_health) {
            populateHeadlines("health");
        } else if(id == R.id.nav_science) {
            populateHeadlines("science");
        } else if(id ==  R.id.nav_sports) {
            populateHeadlines("sports");
        } else if(id ==  R.id.nav_technology) {
            populateHeadlines("technology");
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void trigger(int page) {
        if(page == 1) {
            newsView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return true;
                }
            });
        } else {
            newsView.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    return false;
                }
            });
        }
    }
}
