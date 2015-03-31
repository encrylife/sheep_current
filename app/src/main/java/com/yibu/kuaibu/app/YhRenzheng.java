package com.yibu.kuaibu.app;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.adapterly.SYBannerImageLoader;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.XHttpRequest;
import com.yibu.kuaibu.net.model.auth.auth_Request;
import com.yibu.kuaibu.net.model.auth.auth_model;
import com.yibu.kuaibu.net.model.auth.result_model;
import com.yibu.kuaibu.net.model.leimu.SubcateDo;
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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class YhRenzheng extends Activity implements View.OnClickListener
{

	private ImageView mTitleBack;
	private TextView mrenzhengtexttitle;
    private EditText auth_companyname;
    private EditText renzheng_companyOwner;
    private EditText renzheng_companynumber;
    private EditText edt_carid_number;
    private LinearLayout rlt_business_license;
    private LinearLayout rlt_cardId_image;
    private Button btn_ok;
    private String[] fitems = {"选取", "拍照"};
    private static final int IMAGE_REQUEST_CODE = 10;
    private static final int CAMERA_REQUEST_CODE = 11;
    private static final int RESULT_REQUEST_CODE = 12;
    String state = Environment.getExternalStorageState();
    String[] imgSrc=new String[2];
    private static int cameraType;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.yhrenzheng);
        TextView head= (TextView) findViewById(R.id.main_head_title);
        findViewById(R.id.head_title_left).setOnClickListener(this);
        head.setText(this.getString(R.string.auth_title));
		mTitleBack = (ImageView) findViewById(R.id.head_title_left);
		mTitleBack.setOnClickListener(this);
        rlt_business_license=(LinearLayout)findViewById(R.id.rlt_business_license);
        rlt_business_license.setOnClickListener(this);
        rlt_cardId_image=(LinearLayout)findViewById(R.id.rlt_cardId_image);
        rlt_cardId_image.setOnClickListener(this);
        btn_ok=(Button)findViewById(R.id.btn_ok);
        btn_ok.setOnClickListener(this);
        //公司名称
        auth_companyname=(EditText)findViewById(R.id.auth_company_name);
        //法人姓名
        renzheng_companyOwner=(EditText)findViewById(R.id.renzheng_companyOwner);
        //营业执照号码
        renzheng_companynumber=(EditText)findViewById(R.id.renzheng_companynumber);
        //身份证号码
        edt_carid_number=(EditText)findViewById(R.id.edt_carid_number);


	}


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.head_title_left:
                finish();
            break;
            case R.id.rlt_business_license:
                /*0 business license*/
                cameraType=0;
                showDialog();
                break;
            case R.id.rlt_cardId_image:
                cameraType=1;
                showDialog();
                break;
            case R.id.btn_ok:
                submitVerify();
                break;
        }
    }


    /*
      zoom
    * */
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
    /*
     take photos
    * */
    private void showDialog() {

        new AlertDialog.Builder(this)
                .setTitle("获取扫描件")
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
                                    String fileName= System.currentTimeMillis()+".jpg";
                                    intentFromCapture.putExtra(
                                            MediaStore.EXTRA_OUTPUT,
                                            Uri.fromFile(new File(Environment
                                                    .getExternalStorageDirectory(),
                                                    fileName)));
                                           if(cameraType==0)
                                           {
                                               if(imgSrc[0]==null) {
                                                   imgSrc[0] = fileName;
                                               }
                                           }
                                            if(cameraType==1)
                                            {
                                                if(imgSrc[1]==null) {
                                                    imgSrc[1] = fileName;
                                                }
                                            }
                                    Log.i("imageSrc:","imgsrc0"+imgSrc[0]+"img src 1 :"+imgSrc[1]+"");
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

    private void getImageToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Bitmap photo = extras.getParcelable("data");
            photo.compress(Bitmap.CompressFormat.PNG, 100, out);
            String IMAGE_FILE_NAME="";
            if(cameraType==0)
            {
                IMAGE_FILE_NAME=imgSrc[0];
            }
            if(cameraType==1)
            {
                IMAGE_FILE_NAME=imgSrc[1];
            }
            File f = new File(Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/" + IMAGE_FILE_NAME);
            Log.i("img src[]","0:"+imgSrc[0]+"1 "+imgSrc[1]);
            upload(f);
            //f.delete();
        }
    }

    private void upload(final File file) {
        XHttpRequest xHttpRequest = new UploadPhotoRequest("validate", 0, 0, 0, file);
        HttpHelper.request(xHttpRequest, ImageUrlDo.class, new HttpHelper.Callback<ImageUrlDo>() {
            @Override
            public void onSuccess(ImageUrlDo imageUrlDo) {
//                Log.i("debug", imageUrlDo.thumb);
//                glApplication.getMwzuser().setAvatar(imageUrlDo.thumb);
                Toast.makeText(YhRenzheng.this, "上传成功", Toast.LENGTH_LONG).show();
                if(cameraType==0)
                {
                    findViewById(R.id.tv_business_license).setVisibility(View.VISIBLE);
                }
                if(cameraType==1)
                {
                    findViewById(R.id.tv_card_id).setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(YhRenzheng.this, msg, Toast.LENGTH_LONG).show();
                Log.i("errorMsg:",msg);
                if (errorCode == -11) {
                    YhRenzheng.this.startActivity(new Intent(YhRenzheng.this, Yhlogin.class));
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != 0) {
            if (requestCode == 10) {
                startPhotoZoom(data.getData());
            }
            if (requestCode == 11) {
                if (state.equals(Environment.MEDIA_MOUNTED)) {
                    File tempFile=null;
                    if(cameraType==0) {
                        tempFile=  new File(
                                Environment.getExternalStorageDirectory(),
                                imgSrc[0]);
                    }
                    if(cameraType==1)
                    {
                        tempFile=new File(
                                Environment.getExternalStorageDirectory(),
                                imgSrc[1]);
                    }
                    startPhotoZoom(Uri.fromFile(tempFile));

                } else {
                    Toast.makeText(YhRenzheng.this, "未找到存储卡，无法存储照片！",
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

    private void submitVerify()
    {
        if(imgSrc[0]==null||imgSrc[1]==null)
        {
            Toast.makeText(YhRenzheng.this, "请先上传图片",
                    Toast.LENGTH_LONG).show();
            return ;
        }
        String companyName=auth_companyname.getText().toString();
        String company_Owner_Name=renzheng_companyOwner.getText().toString();
        String renzheng_company_Number =renzheng_companynumber.getText().toString();
        String card_id_Number=edt_carid_number.getText().toString();
        String business_license_src=imgSrc[0];
        String card_id_src=imgSrc[1];
        auth_model model =new auth_model();
        model.setTruename(company_Owner_Name);
        model.setIdcard(renzheng_company_Number);
        model.setThumb(business_license_src);
        model.setTitle(companyName);
        model.setGetThumb1(card_id_src);
        XHttpRequest xHttpRequest = new auth_Request(model);
        HttpHelper.request(xHttpRequest, result_model.class,new HttpHelper.Callback<result_model>(){
            @Override
            public void onSuccess(result_model result) {
                Log.i("verify result",result.toString());
            }

            @Override
            public void onFailure(int errorCode, String msg) {

                Toast.makeText(YhRenzheng.this, msg, Toast.LENGTH_LONG).show();
                if (errorCode == -11) {
                    startActivity(new Intent(YhRenzheng.this, Yhlogin.class));
                }
            }

        });

    }
}
