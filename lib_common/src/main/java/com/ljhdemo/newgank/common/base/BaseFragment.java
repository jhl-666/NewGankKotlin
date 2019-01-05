package com.ljhdemo.newgank.common.base;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.trello.navi2.component.support.NaviFragment;
import com.trello.rxlifecycle2.LifecycleProvider;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.navi.NaviLifecycle;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by ljh on 2017/12/13.
 */

public abstract class BaseFragment extends NaviFragment {

    protected Context mContext;
    protected View view;
    protected Unbinder unbinder;
    protected final LifecycleProvider<FragmentEvent> provider
            = NaviLifecycle.createFragmentLifecycleProvider(this);//用于解决RxJava内存泄漏
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mContext = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initVariable(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(view == null)
            view = inflater.inflate(provideLayoutId(savedInstanceState),container,false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        initData();
        return view;
    }
    protected  void initVariable(Bundle savedInstanceState){

    }

    protected abstract int provideLayoutId(Bundle savedInstanceState);

    protected abstract void initView();

    protected void initData(){

    };

    @Override
    public void onDestroyView() {
        unbinder.unbind();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
