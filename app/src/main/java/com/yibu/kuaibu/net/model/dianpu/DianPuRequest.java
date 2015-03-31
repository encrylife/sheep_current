package com.yibu.kuaibu.net.model.dianpu;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by shanksYao on 3/4/15.
 */
public class DianPuRequest extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getCompanyList.php";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }
    public void setParams(int pageNo,int vip){
        params=new RequestParams();
        addToken(params);
        params.addBodyParameter("vip",vip+"");
        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }

}
