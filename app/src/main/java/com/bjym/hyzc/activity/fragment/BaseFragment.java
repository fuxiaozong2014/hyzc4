package com.bjym.hyzc.activity.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 *
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,Bundle savedInstanceState) {
        this.context = getActivity();
        if(view==null){
            view = setMainView();
        }
        InitData();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ViewParent parent = view.getParent();
        if(parent!=null){
            if(parent instanceof  ViewGroup){
                ((ViewGroup)parent).removeView(view);
            }
        }

    }

    /**
     * 子类实现选择监听事件
     *
     * @param v
     */
    @Override
    public void onClick(View v) {

    }


    public abstract View setMainView();

    /**
     * 加载数据
     */
    public abstract void InitData();



}
