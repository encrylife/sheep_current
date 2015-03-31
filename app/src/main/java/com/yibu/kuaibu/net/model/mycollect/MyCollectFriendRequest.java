package com.yibu.kuaibu.net.model.mycollect;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by zys on 2015/3/25.
 */
public class MyCollectFriendRequest extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getFriend.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }
    public MyCollectFriendRequest(int pageNo)
    {
        params=new RequestParams();
        addToken(params);
        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }
}
