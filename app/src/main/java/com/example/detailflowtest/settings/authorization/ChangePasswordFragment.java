package com.example.detailflowtest.settings.authorization;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.detailflowtest.R;
import com.google.android.material.snackbar.Snackbar;


public class ChangePasswordFragment extends Fragment {

    public ChangePasswordFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    private ColorStateList mDefColor;
    private ColorStateList mErrorColor;

    EditText mCurrentPassword;
    EditText mNewPassword;
    EditText mConfirmPassword;

    TextWatcher mTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            updateConfirmBackGround();
        }
    };

    @SuppressLint("UseCompatLoadingForColorStateLists")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.change_password_fragment, container, false);

        CoordinatorLayout snackBarView = view.findViewById(R.id.snackbar_text);

        ImageView currentPassButton = view.findViewById(R.id.show_current_pass_button);
        ImageView newPassButton = view.findViewById(R.id.show_new_pass_button);
        ImageView confirmPassButton = view.findViewById(R.id.show_confirm_pass_button);
        mCurrentPassword = view.findViewById(R.id.editTextCurrentPassword);
        mNewPassword = view.findViewById(R.id.editTextNewPassword);
        mConfirmPassword = view.findViewById(R.id.editTextNewPasswordConfirm);


        currentPassButton.setOnClickListener(v -> passwordView(currentPassButton, mCurrentPassword));
        newPassButton.setOnClickListener(v -> passwordView(newPassButton, mNewPassword));
        confirmPassButton.setOnClickListener(v -> passwordView(confirmPassButton, mConfirmPassword));

        mDefColor = getActivity().getApplicationContext().getResources().getColorStateList(R.color.selector_password_def);
        mErrorColor = getActivity().getApplicationContext().getResources().getColorStateList(R.color.selector_password);

        mNewPassword.addTextChangedListener(mTextWatcher);
        mConfirmPassword.addTextChangedListener(mTextWatcher);

        PasswordHolder passwordHolder = PasswordHolder.getInstance();

        Button button = view.findViewById(R.id.button_change_password);
        button.setOnClickListener(v -> {
            String newPassword = mNewPassword.getText().toString();
            if (!newPassword.isEmpty()) {
                if (newPassword.equals((mConfirmPassword.getText().toString()))) {
                    if (passwordHolder.checkPassword(mCurrentPassword.getText().toString())) {
                        passwordHolder.passwordUpdate(newPassword);
                        passwordHolder.lock();
                        mCurrentPassword.setText("");
                        mNewPassword.setText("");
                        mConfirmPassword.setText("");
                        Snackbar.make(snackBarView, getContext().getString(R.string.msg_authorization_password_change_successfully), Snackbar.LENGTH_LONG).show();
                    } else {
                        Snackbar.make(snackBarView, getContext().getString(R.string.msg_authorization_incorrect), Snackbar.LENGTH_LONG).show();
                    }
                } else {
                    Snackbar.make(snackBarView, getContext().getString(R.string.msg_authorization_confirm_incorrect), Snackbar.LENGTH_LONG).show();
                }
            } else {
                Snackbar.make(snackBarView, getContext().getString(R.string.msg_authorization_password_empty), Snackbar.LENGTH_LONG).show();
            }
        });

        return view;
    }

    private void updateConfirmBackGround(){
        if (mNewPassword.getText().toString().equals(mConfirmPassword.getText().toString())){
            mConfirmPassword.setBackgroundTintList(mDefColor);
        } else {
            mConfirmPassword.setBackgroundTintList(mErrorColor);
        }
    }

    private void passwordView(ImageView button, EditText text){
        if(text.getTransformationMethod().equals(PasswordTransformationMethod.getInstance())){
            //Show Password
            button.setImageResource(R.drawable.visibility);
            text.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        }
        else{
            //Hide Password
            button.setImageResource(R.drawable.invisibility);
            text.setTransformationMethod(PasswordTransformationMethod.getInstance());
        }
    }
}