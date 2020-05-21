package com.projectx.covid_19stats;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.http.SslError;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab2 extends Fragment {

    public tab2() {
        // Required empty public constructor
    }

    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressBar progressBar2;
    ConnectivityManager connectivityManager;
    NetworkInfo myInfo;
    RelativeLayout rl2;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab2, container, false);

        final WebView web2 = (WebView) view.findViewById(R.id.web2);
        rl2 = (RelativeLayout) view.findViewById(R.id.rl2);
        progressBar2 = (ProgressBar) view.findViewById(R.id.progressBar2);
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);

        final MainActivity mainActivity = new MainActivity();

        web2.clearCache(true);
        web2.setWebViewClient(new SSLTolerentWebViewClient());

        web2.setWebViewClient(new WebViewClient() {
            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                handler.proceed();
            }
        });

        final WebSettings webSettings = web2.getSettings();// Enable Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);

        //for optimising performance
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        web2.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setEnableSmoothTransition(true);

        String url2 = "https://www.covid19india.org";
        web2.loadUrl(url2);
        //force links to open in webView
        web2.setWebViewClient(new WebViewClient());
        web2.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar2.setProgress(newProgress);
                if (progressBar2.getProgress() == 100)
                {
                    progressBar2.setVisibility(View.GONE);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (((MainActivity) getActivity()).isConnected()) {
                    web2.reload();
                    progressBar2.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(rl2, "It is cached one", Snackbar.LENGTH_SHORT).show();
                    web2.getSettings().setCacheMode(webSettings.LOAD_CACHE_ONLY);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }

    private class SSLTolerentWebViewClient extends WebViewClient {

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed(); // Ignore SSL certificate errors
        }

    }
}
