package com.yibu.kuaibu.app.tab;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanksYao on 3/1/15.
 */
public class TabSelect {
    public List<View> list=new ArrayList<View>();
    public TabSelect add(View view){
        list.add(view);
        return this;
    }
    public void selectTab(int id){
        for(int i=0;i<list.size();i++){
            if(list.get(i).getId()==id){
                list.get(i).setSelected(true);
            }else{
                list.get(i).setSelected(false);
            }
        }
        if(fragmentTabHelper!=null)
            fragmentTabHelper.showFragment(id);
    }
    private FragmentTabHelper fragmentTabHelper;
    public void setFragmentTabHelper(FragmentTabHelper fragmentTabHelper){
        this.fragmentTabHelper=fragmentTabHelper;
    }

    public void setOnClickListener(View.OnClickListener listener){
        for(View view:list){
            view.setOnClickListener(listener);
        }
    }
}
