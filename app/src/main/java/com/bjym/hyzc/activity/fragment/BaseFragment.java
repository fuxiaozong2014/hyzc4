package com.bjym.hyzc.activity.fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.bjym.hyzc.R;

/**
 * @author Wisn
 * @time 2016/6/25 18:36
 *
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    public Context context;
    private View view;
    private Dialog dialog;

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
     * 取消进度栏
     */
    public  void dismiss() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    /**
     * 进度等待
     * @param message
     */
    public  void showDialogProgress(String message) {
        if(context!=null){
            dialog = new Dialog(context, R.style.MyDialogStyle);
            dialog.setContentView(R.layout.dialog_progress);// 为对话框设置自定义布局
            Window window = dialog.getWindow();
            WindowManager.LayoutParams params = window.getAttributes();
            params.dimAmount = 0f;
            window.setAttributes(params);
            dialog.setCanceledOnTouchOutside(true);
            TextView messageTv = (TextView) dialog
                    .findViewById(R.id.dialog_message);
            if (message != null) {
                messageTv.setText(message);
            }
            dialog.show();
        }else{
            dialog=null;
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
