/**
 *
 */
package com.yibu.kuaibu.app.caigou;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.util.LogUtils;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.adapter.HomePageAdapter;
import com.yibu.kuaibu.app.Yhlogin;
import com.yibu.kuaibu.app.activity.LeiMuActivity;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.app.supply.AddPicture;
import com.yibu.kuaibu.app.supply.ChoseTime;
import com.yibu.kuaibu.app.supply.ImageShowActivity;
import com.yibu.kuaibu.app.supply.InputTitleName;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.leimu.SubcateDo;
import com.yibu.kuaibu.net.model.publish.CaiGouPublishRequest;
import com.yibu.kuaibu.net.model.publish.GongYingPublishRequest;
import com.yibu.kuaibu.net.model.publish.PublishDo;
import com.yibu.kuaibu.net.model.tupian.ImageUrlDo;
import com.yibu.kuaibu.net.model.tupian.UploadPhotoRequest;
import com.yibu.kuaibu.ui.photoalbum.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class CaigouPublish extends Activity implements OnClickListener {

    private ArrayList<View> list;
    private ViewPager viewPager;
    private ImageView image1;
    private ImageView image2;
    private ImageView image3;
    private ImageView image11;
    private ImageView image12;
    private List<ImageView> imageViews = new ArrayList<ImageView>();
    private ArrayList<SubcateDo> selectLeimu;
    private ImageView point1;
    private ImageView point2;

    private int addposition = 1;
    private List<String> selectphotos = new ArrayList<String>();
    private List<Bitmap> bitmaps = new ArrayList<Bitmap>();

    private LinearLayout back_ll;

    private RelativeLayout namerl;
    private RelativeLayout sort_rl;
    private TextView nameet;
    private TextView timeet;
    private TextView typeet;
    private TextView priceet;
    private TextView publish;
    private Spinner unit;

    private EditText detailet;
    private EditText linkman;
    private EditText linkphone;

    private String leiMuId = "";
    private int[] statechose = new int[]{1, 0, 0};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.caigou_publish);

        initView();

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;// 宽度
        // int height = dm.heightPixels ;//高度

        list = new ArrayList<View>();
        viewPager = (ViewPager) findViewById(R.id.mypager);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) viewPager
                .getLayoutParams(); // 取控件textView当前的布局参数
        linearParams.height = width / 3;// 控件的高强制设成20

        viewPager.setLayoutParams(linearParams); // 使设置好的布局参数应用到控件

        point1 = (ImageView) findViewById(R.id.point1);
        point2 = (ImageView) findViewById(R.id.point2);

        initPageContent();

        HomePageAdapter adapter = new HomePageAdapter(this, list);
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                if (arg0 == 0) {
                    point1.setImageResource(R.drawable.blackpoint);
                    point2.setImageResource(R.drawable.whitepoint);
                } else {
                    point2.setImageResource(R.drawable.blackpoint);
                    point1.setImageResource(R.drawable.whitepoint);
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });

    }

    /**
     *
     */
    private void initView() {
        back_ll = (LinearLayout) findViewById(R.id.back_ll);
        namerl = (RelativeLayout) findViewById(R.id.namerl);
        sort_rl = (RelativeLayout) findViewById(R.id.sort_rl);
        nameet = (TextView) findViewById(R.id.nameet);
        timeet = (TextView) findViewById(R.id.timeet);
        typeet = (TextView) findViewById(R.id.typeet);
        priceet = (EditText) findViewById(R.id.priceet);
        publish = (TextView) findViewById(R.id.publish);
        unit = (Spinner) findViewById(R.id.unit);
        detailet = (EditText) findViewById(R.id.detailet);
        linkman = (EditText) findViewById(R.id.linkman);
        linkphone = (EditText) findViewById(R.id.linkphone);

        back_ll.setOnClickListener(this);
        namerl.setOnClickListener(this);
        timeet.setOnClickListener(this);
        sort_rl.setOnClickListener(this);
        publish.setOnClickListener(this);

    }

    /**
     *
     */
    private void initPageContent() {
        View view = View.inflate(this, R.layout.supply_details_viewpager_item,
                null);
        View view1 = View.inflate(this, R.layout.supply_details_viewpager_item,
                null);
        image1 = (ImageView) view.findViewById(R.id.image1);
        image2 = (ImageView) view.findViewById(R.id.image2);
        image3 = (ImageView) view.findViewById(R.id.image3);
        image1.setVisibility(View.VISIBLE);

        image11 = (ImageView) view1.findViewById(R.id.image1);
        image12 = (ImageView) view1.findViewById(R.id.image2);
        imageViews.add(image1);
        imageViews.add(image2);
        imageViews.add(image3);
        imageViews.add(image11);
        imageViews.add(image12);
        image1.setOnClickListener(this);
        image2.setOnClickListener(this);
        image3.setOnClickListener(this);
        image11.setOnClickListener(this);
        image12.setOnClickListener(this);

        image1.setTag(1);
        image2.setTag(2);
        image3.setTag(3);
        image11.setTag(11);
        image12.setTag(12);
        list.add(view);
        list.add(view1);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image1: {
                int pos = (Integer) v.getTag();

                if (pos == 1) {
                    if (addposition == 6) {
                        Intent intent = new Intent(this, ImageShowActivity.class);
                        intent.putExtra("photopath", selectphotos.get(4));
                        intent.putExtra("position", 4);
                        startActivityForResult(intent, 0);
                    } else {
                        Intent intent = new Intent(this, AddPicture.class);
                        intent.putExtra("canselect", 6 - addposition);
                        startActivityForResult(intent, 0);

                        // Toast.makeText(getApplicationContext(), "page1--1", 0)
                        // .show();
                    }

                } else if (pos == 11) {

                    Intent intent = new Intent(this, ImageShowActivity.class);
                    intent.putExtra("photopath", selectphotos.get(2));
                    intent.putExtra("state", 0);
                    intent.putExtra("position", 2);
                    // startActivity(intent);
                    startActivityForResult(intent, 0);

                }

                break;
            }
            case R.id.image2: {
                int pos = (Integer) v.getTag();

                if (pos == 2) {

                    Intent intent = new Intent(this, ImageShowActivity.class);
                    intent.putExtra("photopath", selectphotos.get(0));
                    intent.putExtra("position", 0);
                    intent.putExtra("state", 0);
                    startActivityForResult(intent, 0);

                } else if (pos == 12) {

                    Intent intent = new Intent(this, ImageShowActivity.class);
                    intent.putExtra("photopath", selectphotos.get(3));
                    intent.putExtra("position", 3);
                    intent.putExtra("state", 0);
                    startActivityForResult(intent, 0);
                }

                break;
            }
            case R.id.image3: {

                Intent intent = new Intent(this, ImageShowActivity.class);
                intent.putExtra("photopath", selectphotos.get(1));
                intent.putExtra("position", 1);
                intent.putExtra("state", 0);
                startActivityForResult(intent, 0);

                break;
            }
            case R.id.back_ll:
                this.finish();
                break;
            case R.id.namerl: {
                Intent intent = new Intent(this, InputTitleName.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.timeet: {
                // 触发动画，打开动画或者关闭动画
                Intent intent = new Intent(this, ChoseTime.class);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.sort_rl: {
                Intent intent = new Intent(this, LeiMuActivity.class);
                intent.putExtra("job", 1);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.publish: {
                publish();
                break;
            }
        }

    }


    private void publish() {
        int typeid = 0;
        XHttpRequest xHttpRequest = new CaiGouPublishRequest(1, nameet.getText().toString().trim(), priceet.getText().toString().trim(), leiMuId, typeid, timeet.getText().toString().trim(), detailet.getText().toString().trim(), linkman.getText().toString().trim(), linkphone.getText().toString().trim(), "", unit.getSelectedItem().toString(), "refresh");
        HttpHelper.request(xHttpRequest, PublishDo.class, new HttpHelper.Callback<PublishDo>() {
            @Override
            public void onSuccess(PublishDo publishDo) {
                Log.i("debug", publishDo.toString());
                if (selectphotos != null && selectphotos.size() > 0) {
                    upload(publishDo, 0);
                } else {
                    Toast.makeText(CaigouPublish.this, "发布成功", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(CaigouPublish.this, msg, Toast.LENGTH_LONG).show();
                if (errorCode == -11) {
                    CaigouPublish.this.startActivity(new Intent(CaigouPublish.this, Yhlogin.class));
                }
            }
        });
    }

    private void upload(final PublishDo publishDo, final int order) {
        LogUtils.i(order + "");
        XHttpRequest xHttpRequest = new UploadPhotoRequest("album", publishDo.itemid, publishDo.moduleid, order, new File(selectphotos.get(order)));
        HttpHelper.request(xHttpRequest, ImageUrlDo.class, new HttpHelper.Callback<ImageUrlDo>() {
            @Override
            public void onSuccess(ImageUrlDo imageUrlDo) {
                if (order < selectphotos.size() - 1) {
                    Log.i("debug", imageUrlDo.thumb);
                    upload(publishDo, order + 1);
                } else {
                    Toast.makeText(CaigouPublish.this, "发布成功", Toast.LENGTH_LONG).show();
                    finish();
                }
            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(CaigouPublish.this, msg, Toast.LENGTH_LONG).show();
                if (errorCode == -11) {
                    CaigouPublish.this.startActivity(new Intent(CaigouPublish.this, Yhlogin.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.i("debug", "resultCode:" + resultCode);
        if (requestCode == 0 && resultCode == 20) {

            ArrayList<String> photos = data.getStringArrayListExtra("photos");
            System.out.println("back--" + photos.size());
            for (int i = 0; i < photos.size(); i++) {
                try {
                    // System.out.println(photos.get(i));
                    if (addposition + i == 5) {
                        selectphotos.add(addposition + i - 1, photos.get(i));
                        Bitmap bitmap = Utils
                                .getPathBitmap(CaigouPublish.this,
                                        Uri.fromFile(new File(photos.get(i))),
                                        200, 200);
                        bitmaps.add(addposition + i - 1, bitmap);
                        imageViews.get(0).setVisibility(View.VISIBLE);
                        imageViews.get(0).setImageBitmap(bitmap);
                    } else {
                        selectphotos.add(addposition + i - 1, photos.get(i));
                        Bitmap bitmap = Utils
                                .getPathBitmap(CaigouPublish.this,
                                        Uri.fromFile(new File(photos.get(i))),
                                        200, 200);
                        bitmaps.add(addposition + i - 1, bitmap);
                        imageViews.get(addposition + i).setVisibility(
                                View.VISIBLE);
                        imageViews.get(addposition + i).setImageBitmap(bitmap);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // if (addposition+photos.size() <= 5)
            // {
            // imageViews.get(addposition+photos.size()-1).setVisibility(View.VISIBLE);
            // imageViews.get(addposition+photos.size()-1).setImageResource(
            // R.drawable.addpic);
            // }
            addposition += photos.size();
        } else if (requestCode == 0 && resultCode == 40) {
            int position = data.getIntExtra("position", 0);
            System.out.println("position---" + position);
            for (int i = 1; i < imageViews.size(); i++) {
                imageViews.get(i).setVisibility(View.GONE);
            }
            imageViews.get(0).setVisibility(View.VISIBLE);
            imageViews.get(0).setImageResource(R.drawable.addpic);
            selectphotos.remove(position);
            bitmaps.remove(position);
            addposition -= 1;
            for (int i = 1; i <= selectphotos.size(); i++) {

                imageViews.get(i).setVisibility(View.VISIBLE);
                imageViews.get(i).setImageBitmap(bitmaps.get(i - 1));

            }

        } else if (requestCode == 0 && resultCode == 60) {
            String tag = data.getStringExtra("tag");
            nameet.setText(tag);
        } else if (requestCode == 0 && resultCode == 80) {
            String sum = data.getStringExtra("sum");
            if (sum.equals("0")) {

            } else {
                timeet.setText(sum);
            }

        } else if (requestCode == 0 && resultCode == 100) {
            typeet.setText("");
            selectLeimu = (ArrayList<SubcateDo>) data.getSerializableExtra("leimu");
            for (int i = 0; i < selectLeimu.size(); i++) {
                if (i != 0) {
                    typeet.append("、");
                    leiMuId += ",";
                }
                typeet.append(selectLeimu.get(i).catname);
                leiMuId += (int) Float.parseFloat(selectLeimu.get(i).catid.trim());
            }
        }
    }

    protected void onResume() {
        super.onResume();
        linkman.setText(glApplication.getMwzuser().truename != null ? glApplication.getMwzuser().truename : "");
        linkphone.setText(glApplication.getMwzuser().mobile != null ? glApplication.getMwzuser().mobile : "");
    }
}
