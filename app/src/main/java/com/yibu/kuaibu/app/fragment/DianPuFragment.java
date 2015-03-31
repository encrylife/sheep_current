package com.yibu.kuaibu.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.Toast;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.adaptor.CompanyListAdapter;
import com.yibu.kuaibu.app.adaptor.GongYingAdaptor;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.dianpu.DianPuDo;
import com.yibu.kuaibu.net.model.dianpu.DianPuRequest;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;
import com.yibu.kuaibu.net.model.gongying.GongYingDo;
import com.yibu.kuaibu.net.model.gongying.GongYingRequest;

/**
 * Created by shanksYao on 2/24/15.
 */
public class DianPuFragment extends BaseFragment {


    private int pageNo=1;
    private boolean hasMore=true;
    private ListView listView;
    private CompanyListAdapter adaptor;
    private boolean  isLoading=false;
    private int vip;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gongying,container,false);
        listView= (ListView) view.findViewById(R.id.listView);
        adaptor=new CompanyListAdapter(getActivity());
        listView.setAdapter(adaptor);
        vip=getArguments().getInt("vip",0);
        requestData();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                       if(!isLoading&&view.getLastVisiblePosition()>adaptor.getCount()+4){
                            requestData();
                       }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(!isLoading&&view.getLastVisiblePosition()>adaptor.getCount()+4){
                    requestData();
                }
            }
        });
        return view;
    }

    public static DianPuFragment getFragment(int vip){
        DianPuFragment fragment=new DianPuFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("vip",vip);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void requestData() {


        if(hasMore){
            DianPuRequest xHttpRequest;
            xHttpRequest=new DianPuRequest();
            xHttpRequest.setParams(pageNo,vip);
            HttpHelper.request(xHttpRequest, DianPuDo.class, new HttpHelper.Callback<DianPuDo>() {
                @Override
                public void onSuccess(DianPuDo dianpu) {
                    pageNo = dianpu.page.pageid;
                    if (pageNo >= dianpu.page.pagetotal) {
                        hasMore = false;
                    }
                    adaptor.addAll(dianpu.rslist);
                    isLoading=false;
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    isLoading=false;
                }
            });
        }

    }
}
