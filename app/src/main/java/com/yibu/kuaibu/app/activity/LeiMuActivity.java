package com.yibu.kuaibu.app.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.model.leimu.LeiMuDo;
import com.yibu.kuaibu.net.model.leimu.LeiMuRequest;
import com.yibu.kuaibu.net.model.leimu.SubcateDo;
import com.yibu.kuaibu.utils.GsonUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shanks on 2/20/15.
 * 类目
 */
public class LeiMuActivity extends Activity implements View.OnClickListener {

    private ViewGroup container;
    private int job;//做什么工作
    private static final int JOB_SEARCH = 0;//搜索
    private static final int JOB_PUBLISH = 1;//发布
    private ArrayList<SubcateDo> selectItem = new ArrayList<SubcateDo>();
    static final int COLOR_P = Color.parseColor("#fe4101");
    static final int COLOR_N = Color.parseColor("#999999");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leimu);
        findViewById(R.id.head_title_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        job = getIntent().getIntExtra("job", 0);

        TextView title = (TextView) findViewById(R.id.main_head_title);
        title.setText("主营类别");
        if (job == 0) {
            findViewById(R.id.head_title_right).setVisibility(View.GONE);
        } else {
            TextView right = (TextView) findViewById(R.id.head_title_right);
            right.setText("完成");
            right.setOnClickListener(this);
        }
        container = (ViewGroup) findViewById(R.id.container);
        SharedPreferences sharedPreferences = getSharedPreferences("leimu", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString("leiMuDos", null);
        if (!TextUtils.isEmpty(str)) {
            Gson gson = new Gson();
            LeiMuDo[] leiMuDos = GsonUtils.jsonToBean(str, LeiMuDo[].class);
            renderView(leiMuDos);

        }

        HttpHelper.request(new LeiMuRequest(), LeiMuDo[].class, new HttpHelper.Callback<LeiMuDo[]>() {
            @Override
            public void onSuccess(LeiMuDo[] leiMuDos) {
                SharedPreferences sharedPreferences = getSharedPreferences("leimu", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                editor.putString("leiMuDos", gson.toJson(leiMuDos));
                editor.commit();

                renderView(leiMuDos);
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(LeiMuActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void renderView(LeiMuDo[] leiMuDos) {
        container.removeAllViews();
        for (int i = 0; i < leiMuDos.length; i++) {
            LeiMuDo leiMuDo = leiMuDos[i];
            View view = LayoutInflater.from(this).inflate(R.layout.cell_leimu, container, false);
            TextView title = (TextView) view.findViewById(R.id.title);
            title.setText(leiMuDo.catname);
            ViewGroup viewGroup = (ViewGroup) view.findViewById(R.id.cellContainer);
            SubcateDo[] subcateDos = leiMuDo.subcate;
            int k = subcateDos.length / 4;
            int last = subcateDos.length % 4;
            View.OnClickListener onClickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SubcateDo subcateDo = (SubcateDo) v.getTag();

                    if (job == 0) {
                        //TODO 跳转到搜索页面
                    } else {
                        TextView tv = (TextView) v;
                        if (v.isSelected()) {
                            selectItem.remove(subcateDo);
                            v.setSelected(false);
                            tv.setTextColor(COLOR_N);
                        } else {
                            selectItem.add(subcateDo);
                            v.setSelected(true);
                            tv.setTextColor(COLOR_P);
                        }
                    }
                }
            };
            for (int j = 0; j < k; j++) {
                View cell4 = LayoutInflater.from(LeiMuActivity.this).inflate(R.layout.cell_item_leimu, viewGroup, false);
                TextView view1 = (TextView) cell4.findViewById(R.id.t1);
                TextView view2 = (TextView) cell4.findViewById(R.id.t2);
                TextView view3 = (TextView) cell4.findViewById(R.id.t3);
                TextView view4 = (TextView) cell4.findViewById(R.id.t4);

                view1.setText(subcateDos[j * 4 + 0].catname);
                view2.setText(subcateDos[j * 4 + 1].catname);
                view3.setText(subcateDos[j * 4 + 2].catname);
                view4.setText(subcateDos[j * 4 + 3].catname);

                view1.setTag(subcateDos[j * 4 + 0]);
                view2.setTag(subcateDos[j * 4 + 1]);
                view3.setTag(subcateDos[j * 4 + 2]);
                view4.setTag(subcateDos[j * 4 + 3]);

                viewGroup.addView(cell4);

            }
            if (last > 0) {
                View cell4 = LayoutInflater.from(LeiMuActivity.this).inflate(R.layout.cell_item_leimu, viewGroup, false);
                TextView view1 = (TextView) cell4.findViewById(R.id.t1);
                TextView view2 = (TextView) cell4.findViewById(R.id.t2);
                TextView view3 = (TextView) cell4.findViewById(R.id.t3);
                TextView view4 = (TextView) cell4.findViewById(R.id.t4);
                if (last == 1) {
                    view1.setText(subcateDos[subcateDos.length - last].catname);
                    view1.setTag(subcateDos[subcateDos.length - last]);
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                } else if (last == 2) {
                    view1.setText(subcateDos[subcateDos.length - last].catname);
                    view2.setText(subcateDos[subcateDos.length - (last - 1)].catname);

                    view1.setTag(subcateDos[subcateDos.length - last]);
                    view2.setTag(subcateDos[subcateDos.length - (last - 1)]);

                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                } else {
                    view1.setText(subcateDos[subcateDos.length - last].catname);
                    view2.setText(subcateDos[subcateDos.length - (last - 1)].catname);
                    view3.setText(subcateDos[subcateDos.length - (last - 2)].catname);

                    view1.setTag(subcateDos[subcateDos.length - last]);
                    view2.setTag(subcateDos[subcateDos.length - (last - 1)]);
                    view3.setTag(subcateDos[subcateDos.length - (last - 2)]);

                    view4.setVisibility(View.INVISIBLE);
                }
                viewGroup.addView(cell4);
            }

            for (int z = 0; z < viewGroup.getChildCount(); z++) {
                ViewGroup cell4 = (ViewGroup) viewGroup.getChildAt(z);
                for (int x = 0; x < cell4.getChildCount(); x++) {
                    cell4.getChildAt(x).setOnClickListener(onClickListener);
                }
            }
            container.addView(view);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.head_title_right: {
                Intent intent = getIntent();
                intent.putExtra("leimu", (Serializable) selectItem);
                this.setResult(100, intent);
                this.finish();
                break;
            }
        }
    }

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, LeiMuActivity.class);
        context.startActivity(intent);

    }
}
