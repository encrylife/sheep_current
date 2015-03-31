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
 * Created by shanksYao on 3/1/15.
 *
 * 首页--搜索
 */
public class SouSuoFragment extends Fragment implements View.OnClickListener{

    private TabSelect tabSelect;
    private final static String TAB="tab";

    private FragmentTabHelper<Fragment> tabHelper;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.frame_search1,container,false);
        tabSelect=new TabSelect();
        tabSelect.add(root.findViewById(R.id.search_caigou)).add(root.findViewById(R.id.search_supply))
                .add(root.findViewById(R.id.search_store)).add(root.findViewById(R.id.search_product));
        tabSelect.setOnClickListener(this);
        int tabId=getArguments().getInt("TAB",R.id.search_caigou);

        tabHelper=new FragmentTabHelper<Fragment>(getFragmentManager(),R.id.search_tab_container);
        tabHelper.put(R.id.search_caigou,new SearchCaiGouFragment());
        tabHelper.put(R.id.search_supply,new SearchGongYingFragment());
        tabHelper.put(R.id.search_store,new SearchDianPuFragment());
        tabHelper.put(R.id.search_product,new SearchChanPingFragment());
        tabSelect.setFragmentTabHelper(tabHelper);

        tabSelect.selectTab(tabId);
        return root;
    }

    public static SouSuoFragment getFragment(int id){
        SouSuoFragment fragment=new SouSuoFragment();
        Bundle bundle=new Bundle();
        bundle.putInt(TAB,id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onClick(View v) {
        int id=v.getId();
        switch (id){
            case R.id.search_caigou:
                tabSelect.selectTab(id);
                break;
            case R.id.search_supply:
                tabSelect.selectTab(id);
                break;
            case R.id.search_store:
                tabSelect.selectTab(id);
                break;
            case R.id.search_product:
                tabSelect.selectTab(id);
                break;
        }
    }
}
