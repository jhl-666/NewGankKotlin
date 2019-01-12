package com.ljhdemo.newgank.common.image;

import android.widget.ImageView;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.ljhdemo.newgank.common.GlideApp;
import com.ljhdemo.newgank.common.R;
import com.ljhdemo.newgank.common.base.BaseApplication;
import com.ljhdemo.newgank.common.utils.GlideCircleEdgeColorTransform;

import java.io.File;


/**
 * Created by ljh on 2018/6/4.
 */

public class ImageLoader {

    private static final int crossFadeTime = 800;//渐显效果时长

    /**
     * 默认加载
     */
    public static void loadImageView(String path, ImageView mImageView) {
        GlideApp.with(BaseApplication.getContext()).load(path)
                .into(mImageView);
    }

    public static void loadImageView(File path, ImageView mImageView) {
        GlideApp.with(BaseApplication.getContext()).load(path)
                .into(mImageView);
    }

    /**
     * CenterCrop
     */
    public static void loadImageViewCenterCrop(Object path, ImageView mImageView) {
        if (path instanceof String || path instanceof Integer || path instanceof File) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .centerCrop()
                   /* .transition(new DrawableTransitionOptions().crossFade(crossFadeTime))//渐显效果*/
                    .into(mImageView);
        }

    }

    /**
     * Circle
     */
    public static void loadCircleImageView(Object path, ImageView mImageView, int crossFade) {
        if (path instanceof String || path instanceof Integer) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .placeholder(R.drawable.lib_common_main_place_holder)
                    .transform(new GlideCircleTransform(BaseApplication.getContext()))
                    .into(mImageView);
        }
    }

    /**
     * Circle
     */
    public static void loadCircleImageView(Object path, ImageView mImageView) {
        if (path instanceof String || path instanceof Integer || path instanceof File) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .placeholder(R.drawable.lib_common_main_place_holder)
                    .transform(new GlideCircleTransform(BaseApplication.getContext()))
                    .into(mImageView);
        }
    }

    /**
     * Circle
     */
    public static void loadCircleEdgeColorImageView(Object path, ImageView mImageView, int borderWidth, int color) {
        if (path instanceof String || path instanceof Integer) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                    .skipMemoryCache(true)
                    .placeholder(R.drawable.lib_common_main_place_holder)
                    .transform(new GlideCircleEdgeColorTransform(BaseApplication.getContext(), borderWidth, color))
                    .into(mImageView);
        }
    }

    /**
     * Round
     */
    public static void loadRoundImageView(Object path, ImageView mImageView) {
        if (path instanceof String || path instanceof Integer) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .transform(new GlideRoundTransform(BaseApplication.getContext()))
                    .into(mImageView);
        }
    }

    /**
     * Round
     */
    public static void loadRoundImageView(Object path, ImageView mImageView, boolean isCrossFade) {
        if (path instanceof String || path instanceof Integer) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .transform(new GlideRoundTransform(BaseApplication.getContext()))
                    .into(mImageView);
        }
    }

    /**
     * Round
     */
    public static void loadRoundImageView(Object path, ImageView mImageView, int radius) {
        if (path instanceof String || path instanceof Integer) {
            GlideApp.with(BaseApplication.getContext())
                    .load(path)
                    .transform(new GlideRoundTransform(BaseApplication.getContext(), radius))
                    .into(mImageView);
        }
    }

    /**
     * 先加载小图
     */
    public static void loadImageViewThumbnail(String path, float thumbnail, ImageView mImageView) {
        GlideApp.with(BaseApplication.getContext())
                .load(path)
                .thumbnail(thumbnail)
                .centerCrop()
                .into(mImageView);
    }
}
