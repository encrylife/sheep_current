package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.Yhlogin;
import com.yibu.kuaibu.app.fragment.CaiGouUnitFragment;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.app.tab.FragmentTabHelper;
import com.yibu.kuaibu.app.tab.TabSelect;

/**
 * Created by shanksYao on 3/16/15.
 */
public class MyCaiGouActivity extends BaseActivity implements View.OnClickListener{
    private FragmentTabHelper<Fragment> tabHelper;

    private TabSelect tabSelect;
    @Nullable
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_cai_gou);
        textView(R.id.main_head_title).setText("我的供应");
        finish(R.id.head_title_left);
        invisible(R.id.head_title_right);
        tabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(), R.id.fragment_cai_gou_container);
        tabHelper.put(R.id.tab1_CaiGouFragment, CaiGouUnitFragment.getCaiGouFragment(0));
        tabHelper.put(R.id.tab2_CaiGouFragment, CaiGouUnitFragment.getCaiGouFragment(1));
        tabSelect=new TabSelect();
        tabSelect.add(findViewById(R.id.tab1_CaiGouFragment));
        tabSelect.add(findViewById(R.id.tab2_CaiGouFragment));
        tabSelect.setOnClickListener(this);
        showTab(R.id.tab1_CaiGouFragment);
        TextView tab1= (TextView) findViewById(R.id.tab1_CaiGouFragment);
        TextView tab2= (TextView) findViewById(R.id.tab2_CaiGouFragment);
        tab1.setText("寻找中");
        tab2.setText("已找到");

    }

    @Override
    public void onClick(View v) {
        showTab(v.getId());
    }

    private void showTab(int id){
        tabHelper.showFragment(id);
        tabSelect.selectTab(id);
    }


    public static void  startActivity(Context context){
        if(glApplication.mwzuser==null){
            Intent intent=new Intent(context, Yhlogin.class);
            context. startActivity(intent);
        }else{
            Intent intent=new Intent(context, MyCaiGouActivity.class);
            context. startActivity(intent);
        }

    }
}
