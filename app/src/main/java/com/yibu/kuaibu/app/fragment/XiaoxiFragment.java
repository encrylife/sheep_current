package com.yibu.kuaibu.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibu.kuaibu.R;

/**
 * Created by shanksYao on 3/1/15.
 * 首页--消息
 */
public class XiaoxiFragment extends BaseFragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.frame_msg,container,false);
        return root;
    }

    public static XiaoxiFragment getFragment(){
        return new XiaoxiFragment();
    }
}
