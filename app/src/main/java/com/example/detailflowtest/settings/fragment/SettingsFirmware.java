package com.example.detailflowtest.settings.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.detailflowtest.R;
import com.example.detailflowtest.databinding.SettingsFirmwareBinding;
import com.example.detailflowtest.settings.tools.EventObserver;
import com.example.detailflowtest.settings.viewmodel.FirmwareViewModel;

public class SettingsFirmware extends Fragment {

    private final String TAG = "SettingsFirmware";
    private FirmwareViewModel mViewModel;
    private SettingsFirmwareBinding mBinding;

    public static SettingsFirmware newInstance() {
        return new SettingsFirmware();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.settings_firmware, container, false);
        View view = mBinding.getRoot();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(FirmwareViewModel.class);
        mBinding.setViewModel(mViewModel);

        mViewModel.getStartActivityForResult().observe(this, new EventObserver<FirmwareViewModel.StartActivityForResult>(
                new EventObserver.OnEventChanged<FirmwareViewModel.StartActivityForResult>(){
                    @Override
                    public void onUnhandledContent(FirmwareViewModel.StartActivityForResult data) {
                        startActivityForResult(data.intent, data.result);
                    }
                }));

        mViewModel.getStartActivity().observe( this, new EventObserver<Class<?>>(new EventObserver.OnEventChanged<Class<?>>() {
            @Override
            public void onUnhandledContent(Class<?> data) {
                startActivity(new Intent(getContext(), data));
            }
        }));
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        mViewModel.onActivityResult(requestCode, resultCode, data);
    }

}