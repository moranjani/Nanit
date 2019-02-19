package moranjani.nanitbday.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class GeneralUtils {

    private static final String SP_NANE = "bdayPrefs";

    public static SharedPreferences getSharedPrefs(Context appContext) {
        return appContext.getSharedPreferences(SP_NANE, Context.MODE_PRIVATE);
    }

    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static String formatString(String template, String param) {
        return String.format(template, param);
    }
}
