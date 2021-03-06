package com.balbadak.nexquickpro;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;


import com.balbadak.nexquickpro.vo.ListViewItem;
import com.balbadak.nexquickpro.vo.OnDelivery;


import java.util.ArrayList;


@SuppressLint("ValidFragment")
public class fragment_order_list extends Fragment {

    ViewPager viewPager;
    ListView quickListView;
    TextView quickListTitle;
    Context context;

    ArrayList<ListViewItem> quickList;
    ArrayList<OnDelivery> list;
    SharedPreferences loginInfo;
    int orderNum;
    int callNum;
    int qpId;
    OnDelivery orderDetail;

    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_order_list, container, false);

        viewPager = getActivity().findViewById(R.id.pager);

        context = getActivity();

        quickListTitle = (TextView) view.findViewById(R.id.insu_title);
        quickListView = (ListView) view.findViewById(R.id.insu_listview);

        quickList = getArguments().getParcelableArrayList("quickList");
        list = getArguments().getParcelableArrayList("list");


        loginInfo = getActivity().getSharedPreferences("setting", 0);

        if (loginInfo != null) {
            qpId = loginInfo.getInt("qpId", 0);

        }

        return view;

    }


    private class CustomAdapter extends ArrayAdapter<ListViewItem> {
        private ArrayList<ListViewItem> data;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<ListViewItem> object1) {
            super(context, textViewResourceId, object1);
            this.data = object1;
//            this.items = object2;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.order_list_item, null);
            }

            TextView titleStrView = (TextView) v.findViewById(R.id.order_list_item_date);
            TextView descStrView = (TextView) v.findViewById(R.id.order_list_item_detail);
            Button detailBtn = (Button) v.findViewById(R.id.detailBtn);


            orderDetail = list.get(position);
            if(data.get(position).getQuickType() == 1) {
                titleStrView.setText(data.get(position).getTitleStr());
                titleStrView.setTextColor(getResources().getColor(R.color.colorGold));

                detailBtn.setOnClickListener(new View.OnClickListener() {
                    OnDelivery orderInfo = orderDetail;

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogDetailActivity.class);
                        intent.putExtra("callNum", orderInfo.getCallNum());
                        intent.putExtra("num", orderInfo.getCallNum());
                        intent.putExtra("name", orderInfo.getSenderName());
                        intent.putExtra("phone", orderInfo.getSenderPhone());
                        intent.putExtra("address", orderInfo.getSenderAddress()+" "+orderInfo.getSenderAddressDetail());
                        intent.putExtra("freights", orderInfo.getFreightList());
                        intent.putExtra("orderPrice", orderInfo.getOrderPrice());
                        intent.putExtra("memo", orderInfo.getMemo());
                        intent.putExtra("deliveryStatus", orderInfo.getDeliveryStatus());

                        startActivity(intent);
                    }
                });

            } else if(data.get(position).getQuickType() == 2) {
                titleStrView.setText(data.get(position).getTitleStr());
                titleStrView.setTextColor(getResources().getColor(R.color.colorEmerald));

                detailBtn.setOnClickListener(new View.OnClickListener() {
                    OnDelivery orderInfo = orderDetail;
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DialogDetailActivity.class);
                        intent.putExtra("callNum", orderInfo.getCallNum());
                        intent.putExtra("num", orderInfo.getOrderNum());
                        intent.putExtra("name", orderInfo.getReceiverName());
                        intent.putExtra("phone", orderInfo.getReceiverPhone());
                        intent.putExtra("address", orderInfo.getReceiverAddress()+" "+orderInfo.getReceiverAddressDetail());
                        intent.putExtra("freights", orderInfo.getFreightList());
                        intent.putExtra("orderPrice", orderInfo.getOrderPrice());
                        intent.putExtra("memo", orderInfo.getMemo());
                        intent.putExtra("deliveryStatus", orderInfo.getDeliveryStatus());
                        startActivity(intent);
                    }
                });



            }

            if(data.get(position).getUrgentStr()!=null) {
                //UrgentStrView.setText("급");
                titleStrView.setTextColor(getResources().getColor(R.color.colorTomato));
            }

            descStrView.setText(data.get(position).getDescStr());
            orderNum = data.get(position).getOrderNum();
            return v;
        }


    }


    @Override
    public void onStart() {
        super.onStart();

        if(quickList != null && quickList.size() != 0) {
            quickListTitle.setText("배송 진행 목록");
            CustomAdapter insuAdapter = new CustomAdapter(getActivity(), 0, quickList);
            quickListView.setAdapter(insuAdapter);
        } else {
            quickListTitle.setText("현재 받은 퀵이 없습니다");
        }
    }


}
