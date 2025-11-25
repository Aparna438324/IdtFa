package com.proj8.idt_fa.Basic;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    public static String getTodaysDate(String dateFormat) {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);
        String formattedDate = df.format(c.getTime());
        return formattedDate;
    }
    public static class DateFormats {

        public static String DATE_FORMAT_DD_MMM_YYYY = "dd MMM yyyy";
        public static String DATE_FORMAT_DASH = "dd-MM-yyyy";

        public static String TIME_FORMAT_12 = "hh:mm a";
        public static String TIME_FORMAT_24 = "HH:mm:ss";

        public static String DATETIME_FORMAT_24 = "dd-MM-yyyy HH:mm:ss";
        public static String DATETIME_FORMAT_DB = "yyyy-MM-dd HH:mm:ss";
        public static String DATETIME_FORMAT_12 = "dd-MM-yyyy hh:mm a";
    }

}
