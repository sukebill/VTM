package com.threemenstudio;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import java.util.LinkedHashMap;
import java.util.List;

import io.fabric.sdk.android.Fabric;

/**
 * Created by Shitman on 4/6/2016.
 */
public class VTMApplication extends Application {

    private static final String PREF_KEY = "com.threemenstudio.vtm.PREFERENCE_FILE_KEY";
    private static final String EXTRA_CLAN = "vtm.clan";
    private static final String EXTRA_DISCIPLINE = "vtm.discipline";
    private static final String EXTRA_TITLE = "VTM.TITLE";
    private static final String EXTRA_OFFICIAL = "vtm.official";
    private static final String EXTRA_PATH = "vtm.path";
    private static final String EXTRA_RITUAL = "vtm.ritual";
    private static final String EXTRA_RITUAL_SYSTEM = "vtm.ritual.system";
    private static Context context;
    private static LinkedHashMap<Integer, List<Boolean>> opened;

    public static String getPrefKey() {
        return PREF_KEY;
    }

    public static Context getContext() {
        return context;
    }

    public static String getExtraClan() {
        return EXTRA_CLAN;
    }

    public static String getExtraDiscipline() {
        return EXTRA_DISCIPLINE;
    }

    public static String getExtraTitle() {
        return EXTRA_TITLE;
    }

    public static String getExtraOfficial() {
        return EXTRA_OFFICIAL;
    }

    public static String getExtraPath() {
        return EXTRA_PATH;
    }

    public static String getExtraRitual() {
        return EXTRA_RITUAL;
    }

    public static String getExtraRitualSystem() {
        return EXTRA_RITUAL_SYSTEM;
    }

    public static LinkedHashMap<Integer, List<Boolean>> getOpened() {
        return opened;
    }

    public static void setOpened(LinkedHashMap<Integer, List<Boolean>> opened) {
        VTMApplication.opened = opened;
    }

    @Override
    public void onCreate() {

        super.onCreate();
        Fabric.with(this, new Crashlytics());
        context = this;

    }

}
