package com.yibu.kuaibu.app.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.adaptor.GongYingAdaptor;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.gongying.BaseDo;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;
import com.yibu.kuaibu.net.model.gongying.GongYingDo;
import com.yibu.kuaibu.net.model.gongying.GongYingRequest;

/**
 * Created by shanksYao on 2/24/15.
 */
public abstract class BaseListFragment extends BaseFragment {


    private int pageNo=1;
    private boolean hasMore=true;
    private ListView listView;
    protected BaseAdapter adaptor;
    private boolean  isLoading=false;
    public abstract BaseAdapter getAdaptor();
    public abstract XHttpRequest getRequest(int pageNo);
    public abstract void  addAll(BaseAdapter adapter,BaseDo baseDo);
    public abstract Class  responseClass();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gongying,container,false);
        listView= (ListView) view.findViewById(R.id.listView);
        adaptor=getAdaptor();
        listView.setAdapter(adaptor);
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


    private void requestData() {


        if(hasMore){
            HttpHelper.request(getRequest(pageNo), responseClass(), new HttpHelper.Callback<BaseDo>() {
                @Override
                public void onSuccess(BaseDo gongYingDo) {
                    pageNo = gongYingDo.page.pageid;
                    if (pageNo >= gongYingDo.page.pagetotal) {
                        hasMore = false;
                    }
                    addAll(adaptor,gongYingDo);
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
