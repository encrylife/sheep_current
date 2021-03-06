package com.yibu.kuaibu.net.model.gongying;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by shanksYao on 2/21/15.
 */
public class GongYingRequest extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/getSellList.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }

   public GongYingRequest(int pageNo, int typeid,int vip){
       params=new RequestParams();
       addToken(params);
       if(typeid!=ALL)
       params.addBodyParameter("typeid",typeid+"");
       params.addBodyParameter("vip",vip+"");
       params.addBodyParameter("pagesize","20");
       params.addBodyParameter("pageid",pageNo+"");
   }
    public GongYingRequest(int pageNo,String userid){
        params=new RequestParams();
        addToken(params);
        params.addBodyParameter("userid",userid);
        params.addBodyParameter("pagesize","20");
        params.addBodyParameter("pageid",pageNo+"");
    }


    public final static int ALL=-1;
    public final static int XIAN_HUO=0;
    public final static int YU_DING=3;
    public final static int CU_XIAO=2;//团购
    ///全部的话 不传值

}
