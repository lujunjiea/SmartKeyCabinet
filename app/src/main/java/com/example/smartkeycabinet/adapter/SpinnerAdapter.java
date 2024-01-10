package com.example.smartkeycabinet.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.smartkeycabinet.R;
import com.example.smartkeycabinet.model.CarListBean;

import java.util.List;


public class SpinnerAdapter extends BaseAdapter {
    private List<CarListBean.ListBean> list;
    private Context context;

    public SpinnerAdapter(List<CarListBean.ListBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View getView(int i, View v, ViewGroup viewGroup) {
        MyHolder holder;
        View view;
        if (v == null) {
            holder = new MyHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.spinner_adapter_layout, viewGroup, false);
            holder.carNameTV = view.findViewById(R.id.tv_spinner_item_name);
            view.setTag(holder);
        } else {
            view = v;
            holder = (MyHolder) view.getTag();
        }
        holder.carNameTV.setText(list.get(i).getParkingArea() + list.get(i).getParkingNo());
        return view;
    }
    class MyHolder {
        TextView carNameTV;
    }
}
