package com.yibu.kuaibu.app.adaptor;

import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.model.gongying.RsDo;
import com.yibu.kuaibu.net.model.mycollect.MyCollectFriedDo;
import com.yibu.kuaibu.net.model.mycollect.MyCollectFriend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zys on 2015/3/25.
 */
public class MyCollectFriendAdaptor extends BaseAdapter {

    private List<MyCollectFriend> list=new ArrayList<MyCollectFriend>();
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            Log.i("list---",list.get(0).truename);
            convertView= LayoutInflater.from(glApplication.getAppContext()).inflate(R.layout.item_collect_friend,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            viewHolder.pic= (NetworkImageView) convertView.findViewById(R.id.pic);
            viewHolder.title= (TextView) convertView.findViewById(R.id.title);
            viewHolder.truename= (TextView) convertView.findViewById(R.id.true_name);

            viewHolder.mall= (TextView) convertView.findViewById(R.id.title_mall);

            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewHolder viewHolder1= (ViewHolder) v.getTag();
                    ///goto detail TODO
                    String id=viewHolder1.itemId;
                    Log.i("index", "--------" + id + "");
                }
            });
        }
        MyCollectFriend collectDo=list.get(position);
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();

        NetImageHelper.setImageUrl(viewHolder.pic, collectDo.avatar, R.drawable.error, R.drawable.error);
        viewHolder.title.setText(collectDo.company);
        viewHolder.truename.setText(collectDo.truename);
        viewHolder.mall.setVisibility(View.VISIBLE);
        viewHolder.mall.setTextColor(Color.WHITE);
        if(collectDo.groupid>6) {

            viewHolder.mall.setBackgroundColor(Color.RED);
            viewHolder.mall.setText("商城");
        }
        else
        {
            viewHolder.mall.setBackgroundColor(Color.GRAY);
            viewHolder.mall.setText("店鋪");
        }



        viewHolder.itemId=collectDo.userid+"";
        return convertView;
    }
    public void addAll(MyCollectFriend[] myCollectFriends){
        list.addAll(Arrays.asList(myCollectFriends) );
        Log.i("listSize",list.size()+"");
        notifyDataSetChanged();
    }
    public void add(MyCollectFriend myCollectFried){
        list.add(myCollectFried);
        notifyDataSetChanged();
    }
    class ViewHolder{
        NetworkImageView pic;
        TextView title;
        TextView truename;
        TextView userid;
        TextView mall;

        String itemId;
    }
}
