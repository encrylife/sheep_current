package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.fragment.GouWuCheFragment;
import com.yibu.kuaibu.app.fragment.ShouYeFragment;
import com.yibu.kuaibu.app.fragment.SouSuoFragment;
import com.yibu.kuaibu.app.fragment.WodeFragment;
import com.yibu.kuaibu.app.fragment.XiaoxiFragment;
import com.yibu.kuaibu.app.tab.FragmentTabHelper;
import com.yibu.kuaibu.app.tab.TabSelect;

/**
 * Created by shanksYao on 2/25/15.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private FragmentTabHelper helper;

    public final static String TAB="tab";

    private TabSelect tabSelect;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_index);
        helper=new FragmentTabHelper(getFragmentManager(),R.id.tab_container);
        helper.put(R.id.tab1, ShouYeFragment.getFragment());
        helper.put(R.id.tab2, SouSuoFragment.getFragment(R.id.search_caigou));
        helper.put(R.id.tab3, XiaoxiFragment.getFragment());
        helper.put(R.id.tab4, GouWuCheFragment.getFragment());
        helper.put(R.id.tab5, WodeFragment.getFragment());

        findViewById(R.id.tab1).setOnClickListener(this);
        findViewById(R.id.tab2).setOnClickListener(this);
        findViewById(R.id.tab3).setOnClickListener(this);
        findViewById(R.id.tab4).setOnClickListener(this);
        findViewById(R.id.tab5).setOnClickListener(this);

        int tabId=getIntent().getIntExtra(TAB,R.id.tab1);
        helper.showFragment(tabId);

        tabSelect=new TabSelect();
        tabSelect.add(findViewById(R.id.tab1)).add(findViewById(R.id.tab2)).add(findViewById(R.id.tab3))
                .add(findViewById(R.id.tab4)).add(findViewById(R.id.tab5));

        tabSelect.selectTab(tabId);
    }

    public static void startActivity(Context context, int id) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(TAB, id);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View v) {

        tabSelect.selectTab(v.getId());

        switch (v.getId()){
            case R.id.tab1:
                helper.showFragment(R.id.tab1);
                break;
            case R.id.tab2:
                helper.showFragment(R.id.tab2);
                break;
            case R.id.tab3:
                helper.showFragment(R.id.tab3);
                break;
            case R.id.tab4:
                helper.showFragment(R.id.tab4);
                break;
            case R.id.tab5:
                helper.showFragment(R.id.tab5);
                break;
        }
    }
}
