package com.yibu.kuaibu.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.BaseAdapter;

import com.yibu.kuaibu.app.Yhlogin;
import com.yibu.kuaibu.app.adaptor.CaiGouAdaptor;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.gongying.BaseDo;
import com.yibu.kuaibu.net.model.gongying.CaiGouDo;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;

/**
 * Created by shanksYao on 3/16/15.
 */
public class CaiGouUnitFragment extends BaseListFragment{
    private int vip;

    private int typeid;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vip=getArguments().getInt("vip",-1);
        typeid=getArguments().getInt("typeid",-1);

    }

    public static CaiGouUnitFragment getCaiGouFragment(int vip){
        CaiGouUnitFragment gongYingFragment=new CaiGouUnitFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("vip",vip);
        gongYingFragment.setArguments(bundle);
        return gongYingFragment;
    }

    public static CaiGouUnitFragment getMyCaiGouFragment(int typeid){
        CaiGouUnitFragment gongYingFragment=new CaiGouUnitFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("typeid",typeid);
        gongYingFragment.setArguments(bundle);
        return gongYingFragment;
    }

    @Override
    public BaseAdapter getAdaptor() {
        return new CaiGouAdaptor();
    }

    @Override
    public XHttpRequest getRequest(int pageNo) {
        if(vip==-1)
        if(glApplication.mwzuser!=null){

            return new CaiGouRequest(pageNo,typeid,glApplication.mwzuser.userid);
        }

        return new CaiGouRequest(pageNo,vip);
    }

    @Override
    public void addAll(BaseAdapter adapter, BaseDo basedo) {
        ((CaiGouAdaptor)adaptor).addAll(((CaiGouDo)basedo).rslist);

    }

    @Override
    public Class responseClass() {
        return CaiGouDo.class;
    }
}
