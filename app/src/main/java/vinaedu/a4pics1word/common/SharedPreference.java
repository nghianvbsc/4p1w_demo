package vinaedu.a4pics1word.common;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by bscenter on 14/08/2016
 */
public class SharedPreference {
    public static SharedPreference pref;
    public SharedPreferences prefs;

    public SharedPreference(Context context) {
        prefs = context.getSharedPreferences(Constance.PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPreference getInstance(Context context) {
        if (pref == null) {
            pref = new SharedPreference(context);
        }
        return pref;
    }

    public void saveCurrentQuestion(int question) {
        SharedPreferences.Editor e = prefs.edit();
        e.putInt(Constance.CURRENT_QUESTION, question);
        e.apply();
    }

    public int getCurrentQuestion() {
        return prefs.getInt(Constance.CURRENT_QUESTION, 1);
    }

    public void saveGold(int gold) {
        SharedPreferences.Editor e = prefs.edit();
        e.putInt(Constance.GOLD, gold);
        e.apply();
    }

    public int getGold() {
        return prefs.getInt(Constance.GOLD, 0);
    }

    public void addGold(int gold) {
        int newGold = getGold() + gold;
        saveGold(newGold);
    }

    public boolean isFirstOpenApp() {
        return prefs.getBoolean(Constance.FIRST_OPEN, true);
    }

    public void setFirstOpenApp() {
        SharedPreferences.Editor e = prefs.edit();
        e.putBoolean(Constance.FIRST_OPEN, false);
        e.apply();
    }
}
