package com.ljhdemo.newgank.common.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.kogitune.activity_transition.ActivityTransitionLauncher;

/**
 * Created by ljh on 2018/1/10.
 */

public class IntentUtils {

    /**
     * 打开图片浏览页面
     *
     * @param context
     * @param intent
     * @param view
     */
    public static void startImagePagerActivity(Context context, Intent intent, View view) {
        ActivityTransitionLauncher.with((Activity) context).from(view).launch(intent);
        /*//android V4包的类,用于两个activity转场时的缩放效果实现
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.getWidth() / 2, view.getHeight() / 2, 0, 0);
        try {
            ActivityCompat.startActivity(context, intent, optionsCompat.toBundle());//API16及以上
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            context.startActivity(intent);
            ((Activity) context).overridePendingTransition(R.anim.browser_enter_anim, 0);
        }*/
    }

    public static void startAppShareText(Context context, String shareTitle, String shareText) {
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain"); // 纯文本
        shareIntent.putExtra(Intent.EXTRA_TITLE, shareTitle);
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(shareIntent, "分享到"));
    }
}
