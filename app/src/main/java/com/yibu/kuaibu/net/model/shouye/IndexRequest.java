package com.yibu.kuaibu.net.model.shouye;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by shanksYao on 3/1/15.
 */
public class IndexRequest extends XHttpRequest
{
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getIndex.php?";
    }

    @Override
    public RequestParams getParams() {
        RequestParams params= new RequestParams();
        addToken(params);
        return params;
    }
}
