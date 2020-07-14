package com.projectx.covid_19stats;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

/**
 * A simple {@link Fragment} subclass.
 */
public class tab3 extends Fragment {

    public tab3() {
        // Required empty public constructor
    }

    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressBar progressBar3;
    ConnectivityManager connectivityManager;
    NetworkInfo myInfo;
    RelativeLayout rl3;
    FloatingActionButton fabDownload3;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tab3, container, false);

        final WebView web3 = (WebView) view.findViewById(R.id.web3);
        rl3 = (RelativeLayout) view.findViewById(R.id.rl3);
        progressBar3 = (ProgressBar) view.findViewById(R.id.progressBar3);
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        fabDownload3 = (FloatingActionButton) view.findViewById(R.id.fabDownload3);

        final MainActivity mainActivity = new MainActivity();

        web3.clearCache(true);
        final WebSettings webSettings = web3.getSettings();// Enable Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setBuiltInZoomControls(true);

        //for optimising performance
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        web3.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setEnableSmoothTransition(true);

        String url3 = "https://www.mohfw.gov.in";
        web3.loadUrl(url3);
        //force links to open in webView
        web3.setWebViewClient(new WebViewClient());
        web3.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar3.setProgress(newProgress);
                if (progressBar3.getProgress() == 100)
                {
                    progressBar3.setVisibility(View.GONE);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        web3.setWebViewClient(new WebViewClient(){
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onPageFinished(WebView view, String url) {
                fabDownload3.setVisibility(View.VISIBLE);
                fabDownload3.setEnabled(true);
            }
        });

        fabDownload3.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                createWebPrintJob(web3);
            }
        });

        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (((MainActivity) getActivity()).isConnected()) {
                    fabDownload3.setVisibility(View.INVISIBLE);
                    fabDownload3.setEnabled(false);
                    web3.reload();
                    progressBar3.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(rl3, "It is cached one", Snackbar.LENGTH_SHORT).show();
                    web3.getSettings().setCacheMode(webSettings.LOAD_CACHE_ONLY);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createWebPrintJob(WebView webView) {
        PrintManager printManager = (PrintManager)getActivity().getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            printAdapter = webView.createPrintDocumentAdapter("MoH"+" India");
        }

        String jobName = getString(R.string.app_name) + " Print Test";

        printManager.print(jobName, printAdapter,
                new PrintAttributes.Builder().build());
    }
}
/*webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setDatabaseEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setSupportZoom(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setBuiltInZoomControls(true);
        //webView.setWebViewClient(new GeoWebViewClient());*/