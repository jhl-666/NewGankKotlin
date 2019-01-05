package com.ljhdemo.newgank.common;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;

import java.io.InputStream;

/**
 * Created by ljh on 2018/1/16.
 */
@GlideModule
public class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .format(DecodeFormat.PREFER_ARGB_8888)
                        .placeholder(R.drawable.lib_common_shape_place_holder));
    }

    @Override
    public void registerComponents(Context context, Glide glide, Registry registry) {
        registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory());//替换为okhttp3加载
    }

    @Override
    public boolean isManifestParsingEnabled() {
        //如果你已经迁移到 Glide v4 的 AppGlideModule 和 LibraryGlideModule ，
        // 你可以完全禁用清单解析。这样可以改善 Glide 的初始启动时间，
        // 并避免尝试解析元数据时的一些潜在问题
        return false;
    }
}
