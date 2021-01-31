package com.example.detailflowtest.settings;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Log;

import com.example.detailflowtest.R;
import com.example.detailflowtest.databinding.FirmwareProcessBinding;
import com.example.detailflowtest.settings.viewmodel.FirmwareProcessViewModel;

public class FirmwareProcess extends AppCompatActivity {

    private FirmwareProcessViewModel mModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firmware_process);

        mModel = new ViewModelProvider(this).get(FirmwareProcessViewModel.class);
        FirmwareProcessBinding binding = DataBindingUtil.setContentView(this, R.layout.firmware_process);
        binding.setViewModel(mModel);
    }

    @Override
    public void onBackPressed() {
//            super.onBackPressed();
    }
}