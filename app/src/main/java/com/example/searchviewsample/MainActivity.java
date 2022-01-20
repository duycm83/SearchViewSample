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
import android.widget.CursorAdapter;
import android.widget.SearchView;
import android.widget.SimpleCursorAdapter;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
//    private static final String[] SUGGESTIONS = {
//            "Bauru", "Sao Paulo", "Rio de Janeiro",
//            "Bahia", "Mato Grosso", "Minas Gerais",
//            "Tocantins", "Rio Grande do Sul"
//    };
    private ArrayList<Place> listPlaces;
    private SimpleCursorAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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