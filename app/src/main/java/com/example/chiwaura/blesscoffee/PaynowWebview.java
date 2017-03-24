package com.example.chiwaura.blesscoffee;

import android.app.Activity;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;

import static java.net.URLEncoder.encode;

/**
 * Created by blessing on 3/21/2017.
 */

public class PaynowWebview extends Activity {

    final String paynowUrl = Paynow();
    //final int total = Payment.total;
    final String amountDue = Payment.amountDue;
    WebView myWebView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.paynow_open);
        myWebView = (WebView) findViewById(R.id.webview);
        Toast.makeText(getApplicationContext(),amountDue,Toast.LENGTH_LONG).show();

        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.setWebViewClient(new WebViewClient());
        // String paynowUrl = Paynow();
        myWebView.loadUrl(paynowUrl);

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            }

            @Override
            public void onReceivedHttpError(
                    WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler,
                                           SslError error) {
            }
        });


    }


    private String Paynow() {
        String merchantUrl = "search=blesschews%40gmail.com&amount=" + amountDue + "&reference=blessCoffee&I=1";
        String codedMerchantUrl = Base64.encodeToString(merchantUrl.getBytes(), Base64.DEFAULT);
        String encodedurl = "";

        try {

            encodedurl = encode(codedMerchantUrl, "UTF-8");

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String paynowUrl = "https://www.paynow.co.zw/payment/link/?q=" + encodedurl;
        // String paynowUrl = "https://www.paynow.co.zw/payment/link/?q=" + url;
        return paynowUrl;


    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // Check if the key event was the Back button and if there's history
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();
            overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
            return true;
        }
        // If it wasn't the Back key or there's no web page history, bubble up to the default
        // system behavior (probably exit the activity)
        return super.onKeyDown(keyCode, event);
    }

}
