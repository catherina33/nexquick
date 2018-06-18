package com.balbadak.nexquickapplication;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.annotation.SuppressLint;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.balbadak.nexquickapplication.vo.ListViewItem;
import com.balbadak.nexquickapplication.vo.OnDelivery;
import com.balbadak.nexquickapplication.vo.OrderInfo;
import com.tsengvn.typekit.TypekitContextWrapper;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Order3Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private String mainUrl;

    private Context context = this;
    private SharedPreferences loginInfo;

    private String payUrl;

    private int totalPrice;
    private int callNum;
    private ContentValues values;
    private ListView orderListview;

    OnDelivery orderDetail;
    ArrayList<ListViewItem> dateList;
    ArrayList<OnDelivery> list;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(TypekitContextWrapper.wrap(newBase));
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainUrl = getResources().getString(R.string.main_url);
        setContentView(R.layout.activity_neworder3);
        loginInfo = getSharedPreferences("setting", 0);
        totalPrice = loginInfo.getInt("totalPrice", 0);
        callNum =  getIntent().getExtras().getInt("cn");
        Log.e("callNum3", callNum + "!");
        dateList = new ArrayList<>();
        list = new ArrayList<>();

        String url = mainUrl + "appCall/getOrderListLast.do";
        values = new ContentValues();
        values.put("callNum", callNum);
        GetListTask getListTask = new GetListTask(url, values);
        getListTask.execute();

        orderListview = findViewById(R.id.order_listview);
        TextView tvTotalPrice = (TextView) findViewById(R.id.totalPrice);
        Button prevBtn = (Button) findViewById(R.id.prev2p);

        tvTotalPrice.setText(totalPrice +"원");

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        payUrl = mainUrl + "appCall/registCall.do";

        Button payAppCard = (Button) findViewById(R.id.payAppCard);
        Button payAppDeposit = (Button) findViewById(R.id.payAppDeposit);
        Button paySenderCard = (Button) findViewById(R.id.paySenderCard);
        Button paySenderMoney = (Button) findViewById(R.id.paySenderMoney);
        Button payReceiverCard = (Button) findViewById(R.id.payReceiverCard);
        Button payReceiverMoney = (Button) findViewById(R.id.payReceiverMoney);
        Button payCredit = (Button) findViewById(R.id.payCredit);

        payAppCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DialogPayActivity.class);
                startActivityForResult(intent, 2000);
            }
        });

        payAppDeposit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DialogPayActivity.class);
                startActivityForResult(intent, 2020);
            }
        });

        paySenderCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put("payType", 2);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                values.put("callNum", callNum);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        paySenderMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put("payType", 3);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                values.put("callNum", callNum);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payReceiverCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put("payType", 4);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                values.put("callNum", callNum);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payReceiverMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put("payType", 5);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                values.put("callNum", callNum);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });

        payCredit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                values.put("payType", 6);
                values.put("payStatus", 0);
                values.put("totalPrice", totalPrice);
                values.put("callNum", callNum);
                MainTask mainTask = new MainTask(payUrl, values);
                mainTask.execute();
                Intent intent = new Intent(context, OrderCompleteActivity.class);
                startActivity(intent);
            }
        });


        // 내비게이션 서랍을 위한 툴바
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 내비게이션 서랍 관련 설정
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        switch (requestCode) {

            case 2000:
                if (resultCode == RESULT_OK) {
                    values.put("deliveryStatus", 1);
                    values.put("payType", 0);
                    values.put("payStatus", 1);
                    values.put("totalPrice", totalPrice);
                    Intent i = new Intent(context, OrderCompleteActivity.class);
                    startActivity(i);
                }
                break;
            case 2020 :
                if (resultCode == RESULT_OK) {
                    values.put("deliveryStatus", 1);
                    values.put("payType", 1);
                    values.put("payStatus", 1); // 일단 실시간계좌이체라고 설정
                    values.put("totalPrice", totalPrice);
                    Intent i = new Intent(context, OrderCompleteActivity.class);
                    startActivity(i);
                }
                break;
        }
    }


    public void setDesc () {


    }

    private class CustomAdapter extends ArrayAdapter<ListViewItem> {
        private ArrayList<ListViewItem> data;
        private ArrayList<String> items;

        public CustomAdapter(Context context, int textViewResourceId, ArrayList<ListViewItem> object) {
            super(context, textViewResourceId, object);
            this.data = object;
        }

        public View getView(final int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if (v == null) {
                LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = vi.inflate(R.layout.order_list_item, null);
            }

            TextView titleStrView = (TextView) v.findViewById(R.id.order_list_item_date);
            TextView descStrView = (TextView) v.findViewById(R.id.order_list_item_detail);
            Button detailBtn = (Button) v.findViewById(R.id.detailBtn);

            orderDetail = list.get(position);
            SpannableStringBuilder ssb = new SpannableStringBuilder();
            switch(orderDetail.getDeliveryStatus()){
                case -1:
                    ssb.append("배차실패   ").setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorTomato)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
                case 1:
                    ssb.append("배차 중     ");
                    break;
                case 2:
                    ssb.append("배차완료   ");
                    break;
                case 3:
                    ssb.append("배송 중     ").setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorEmerald)), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    break;
            }
            ssb.append(data.get(position).getTitleStr());
            if (orderDetail.getUrgent() == 1) {
                ssb.append("   급송");
            }

            titleStrView.setText(ssb);
            descStrView.setText(data.get(position).getDescStr());


            detailBtn.setOnClickListener(new View.OnClickListener() {
                OnDelivery orderInfo = orderDetail;
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, DialogDetailActivity.class);
                    intent.putExtra("orderNum", orderInfo.getOrderNum());
                    intent.putExtra("callNum", orderInfo.getCallNum());
                    intent.putExtra("receiverName", orderInfo.getReceiverName());
                    intent.putExtra("receiverPhone", orderInfo.getReceiverPhone());
                    intent.putExtra("receiverAddress", orderInfo.getReceiverAddress()+" "+orderInfo.getReceiverAddressDetail());
                    intent.putExtra("freights", orderInfo.getFreightList());
                    intent.putExtra("orderPrice", orderInfo.getOrderPrice());
                    intent.putExtra("memo", orderInfo.getMemo());
                    intent.putExtra("deliveryStatus", orderInfo.getDeliveryStatus());
                    startActivity(intent);
                }
            });

            return v;
        }


    }


    // 여기부터 AsyncTask 영역
    public class GetListTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public GetListTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            StringBuilder titleSb = new StringBuilder();
            StringBuilder descSb = new StringBuilder();
            super.onPostExecute(s);

            if (s != null && s.toString().trim().length()!= 0) {
                Log.e("받아온 것", s);
                try {
                    JSONArray ja = new JSONArray(s);
                    JSONObject data;
                    OnDelivery order;
                    ListViewItem item;
                    for (int i = 0; i < ja.length(); i++) {
                        data = ja.getJSONObject(i);

                        item = new ListViewItem();
                        order = new OnDelivery();
                        titleSb.setLength(0);
                        descSb.setLength(0);

                        order.setOrderNum(data.getInt("orderNum"));
                        order.setCallNum(data.getInt("callNum"));
                        order.setReceiverName(data.getString("receiverName"));
                        order.setReceiverPhone(data.getString("receiverPhone"));
                        order.setReceiverAddress(data.getString("receiverAddress"));
                        order.setReceiverAddressDetail(data.getString("receiverAddressDetail"));
                        order.setOrderPrice(data.getInt("orderPrice"));
                        order.setMemo(data.getString("memo"));
                        order.setFreightList(data.getString("freightList"));
                        titleSb.append("#" + order.getOrderNum());
                        descSb.append("   수령인   ");
                        descSb.append(order.getReceiverName()).append("\n");
                        descSb.append("   수령지   ");
                        descSb.append(order.getReceiverAddress());

                        item.setTitleStr(titleSb.toString());
                        item.setDescStr(descSb.toString());
                        item.setCallNum(order.getOrderNum());

                        dateList.add(item);
                        list.add(order);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                if (dateList.size() != 0) {
                    CustomAdapter adapter = new CustomAdapter(context, 0, dateList);
                    orderListview.setAdapter(adapter);
                } else {


                }
            } else {



            }
        }
    }
    //새로운 콜을 보내는 태스크
    public class MainTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public MainTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.
            return result;
        }

        @Override
        protected void onPostExecute(String s) {

            SharedPreferences.Editor editor= loginInfo.edit();
            editor.putInt("totalPrice", 0);
        }
    }


    //------------------------------여기부터 내비 영역 -----------------------------
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_order) {
            Intent intent = new Intent(getApplicationContext(), Order1Activity.class);
            startActivity(intent);
        } else if (id == R.id.nav_order_list) {
            Intent intent = new Intent(getApplicationContext(), OrderListActivity.class);
            startActivity(intent);
        } else if(id == R.id.chatBot) {
            Intent intent = new Intent(getApplicationContext(), ChatBotActivity.class);
            startActivity(intent);
        } else if(id == R.id.userUpdate) {
            Intent intent = new Intent(getApplicationContext(), UserInfoUpdateActivity.class);
            startActivity(intent);
        }else if(id == R.id.insuindo) {
            Intent intent = new Intent(getApplicationContext(), CSBeamActivity.class);
            startActivity(intent);
        } else if(id == R.id.logout) {
            SharedPreferences.Editor editor = getSharedPreferences("setting", 0).edit();
            editor.clear().commit();
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
