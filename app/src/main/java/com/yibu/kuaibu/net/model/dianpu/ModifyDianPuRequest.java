package com.yibu.kuaibu.net.model.dianpu;

import com.lidroid.xutils.http.RequestParams;
import com.yibu.kuaibu.net.helper.XHttpRequest;

/**
 * Created by HS on 3/22/15.
 */
public class ModifyDianPuRequest extends XHttpRequest {
    @Override
    public String getUrl() {
        return "http://www.51kuaibu.com/app/postUser.php";
    }

    @Override
    public RequestParams getParams() {
        return params;
    }

    public ModifyDianPuRequest(String truename, int areaid, String company, String telephone, String catid, String business, String address, String introduce) {
        params = new RequestParams();
        addToken(params);
        params.addBodyParameter("truename", truename);
        params.addBodyParameter("areaid", areaid + "");
        params.addBodyParameter("company", company);
        params.addBodyParameter("telephone", telephone);
        params.addBodyParameter("catid", catid);
        params.addBodyParameter("business", business);
        params.addBodyParameter("address", address);
        params.addBodyParameter("introduce", introduce);
    }

}
