package com.bjym.hyzc.activity.activity;

import android.view.View;
import android.widget.ListView;

import com.bjym.hyzc.R;

/**
 * Created by fushaoqing on 2016/8/9.
 */
public class ExistingNursingContentActivity extends BaseActivity{
    private ListView lv;
    @Override
    public View setMainView() {
        View view = View.inflate(context, R.layout.activity_existingnursingcontent, null);
        lv = (ListView) view.findViewById(R.id.lv);

        return view;
    }

    @Override
    public void InitData() {


    }
}
