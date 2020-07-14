package com.projectx.covid_19stats;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    ConnectivityManager connectivityManager;
    NetworkInfo myInfo;

    TabLayout myTab;
    ViewPager myPage;
    RelativeLayout rl;
    private FloatingActionButton fabDownload;
//use of stack for to and fro movement of webpages
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rl=(RelativeLayout)findViewById(R.id.rl);
        myPage=findViewById(R.id.myPage);
        myTab=findViewById(R.id.myTab);
        //fabDownload = (FloatingActionButton) findViewById(R.id.fabDownload);

        /*fabDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadWebPage(myTab.getSelectedTabPosition());
            }
        });*/
        myPage.setAdapter(new MyOwnPageAdapter(getSupportFragmentManager()));
        myTab.setupWithViewPager(myPage);

        myTab.getTabAt(0).setIcon(R.drawable.worldometer);
        myTab.getTabAt(1).setIcon(R.drawable.covid19_india);
        myTab.getTabAt(2).setIcon(R.drawable.moh_india);
        //myTab.setTabTextColors(ColorStateList.valueOf(getResources().getColor(R.color.orange)));
        myTab.getTabAt(0).setText("WorldOmeters");
        /*if (myTab.getSelectedTabPosition()==myTab.getTabAt(0))
        {

        }*/
        final String data[] = getResources().getStringArray(R.array.tabs);
        myTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                myPage.setCurrentItem(tab.getPosition());
                switch(myTab.getSelectedTabPosition())
                {
                    case 1:myTab.getTabAt(1).setText("Covid-19 India");

                    ;break;
                    case 2:myTab.getTabAt(2).setText("MoH India");break;
                    default:myTab.getTabAt(0).setText("WorldOmeters");break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch(tab.getPosition())
                {
                    case 1:myTab.getTabAt(1).setText("");break;
                    case 2:myTab.getTabAt(2).setText("");break;
                    default:myTab.getTabAt(0).setText("");break;
                }
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

   /* private void downloadWebPage(int selectedTabPosition) {
        switch(selectedTabPosition){
            case 0://TODO download 'WorldOmeter' page
                Snackbar.make(rl,"TODO download 'WorldOmeter' page",Snackbar.LENGTH_SHORT).show();
                private void createWebPrintJob(WebView webView) {

                PrintManager printManager = (PrintManager) this
                        .getSystemService(Context.PRINT_SERVICE);

                PrintDocumentAdapter printAdapter =
                        webView.createPrintDocumentAdapter("MyDocument");

                String jobName = getString(R.string.app_name) + " Print Test";

                printManager.print(jobName, printAdapter,
                        new PrintAttributes.Builder().build());
            }
                break;
            case 1://TODO download 'Covid 19 India' page
                Snackbar.make(rl,"TODO download 'Covid 19 India' page",Snackbar.LENGTH_SHORT).show();
                break;
            case 2://TODO download 'MoH India' page
                Snackbar.make(rl,"TODO download 'MoH India' page",Snackbar.LENGTH_SHORT).show();
                break;
        }
    }*/

    @Override
    protected void onResume() {
        super.onResume();
        isConnected();
    }

    class MyOwnPageAdapter extends FragmentPagerAdapter {
        String data[] = getResources().getStringArray(R.array.tabs);
        public MyOwnPageAdapter(FragmentManager fm)
        {
            super(fm);
        }
        @Override
        public Fragment getItem(int pos) {
            if (pos==0)
                return new tab1();
            if (pos==1)
                return new tab2();
            if (pos==2)
                return new tab3();
            return null;
        }
        @Override
        public int getCount() {
            return data.length;
        }
        /*@Override
        public CharSequence getPageTitle(int pos){
            return data[pos];
        }*/
    }

    public boolean isConnected() {
        super.onStart();
        connectivityManager=(ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        myInfo=connectivityManager.getActiveNetworkInfo();
        if(myInfo != null && myInfo.isConnected())
            return true;
        else {
            Snackbar snackbar=Snackbar.make(rl,"No Internet Connection",Snackbar.LENGTH_LONG);
            snackbar.setAction("Dismiss", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            snackbar.show();
        }
        return false;
    }

    boolean doubleBackToExitPressedOnce = false;
    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Snackbar snackbar=Snackbar.make(rl,"Please click BACK again to exit",Snackbar.LENGTH_SHORT);
        snackbar.show();
        //Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
    /*@Override
    public void onBackPressed() {
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(R.string.msg);
        alert.setIcon(R.drawable.alert);
        alert.setMessage(R.string.exit);
        alert.setCancelable(false);
        alert.setPositiveButton(getResources().getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                System.exit(0);
            }
        });
        alert.setNegativeButton(getResources().getString(R.string.no), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                return;
            }
        });
        final AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }*/
}
