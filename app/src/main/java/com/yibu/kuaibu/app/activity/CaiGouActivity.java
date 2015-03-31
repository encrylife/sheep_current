package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.fragment.CaiGouFragment;
import com.yibu.kuaibu.app.fragment.GongYingFragment;
import com.yibu.kuaibu.app.tab.FragmentTabHelper;
import com.yibu.kuaibu.app.tab.TabSelect;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanksYao on 2/25/15.
 */
public class CaiGouActivity extends Activity implements View.OnClickListener{



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cai_gou);
        TextView head= (TextView) findViewById(R.id.main_head_title);
        head.setText("查看供应");
        findViewById(R.id.head_title_right).setVisibility(View.GONE);
        findViewById(R.id.head_title_left).setOnClickListener(this);
        getFragmentManager().beginTransaction().add(R.id.cai_gou_container,new CaiGouFragment()).commit();

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.head_title_left) {
            finish();
        }
    }

    public static void startActivity(Context context){
        Intent intent=new Intent(context,CaiGouActivity.class);
        context.startActivity(intent);
    }

}
