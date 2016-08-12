package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;

import com.bjym.hyzc.R;

/**
 * Created by fushaoqing on 2016/7/6.
 */
public class PationteDetailMsgActivity extends BaseActivity {

    private ListView lv;

    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_pationtedetailmsg, null);
        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {

        lv.setAdapter(new MyAdapter());
    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view=null;
            if (convertView==null){
                view=View.inflate(context,R.layout.item_list_pationdetailmsg,null);
            }else{
                view=convertView;
            }

            return null;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }


    }
}
