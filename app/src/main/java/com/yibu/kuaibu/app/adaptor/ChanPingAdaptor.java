package com.yibu.kuaibu.app.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.model.chanping.ChanPingItemDo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shanksYao on 3/11/15.
 */
public class ChanPingAdaptor extends BaseAdapter {



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public ChanPingItemDo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private List<ChanPingItemDo> list=new ArrayList<ChanPingItemDo>();

    public void addAll(ChanPingItemDo[] itemDos){
        if(itemDos==null)
            return;
        for(ChanPingItemDo chanPingItemDo:itemDos){
            list.add(chanPingItemDo);
        }
    }




    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chan_ping,parent,false);
            ViewHolder holder=new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        ViewHolder holder= (ViewHolder) convertView.getTag();
        ChanPingItemDo itemDo=getItem(position);
        holder.render(itemDo);
        return convertView;
    }

    class ViewHolder{
        public TextView title;
        public TextView price;
        public TextView date;
        public NetworkImageView pic;
        ViewHolder(View view){
           title= (TextView) view.findViewById(R.id.title);
           price= (TextView) view.findViewById(R.id.price);
           date= (TextView) view.findViewById(R.id.date);
           pic= (NetworkImageView) view.findViewById(R.id.pic);
        }

        void render(ChanPingItemDo itemDo){
            title.setText(itemDo.title);
            price.setText(itemDo.price);
            date.setText(itemDo.editdate);
            NetImageHelper.setImageUrl(pic,itemDo.thumb,R.drawable.error,R.drawable.error);
        }
    }
}
