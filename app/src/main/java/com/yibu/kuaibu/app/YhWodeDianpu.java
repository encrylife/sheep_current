package com.yibu.kuaibu.app;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.lidroid.xutils.util.LogUtils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.activity.LeiMuActivity;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.dianpu.ModifyDianPuRequest;
import com.yibu.kuaibu.net.model.leimu.SubcateDo;
import com.yibu.kuaibu.net.model.publish.CaiGouPublishRequest;
import com.yibu.kuaibu.net.model.publish.PublishDo;
import com.yibu.kuaibu.net.model.tupian.ImageUrlDo;
import com.yibu.kuaibu.net.model.tupian.UploadPhotoRequest;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class YhWodeDianpu extends Activity implements View.OnClickListener {
    private ArrayList<SubcateDo> selectLeimu;
    private ImageView mTitleBack, img_add;
    private TextView mtitletxt, guanzhu;
    private EditText username, company, phone, address, chanpin, jianjie;
    private NetworkImageView thumb;
    private ImageView mine_icon;
    private String leiMuId = "";
    private static String PhotoName = "";
    private String[] fitems = {"从手机相册选取", "拍照"};
    private static final int IMAGE_REQUEST_CODE = 10;
    private static final int CAMERA_REQUEST_CODE = 11;
    private static final int RESULT_REQUEST_CODE = 12;
    private static final String IMAGE_FILE_NAME = "faceImage.jpg";
    String state = Environment.getExternalStorageState();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.yhwodedianpu);
        mTitleBack = (ImageView) findViewById(R.id.head_title_left);
        mTitleBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                finish();
            }
        });

        mtitletxt = (TextView) findViewById(R.id.main_head_title);
        mtitletxt.setText("我的店铺");

        img_add = (ImageView) findViewById(R.id.img_add);
        thumb = (NetworkImageView) findViewById(R.id.thumb);
        mine_icon = (ImageView) findViewById(R.id.mine_icon);
        username = (EditText) findViewById(R.id.username);
        company = (EditText) findViewById(R.id.company);
        phone = (EditText) findViewById(R.id.phone);
        guanzhu = (TextView) findViewById(R.id.guanzhu);
        address = (EditText) findViewById(R.id.address);
        chanpin = (EditText) findViewById(R.id.chanpin);
        jianjie = (EditText) findViewById(R.id.jianjie);

        if (!TextUtils.isEmpty(glApplication.getMwzuser().avatar)) {
//            NetImageHelper.setImageUrl(mine_icon, glApplication.getMwzuser().avatar, R.drawable.kb_user_icon, R.drawable.kb_user_icon);
            ImageLoader.getInstance().displayImage(
                    glApplication.getMwzuser().avatar, mine_icon);
        }
        if (TextUtils.isEmpty(glApplication.getMwzuser().getThumb())) {
            img_add.setVisibility(View.VISIBLE);
        } else {
            img_add.setVisibility(View.GONE);
            NetImageHelper.setImageUrl(thumb, glApplication.getMwzuser().getThumb(), R.drawable.wodedianpu_backg, R.drawable.wodedianpu_backg);
        }
        username.setText(glApplication.getMwzuser().truename);
        company.setText(glApplication.getMwzuser().getCompany());
        phone.setText(glApplication.getMwzuser().mobile);
        guanzhu.setText(glApplication.getMwzuser().getCatname());
        address.setText(glApplication.getMwzuser().getAddress());
        chanpin.setText(glApplication.getMwzuser().getBusiness());
        jianjie.setText(glApplication.getMwzuser().getIntroduce());
        findViewById(R.id.confirm).setOnClickListener(this);
        findViewById(R.id.sort_rl).setOnClickListener(this);
        findViewById(R.id.modify_head).setOnClickListener(this);

    }

    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("设置头像")
                .setItems(fitems, new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Intent intentFromGallery = new Intent();
                                intentFromGallery.setType("image/*"); // 设置文件类型
                                intentFromGallery
                                        .setAction(Intent.ACTION_GET_CONTENT);
                                startActivityForResult(intentFromGallery,
                                        IMAGE_REQUEST_CODE);
                                break;
                            case 1:

                                Intent intentFromCapture = new Intent(
                                        MediaStore.ACTION_IMAGE_CAPTURE);
                                if (state.equals(Environment.MEDIA_MOUNTED)) {
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    IMAGE_FILE_NAME)));
                                }
                                startActivityForResult(intentFromCapture,
                                        CAMERA_REQUEST_CODE);
                                break;
                        }
                    }
                })
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

    }

    private void confirm() {
        XHttpRequest xHttpRequest = new ModifyDianPuRequest(username.getText().toString().trim(), 2, company.getText().toString().trim(), phone.getText().toString().trim(), leiMuId, chanpin.getText().toString().trim(), address.getText().toString().trim(), jianjie.getText().toString().trim());
        HttpHelper.request(xHttpRequest, PublishDo.class, new HttpHelper.Callback<PublishDo>() {
            @Override
            public void onSuccess(PublishDo publishDo) {
                Toast.makeText(YhWodeDianpu.this, "修改成功", Toast.LENGTH_LONG).show();
                glApplication.getMwzuser().setTruename(username.getText().toString().trim());
                glApplication.getMwzuser().setCompany(company.getText().toString().trim());
                glApplication.getMwzuser().setMobile(phone.getText().toString().trim());
                glApplication.getMwzuser().setCatname(guanzhu.getText().toString().trim());
                glApplication.getMwzuser().setBusiness(chanpin.getText().toString().trim());
                glApplication.getMwzuser().setIntroduce(jianjie.getText().toString().trim());
                glApplication.getMwzuser().setAddress(address.getText().toString().trim());
            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(YhWodeDianpu.this, msg, Toast.LENGTH_LONG).show();
                if (errorCode == -11) {
                    YhWodeDianpu.this.startActivity(new Intent(YhWodeDianpu.this, Yhlogin.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 0 && resultCode == 100) {
            guanzhu.setText("");
            selectLeimu = (ArrayList<SubcateDo>) data.getSerializableExtra("leimu");
            for (int i = 0; i < selectLeimu.size(); i++) {
                if (i != 0) {
                    guanzhu.append("、");
                    leiMuId += ",";
                }
                guanzhu.append(selectLeimu.get(i).catname);
                leiMuId += (int) Float.parseFloat(selectLeimu.get(i).catid.trim());
            }
        }
        Log.i("debug", "requestCode" + requestCode + "resultCode" + resultCode);
        if (resultCode != 0) {
            if (requestCode == 10) {
                startPhotoZoom(data.getData());
            }
            if (requestCode == 11) {
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File tempFile = new File(
                            Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME);
                    startPhotoZoom(Uri.fromFile(tempFile));
                } else {
                    Toast.makeText(YhWodeDianpu.this, "未找到存储卡，无法存储照片！",
                            Toast.LENGTH_LONG).show();
                }
            }
            if (requestCode == 12) {
                if (data != null) {
                    getImageToView(data);
                }
            }
        }
    }

    public void startPhotoZoom(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 320);
        intent.putExtra("outputY", 320);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 12);
    }

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap photo = extras.getParcelable("data");
            photo.compress(Bitmap.CompressFormat.PNG, 100, out);
            byte[] img = out.toByteArray();
            Drawable drawable = new BitmapDrawable(photo);
            mine_icon.setImageDrawable(drawable);
            File f = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + IMAGE_FILE_NAME);
//            f.delete();
            try {
                BufferedOutputStream stream = null;
                FileOutputStream fstream = new FileOutputStream(f);
                stream = new BufferedOutputStream(fstream);
                stream.write(img);
                upload(f);
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(YhWodeDianpu.this, "上传失败",
                        Toast.LENGTH_LONG).show();
            }
        }
    }

    private void upload(final File file) {
        XHttpRequest xHttpRequest = new UploadPhotoRequest("avatar", 0, 0, 0, file);
        HttpHelper.request(xHttpRequest, ImageUrlDo.class, new HttpHelper.Callback<ImageUrlDo>() {
            @Override
            public void onSuccess(ImageUrlDo imageUrlDo) {
//                Log.i("debug", imageUrlDo.thumb);
//                glApplication.getMwzuser().setAvatar(imageUrlDo.thumb);
                Toast.makeText(YhWodeDianpu.this, "上传成功", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(YhWodeDianpu.this, msg, Toast.LENGTH_LONG).show();
                if (errorCode == -11) {
                    YhWodeDianpu.this.startActivity(new Intent(YhWodeDianpu.this, Yhlogin.class));
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.confirm: {
                confirm();
                break;
            }
            case R.id.sort_rl: {
                Intent intent = new Intent(this, LeiMuActivity.class);
                intent.putExtra("job", 1);
                startActivityForResult(intent, 0);
                break;
            }
            case R.id.modify_head: {
                showDialog();
                break;
            }

        }
    }
}
