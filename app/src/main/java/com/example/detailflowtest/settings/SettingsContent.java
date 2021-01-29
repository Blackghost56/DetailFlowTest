package com.example.detailflowtest.settings;

import android.content.Context;
import android.content.res.Resources;

import androidx.fragment.app.Fragment;

import com.example.detailflowtest.R;
import com.example.detailflowtest.settings.fragment.SettingsAbout;
import com.example.detailflowtest.settings.fragment.SettingsFirmware;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class SettingsContent {

    /**
     * An array of sample (settings) items.
     */
    public static final List<ISettingsItem> ITEMS = new ArrayList<ISettingsItem>();

    /**
     * A map of sample (settings) items, by ID.
     */
    public static final Map<Integer, ISettingsItem> ITEM_MAP = new HashMap<Integer, ISettingsItem>();


    static {
        addItem(new SettingsAboutItem());
        addItem(new SettingsFirmwareUpdateItem());
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

    public static class SettingsAboutItem implements ISettingsItem{

        @Override
        public int getId() {
            return 0;
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


    public static class SettingsFirmwareUpdateItem implements ISettingsItem{

        @Override
        public int getId() {
            return 1;
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

}