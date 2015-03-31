package com.yibu.kuaibu.net.model.chanping;

import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by shanksYao on 3/4/15.
 */
public class ChanPingRequest extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getMallList.php";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }
    ////0产品1样板

   public void setParams(int type,int pageNum,String keyword){
       params=new RequestParams();
       params.addBodyParameter("typeid",type+"");
       params.addBodyParameter("pageid",pageNum+"");
       params.addBodyParameter("pagesize","20");
       if(!TextUtils.isEmpty(keyword))
       params.addBodyParameter("keyword",keyword);
   }

}
