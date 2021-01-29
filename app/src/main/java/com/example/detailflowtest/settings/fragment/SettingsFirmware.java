package com.example.detailflowtest.settings.fragment;

import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.detailflowtest.R;
import com.example.detailflowtest.databinding.SettingsFirmwareBinding;
import com.example.detailflowtest.settings.PasswordDialog;
import com.example.detailflowtest.settings.viewmodel.SettingsFirmwareViewModel;

public class SettingsFirmware extends Fragment {

    private SettingsFirmwareViewModel mViewModel;
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
        mViewModel = new ViewModelProvider(this).get(SettingsFirmwareViewModel.class);
        mBinding.setViewModel(mViewModel);
        // TODO: Use the ViewModel

//        PasswordDialog passwordDialog = new PasswordDialog();
//        passwordDialog.show(getFragmentManager(),"");
    }

}