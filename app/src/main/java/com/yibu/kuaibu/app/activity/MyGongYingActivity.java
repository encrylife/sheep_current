package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.Yhlogin;
import com.yibu.kuaibu.app.fragment.GongYingFragment;
import com.yibu.kuaibu.app.glApplication;

/**
 * Created by shanksYao on 3/18/15.
 */
public class MyGongYingActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_gong_ying);
        textView(R.id.main_head_title).setText("我的采购");
        finish(R.id.head_title_left);
        invisible(R.id.head_title_right);
        getFragmentManager().
                beginTransaction().
                add(R.id.container, GongYingFragment.getMyGongYingFragment()).commit();
    }


    public static void  startActivity(Context context){
        if(glApplication.mwzuser==null){
            Intent intent=new Intent(context, Yhlogin.class);
            context. startActivity(intent);
        }else{
            Intent intent=new Intent(context, MyGongYingActivity.class);
            context. startActivity(intent);
        }

    }
}
