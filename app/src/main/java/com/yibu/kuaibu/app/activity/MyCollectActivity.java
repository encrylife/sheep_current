package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.fragment.MyCollectFragment;
import com.yibu.kuaibu.app.tab.FragmentTabHelper;
import com.yibu.kuaibu.net.model.gongying.GongYingRequest;
import com.yibu.kuaibu.net.model.mycollect.MyCollectRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyCollectActivity extends Activity implements View.OnClickListener{

    private List<View> tabs=new ArrayList<View>();
    private Map<Integer,Integer> map=new HashMap<Integer, Integer>();
    private FragmentTabHelper<Fragment> fragmentTabHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        TextView head= (TextView) findViewById(R.id.main_head_title);
        head.setText(this.getString(R.string.my_collect_title));
        findViewById(R.id.head_title_right).setVisibility(View.GONE);
        findViewById(R.id.head_title_left).setOnClickListener(this);
        tabs.add(findViewById(R.id.tab1));
        tabs.add(findViewById(R.id.tab2));
        tabs.add(findViewById(R.id.tab3));
        tabs.add(findViewById(R.id.tab4));
        map.put(R.id.tab1,MyCollectRequest.PURCHASE);
        map.put(R.id.tab2,MyCollectRequest.SUPPY);
        map.put(R.id.tab3,MyCollectRequest.PRODUCT);
        map.put(R.id.tab4,MyCollectRequest.MALL);

        fragmentTabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(),R.id.container);


        for(View view:tabs){
            view.setOnClickListener(this);
            Log.i("view.getId",view.getId()+""+map.get(view.getId()));
            fragmentTabHelper.put(view.getId(), MyCollectFragment.getFragment(map.get(view.getId())));
        }

        int typeid=getIntent().getIntExtra("typeid", MyCollectRequest.PURCHASE);
        showTab(typeid);
    }


    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.head_title_left){
            finish();
        }else{
            Log.i("id:",(v.getId()==R.id.tab1)+"");
            showTab(map.get(v.getId()));
        }
    }
    public void showTab(int typeid){
        Log.i("showTabID:",typeid+"");
        switch (typeid){
            case MyCollectRequest.PURCHASE:
                selectTab(0);
                fragmentTabHelper.showFragment(R.id.tab1);
                break;
            case MyCollectRequest.SUPPY:
                selectTab(1);
                fragmentTabHelper.showFragment(R.id.tab2);
                break;
            case MyCollectRequest.PRODUCT:
                selectTab(2);
                fragmentTabHelper.showFragment(R.id.tab3);
                break;
            case MyCollectRequest.MALL:
                selectTab(3);
                fragmentTabHelper.showFragment(R.id.tab4);
                break;


        }

    }
    private void selectTab(int tab){
        for(int i=0;i<tabs.size();i++){
            if(i==tab)
                tabs.get(i).setSelected(true);
            else
                tabs.get(i).setSelected(false);
        }
    }
    public static void startActivity(Context context,int typeid){
        Intent intent=new Intent(context,MyCollectActivity.class);
        intent.putExtra("typeid",typeid);
        context.startActivity(intent);
    }
}
