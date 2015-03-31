package com.yibu.kuaibu.app.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.bean.SocializeEntity;
import com.umeng.socialize.controller.UMServiceFactory;
import com.umeng.socialize.controller.UMSocialService;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.weixin.controller.UMWXHandler;
import com.umeng.socialize.controller.listener.SocializeListeners.SnsPostListener;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.WebViewer;
import com.yibu.kuaibu.app.YhRenzheng;
import com.yibu.kuaibu.app.YhWodeDianpu;
import com.yibu.kuaibu.app.Yhlogin;
import com.yibu.kuaibu.app.activity.MyCollectActivity;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.NetImageHelper;

/**
 * Created by shanksYao on 3/1/15.
 * 首页-我的
 */
public class WodeFragment extends BaseFragment implements View.OnClickListener {
    private Button login;
    private LinearLayout userinfo;
    private ImageView mine_icon;
    private TextView mine_name;
    private LinearLayout loginllt;
    private RelativeLayout mrlt_share;
    private SnsPostListener mSnsPostListener;
    final UMSocialService mController = UMServiceFactory.getUMSocialService("com.umeng.share");
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frame_user, container, false);


        login = (Button) root.findViewById(R.id.login);
        login.setOnClickListener(this);
        mrlt_share=(RelativeLayout)root.findViewById(R.id.rlt_share);
        mrlt_share.setOnClickListener(this);
        /*share*/
        mController.setShareContent("友盟社会化组件（SDK）让移动应用快速整合社交分享功能，http://www.umeng.com/social");
        mController.setShareImage(new UMImage(getActivity(),R.drawable.kuaibuicon1));
        com.umeng.socialize.utils.Log.LOG = true;
        /*
          html5 files
        * */
        root.findViewById(R.id.rlt_help).setOnClickListener(this);
        root.findViewById(R.id.rlt_fuwu).setOnClickListener(this);
        root.findViewById(R.id.mineguanyuwomen).setOnClickListener(this);
        root.findViewById(R.id.rlt_mycollect).setOnClickListener(this);
        root.findViewById(R.id.minemyshop).setOnClickListener(this);
        root.findViewById(R.id.minewoderenzheng).setOnClickListener(this);
         mSnsPostListener  = new SnsPostListener() {

            @Override
            public void onStart() {

            }

            @Override
            public void onComplete(SHARE_MEDIA platform, int stCode,
                                   SocializeEntity entity) {
                if (stCode == 200) {
                    Toast.makeText(getActivity(), "分享成功", Toast.LENGTH_SHORT)
                            .show();
                } else {
                    Toast.makeText(getActivity(),
                            "分享失败 : error code : " + stCode, Toast.LENGTH_SHORT)
                            .show();
                }
            }
        };
        String appID = "wxdf71533663734340";
        String appSecret = "dec9729d50175d100d09b177586b0243";
        // 添加微信平台
        UMWXHandler wxHandler = new UMWXHandler(getActivity(), appID, appSecret);
        wxHandler.setTargetUrl(this
                .getString(R.string.share_download_url));
        wxHandler.setTitle(this.getString(R.string.share_title));

        wxHandler.addToSocialSDK();
        // 支持微信朋友圈
        UMWXHandler wxCircleHandler = new UMWXHandler(getActivity(), appID,
                appSecret);
        wxCircleHandler.setTargetUrl(this
                .getString(R.string.share_download_url));
        wxCircleHandler.setTitle(this.getString(R.string.share_title));
        wxCircleHandler.setToCircle(true);
        wxCircleHandler.addToSocialSDK();

        userinfo = (LinearLayout) root.findViewById(R.id.userinfo);
        loginllt = (LinearLayout) root.findViewById(R.id.loginllt);
        mine_icon = (ImageView) root.findViewById(R.id.mine_icon);
        mine_name = (TextView) root.findViewById(R.id.mine_name);

        return root;
    }

    public static WodeFragment getFragment() {
        return new WodeFragment();
    }

    public void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(glApplication.getMwzuser().truename)) {
            userinfo.setVisibility(View.GONE);
            loginllt.setVisibility(View.VISIBLE);
        } else {
            userinfo.setVisibility(View.VISIBLE);
            loginllt.setVisibility(View.GONE);
            mine_name.setText(glApplication.getMwzuser().truename);
        }
        if (!TextUtils.isEmpty(glApplication.getMwzuser().avatar)) {
            ImageLoader.getInstance().displayImage(
                    glApplication.getMwzuser().avatar, mine_icon);
        }
    }

    @Override
    public void onClick(View v) {
        Intent it = new Intent(getActivity(), WebViewer.class);
        Bundle bundle=new Bundle();
        switch (v.getId()) {
            case R.id.login: {
                getActivity().startActivity(new Intent(getActivity(), Yhlogin.class));
                break;
            }
            case R.id.rlt_share:
                mController.registerListener(mSnsPostListener);
                mController.openShare(getActivity(), false);
                break;
            case R.id.mineguanyuwomen:
                bundle.putString(this.getString(R.string.webview_key),this.getString(R.string.html_about));
                it.putExtras(bundle);
                startActivity(it);
                break;
            case R.id.rlt_help:
                bundle.putString(this.getString(R.string.webview_key),this.getString(R.string.html_help));
                it.putExtras(bundle);
                startActivity(it);
                break;
            case R.id.rlt_fuwu:
                bundle.putString(this.getString(R.string.webview_key),this.getString(R.string.html_service));
                it.putExtras(bundle);
                startActivity(it);
                break;
            case R.id.rlt_mycollect:
                bundle.putInt("typeid",0);
                Intent itMycollect=new Intent(getActivity(), MyCollectActivity.class);
                itMycollect.putExtras(bundle);
                startActivity(itMycollect);
                break;
            case R.id.minemyshop:
                if (TextUtils.isEmpty(glApplication.getMwzuser().truename)) {
                    getActivity().startActivity(new Intent(getActivity(), Yhlogin.class));
                } else
                    getActivity().startActivity(new Intent(getActivity(), YhWodeDianpu.class));
                break;
            case R.id.minewoderenzheng:
                Intent itMyAuth=new Intent(getActivity(),YhRenzheng.class);
                startActivity(itMyAuth);
                break;
        }
    }
}