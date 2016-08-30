package com.santinowu.cordova.shortcut;

import java.net.URL;
import java.lang.Exception;
import org.json.JSONArray;
import org.json.JSONException;
import android.util.Log;
import android.net.Uri;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;

public class Shortcut extends CordovaPlugin {
    public final static String LOG_TAG = "com.santinowu.cordova.shortcut";

    @Override
    public boolean execute(String action, JSONArray args, final CallbackContext callbackContext) throws JSONException {
        if ("install".equals(action)) {
            String title   = args.optString(0);
            String iconUrl = args.optString(1);
            String data    = args.optString(2);

            try {
                install(title, iconUrl, data);

                callbackContext.success("success");
            } catch (Exception e) {
                Log.e(LOG_TAG, e.getMessage());

                callbackContext.success("failure");
            }

            return true;
        }

        return false;
    }

    private void install(String title, String iconUrl, String data) throws Exception {
        Bitmap icon = downloadIcon(iconUrl);

        Intent i = new Intent();
        i.setData(Uri.parse(data));

        Intent installer = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
        installer.putExtra("android.intent.extra.shortcut.INTENT", i);
        installer.putExtra("android.intent.extra.shortcut.NAME", title);
        installer.putExtra("android.intent.extra.shortcut.ICON", icon);

        cordova.getActivity().sendBroadcast(installer);
    }

    private Bitmap downloadIcon(String iconUrl) throws Exception {
        URL url = new URL(iconUrl);

        return BitmapFactory.decodeStream(
            url.openConnection().getInputStream()
        );
    }
}
