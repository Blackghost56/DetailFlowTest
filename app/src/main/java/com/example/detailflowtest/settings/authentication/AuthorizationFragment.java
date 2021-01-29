package com.example.detailflowtest.settings.authentication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.detailflowtest.R;
import com.google.android.material.snackbar.Snackbar;


public class AuthorizationFragment extends Fragment {


    private final String TAG = "AuthorizationFragment";

    public interface IAuthorization{
        public void successfully(int param);
    }

    private IAuthorization mCallBack = null;
    public void registerCallback(IAuthorization callback){
        mCallBack = callback;
    }


    private static final String ARG_PARAM = "param";
    private int mParam;

    public AuthorizationFragment() {
        // Required empty public constructor
    }


    public static AuthorizationFragment newInstance(int param) {
        AuthorizationFragment fragment = new AuthorizationFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getInt(ARG_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.authorization_fragment, container, false);

        ImageView imageView = view.findViewById(R.id.show_pass_btn);
        EditText editText = view.findViewById(R.id.editTextPassword);

        imageView.setOnClickListener(v -> {
            if(editText.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
                //Show Password
                imageView.setImageResource(R.drawable.visibility);
                editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
            }
            else{
                //Hide Password
                imageView.setImageResource(R.drawable.invisibility);
                editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
            }
        });

        PasswordHolder passwordHolder = PasswordHolder.getInstance();
        if (passwordHolder.isUnlocked())
            callBack();

        Button button = view.findViewById(R.id.button_enter_password);
        button.setOnClickListener(v -> {
//            passwordHolder.resetPreference();
            if (passwordHolder.checkPassword(editText.getText().toString())){
//                Snackbar.make(view, "Done", Snackbar.LENGTH_LONG).show();
                callBack();
            } else {
                Snackbar.make(view, getActivity().getApplicationContext().getString(R.string.msg_authorization_incorrect), Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void callBack(){
        if (mCallBack != null) {
            mCallBack.successfully(mParam);
        } else {
            Log.e(TAG, "callback not registered");
        }
    }





}