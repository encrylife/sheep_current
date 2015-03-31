package com.yibu.kuaibu.app.adaptor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.app.glApplication;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.model.gongying.CaiGouRsDo;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by shanksYao on 2/24/15.
 */
public class CaiGouAdaptor extends BaseAdapter {


    private List<CaiGouRsDo> list=new ArrayList<CaiGouRsDo>();

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CaiGouRsDo getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addAll(CaiGouRsDo[] rsDos){
        list.addAll(Arrays.asList(rsDos) );
        notifyDataSetChanged();
    }

    public void add(CaiGouRsDo rsDo){
        list.add(rsDo);
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(glApplication.getAppContext()).inflate(R.layout.item_cai_gou,parent,false);
            ViewHolder viewHolder=new ViewHolder();
            renderById(viewHolder,convertView);
            convertView.setTag(viewHolder);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        ViewHolder viewHolder1= (ViewHolder) v.getTag();
                         ///goto detail TODO
                        String id=viewHolder1.itemId;
                }
            });
        }
        CaiGouRsDo rsDo=list.get(position);
        ViewHolder viewHolder= (ViewHolder) convertView.getTag();

        NetImageHelper.setImageUrl(viewHolder.pic,rsDo.thumb,R.drawable.error,R.drawable.error);
        viewHolder.title.setText(rsDo.title);
        viewHolder.cycleDay.setText("供应周期："+rsDo.today+"天");
         viewHolder.amount.setText("采购数量："+rsDo.amount+rsDo.unit);

        viewHolder.state.setText("状态："+rsDo.typename);
        viewHolder.date.setText(rsDo.editdate);

        viewHolder.itemId=rsDo.itemid;
        return convertView;
    }
    class ViewHolder{
        NetworkImageView pic;
        TextView title;
        TextView cycleDay;
        TextView amount;
        TextView state;
        TextView date;
        String itemId;
    }
    public static void renderById(Object holder,View view){
        Field[] fields=holder.getClass().getDeclaredFields();
        for(Field field:fields){
            if(View.class.isAssignableFrom(field.getType())){
                try {
                    field.set(holder,view.findViewById(R.id.class.getField(field.getName()).getInt(null)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
