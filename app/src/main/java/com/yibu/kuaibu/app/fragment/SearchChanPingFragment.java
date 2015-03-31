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

/**
 * Created by shanksYao on 3/10/15.
 */
public class SearchChanPingFragment extends BaseFragment implements View.OnClickListener{


    private FragmentTabHelper<Fragment> tabHelper;

    private TabSelect tabSelect;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_search_chan_ping,container,false);
        tabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(), R.id.fragment_chang_ping_container);
        tabHelper.put(R.id.tab1_cp, ChanPingFragment.getFragment(0));
        tabHelper.put(R.id.tab2_cp, ChanPingFragment.getFragment(1));
        tabSelect=new TabSelect();
        tabSelect.add(root.findViewById(R.id.tab1_cp));
        tabSelect.add(root.findViewById(R.id.tab2_cp));
        tabSelect.setOnClickListener(this);
        showTab(R.id.tab1_cp);
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
