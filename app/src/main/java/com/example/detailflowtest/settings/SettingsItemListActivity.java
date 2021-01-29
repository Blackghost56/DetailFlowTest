package com.example.detailflowtest.settings;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.detailflowtest.R;
import com.example.detailflowtest.settings.authentication.AuthorizationFragment;
import com.example.detailflowtest.settings.fragment.SettingsStub;

import java.util.List;


public class SettingsItemListActivity extends AppCompatActivity {


    private static Context mContext;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getApplicationContext();

        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // Create view stub
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, SettingsStub.newInstance()).commit();
            }
        }

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);
    }


    public static Context getAppContext() {
        return mContext;
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(this, SettingsContent.ITEMS, mTwoPane));
    }

    public static class SimpleItemRecyclerViewAdapter extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final SettingsItemListActivity mParentActivity;
        private final List<SettingsContent.ISettingsItem> mValues;
        private final boolean mTwoPane;
        private final View.OnClickListener mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SettingsContent.ISettingsItem item = (SettingsContent.ISettingsItem) view.getTag();
                if (mTwoPane) {
                    // Создание фрагмента из SettingsContent
                    if (item.isPasswordRequired()){
                        AuthorizationFragment fragment = AuthorizationFragment.newInstance(item.getId());
                        fragment.registerCallback(new AuthorizationFragment.IAuthorization() {
                            @Override
                            public void successfully(int param) {
                                SettingsContent.ISettingsItem item = SettingsContent.ITEM_MAP.get(param);
                                if (item != null)
                                    mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, item.getFragment()).commit();
                            }
                        });
                        mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, fragment).commit();
                    } else {
                        mParentActivity.getSupportFragmentManager().beginTransaction().replace(R.id.item_detail_container, item.getFragment()).commit();
                    }

                } else {
                    // Создание активити
                    Context context = view.getContext();
                    Intent intent = new Intent(context, SettingsItemDetailActivity.class);
                    intent.putExtra(SettingsItemDetailActivity.ARG_ITEM_ID, item.getId());
                    context.startActivity(intent);
                }
            }
        };

        SimpleItemRecyclerViewAdapter(SettingsItemListActivity parent, List<SettingsContent.ISettingsItem> items, boolean twoPane) {
            mValues = items;
            mParentActivity = parent;
            mTwoPane = twoPane;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.mContentView.setText(mValues.get(position).getContent(mParentActivity));

            holder.itemView.setTag(mValues.get(position));
            holder.itemView.setOnClickListener(mOnClickListener);
        }

        @Override
        public int getItemCount() {
            return mValues.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final TextView mContentView;

            ViewHolder(View view) {
                super(view);
                mContentView = (TextView) view.findViewById(R.id.content);
            }
        }
    }
}