package com.example.billyvalvo.myapplication;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Date;


public class WebViewDemoActivity extends Activity {

    WebView browser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_demo);

        browser = (WebView) findViewById(R.id.webkit);

        // JavaScript is disabled by default so enable like so.
        browser.getSettings().setJavaScriptEnabled(true);
        browser.loadUrl(getString(R.string.url));

        /*  --loadUrl()-- and alternatives
            It can load http(s), file://, file:///android_asset/, and content://
            Otherwise use
            --loadData()--
            This loads and renders raw html as a web page would.
            Takes content, MIME type, and encoding as strings.
            or use
            --loadDataWithBaseURL()--
            Use this if html refers to relative file locations.
            Also apparently sometimes works better than loadData with null URL.
         */

        browser.setWebViewClient(new CallBack());


    }

    protected void loadTime() {
        String page=
                "<html><body><a href='" + getString(R.string.url) +"'>"
                        + DateUtils.formatDateTime(this, new Date().getTime(),
                        DateUtils.FORMAT_SHOW_DATE
                                | DateUtils.FORMAT_SHOW_TIME)
                        + "</a></body></html>";
        browser.loadData(page, "text/html", "UTF-8");
    }

    private class CallBack extends WebViewClient {


        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {

            loadTime();

            return true;
        }

    }

}

/*
Because I define that shouldOverrideUrlLoading to simply run loadTime,
although it loads R.string.url at first, any link clicked (url loaded)
will always generate the html page defined in loadTime method.
So even though I have R.string.url as the links href on that page,
it will just load what is in the loadTime method.
 */
