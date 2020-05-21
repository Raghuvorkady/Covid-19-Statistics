package com.projectx.covid_19stats;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class tab1 extends Fragment {

    public tab1() {
        // Required empty public constructor
    }

    SwipeRefreshLayout mySwipeRefreshLayout;
    ProgressBar progressBar1;
    ConnectivityManager connectivityManager;
    NetworkInfo myInfo;
    RelativeLayout rl1;
    MainActivity mainActivity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_tab1, container, false);

        progressBar1 = (ProgressBar) view.findViewById(R.id.progressBar1);
        rl1 = (RelativeLayout) view.findViewById(R.id.rl1);
        mySwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        final WebView web1 = (WebView) view.findViewById(R.id.web1);

        final MainActivity mainActivity = new MainActivity();

        web1.clearCache(true);
        final WebSettings webSettings = web1.getSettings();// Enable Javascript
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDisplayZoomControls(false);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setJavaScriptEnabled(true);

        //for optimising performance
        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        web1.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        webSettings.setDomStorageEnabled(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setUseWideViewPort(true);
        webSettings.setSaveFormData(true);
        webSettings.setSavePassword(true);
        webSettings.setEnableSmoothTransition(true);

        String urlClg = "https://bmsit.ac.in/";
        String url1 = "https://www.worldometers.info/coronavirus";
        web1.loadUrl(url1);
        web1.setWebViewClient(new WebViewClient());   //force links to open in webView
        web1.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar1.setProgress(newProgress);
                if (progressBar1.getProgress() == 100)
                {
                    progressBar1.setVisibility(View.GONE);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        mySwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (((MainActivity) getActivity()).isConnected()) {
                    web1.reload();
                    progressBar1.setVisibility(View.VISIBLE);
                } else {
                    Snackbar.make(rl1, "It is cached one", Snackbar.LENGTH_SHORT).show();
                    web1.getSettings().setCacheMode(webSettings.LOAD_CACHE_ONLY);
                    mySwipeRefreshLayout.setRefreshing(false);
                }
            }
        });
        return view;
    }

    /*@Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getContext(),"started",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(getContext(),"paused",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Toast.makeText(getContext(),"attached",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResume() {
        super.onResume();
        //onViewStateRestored(savedInstanceState);
        Toast.makeText(getContext(),"resumed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Toast.makeText(getContext(),"view created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getContext(),"activity created",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop() {
        super.onStop();
        Toast.makeText(getContext(),"stopped",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Toast.makeText(getContext(),"view destroyed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(getContext(),"destroyed",Toast.LENGTH_SHORT).show();

        onDetach();
    }*/
}
