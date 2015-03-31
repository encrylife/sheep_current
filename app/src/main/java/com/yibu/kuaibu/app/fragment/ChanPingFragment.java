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
import com.yibu.kuaibu.app.adaptor.ChanPingAdaptor;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.model.chanping.ChanPingDo;
import com.yibu.kuaibu.net.model.chanping.ChanPingRequest;

/**
 * Created by shanksYao on 3/10/15.
 */
public class ChanPingFragment extends BaseFragment {
    private int pageNo=1;
    private boolean hasMore=true;
    private ListView listView;
    private ChanPingAdaptor adaptor;
    private boolean  isLoading=false;
    private int type;
    public String keyword;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gongying,container,false);
        listView= (ListView) view.findViewById(R.id.listView);
        adaptor=new ChanPingAdaptor();
        listView.setAdapter(adaptor);
        type =getArguments().getInt("type",0);
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

    public static ChanPingFragment getFragment(int type){
        ChanPingFragment fragment=new ChanPingFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("type",type);
        fragment.setArguments(bundle);
        return fragment;
    }

    private void requestData() {


        if(hasMore){
            ChanPingRequest xHttpRequest;
            xHttpRequest=new ChanPingRequest();
            xHttpRequest.setParams(type,pageNo,keyword);
            HttpHelper.request(xHttpRequest, ChanPingDo.class, new HttpHelper.Callback<ChanPingDo>() {
                @Override
                public void onSuccess(ChanPingDo chanping) {
                    pageNo = chanping.page.pageid;
                    if (pageNo >= chanping.page.pagetotal) {
                        hasMore = false;
                    }
                    adaptor.addAll(chanping.rslist);
                    isLoading = false;
                }

                @Override
                public void onFailure(int errorCode, String msg) {
                    Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                    isLoading = false;
                }
            });
        }

    }


}
