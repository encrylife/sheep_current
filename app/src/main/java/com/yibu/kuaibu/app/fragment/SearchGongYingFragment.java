package com.yibu.kuaibu.app.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.tab.FragmentTabHelper;
import com.yibu.kuaibu.app.tab.TabSelect;
import com.yibu.kuaibu.net.model.gongying.CaiGouRequest;

/**
 * Created by shanksYao on 3/4/15.
 */
public class SearchGongYingFragment extends BaseFragment implements View.OnClickListener{
    private FragmentTabHelper<Fragment> tabHelper;

    private TabSelect tabSelect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_search_gong_ying,container,false);
        tabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(),R.id.container_sgy);
        tabSelect=new TabSelect();
        tabHelper.put(R.id.tab1_gy,GongYingFragment.getGYFragment(CaiGouRequest.ALL));
        tabHelper.put(R.id.tab2_gy,GongYingFragment.getGYFragment(CaiGouRequest.VIP));
        tabSelect.add(root.findViewById(R.id.tab1_gy)).add(root.findViewById(R.id.tab2_gy));
        tabSelect.setFragmentTabHelper(tabHelper);
        tabSelect.setOnClickListener(this);
        tabSelect.selectTab(R.id.tab1_gy);
        return root;
    }

    @Override
    public void onClick(View v) {
        tabSelect.selectTab(v.getId());
    }
}
