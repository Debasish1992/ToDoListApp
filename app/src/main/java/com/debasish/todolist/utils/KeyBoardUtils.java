package com.debasish.todolist.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;

import static android.content.ContentValues.TAG;

public class KeyBoardUtils {

    public static void hideKeyboard(Context context) {
        try {
            // use application level context to avoid unnecessary leaks.
            InputMethodManager inputManager = (InputMethodManager) context.getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(((Activity) context).getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        } catch (Exception e) {
            Log.e(TAG, "Sigh, cant even hide keyboard " + e.getMessage());
        }
    }
}
