package com.ljhdemo.newgank.common.utils;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * RecyclerView设置分割线
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration {

    private int space;//分割线大小
    private int num;//每列的个数

    public SpaceItemDecoration(int space, int num) {
        this.space = space;
        this.num = num;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
      /*  //不是第一个的格子都设一个左边和底部的间距
        outRect.left = space;
        outRect.bottom = space;
        //由于每行都只有num个，所以第一个都是num的倍数，把左边距设为0
        if (parent.getChildLayoutPosition(view) % num == 0) {
            outRect.left = 0;
        }*/


        int itemPosition = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
        int childCount = parent.getAdapter().getItemCount();

        boolean isLastRow = isLastRow(parent, itemPosition, num, childCount);

        int top = 0;
        int left;
        int right;
        int bottom;

        int eachWidth = (num - 1) * space / num;

        left = itemPosition % num * (space - eachWidth);
        right = eachWidth - left;
        bottom = space;
        if (isLastRow){
            bottom = 0;
        }
        outRect.set(left, top, right, bottom);
    }
    private boolean isLastRow(RecyclerView parent, int pos, int spanCount,
                              int childCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            int lines = childCount % spanCount == 0 ? childCount / spanCount : childCount / spanCount + 1;
            return lines == pos / spanCount + 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager)
                    .getOrientation();
            // StaggeredGridLayoutManager 且纵向滚动
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount)
                    return true;
            } else {
                // 如果是最后一行，则不需要绘制底部
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}