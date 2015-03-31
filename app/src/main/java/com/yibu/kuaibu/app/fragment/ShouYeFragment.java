package com.yibu.kuaibu.app.fragment;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.NetworkImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.YhShangCheng;
import com.yibu.kuaibu.app.activity.CaiGouActivity;
import com.yibu.kuaibu.app.activity.GongYingActivity;
import com.yibu.kuaibu.app.activity.LeiMuActivity;
import com.yibu.kuaibu.app.adaptor.BannerAdaptor;
import com.yibu.kuaibu.app.caigou.CaigouPublish;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.app.supply.SupplyPublish;
import com.yibu.kuaibu.net.helper.HttpHelper;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.model.gongying.GongYingRequest;
import com.yibu.kuaibu.net.model.shouye.IndexDo;
import com.yibu.kuaibu.net.model.shouye.IndexRequest;
import com.yibu.kuaibu.net.model.shouye.MallDo;
import com.yibu.kuaibu.net.model.shouye.SellDo;
import com.yibu.kuaibu.net.model.shouye.SlideDo;
import com.yibu.kuaibu.ui.autoscroll.CirclePageIndicator;
import com.yibu.kuaibu.ui.autoscroll.CustomViewPager;
import com.yibu.kuaibu.ui.autoscroll.CustomViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanksYao on 3/1/15.
 * <p/>
 * 首页-搜索
 */
public class ShouYeFragment extends BaseFragment implements View.OnClickListener {


    private CustomViewPager viewPager;
    private CustomViewPagerAdapter adapter;
    private CirclePageIndicator indicator;
    private BannerAdaptor bannerAdaptor;

    private ViewGroup rmContainer;
    private ViewGroup mallContainer;
    private ViewGroup sellContainer;

    private DisplayMetrics dm;
    private int picHeight;
    List<View> viewList = new ArrayList<View>();

    public ShouYeFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.frame_shouye, container, false);
        root.findViewById(R.id.leimu).setOnClickListener(this);
        root.findViewById(R.id.tuangou).setOnClickListener(this);
        root.findViewById(R.id.gongying).setOnClickListener(this);
        root.findViewById(R.id.caigou).setOnClickListener(this);
        root.findViewById(R.id.s_shangcheng).setOnClickListener(this);
        root.findViewById(R.id.caigoupublish).setOnClickListener(this);
        root.findViewById(R.id.supplypublish).setOnClickListener(this);

        viewPager = (CustomViewPager) root.findViewById(R.id.viewpager);
        indicator = (CirclePageIndicator) root.findViewById(R.id.indicator);


        bannerAdaptor = new BannerAdaptor();
        viewPager.setAdapter(bannerAdaptor);

        rmContainer = (ViewGroup) root.findViewById(R.id.re_men_biao_qian);
        mallContainer = (ViewGroup) root.findViewById(R.id.mallContainer);
        sellContainer = (ViewGroup) root.findViewById(R.id.sellContainer);
        dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        picHeight = (int) ((dm.widthPixels - 40 * dm.density) / 3);
        HttpHelper.request(new IndexRequest(), IndexDo.class, new HttpHelper.Callback<IndexDo>() {

            @Override
            public void onSuccess(IndexDo indexDo) {
                ///set banner viewPager
                String[] taglist = indexDo.taglist;
                renderView(taglist);
                renderMalls(indexDo.malllist);
                renderSells(indexDo.selllist);
                for (int i = 0; i < indexDo.slidelist.length; i++) {
                    SlideDo mSlideDo = indexDo.slidelist[i];
//                    ImageView img = new ImageView(getActivity());
//                    img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
//                    ImageLoader.getInstance().displayImage(
//                            mSlideDo.thumb, img);
                    NetworkImageView img = new NetworkImageView(getActivity());
                    img.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT));
                    NetImageHelper.setImageUrl(img, mSlideDo.thumb, R.drawable.wodedianpu_backg, R.drawable.wodedianpu_backg);
                    img.setScaleType(ImageView.ScaleType.CENTER_CROP);
                    viewList.add(img);
                }
                adapter = new CustomViewPagerAdapter(viewList);
                viewPager.setAdapter(adapter, adapter.getRealCount());
                indicator.setCircleViewPager(viewPager, adapter.getRealCount());
            }

            @Override
            public void onFailure(int errorCode, String msg) {
                Toast.makeText(getActivity(), msg, Toast.LENGTH_LONG).show();
            }
        });
        return root;
    }

    private void renderSells(SellDo[] sellDos) {
        if (sellDos == null || sellDos.length == 0)
            return;
        sellContainer.removeAllViews();
        int k = sellDos.length / 3;
        int last = sellDos.length % 3;


        for (int j = 0; j < k; j++) {
            View cells = LayoutInflater.from(getActivity()).inflate(R.layout.index_item_sell, sellContainer, false);

            View cell1 = cells.findViewById(R.id.cell1);
            View cell2 = cells.findViewById(R.id.cell2);
            View cell3 = cells.findViewById(R.id.cell3);

            getSellView(cell1, sellDos[j * 3 + 0]);
            getSellView(cell2, sellDos[j * 3 + 1]);
            getSellView(cell3, sellDos[j * 3 + 2]);
            sellContainer.addView(cells);
        }

        if (last > 0) {
            View cells = LayoutInflater.from(getActivity()).inflate(R.layout.index_item_sell, sellContainer, false);

            View cell1 = cells.findViewById(R.id.cell1);
            View cell2 = cells.findViewById(R.id.cell2);
            View cell3 = cells.findViewById(R.id.cell3);
            if (last == 1) {
                getSellView(cell1, sellDos[sellDos.length - 1]);
                cell2.setVisibility(View.INVISIBLE);
                cell3.setVisibility(View.INVISIBLE);
            } else if (last == 2) {
                getSellView(cell1, sellDos[sellDos.length - 1]);
                getSellView(cell2, sellDos[sellDos.length - 2]);
                cell3.setVisibility(View.INVISIBLE);

            }
            sellContainer.addView(cells);
        }
    }

    private void getSellView(View parent, SellDo sellDo) {

        RelativeLayout picWrap = (RelativeLayout) parent.findViewById(R.id.pic_wrap);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) picWrap.getLayoutParams();
        layoutParams.height = picHeight;
        layoutParams.width = picHeight;
        picWrap.setLayoutParams(layoutParams);

        NetworkImageView pic = (NetworkImageView) parent.findViewById(R.id.pic);
        NetImageHelper.setImageUrl(pic, sellDo.thumb, R.drawable.error, R.drawable.error);

        TextView title = (TextView) parent.findViewById(R.id.title);
        title.setText(sellDo.title);

        TextView time = (TextView) parent.findViewById(R.id.time);
        time.setText(sellDo.editdate);

        TextView eye = (TextView) parent.findViewById(R.id.eye_count);
        eye.setText(sellDo.hits + "");
    }

    private void renderMalls(MallDo[] mallDos) {
        if (mallDos == null || mallDos.length == 0)
            return;
        mallContainer.removeAllViews();
        int k = mallDos.length / 3;
        int last = mallDos.length % 3;


        for (int j = 0; j < k; j++) {
            View cells = LayoutInflater.from(getActivity()).inflate(R.layout.index_item_mall, mallContainer, false);

            View cell1 = cells.findViewById(R.id.cell1);
            View cell2 = cells.findViewById(R.id.cell2);
            View cell3 = cells.findViewById(R.id.cell3);

            getMallView(cell1, mallDos[j * 3 + 0]);
            getMallView(cell2, mallDos[j * 3 + 1]);
            getMallView(cell3, mallDos[j * 3 + 2]);
            mallContainer.addView(cells);
        }

        if (last > 0) {
            View cells = LayoutInflater.from(getActivity()).inflate(R.layout.index_item_mall, mallContainer, false);

            View cell1 = cells.findViewById(R.id.cell1);
            View cell2 = cells.findViewById(R.id.cell2);
            View cell3 = cells.findViewById(R.id.cell3);
            if (last == 1) {
                getMallView(cell1, mallDos[mallDos.length - 1]);
                cell2.setVisibility(View.INVISIBLE);
                cell3.setVisibility(View.INVISIBLE);
            } else if (last == 2) {
                getMallView(cell1, mallDos[mallDos.length - 1]);
                getMallView(cell2, mallDos[mallDos.length - 2]);
                cell3.setVisibility(View.INVISIBLE);

            }
            mallContainer.addView(cells);
        }

    }

    private void getMallView(View parent, MallDo mallDo) {
        NetworkImageView pic = (NetworkImageView) parent.findViewById(R.id.pic);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) pic.getLayoutParams();
        layoutParams.height = picHeight;
        layoutParams.width = picHeight;
        pic.setLayoutParams(layoutParams);
        NetImageHelper.setImageUrl(pic, mallDo.thumb, R.drawable.error, R.drawable.error);

        TextView title = (TextView) parent.findViewById(R.id.title);
        title.setText(mallDo.title);

        TextView price = (TextView) parent.findViewById(R.id.price);
        price.setText("￥" + mallDo.price);


    }


    private void renderView(String[] taglist) {

        if (taglist == null || taglist.length == 0)
            return;
        rmContainer.removeAllViews();

        String[] tags = taglist;

        int k = tags.length / 4;
        int last = tags.length % 4;
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tag = (String) v.getTag();
                //TODO 跳转到搜索页面
            }
        };
        for (int j = 0; j < k; j++) {
            View cell4 = LayoutInflater.from(getActivity()).inflate(R.layout.cell_item_leimu, rmContainer, false);
            TextView view1 = (TextView) cell4.findViewById(R.id.t1);
            TextView view2 = (TextView) cell4.findViewById(R.id.t2);
            TextView view3 = (TextView) cell4.findViewById(R.id.t3);
            TextView view4 = (TextView) cell4.findViewById(R.id.t4);

            view1.setText(tags[j * 4 + 0]);
            view2.setText(tags[j * 4 + 1]);
            view3.setText(tags[j * 4 + 2]);
            view4.setText(tags[j * 4 + 3]);

            view1.setTag(tags[j * 4 + 0]);
            view2.setTag(tags[j * 4 + 1]);
            view3.setTag(tags[j * 4 + 2]);
            view4.setTag(tags[j * 4 + 3]);

            rmContainer.addView(cell4);

        }
        if (last > 0) {
            View cell4 = LayoutInflater.from(getActivity()).inflate(R.layout.cell_item_leimu, rmContainer, false);
            TextView view1 = (TextView) cell4.findViewById(R.id.t1);
            TextView view2 = (TextView) cell4.findViewById(R.id.t2);
            TextView view3 = (TextView) cell4.findViewById(R.id.t3);
            TextView view4 = (TextView) cell4.findViewById(R.id.t4);
            if (last == 1) {
                view1.setText(tags[tags.length - last]);
                view1.setTag(tags[tags.length - last]);
                view2.setVisibility(View.INVISIBLE);
                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
            } else if (last == 2) {
                view1.setText(tags[tags.length - last]);
                view2.setText(tags[tags.length - (last - 1)]);

                view1.setTag(tags[tags.length - last]);
                view2.setTag(tags[tags.length - (last - 1)]);

                view3.setVisibility(View.INVISIBLE);
                view4.setVisibility(View.INVISIBLE);
            } else {
                view1.setText(tags[tags.length - last]);
                view2.setText(tags[tags.length - (last - 1)]);
                view3.setText(tags[tags.length - (last - 2)]);

                view1.setTag(tags[tags.length - last]);
                view2.setTag(tags[tags.length - (last - 1)]);
                view3.setTag(tags[tags.length - (last - 2)]);

                view4.setVisibility(View.INVISIBLE);
            }
            rmContainer.addView(cell4);
        }

        for (int z = 0; z < rmContainer.getChildCount(); z++) {
            ViewGroup cell4 = (ViewGroup) rmContainer.getChildAt(z);
            for (int x = 0; x < cell4.getChildCount(); x++) {
                cell4.getChildAt(x).setOnClickListener(onClickListener);
            }
        }

    }

    public static ShouYeFragment getFragment() {
        return new ShouYeFragment();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leimu:
                LeiMuActivity.startActivity(getActivity());
                break;
            case R.id.tuangou:
                GongYingActivity.startActivity(getActivity(), GongYingRequest.CU_XIAO);
                break;
            case R.id.gongying:
                GongYingActivity.startActivity(getActivity(), GongYingRequest.ALL);
                break;
            case R.id.caigou:
                CaiGouActivity.startActivity(getActivity());
                break;
            case R.id.s_shangcheng:
                Intent intent = new Intent(getActivity(), YhShangCheng.class);
                this.startActivity(intent);
                break;
            case R.id.caigoupublish: { // 卖布（发布采购）
                this.startActivity(new Intent(getActivity(), CaigouPublish.class));
                break;
            }
            case R.id.supplypublish: { // 找布（发布供应）
                this.startActivity(new Intent(getActivity(), SupplyPublish.class));
                break;
            }

        }
    }
}
