package com.ljhdemo.newgank.common.widget.x5webview;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ljhdemo.newgank.common.R;
import com.ljhdemo.newgank.common.base.BaseActivity;
import com.ljhdemo.newgank.common.utils.IntentUtils;
import com.ljhdemo.newgank.common.utils.ToastUtils;
import com.ljhdemo.newgank.common.widget.x5webview.util.X5WebView;
import com.tencent.smtt.export.external.interfaces.IX5WebChromeClient;
import com.tencent.smtt.export.external.interfaces.JsResult;
import com.tencent.smtt.export.external.interfaces.WebResourceRequest;
import com.tencent.smtt.export.external.interfaces.WebResourceResponse;
import com.tencent.smtt.sdk.DownloadListener;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.ArrayList;


public class TencentBrowserActivity extends BaseActivity {

    public static Intent newIntent(Context context, String url, String title) {
        Intent intent = new Intent(context, TencentBrowserActivity.class);
        intent.putExtra(URL, url);
        intent.putExtra(TITLE, title);
        return intent;
    }

    public static final String TITLE = "TencentBrowserActivity.title";
    public static final String URL = "TencentBrowserActivity.url";

    private String url;
    private String title;
    private ValueCallback<Uri> uploadFile;

    private Toolbar mToolBar;
    private X5WebView mWebView;
    private ProgressBar mPageLoadingProgressBar = null;

    @Override
    protected void initVariable() {
        super.initVariable();
        url = getIntent().getStringExtra(URL);
        title = getIntent().getStringExtra(TITLE);
    }

    @Override
    protected int provideLayoutId(Bundle savedInstanceState) {
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        return R.layout.lib_common_activity_tencent_browser;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        //deleteX5();//去除qq浏览器的推广

        initProgressBar();

        mWebView = (X5WebView) findViewById(R.id.web_view);

       // initToolBar(mToolBar, title, R.drawable.tool_back_img_white);

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                return false;
            }

            @Override
            public WebResourceResponse shouldInterceptRequest(WebView view,
                                                              WebResourceRequest request) {

                return super.shouldInterceptRequest(view, request);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }
        });

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView webView, int newProgress) {
                if (newProgress == 100) {
                    mPageLoadingProgressBar.setVisibility(View.GONE);//加载完网页进度条消失
                } else {
                    mPageLoadingProgressBar.setVisibility(View.VISIBLE);//开始加载网页时显示进度条
                    mPageLoadingProgressBar.setProgress(newProgress);//设置进度值
                }

            }

            @Override
            public boolean onJsConfirm(WebView arg0, String arg1, String arg2, JsResult arg3) {
                return super.onJsConfirm(arg0, arg1, arg2, arg3);
            }

            View myVideoView;
            View myNormalView;
            IX5WebChromeClient.CustomViewCallback callback;

            ///////////////////////////////////////////////////////////
            //

            /**
             * 全屏播放配置
             */
            @Override
            public void onShowCustomView(View view, IX5WebChromeClient.CustomViewCallback customViewCallback) {
                FrameLayout normalView = (FrameLayout) findViewById(R.id.web_filechooser);
                ViewGroup viewGroup = (ViewGroup) normalView.getParent();
                viewGroup.removeView(normalView);
                viewGroup.addView(view);
                myVideoView = view;
                myNormalView = normalView;
                callback = customViewCallback;
            }

            @Override
            public void onHideCustomView() {
                if (callback != null) {
                    callback.onCustomViewHidden();
                    callback = null;
                }
                if (myVideoView != null) {
                    ViewGroup viewGroup = (ViewGroup) myVideoView.getParent();
                    viewGroup.removeView(myVideoView);
                    viewGroup.addView(myNormalView);
                }
            }

            @Override
            public boolean onShowFileChooser(WebView arg0,
                                             ValueCallback<Uri[]> arg1, FileChooserParams arg2) {
                // TODO Auto-generated method stub
                Log.e("app", "onShowFileChooser");
                return super.onShowFileChooser(arg0, arg1, arg2);
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> uploadFile, String acceptType, String captureType) {
                TencentBrowserActivity.this.uploadFile = uploadFile;
                Intent i = new Intent(Intent.ACTION_GET_CONTENT);
                i.addCategory(Intent.CATEGORY_OPENABLE);
                i.setType("*/*");
                startActivityForResult(Intent.createChooser(i, "test"), 0);
            }


            @Override
            public boolean onJsAlert(WebView arg0, String arg1, String arg2, JsResult arg3) {
                /**
                 * 这里写入你自定义的window alert
                 */
                // AlertDialog.Builder builder = new Builder(getContext());
                // builder.setTitle("X5内核");
                // builder.setPositiveButton("确定", new
                // DialogInterface.OnClickListener() {
                //
                // @Override
                // public void onClick(DialogInterface dialog, int which) {
                // // TODO Auto-generated method stub
                // dialog.dismiss();
                // }
                // });
                // builder.show();
                // arg3.confirm();
                // return true;
                Log.i("yuanhaizhou", "setX5webview = null");
                return super.onJsAlert(null, "www.baidu.com", "aa", arg3);
            }

            /**
             * 对应js 的通知弹框 ，可以用来实现js 和 android之间的通信
             */


            @Override
            public void onReceivedTitle(WebView arg0, final String arg1) {
                super.onReceivedTitle(arg0, arg1);
                Log.i("yuanhaizhou", "webpage title is " + arg1);

            }
        });

        mWebView.setDownloadListener(new DownloadListener() {

            @Override
            public void onDownloadStart(String arg0, String arg1, String arg2,
                                        String arg3, long arg4) {
                new AlertDialog.Builder(TencentBrowserActivity.this)
                        .setTitle("确定下载？")
                        .setPositiveButton("是的",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Toast.makeText(
                                                TencentBrowserActivity.this,
                                                "开始下载",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                })
                        .setNegativeButton("不了",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                    }
                                })
                        .setOnCancelListener(
                                new DialogInterface.OnCancelListener() {

                                    @Override
                                    public void onCancel(DialogInterface dialog) {
                                        // TODO Auto-generated method stub
                                        Toast.makeText(
                                                TencentBrowserActivity.this,
                                                "取消下载",
                                                Toast.LENGTH_SHORT).show();
                                    }
                                }).show();
            }
        });


        WebSettings webSetting = mWebView.getSettings();
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(false);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(false);
        webSetting.setDisplayZoomControls(false);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setJavaScriptEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.setAppCachePath(this.getDir("appcache", 0).getPath());
        webSetting.setDatabasePath(this.getDir("databases", 0).getPath());
        webSetting.setGeolocationDatabasePath(this.getDir("geolocation", 0)
                .getPath());
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        // webSetting.setPreFectch(true);

        mWebView.loadUrl(url);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.lib_common_menu_browser, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int i = item.getItemId();
        if (i == android.R.id.home) {
            onBackPressed();

        } else if (i == R.id.action_copy) {// 从API11开始android推荐使用android.content.ClipboardManager
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            // 将文本内容放到系统剪贴板里。
            ClipData clipData = ClipData.newPlainText("text", url);
            cm.setPrimaryClip(clipData);
            ToastUtils.showToast(getString(R.string.lib_common_copy_success));

        } else if (i == R.id.action_open) {
            Intent intent = new Intent();
            intent.setAction("android.intent.action.VIEW");
            Uri content_url = Uri.parse(url);
            intent.setData(content_url);
            startActivity(intent);

        } else if (i == R.id.action_share) {
            IntentUtils.startAppShareText(TencentBrowserActivity.this, "Gank分享", "Gank分享：" + url);

        }
        return super.onOptionsItemSelected(item);
    }

    private void initProgressBar() {
        mPageLoadingProgressBar = (ProgressBar) findViewById(R.id.progressBar1);// new
        // ProgressBar(getApplicationContext(),
        // null,
        // android.R.attr.progressBarStyleHorizontal);
        mPageLoadingProgressBar.setMax(100);
        mPageLoadingProgressBar.setProgressDrawable(this.getResources()
                .getDrawable(R.drawable.lib_common_color_progressbar));
    }

    private void deleteX5() {
        getWindow().getDecorView().addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ArrayList<View> outView = new ArrayList<View>();
                getWindow().getDecorView().findViewsWithText(outView, "QQ浏览器", View.FIND_VIEWS_WITH_TEXT);
                int size = outView.size();
                if (outView != null && outView.size() > 0) {
                    outView.get(0).setVisibility(View.GONE);
                }
            }
        });
    }

    /* @Override
     protected void onNewIntent(Intent intent) {
         if (intent == null || mWebView == null || intent.getData() == null)
             return;
         mWebView.loadUrl(intent.getData().toString());
     }*/
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mWebView != null && mWebView.canGoBack()) {
                mWebView.goBack();
                return true;
            } else {
                return super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 0:
                    if (null != uploadFile) {
                        Uri result = data == null || resultCode != RESULT_OK ? null
                                : data.getData();
                        uploadFile.onReceiveValue(result);
                        uploadFile = null;
                    }
                    break;
                case 1:

                    Uri uri = data.getData();
                    String path = uri.getPath();


                    break;
                default:
                    break;
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != uploadFile) {
                uploadFile.onReceiveValue(null);
                uploadFile = null;
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (mWebView != null) {
            mWebView.destroy();
        }
        super.onDestroy();
    }

}
