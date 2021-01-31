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
import com.example.detailflowtest.databinding.SettingsAboutBinding;
import com.example.detailflowtest.settings.viewmodel.AboutViewModel;

public class SettingsAbout extends Fragment {

    private AboutViewModel mViewModel;
    private SettingsAboutBinding mBinding;

    public static SettingsAbout newInstance() {
        return new SettingsAbout();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, R.layout.settings_about, container, false);
        View view = mBinding.getRoot();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AboutViewModel.class);
        mBinding.setViewModel(mViewModel);

        // TODO: Use the ViewModel
    }

}