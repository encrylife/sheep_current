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
 * Created by shanksYao on 3/2/15.
 */
public class CaiGouFragment extends BaseFragment implements View.OnClickListener{

    private FragmentTabHelper<Fragment> tabHelper;

    private TabSelect tabSelect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_cai_gou,container,false);
        tabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(), R.id.fragment_cai_gou_container);
        tabHelper.put(R.id.tab1_CaiGouFragment, CaiGouUnitFragment.getCaiGouFragment(CaiGouRequest.ALL));
        tabHelper.put(R.id.tab2_CaiGouFragment, CaiGouUnitFragment.getCaiGouFragment(CaiGouRequest.VIP));
        tabSelect=new TabSelect();
        tabSelect.add(root.findViewById(R.id.tab1_CaiGouFragment));
        tabSelect.add(root.findViewById(R.id.tab2_CaiGouFragment));
        tabSelect.setOnClickListener(this);
        showTab(R.id.tab1_CaiGouFragment);
        return root;
    }

    @Override
    public void onClick(View v) {
            showTab(v.getId());
    }

    private void showTab(int id){
        tabHelper.showFragment(id);
        tabSelect.selectTab(id);
    }

}
