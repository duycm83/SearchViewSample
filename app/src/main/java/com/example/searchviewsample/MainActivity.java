package com.example.searchviewsample;

import androidx.appcompat.app.AppCompatActivity;

import android.app.SearchManager;
import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.CursorAdapter;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private ArrayList<Place> listPlaces;
    private SimpleCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayout linear_layout = findViewById(R.id.bottomLayout);
        findViewById(R.id.tv_title).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Snackbar.make(v,"clicked", BaseTransientBottomBar.LENGTH_LONG).show();
                Animation slide_up = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_up);
                linear_layout.setVisibility(View.VISIBLE);
                linear_layout.setAnimation(slide_up);
            }
        });
        findViewById(R.id.close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Load animation
                Animation slide_down = AnimationUtils.loadAnimation(getApplicationContext(),
                        R.anim.slide_down);
                // Start animation
                linear_layout.startAnimation(slide_down);
                linear_layout.setVisibility(View.GONE);
            }
        });
        setupData();
        mAdapter = new SimpleCursorAdapter(this,
                R.layout.item_layout,
                null,
                new String[] {"title", "address"},
                new int[] {R.id.text1, R.id.text2},
                CursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSuggestionsAdapter(mAdapter);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        // Getting selected (clicked) item suggestion
        searchView.setOnSuggestionListener(new SearchView.OnSuggestionListener() {
            @Override
            public boolean onSuggestionClick(int position) {
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                int index1 = cursor.getColumnIndex("title");
                int index2 = cursor.getColumnIndex("address");
                String txt = cursor.getString(index1);
                searchView.setQuery(txt, true);
                return true;
            }

            @Override
            public boolean onSuggestionSelect(int position) {
                // Your code here
                Cursor cursor = (Cursor) mAdapter.getItem(position);
                int index1 = cursor.getColumnIndex("title");
                int index2 = cursor.getColumnIndex("address");
                String txt = cursor.getString(index1);
                searchView.setQuery(txt, true);
                return true;
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                populateAdapter(s);
                return false;
            }
        });
        return true;
    }

    // You must implements your logic to get data using OrmLite
    private void populateAdapter(String query) {
        final MatrixCursor c = new MatrixCursor(new String[]{BaseColumns._ID,"title", "address" });
        int index = 0;
        if (!"".equals(query)) {
            for (Place place: listPlaces) {
                if (place.getTitle().toLowerCase().startsWith(query) ||
                        place.getAddress().toLowerCase().contains(query)) {
                    c.addRow(new Object[]{index++,place.getTitle(), place.getAddress()});
                }
            }
        }
        mAdapter.changeCursor(c);
    }

    private void setupData() {
        listPlaces = new ArrayList<Place>();
        for (int i = 0; i < 10; i++) {
            Place place = new Place("title"+i, "address"+i);
            listPlaces.add(place);
        }
    }
}