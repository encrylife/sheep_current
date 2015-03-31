package com.yibu.kuaibu.net.model.mycollect;

/**
 * Created by zys on 2015/3/25.
 */
import android.util.Log;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;
public class MyCollectRequest extends XHttpRequest {

    public final static int SUPPY=1;
    public final static int PURCHASE=0;
    public final static int PRODUCT=2;
    public final static int MALL=3;
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getFavorite.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }

    /*
        PAGE NO:页面索引
        typeid：类型
    * */
    public MyCollectRequest(int pageNo,int typeid)
    {
        params=new RequestParams();
        addToken(params);
        Log.i("Params:typeid",typeid+"");
        switch (typeid)
        {
            case 0:
                params.addBodyParameter("action","buy");
                break;
            case 1:
                params.addBodyParameter("action","sell");
                break;
            case 2:
                params.addBodyParameter("action","mall");
                break;
        }

        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }

}
