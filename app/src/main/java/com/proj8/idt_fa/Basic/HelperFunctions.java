package com.proj8.idt_fa.Basic;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

public class HelperFunctions {
//    public static final String BaseURL = "https://ams.kecrpg.com:60516/index.php/Api/";
    public static final String BaseURL = "http://kec.petregistryindia.com/kec/index.php/Api/";
    public static void showToastMessage(Context ctx,String str){
        Toast.makeText(ctx, str,
                Toast.LENGTH_SHORT).show();
    }

    ProgressDialog progressBar;
    public void showProgress(Context ctxt){
        progressBar = new ProgressDialog(ctxt);
        progressBar.setCancelable(false);
        progressBar.setMessage("Loading....");
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.show();
    }
    public void hideProgress(){
        progressBar.dismiss();
    }

    ProgressDialog progressDialog;

    public void showLoadingIndicator(Context ctxt) {
        progressDialog = new ProgressDialog(ctxt);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.setMax(100);
        progressDialog.setMessage("Syncing in progress, please wait.....");
        progressDialog.show();
    }
    public void hideLoadingIndicator() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }
    public void setProgress(int progressVal){
        progressDialog.setProgress(progressVal);
    }
}
