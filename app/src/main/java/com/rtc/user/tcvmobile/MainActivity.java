package com.rtc.user.tcvmobile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;
import com.amnix.adblockwebview.ui.AdBlocksWebViewActivity;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.webianks.easy_feedback.EasyFeedback;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import dmax.dialog.SpotsDialog;
import me.leolin.shortcutbadger.ShortcutBadger;
import static android.provider.Settings.ACTION_WIFI_SETTINGS;

public class MainActivity extends AppCompatActivity
implements NavigationView.OnNavigationItemSelectedListener {

    public WebView webView;

    ProgressDialog mProgress;
    //private MoPubView moPubView;
    AlertDialog dialog;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
        protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeContainer);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        webView.reload();
                    }
                }
        );


        /*PUBLICIDADE NA APP
        moPubView = (MoPubView) findViewById(R.id.mopub_sample_ad);
        // TODO: Replace this test id with your personal ad unit id
        moPubView.setAdUnitId("c2e6edca4ede461188472976dd0624d4");
        moPubView.loadAd(); */

        /*Limpa Badge(Notificaçãoes) ao entrar na aplicação*/
        ShortcutBadger.removeCount(this); //for 1.1.4+

        //UPDATE CHECKER
       // new GPVersionChecker.Builder(this).create();

        //FIREBASE NOTIFICATONS
        FirebaseMessaging.getInstance().subscribeToTopic("message");
        FirebaseInstanceId.getInstance().getToken();


        //REMOVER PUBLICIDADE NA WEBVIEW.
        AdBlocksWebViewActivity.init(this);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED, WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        webView = (WebView) findViewById(R.id.webView);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        webView.setScrollbarFadingEnabled(false);
        webView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webView.getSettings().setAppCacheEnabled(true);
        webView.getSettings().setPluginState(WebSettings.PluginState.ON);
        webView.getSettings().setAllowFileAccess(true);


      //  AdBlocksWebViewActivity.startWebView(MainActivity.this,((EditText)findViewById(R.id.edittext)).getText().toString(),getResources().getColor(R.color.colorPrimary));
        /*set coockies
        CookieSyncManager cookieSyncManager = CookieSyncManager.createInstance(webView.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();
        cookieSyncManager.sync();
        String cookie = cookieManager.getCookie("http://www.rtc.cv/index.php");
        cookieManager.setCookie("http://www.rtc.cv/index.php",cookie);*/

        if (Build.VERSION.SDK_INT >= 19) {
            webView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        }
        else {
            webView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }

        //verificar conexao a net antes de aceder

        /*
        ConnectivityManager connManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mWifi = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobile = connManager .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (mWifi.isConnected()&& mobile.isConnected()) {
           //nada
        }
        else{
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
            alertDialog.setTitle("Erro");
            alertDialog.setMessage("Wifi/Dados moveis não Ligado!");
            alertDialog.setButton("Lige o Wifi/Dados moveis !!", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                    startActivity(getIntent());
                }
            });
        }
        */
        // set Javascript

            WebSettings settings = webView.getSettings();
            settings.setJavaScriptEnabled(true);
            settings.setEnableSmoothTransition(true);
            settings.setDomStorageEnabled(true);
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
            settings.setUseWideViewPort(true);
            settings.setSavePassword(true);
            settings.setSaveFormData(true);

            //mProgress = ProgressDialog.show(this, "A Carregar Noticias", "Por favor aguarde ...");
            final AlertDialog dialog = new SpotsDialog(this, "Por favor aguarde ...");
            dialog.show();

             webView.setWebViewClient(new WebViewClient() {

                // load url
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
                }

                //Se nao houver net
                public void onReceivedError(WebView webView, int errorCode, String description, String failingUrl) {
                try {
                    webView.stopLoading();
                } catch (Exception e) {
                }
                try {
                    webView.clearView();
                } catch (Exception e) {
                }

                if (webView.canGoBack()) {
                    webView.goBack();
                }

                   webView.loadUrl("file:///android_asset/index.html");
                            new LovelyStandardDialog(MainActivity.this)
                            .setTopColorRes(R.color.colorPrimary)
                            .setTitleGravity(Gravity.CENTER)
                            .setMessageGravity(Gravity.CENTER)
                            .setButtonsColorRes(R.color.colorPrimaryDark)
                            .setIcon(R.mipmap.ic_error_info)
                            .setTitle(R.string.error)
                            .setMessage(R.string.error_message_internet)
                            .setPositiveButton(android.R.string.ok, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   // startActivity(new Intent(ACTION_WIFI_SETTINGS));
                                }
                            })
                            .setNegativeButton(android.R.string.no, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    finish();
                                }
                            })

                            .show();
                super.onReceivedError(webView, errorCode, description, failingUrl);
            }

            // when finish loading page
            public void onPageFinished(WebView view, String url) {
            if(dialog.isShowing()) {
            view.clearCache(true);
                dialog.dismiss();

        }
        swipeRefreshLayout.setRefreshing(false);
    }

});
        // set url for webview to load
       webView.loadUrl("http://www.rtc.cv/index.php?paginas=61");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
        }


        @Override
        public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
        }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override

        public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getFragmentManager();

            //Tentiva de remover bug on live streaming LOL ↓↓ !!!!
            //FragmentManager.popBackStack(String fragmentManager, FragmentManager.POP_BACK_STACK_INCLUSIVE)

            for(int i = 0; i < fragmentManager.getBackStackEntryCount(); ++i) {
                fragmentManager.popBackStack();
            }

            if(getSupportFragmentManager().findFragmentById(R.id.content_frame) != null) {
              getSupportFragmentManager()
             .beginTransaction()
             .remove(getSupportFragmentManager()
             .findFragmentById(R.id.content_frame))
             .commit();
            }

            //Tentiva de remover bug on live streaming LOL ↑↑ !!!!

            //Select items

             if (id == R.id.nav_third_layout) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, new Noticias())
                        .addToBackStack(null)
                        .commit();
            }

            else if (id == R.id.nav_first_layout) {
                    fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new LiveStream())
                    .addToBackStack(null)
                    .commit();

        } else if (id == R.id.nav_second_layout) {
            fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new RcvRadio())
                    .addToBackStack(null)
                    .commit();
        }

        else if (id == R.id.nav_share) {

            final Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=com.rtc.tcv.tcvmobile");
            startActivity(Intent.createChooser(intent, "Partilhar App com:"));
        }

        else if (id == R.id.nav_send) {

            Dialog settingsDialog = new Dialog(this);
            settingsDialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            settingsDialog.setContentView(getLayoutInflater().inflate(R.layout.image_layout, null));
            settingsDialog.show();
            settingsDialog.setCancelable(true);
            settingsDialog.setCanceledOnTouchOutside(true);

        }

        else if (id == R.id.tempo) {
                    fragmentManager.beginTransaction()
                    .replace(R.id.content_frame, new Tempo())
                    .addToBackStack(null)
                    .commit();
        }

        else if (id == R.id.email) {
                    sendEmail();
        }

                else if (id == R.id.contacto) {
                new EasyFeedback.Builder(this)
                    .withEmail("tcvinformacao@rtc.cv")
                    .withSystemInfo()
                    .build()
                    .start();
                 }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
        }

        public void rcv (View view){
        Intent rcv = new Intent (MainActivity.this, Rcv.class);
        startActivity(rcv);

        }

        public void rcvplus (View view){
        Intent rcv = new Intent (MainActivity.this, RcvJovem.class);
        startActivity(rcv);
        }

        public void tempo (View view){
        Intent rcv = new Intent (MainActivity.this, WeatherActivity.class);
        startActivity(rcv);
        }

        protected void sendEmail() {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL  , new String[]{"kevindiasdeda@hotmail.com"});
        i.putExtra(Intent.EXTRA_SUBJECT, "Assunto...");
        i.putExtra(Intent.EXTRA_TEXT   , "Mensagem...");
        try {
            startActivity(Intent.createChooser(i, "Enviar Email..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(MainActivity.this, "Ops! erro ao enviar o email", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy(){
        //moPubView.destroy();
        super.onDestroy();
    }

}
