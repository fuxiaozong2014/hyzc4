package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bjym.hyzc.R;



/**
 * Created by fushaoqing on 2016/10/10.
 */

public class NursingContentSunStageActivity extends BaseActivity {
    private ListView lv;
    private Button bt_titlebar_right;
    private Button bt_titlebar_left;
    private TextView tv_titlebar_center;
    private LinearLayout ll_parent;

    class MyOnItemClickListener implements AdapterView.OnItemClickListener{


        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //点击条目跳转到NurseContent界面，传递此阶段的未执行和已执行的护理内容集合过去

        }
    }

    @Override
    public View setMainView() {
        View view=View.inflate(context, R.layout.activity_nursingcontentparentstage,null);
        lv =  (ListView) view.findViewById(R.id.lv);
        bt_titlebar_left = (Button) view.findViewById(R.id.bt_titlebar_left);
        bt_titlebar_right = (Button) view.findViewById(R.id.bt_titlebar_right);
        tv_titlebar_center = (TextView) view.findViewById(R.id.tv_titlebar_center);

        return view;
    }

    @Override
    public void InitData() {

        //三级阶段需要有点击事件，点击过后跳转到NurseCintent界面，

        lv.setOnItemClickListener(new MyOnItemClickListener());

    }
}
