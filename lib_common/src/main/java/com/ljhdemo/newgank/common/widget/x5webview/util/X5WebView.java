package com.ljhdemo.newgank.common.widget.x5webview.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebSettings.LayoutAlgorithm;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.util.HashMap;
import java.util.Map;

//import com.tencent.smtt.sdk.WebBackForwardList;
//import com.tencent.smtt.sdk.WebHistoryItem;
//import com.tencent.smtt.sdk.WebStorage;
//import com.tencent.smtt.sdk.WebViewDatabase;

public class X5WebView extends WebView {
    public static final int FILE_CHOOSER = 0;
    private String resourceUrl = "";
    private WebView smallWebView;
    private static boolean isSmallWebViewDisplayed = false;
    private boolean isClampedY = false;
    private Map<String, Object> mJsBridges;
    private TextView tog;
    RelativeLayout.LayoutParams layoutParams;
    private RelativeLayout refreshRela;
    TextView title;
    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        public void onReceivedHttpAuthRequest(WebView webview,
                                              com.tencent.smtt.export.external.interfaces.HttpAuthHandler httpAuthHandlerhost, String host,
                                              String realm) {
            boolean flag = httpAuthHandlerhost.useHttpAuthUsernamePassword();
        }
    };

    @SuppressLint("SetJavaScriptEnabled")
    public X5WebView(Context arg0, AttributeSet arg1) {
        super(arg0, arg1);
        this.setWebViewClientExtension(new X5WebViewEventHandler(this));// 配置X5webview的事件处理
        this.setWebViewClient(client);
        //this.setWebChromeClient(chromeClient);
        //WebStorage webStorage = WebStorage.getInstance();
        initWebViewSettings();
        this.getView().setClickable(true);
        this.getView().setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return false;
            }
        });
    }

    private void initWebViewSettings() {
        WebSettings webSetting = this.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        //webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        //webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        //webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        // this.getSettingsExtension().setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);//extension
        // settings 的设计
    }
    @Override
    protected boolean drawChild(Canvas canvas, View child, long drawingTime) {
        boolean ret = super.drawChild(canvas, child, drawingTime);
        canvas.save();
        Paint paint = new Paint();
        paint.setColor(0x7fff0000);
        paint.setTextSize(24.f);
        paint.setAntiAlias(true);
        if (getX5WebViewExtension() != null) {
            canvas.drawText(this.getContext().getPackageName() + "-pid:" + android.os.Process.myPid(), 10, 50, paint);
            canvas.drawText("X5  Core:" + QbSdk.getTbsVersion(this.getContext()), 10, 100, paint);
        } else {
            canvas.drawText(this.getContext().getPackageName() + "-pid:" + android.os.Process.myPid(), 10, 50, paint);
            canvas.drawText("Sys Core", 10, 100, paint);
        }
        canvas.drawText(Build.MANUFACTURER, 10, 150, paint);
        canvas.drawText(Build.MODEL, 10, 200, paint);
        canvas.restore();
        return ret;
    }

    public X5WebView(Context arg0) {
        super(arg0);
        setBackgroundColor(85621);
    }

    public static void setSmallWebViewEnabled(boolean enabled) {
        isSmallWebViewDisplayed = enabled;
    }

    public void addJavascriptBridge(SecurityJsBridgeBundle jsBridgeBundle) {
        if (this.mJsBridges == null) {
            this.mJsBridges = new HashMap<String, Object>(5);
        }

        if (jsBridgeBundle != null) {
            String tag = SecurityJsBridgeBundle.BLOCK + jsBridgeBundle.getJsBlockName() + "-"
                    + SecurityJsBridgeBundle.METHOD + jsBridgeBundle.getMethodName();
            this.mJsBridges.put(tag, jsBridgeBundle);
        }
    }

    /**
     * 当webchromeClient收到 web的prompt请求后进行拦截判断，用于调起本地android方法
     *
     * @param methodName 方法名称
     * @param blockName  区块名称
     * @return true ：调用成功 ； false ：调用失败
     */
    private boolean onJsPrompt(String methodName, String blockName) {
        String tag = SecurityJsBridgeBundle.BLOCK + blockName + "-" + SecurityJsBridgeBundle.METHOD + methodName;

        if (this.mJsBridges != null && this.mJsBridges.containsKey(tag)) {
            ((SecurityJsBridgeBundle) this.mJsBridges.get(tag)).onCallMethod();
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判定当前的prompt消息是否为用于调用native方法的消息
     *
     * @param msg 消息名称
     * @return true 属于prompt消息方法的调用
     */
    private boolean isMsgPrompt(String msg) {
        if (msg != null && msg.startsWith(SecurityJsBridgeBundle.PROMPT_START_OFFSET)) {
            return true;
        } else {
            return false;
        }
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_dispatchTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_dispatchTouchEvent(ev);
        android.util.Log.d("Bran", "dispatchTouchEvent " + ev.getAction() + " " + r);
        return r;
    }

    // TBS: Do not use @Override to avoid false calls
    public boolean tbs_onInterceptTouchEvent(MotionEvent ev, View view) {
        boolean r = super.super_onInterceptTouchEvent(ev);
        return r;
    }

    protected void tbs_onScrollChanged(int l, int t, int oldl, int oldt, View view) {
        super_onScrollChanged(l, t, oldl, oldt);
    }

    protected void tbs_onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY, View view) {
      /*  if (getContext() instanceof RefreshActivity) {
            if (this.tog == null) {
                this.tog = (TextView) ((Activity) getContext()).findViewById(R.id.refreshText);
                layoutParams = (RelativeLayout.LayoutParams) (this.tog.getLayoutParams());
                this.refreshRela = (RelativeLayout) ((Activity) getContext()).findViewById(R.id.refreshPool);
            }
            if (isClampedY && !clampedY) {
                this.reload();
            }
            if (clampedY) {
                this.isClampedY = true;

            } else {
                this.isClampedY = false;
            }
        }*/
        super_onOverScrolled(scrollX, scrollY, clampedX, clampedY);
    }

    protected void tbs_computeScroll(View view) {
        super_computeScroll();
    }

    protected boolean tbs_overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY, int scrollRangeX,
                                       int scrollRangeY, int maxOverScrollX, int maxOverScrollY, boolean isTouchEvent, View view) {
       /* if (getContext() instanceof RefreshActivity) {
            if (this.isClampedY) {
                if ((refreshRela.getTop() + (-deltaY)) / 2 < 255) {
                    this.tog.setAlpha((refreshRela.getTop() + (-deltaY)) / 2);
                } else
                    this.tog.setAlpha(255);
                this.refreshRela.layout(refreshRela.getLeft(), refreshRela.getTop() + (-deltaY), refreshRela.getRight(),
                        refreshRela.getBottom() + (-deltaY));
                this.layout(this.getLeft(), this.getTop() + (-deltaY) / 2, this.getRight(),
                        this.getBottom() + (-deltaY) / 2);
            }
        }*/
        return super_overScrollBy(deltaX, deltaY, scrollX, scrollY, scrollRangeX, scrollRangeY, maxOverScrollX,
                maxOverScrollY, isTouchEvent);
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    protected boolean tbs_onTouchEvent(MotionEvent event, View view) {
      /*  if (getContext() instanceof RefreshActivity) {
            if (event.getAction() == MotionEvent.ACTION_UP && this.tog != null) {
                this.isClampedY = false;
                this.tog.setAlpha(0);
                this.refreshRela.layout(refreshRela.getLeft(), 0, refreshRela.getRight(), refreshRela.getBottom());
                this.layout(this.getLeft(), 0, this.getRight(), this.getBottom());
            }

        }*/
        return super_onTouchEvent(event);
    }
}
