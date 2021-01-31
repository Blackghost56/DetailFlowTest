package com.example.detailflowtest.settings;

import android.content.Context;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;

import com.example.detailflowtest.R;
import com.example.detailflowtest.settings.authorization.ChangePasswordFragment;
import com.example.detailflowtest.settings.fragment.SettingsAbout;
import com.example.detailflowtest.settings.fragment.SettingsFirmware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingsContent {

    public static final List<ISettingsItem> ITEMS = new ArrayList<ISettingsItem>();
    public static final Map<Integer, ISettingsItem> ITEM_MAP = new HashMap<Integer, ISettingsItem>();


    public static final int SETTINGS_ITEM_ABOUT_ID = 0;
    public static final int SETTINGS_ITEM_FIRMWARE_UPDATE_ID = 1;
    public static final int SETTINGS_ITEM_CHANGE_PASSWORD_ID = 2;


    static {
        addItem(new AboutItem());
        addItem(new FirmwareUpdateItem());
        addItem(new ChangePasswordItem());
    }


    private static void addItem(ISettingsItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }



    public interface ISettingsItem{
        public int getId();
        public String getContent(Context context);
        public Fragment getFragment();
        public boolean isPasswordRequired();
    }

    public static class AboutItem implements ISettingsItem{

        @Override
        public int getId() {
            return SETTINGS_ITEM_ABOUT_ID;
        }

        @Override
        public String getContent(Context context) {
            String content = "";
            try {
                content = context.getApplicationContext().getResources().getString(R.string.msg_settings_about);
            } catch (Resources.NotFoundException e){
                e.printStackTrace();
                return "About";
            }
            return content;
        }

        @Override
        public Fragment getFragment() {
            return new SettingsAbout();
        }

        @Override
        public boolean isPasswordRequired() {
            return false;
        }
    }


    public static class FirmwareUpdateItem implements ISettingsItem{

        @Override
        public int getId() {
            return SETTINGS_ITEM_FIRMWARE_UPDATE_ID;
        }

        @Override
        public String getContent(Context context) {
            String content = "";
            try {
                content = context.getApplicationContext().getResources().getString(R.string.msg_settings_firmware_upadte);
            } catch (Resources.NotFoundException e){
                e.printStackTrace();
                return "Firmware update";
            }
            return content;
        }

        @Override
        public Fragment getFragment() {
            return new SettingsFirmware();
        }

        @Override
        public boolean isPasswordRequired() {
            return true;
        }
    }


    public static class ChangePasswordItem implements ISettingsItem{

        @Override
        public int getId() {
            return SETTINGS_ITEM_CHANGE_PASSWORD_ID;
        }

        @Override
        public String getContent(Context context) {
            String content = "";
            try {
                content = context.getApplicationContext().getResources().getString(R.string.msg_authorization_change_password);
            } catch (Resources.NotFoundException e){
                e.printStackTrace();
                return "Change password";
            }
            return content;
        }

        @Override
        public Fragment getFragment() {
            return new ChangePasswordFragment();
        }

        @Override
        public boolean isPasswordRequired() {
            return false;
        }
    }

}