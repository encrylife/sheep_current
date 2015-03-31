package com.yibu.kuaibu.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {

    public static String getJsonString(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return null;
        } else {
            return obj.getString(key);
        }
    }
    public static int getJsonInt(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return 0;
        } else {
            return obj.getInt(key);
        }
    }
    public static double getJsonDouble(JSONObject obj, String key)
            throws JSONException {
        if (obj.isNull(key)) {
            return 0;
        } else {
            return obj.getDouble(key);
        }
    }
}
