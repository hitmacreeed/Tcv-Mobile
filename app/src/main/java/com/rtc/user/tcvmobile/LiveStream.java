package com.rtc.user.tcvmobile;

import android.app.AlertDialog;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.amnix.adblockwebview.ui.AdBlocksWebViewActivity;
import com.yarolegovich.lovelydialog.LovelyStandardDialog;

import dmax.dialog.SpotsDialog;

public class LiveStream extends Fragment {

    View myView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.first_layout, container, false);
        //getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);

         //REMOVER PUBLICIDADE NA WEBVIEW.
        AdBlocksWebViewActivity.init(getActivity());

        WebView webView = (WebView)myView.findViewById(R.id.webView2);
       // mProgress = ProgressDialog.show(getActivity(), "A Carregar Live Stream", "Por favor aguarde...");
        final AlertDialog dialog = new SpotsDialog(getActivity(), "Por favor aguarde ...");
        dialog.show();
        webView.setHorizontalScrollBarEnabled(false);
        webView.getSettings().setDomStorageEnabled(true);
        // set Javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);


        webView.setWebViewClient(new WebViewClient() {

            // load url
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

                        new LovelyStandardDialog(getActivity())
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
                                getActivity().finish();
                            }
                        })

                        .show();

                super.onReceivedError(webView, errorCode, description, failingUrl);
            }

            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }


            // when finish loading page
            public void onPageFinished(WebView view, String url) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }
        });
        // set url for webview to load
        webView.loadUrl("http://tcvmobile.niveksaid.com/");

        /*AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setTitle("OBS");
        alertDialog.setMessage("A stream por vezes poderá parar, dependendo da sua ligação a internet!!! ");
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        alertDialog.show();*/


        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
        alert.setTitle("PROGRAMAÇÃO TCV");

        WebView wv = new WebView(getActivity());
        wv.loadUrl("http://tcvmobile.niveksaid.com/programacao.html");
        wv.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;

            }

            public void onReceivedError(WebView wv, int errorCode, String description, String failingUrl) {
                try {
                    wv.stopLoading();
                } catch (Exception e) {

                }
                try {

                wv.loadUrl("about:blank");
                } catch (Exception e) {

                }

                if (wv.canGoBack()) {
                    wv.goBack();
                }
            }

            //wv.loadUrl("http://tcvmobile.niveksaid.com/programacao.html");

        });

        alert.setView(wv);
        alert.setNegativeButton("Fechar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alert.show();

        return myView;

    }

    // return myView;
}





