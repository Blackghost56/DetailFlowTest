package com.example.detailflowtest.settings;

import android.content.Intent;
import android.os.Bundle;

import com.example.detailflowtest.R;

import androidx.appcompat.widget.Toolbar;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.ActionBar;

import android.view.MenuItem;

public class SettingsItemDetailActivity extends AppCompatActivity {

    private final String TAG = "ItemDetailActivity";
    public static final String ARG_ITEM_ID = "item_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);


        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don"t need to manually add it.

        if (savedInstanceState == null) {
            int itemId = getIntent().getIntExtra(ARG_ITEM_ID, -1);        // todo Id string
            if (itemId != -1) {
                SettingsContent.ISettingsItem item = SettingsContent.ITEM_MAP.get(itemId);
                getSupportActionBar().setTitle(item.getContent(this));
                getSupportFragmentManager().beginTransaction().add(R.id.item_detail_container, item.getFragment()).commit();
            } else {
                Log.e(TAG, "Incorrect item ID");
                finish();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown.
            navigateUpTo(new Intent(this, SettingsItemListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}