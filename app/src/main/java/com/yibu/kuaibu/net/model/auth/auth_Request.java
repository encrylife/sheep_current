package com.yibu.kuaibu.net.model.auth;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by zys on 2015/3/30.
 */
public class auth_Request extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/postValidate.php?";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }

    public auth_Request(auth_model model)
    {
        params = new RequestParams();
        addToken(params);
        params.addBodyParameter("action", "company");

        params.addBodyParameter("title",model.getTitle());
        params.addBodyParameter("truename",model.getTruename());
        params.addBodyParameter("idcard",model.getIdcard());
        /*营业执照扫描件*/
        params.addBodyParameter("thumb",model.getThumb());
        /*身份证扫描件*/
        params.addBodyParameter("thumb1",model.getGetThumb1());
    }
}
