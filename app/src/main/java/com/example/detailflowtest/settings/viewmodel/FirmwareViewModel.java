package com.example.detailflowtest.settings.viewmodel;

import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.detailflowtest.settings.FirmwareProcess;
import com.example.detailflowtest.settings.tools.Event;

import static android.app.Activity.RESULT_OK;

public class FirmwareViewModel extends ViewModel {

    private final String TAG = "FirmwareViewModel";
    private static final int FILE_SELECT_CODE = 0;
    public ObservableField<String> mPath = new ObservableField<>("/storage/emulated/0/Download/sanitizer_dev.bin");

    public class StartActivityForResult{
        public Intent intent;
        public int result;
    }

    private final MutableLiveData<Event<Class<?>>> m_startActivity = new MutableLiveData<>();
    public LiveData<Event<Class<?>>> getStartActivity(){
        return m_startActivity;
    }

    private final MutableLiveData<Event<StartActivityForResult>> m_startActivityForResult = new MutableLiveData<>();
    public LiveData<Event<StartActivityForResult>> getStartActivityForResult(){
        return m_startActivityForResult;
    }

    public void selectAction(){
        StartActivityForResult startActivityForResult = new StartActivityForResult();
        startActivityForResult.intent = new Intent(Intent.ACTION_GET_CONTENT);
//        startActivityForResult.intent.setType("*/*");
        startActivityForResult.intent.setType("application/octet-stream");  // MIME type .bin

        startActivityForResult.intent.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult.result = FILE_SELECT_CODE;

        m_startActivityForResult.setValue(new Event<StartActivityForResult>(startActivityForResult));
    }


    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.toString());
                    String path  = uri.getPath();
                    Log.d(TAG, "File Path: " + path);
                    String[] split = path.split(":");//split the path.
                    if (split.length > 1) {
                        String path2 = split[1];//assign it to a string(your choice).
                        if (!path2.isEmpty()) {
                            mPath.set(path2);

//                            File file = new File(path2);
                        }
                    } else {
                        mPath.set(path);
                    }
                }
                break;
        }
    }

    public void updateAction(){
//        StartActivityForResult startActivityForResult = new StartActivityForResult();
//        startActivityForResult.intent = new Intent(getApplication().getApplicationContext(), FirmwareProcess.class);
//        startActivityForResult.result = FILE_SELECT_CODE;
//
//        m_startActivityForResult.setValue(new Event<StartActivityForResult>(startActivityForResult));

        m_startActivity.setValue(new Event<Class<?>>(FirmwareProcess.class));
    }

}