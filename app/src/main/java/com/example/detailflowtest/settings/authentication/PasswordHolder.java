package com.example.detailflowtest.settings.authentication;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.detailflowtest.settings.SettingsItemListActivity;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Arrays;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import kotlin.text.Charsets;

import static android.content.Context.MODE_PRIVATE;

public class PasswordHolder {

    enum VerificationMode {EVERYTIME,  ONCE}

    private final String TAG = "PasswordHolder";
    private static final String DEFAULT_PASSWORD = "12345";
//    private static final int ITERATION_COUNT = 65536;
    private static final int ITERATION_COUNT = 4096;
    private static final int KEY_LENGTH = 128;
    private final String PASSWORD_PREFERENCE_FILE = "password_preference";
    private final String SALT_TAG = "salt";
    private final String PASSWORD_TAG = "password";
    private byte[] mSalt = new byte[0];
    private String mPassword = "";
    private static boolean mUnlockState = false;
//    private static VerificationMode mMode = VerificationMode.EVERYTIME;
    private static VerificationMode mMode = VerificationMode.ONCE;
    private static PasswordHolder instance;


    public static  PasswordHolder getInstance(){
        if (instance == null)
            instance =  new PasswordHolder();
        return instance;
    }


    public boolean checkPassword(String password){
        if (mUnlockState && (mMode == VerificationMode.ONCE))
            return true;

        byte[] verifiable = generatePasswordHash(mSalt, password);
        if (mPassword.contains(Arrays.toString(verifiable))){
            mUnlockState = true;
            return true;
        }
        return false;
    }

    public boolean isUnlocked(){
        switch (mMode){
            case ONCE:
                return mUnlockState;
            case EVERYTIME:
                return false;
        }
        return false;
    }

    public void block(){
        mUnlockState = false;
    }

    public VerificationMode getVerificationMode(){
        return mMode;
    }

    public void setVerificationMode(VerificationMode mode){
        mMode = mode;
    }

    PasswordHolder(){
        saltInitialization();
        passwordInitialization();
    }

    public void resetPreference(){
        mSalt = generateSalt();
        saveSalt(mSalt);
        passwordUpdate(DEFAULT_PASSWORD);
    }

    private void saltInitialization(){
        if (!loadSalt()){
            mSalt = generateSalt();
            saveSalt(mSalt);
        }
        Log.d(TAG, "saltInitialization salt: " + Arrays.toString(mSalt));
    }


    private void passwordInitialization(){
        if (!loadPassword()){
            mPassword = Arrays.toString(generatePasswordHash(mSalt, DEFAULT_PASSWORD));
            savePassword(mPassword);
        }
        Log.d(TAG, "passwordInitialization password: " + mPassword);
    }

    private void passwordUpdate(String newPassword){
        mPassword = Arrays.toString(generatePasswordHash(mSalt, newPassword));
        savePassword(mPassword);
        Log.d(TAG, "passwordUpdate password: " + mPassword);
    }

    private byte[] generatePasswordHash(byte[] salt, String password){
        byte[] hash = new byte[0];

        if(password.isEmpty())
            return hash;

        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATION_COUNT, KEY_LENGTH);
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
            Log.d(TAG, "NoSuchAlgorithmException | InvalidKeySpecException");
        }

        return hash;
    }

    private boolean loadPassword(){
        SharedPreferences sharedPreferences = SettingsItemListActivity.getAppContext().getSharedPreferences(PASSWORD_PREFERENCE_FILE, MODE_PRIVATE);
        if (sharedPreferences.contains(PASSWORD_TAG)){
            String password = sharedPreferences.getString(PASSWORD_TAG, null);
            if (password != null){
                mPassword = password;
                return true;
            }
        }

        return false;
    }

    private void savePassword(String password){
        SharedPreferences sharedPreferences = SettingsItemListActivity.getAppContext().getSharedPreferences(PASSWORD_PREFERENCE_FILE, MODE_PRIVATE);

        sharedPreferences.edit().putString(PASSWORD_TAG, password).apply();
    }


    private byte[] generateSalt(){
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        Log.d(TAG, "generateSalt salt: " + Arrays.toString(salt));
        return salt;
    }


    private boolean loadSalt(){
        SharedPreferences sharedPreferences = SettingsItemListActivity.getAppContext().getSharedPreferences(PASSWORD_PREFERENCE_FILE, MODE_PRIVATE);
        if (sharedPreferences.contains(SALT_TAG)){
            String str = sharedPreferences.getString(SALT_TAG, null);
            if (str != null){
                mSalt = str.getBytes(Charsets.ISO_8859_1);
                return true;
            }
        }

        return false;
    }

    private void saveSalt(byte[] salt){
        SharedPreferences sharedPreferences = SettingsItemListActivity.getAppContext().getSharedPreferences(PASSWORD_PREFERENCE_FILE, MODE_PRIVATE);
        sharedPreferences.edit().putString(SALT_TAG, new String(salt, Charsets.ISO_8859_1)).apply();
    }


}
