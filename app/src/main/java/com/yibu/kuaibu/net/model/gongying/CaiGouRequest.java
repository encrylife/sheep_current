package com.yibu.kuaibu.net.model.gongying;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by shanksYao on 2/25/15.
 */
public class CaiGouRequest extends XHttpRequest {

    public final static int VIP=1;
    public final static int ALL=0;
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getBuyList.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }


    public CaiGouRequest(int pageNo,int vip) {
        params=new RequestParams();
        addToken(params);
        params.addBodyParameter("vip",vip+"");
        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }

    public CaiGouRequest(int pageNo,int typeid,String userid) {
        params=new RequestParams();
        addToken(params);
        params.addBodyParameter("typeid",typeid+"");
        params.addBodyParameter("userid",userid+"");
        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }
}
