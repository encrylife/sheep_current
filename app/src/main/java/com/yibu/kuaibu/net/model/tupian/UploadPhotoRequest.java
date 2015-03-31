package com.yibu.kuaibu.net.model.tupian;

import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.XHttpRequest;

import java.io.File;

/**
 * Created by HS on 3/7/15.
 */
public class UploadPhotoRequest extends XHttpRequest {

    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/upload.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }


    public UploadPhotoRequest(String action, int itemid, int mid, int order, File file) {
        params = new RequestParams();
        addToken(params);
        params.addBodyParameter("action", action);
        params.addBodyParameter("itemid", itemid + "");
        params.addBodyParameter("mid", mid + "");
        params.addBodyParameter("order", order + "");
        params.addBodyParameter("files", file);
        StringBuilder sb = new StringBuilder();
        sb.append("token:" + glApplication.mwzuser.getUtoken() != null ? glApplication.mwzuser.getUtoken() : "");
        sb.append(" action:" + action);
        sb.append(" itemid:" + itemid);
        sb.append(" mid:" + mid);
        sb.append(" order:" + order);
        Log.i("debug", sb.toString());
    }
}
