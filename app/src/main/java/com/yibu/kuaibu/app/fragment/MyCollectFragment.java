package com.yibu.kuaibu.app.fragment;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.adaptor.GongYingAdaptor;
import com.yibu.kuaibu.app.adaptor.MyCollectAdaptor;
import com.yibu.kuaibu.app.adaptor.MyCollectFriendAdaptor;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;
import com.yibu.kuaibu.net.model.gongying.GongYingDo;
import com.yibu.kuaibu.net.model.gongying.GongYingRequest;
import com.yibu.kuaibu.net.model.mycollect.MyCollectFriedDo;
import com.yibu.kuaibu.net.model.mycollect.MyCollectFriend;
import com.yibu.kuaibu.net.model.mycollect.MyCollectFriendRequest;
import com.yibu.kuaibu.net.model.mycollect.MyCollectRequest;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCollectFragment extends BaseFragment {

    private int pageNo=1;
    private boolean hasMore=true;
    private ListView listView;
    private boolean  isLoading=false;

    private int typeId;
    private MyCollectAdaptor adaptor;
    private MyCollectFriendAdaptor friendAdaptor;
    private int requestType;
    private static final int PURCHASE=1;
    private int vip;
    private boolean me;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_gongying,container,false);
        listView= (ListView) view.findViewById(R.id.listView);
        requestType=getArguments().getInt("requestType",-1);
        typeId=getArguments().getInt("typeid",-1);
        adaptor=new MyCollectAdaptor(typeId);
        friendAdaptor=new MyCollectFriendAdaptor();





        vip=getArguments().getInt("vip",0);

        me=getArguments().getBoolean("me",false);
        requestData();
        if(typeId!=-1&&typeId<3) {
            listView.setAdapter(adaptor);
        }else{
            if(typeId==3)
            {
                listView.setAdapter(friendAdaptor);
            }
        }
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
    public static MyCollectFragment getFragment(int typeid){
        Log.i("typeid",typeid+"");
        MyCollectFragment myCollectFragment=new MyCollectFragment();
        Bundle bundle=new Bundle();
        bundle.putInt("typeid",typeid);
        myCollectFragment.setArguments(bundle);
        return myCollectFragment;
    }

    private void requestData() {


        if(hasMore){
            XHttpRequest xHttpRequest;
            if(typeId!=-1){
                if(typeId!=3) {
                    xHttpRequest = new MyCollectRequest(pageNo, typeId);
                }else{
                    xHttpRequest=new MyCollectFriendRequest(pageNo);
                }

            }
            else{
                xHttpRequest=new MyCollectRequest(pageNo,MyCollectRequest.PURCHASE);
            }
            if(typeId!=3) {
                HttpHelper.request(xHttpRequest, GongYingDo.class, new HttpHelper.Callback<GongYingDo>() {
                    @Override
                    public void onSuccess(GongYingDo gongYingDo) {
                        pageNo = gongYingDo.page.pageid;
                        if (pageNo >= gongYingDo.page.pagetotal) {
                            hasMore = false;
                        }
                        adaptor.addAll(gongYingDo.rslist);
                        isLoading = false;
                    }

                    @Override
                    public void onFailure(int errorCode, String msg) {
                        Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
                        isLoading = false;
                    }
                });
            }
            if(typeId==3)
            {
                HttpHelper.request(xHttpRequest, MyCollectFriedDo.class, new HttpHelper.Callback<MyCollectFriedDo>() {
                    @Override
                    public void onSuccess(MyCollectFriedDo collectFriedDo) {
                        pageNo = collectFriedDo.page.pageid;
                        if (pageNo >= collectFriedDo.page.pagetotal) {
                            hasMore = false;
                        }
                        friendAdaptor.addAll(collectFriedDo.rslist);
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

}
