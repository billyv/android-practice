package com.commonsware.empublite;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebViewFragment;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SimpleContentFragment extends WebViewFragment {

    private static final String KEY_FILE = "file";

    protected static SimpleContentFragment newInstance(String file) {
        SimpleContentFragment f = new SimpleContentFragment();
        Bundle args = new Bundle();

        args.putString(KEY_FILE, file);
        f.setArguments(args);

        // Create a bundle containing KEY_FILE and file to load,
        // attach it to your newly created SimpleContentFragment and return that.

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        // Tells android to keep this fragment across config change (portrait -> landscape etc)
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Chain to super to create WebView
        View result = super.onCreateView(inflater, container, savedInstanceState);

        // Splice in your own configurations
        getWebView().getSettings().setJavaScriptEnabled(true);
        getWebView().getSettings().setSupportZoom(true);
        getWebView().getSettings().setBuiltInZoomControls(true);
        getWebView().loadUrl(getPage());

        // And return it.
        return result;
    }

    private String getPage() {
        // This simply returns the string we passed into the args
        // identified by KEY_FILE. The page to load.

        return getArguments().getString(KEY_FILE);
    }

}
