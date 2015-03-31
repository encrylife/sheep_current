/**
 * 
 */
package com.yibu.kuaibu.app.adaptor;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.toolbox.NetworkImageView;
import com.yibu.kuaibu.R;
import com.yibu.kuaibu.net.helper.NetImageHelper;
import com.yibu.kuaibu.net.model.dianpu.DianPuItemDo;

import java.util.ArrayList;
import java.util.List;


public class CompanyListAdapter extends BaseAdapter
{
	private Context context;
	private List<DianPuItemDo> list=new ArrayList<DianPuItemDo>();
	
	public void addAll(DianPuItemDo[] dianPuItemDos){
        if(dianPuItemDos==null||dianPuItemDos.length==0)
            return;
        for(DianPuItemDo dianPuItemDo:dianPuItemDos)
        list.add(dianPuItemDo);
        notifyDataSetChanged();
    }

	public CompanyListAdapter(Context context)
	{
		this.context = context;
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder holder = null;
		if(convertView == null){
			holder = new ViewHolder();
			convertView = View.inflate(context, R.layout.company_list_item, null);
			holder.image = (NetworkImageView) convertView.findViewById(R.id.image);
			holder.vip_mark = (ImageView) convertView.findViewById(R.id.vip_mark);
		    holder.title = (TextView) convertView.findViewById(R.id.title);
		    holder.author = (TextView) convertView.findViewById(R.id.author);
		    holder.state_tv = (TextView) convertView.findViewById(R.id.state_tv);
		    holder.true_tv = (TextView) convertView.findViewById(R.id.true_tv);
		    
		    convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
        DianPuItemDo item = list.get(position);
        holder.state_tv.setText("服务态度："+item.star1);
        holder.true_tv.setText("描述相符："+item.star2);
		 if(item.groupid > 6){
			 holder.vip_mark.setImageResource(R.drawable.shangc);
             holder.state_tv.setVisibility(View.VISIBLE);
             holder.true_tv.setVisibility(View.VISIBLE);
		 }else {
			 holder.vip_mark.setImageResource(R.drawable.dianpu);
             holder.state_tv.setVisibility(View.INVISIBLE);
             holder.true_tv.setVisibility(View.INVISIBLE);
		 }
		 holder.title.setText(item.company);
		 holder.author.setText("掌柜："+item.truename);

         NetImageHelper.setImageUrl(holder.image, item.avatar, R.drawable.error, R.drawable.error);

        return convertView;
	}
	
	private static class ViewHolder{
		NetworkImageView image;
		ImageView vip_mark;
		TextView title;
		TextView author;
		TextView state_tv;
		TextView true_tv;
	}
}
