package com.ljhdemo.newgank.common.base;


/**
 * Created by ljh on 2017/12/13.
 */

public abstract class LazyLoadFragment extends BaseFragment {
    /**
     * 是否加载过数据
     */
    protected boolean isLoad = false;

    /**
     * 视图是否已经初始化
     */
    protected boolean isInit = false;

    /**
     * 视图是否已经对用户可见
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    @Override
    protected void initData() {
        isInit = true;//已经调用过initView，视图已经初始化
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit || isLoad) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    @Override
    public void onDestroy() {
        isInit = false;
        isLoad = false;
        super.onDestroy();
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected void stopLoad() {
    }
}
