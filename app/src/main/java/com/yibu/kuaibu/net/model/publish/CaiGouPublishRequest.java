package com.yibu.kuaibu.net.model.publish;

import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by HS on 3/7/15.
 */
public class CaiGouPublishRequest extends XHttpRequest {

    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/postBuy.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }


    public CaiGouPublishRequest(int itemid, String title, String amount, String catid, int typeid, String today, String introduce, String truename, String mobile, String album, String unit, String action) {
        params = new RequestParams();
        addToken(params);
//        params.addBodyParameter("itemid", itemid + "");
        params.addBodyParameter("title", title);
        params.addBodyParameter("amount", amount);
        params.addBodyParameter("catid", catid);
        params.addBodyParameter("typeid", typeid + "");
        params.addBodyParameter("today", today);
        params.addBodyParameter("introduce", introduce);
        params.addBodyParameter("truename", truename);
        params.addBodyParameter("mobile", mobile);
        params.addBodyParameter("album", album);
        params.addBodyParameter("unit", unit);
//        params.addBodyParameter("action", action);
        StringBuilder sb = new StringBuilder();
        sb.append("token:" + glApplication.mwzuser.getUtoken() != null ? glApplication.mwzuser.getUtoken() : "");
        sb.append(" itemid:" + itemid);
        sb.append(" title:" + title);
        sb.append(" amount:" + amount);
        sb.append(" catid:" + catid);
        sb.append(" typeid:" + typeid);
        sb.append(" today:" + today);
        sb.append(" introduce:" + introduce);
        sb.append(" truename:" + truename);
        sb.append(" mobile:" + mobile);
        sb.append(" album:" + album);
        sb.append(" unit:" + unit);
        sb.append(" action:" + action);
        Log.i("debug", sb.toString());
    }
}
