package com.proj8.idt_fa.UI;

import static android.content.ContentValues.TAG;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.proj8.idt_fa.ApiHandling.API;
import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.Basic.HelperFunctions;
import com.proj8.idt_fa.Model.Identify.DataIdentifyList;
import com.proj8.idt_fa.Model.Identify.IdentifyData;
import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.ReaderManager;
import com.proj8.idt_fa.RfidReader.TagReadCallback;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IdentifyActivity extends BaseActivity {

    HelperFunctions helperFunctions;
    TextView Viewscanidentify, Tagidtv,Costcentertv,Assetprimarykeytv,Assetclass1tv,Plannttv,
            Departmenttv,Usernametv,Locationtv,Descriptiontv,
            Assetsubclasstv,Sbutv,Capitalizedtv,Modeltv,Serialnotv,Ponotv,Assettypetv,Statustv,Makeoemtv,Vendortv,Financeassettv,
            Quantitytv,AssetStatustv,Assetclass2tv,MonthyearofPurchasetv,Itassetscodetv;
    Button ScanBtnIdentify;
    Boolean checkprocess=true;
    List<DataIdentifyList> identifyLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify);
        setHeaderTitle("Identify");

        helperFunctions=new HelperFunctions();

        updateConnectionStatus(ReaderManager.getInstance().isConnected());

        ReaderManager.getInstance().setStatusListener(isConnected -> {
            runOnUiThread(() -> updateConnectionStatus(isConnected));
        });

        Viewscanidentify = findViewById(R.id.viewscanidentify);

        Tagidtv = findViewById(R.id.tagidtv);
        Costcentertv = findViewById(R.id.costcentertv);
        Assetprimarykeytv = findViewById(R.id.assetprimarykeytv);
        Assetclass1tv = findViewById(R.id.assetclass1tv);
        Plannttv = findViewById(R.id.plannttv);
        Departmenttv = findViewById(R.id.departmenttv);
        Usernametv = findViewById(R.id.usernametv);
        Locationtv = findViewById(R.id.locationtv);
        Descriptiontv = findViewById(R.id.descriptiontv);
        Assetsubclasstv = findViewById(R.id.assetsubclasstv);
        Sbutv = findViewById(R.id.sbutv);
        Capitalizedtv = findViewById(R.id.capitalizedtv);
        Modeltv = findViewById(R.id.modeltv);
        Serialnotv = findViewById(R.id.serialnotv);
        Ponotv = findViewById(R.id.ponotv);
        Assettypetv = findViewById(R.id.assettypetv);
        Statustv = findViewById(R.id.statustv);
        Makeoemtv = findViewById(R.id.makeoemtv);
        Vendortv = findViewById(R.id.vendortv);
        Financeassettv = findViewById(R.id.financeassettv);
        Quantitytv = findViewById(R.id.quantitytv);
        AssetStatustv = findViewById(R.id.assetStatustv);
        Assetclass2tv = findViewById(R.id.assetclass2tv);
        MonthyearofPurchasetv = findViewById(R.id.monthyearofPurchasetv);
        Itassetscodetv = findViewById(R.id.itassetscodetv);

        ScanBtnIdentify = (Button) findViewById(R.id.scanBtnIdentify);
        ScanBtnIdentify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helperFunctions.showProgress(IdentifyActivity.this);
                readEPC();
            }
        });

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==142 || keyCode == 139 || keyCode == 280 || keyCode == 293){
            if (event.getRepeatCount() == 0 ) {
                Log.d( TAG,""+"onCountRepeat" );
                helperFunctions.showProgress(IdentifyActivity.this);
                readEPC();
            }
        }
        return super.onKeyDown( keyCode, event );
    }


    private void readEPC() {
        ReaderManager.getInstance(this).readEPC(new TagReadCallback() {
            @Override
            public void onTagReadSuccess(String epc) {
                runOnUiThread(() -> {
                    helperFunctions.hideProgress();
                    setIdentifyCheck(epc);
                });
            }

            @Override
            public void onTagReadFailure(String reason) {
                runOnUiThread(() -> {
                    helperFunctions.hideProgress();
                    helperFunctions.showToastMessage(getApplicationContext(), reason);
                });
            }
        });
    }
    private void setIdentifyCheck(String tagNoVar) {
        if (tagNoVar.isEmpty()){
            helperFunctions.showToastMessage(getApplicationContext(),"Attach Associated Tag to Reader");
            helperFunctions.hideProgress();
        }
        else
        {
            Viewscanidentify.setText(tagNoVar);
            if (checkprocess){
                setIdentifyData(tagNoVar);
            }
        }
    }

    private void setIdentifyData(final String tagNo) {
        try{
            checkprocess = false;
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(helperFunctions.BaseURL)
//                    .client(SSLCertificate.getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<IdentifyData> call = api.getIdentifyList(tagNo);
            call.enqueue(new Callback<IdentifyData>() {
                @Override
                public void onResponse(Call<IdentifyData> call, Response<IdentifyData> response) {
                    checkprocess = true;
                    if (response.body() != null) {
                        IdentifyData data = response.body();
                        if (data.getMsg().equals(" Not Found")){
                            reset();
                            Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                        }else if (data.getMsg().equals(" No Input Found !")){
                            reset();
                            Toast.makeText(getApplicationContext(), "No Input Available", Toast.LENGTH_SHORT).show();
                        }else{

                            identifyLists = data.getData();

                            Tagidtv.setText(identifyLists.get(0).getTagId());
                            Costcentertv.setText(identifyLists.get(0).getCostCenter());
                            Assetprimarykeytv.setText(identifyLists.get(0).getAssetPrimaryCode());
                            Assetclass1tv.setText(identifyLists.get(0).getAssetTypeName());
                            Plannttv.setText(identifyLists.get(0).getPlantName());
                            Departmenttv.setText(identifyLists.get(0).getDeptName());
                            Usernametv.setText(identifyLists.get(0).getCreatedBy());

                            Locationtv.setText(identifyLists.get(0).getLocName());
                            Descriptiontv.setText(identifyLists.get(0).getDescription());
                            Assetsubclasstv.setText(identifyLists.get(0).getAssetSubClass());
                            Sbutv.setText(identifyLists.get(0).getSbu());
                            Capitalizedtv.setText(identifyLists.get(0).getCapitalizedOn());
                            Modeltv.setText(identifyLists.get(0).getModel());
                            Serialnotv.setText(identifyLists.get(0).getDeviceSlNo());
                            Ponotv.setText(identifyLists.get(0).getPoNo());
                            Assettypetv.setText(identifyLists.get(0).getAssetTypeName());
                            Statustv.setText(identifyLists.get(0).getScanStatus());
                            Makeoemtv.setText(identifyLists.get(0).getMakeOem());
                            Vendortv.setText(identifyLists.get(0).getVendor());
                            Financeassettv.setText(identifyLists.get(0).getFinanceAssetId());
                            Quantitytv.setText(identifyLists.get(0).getQuantity());
                            AssetStatustv.setText(identifyLists.get(0).getAssetStatus());
                            Assetclass2tv.setText(identifyLists.get(0).getAssetClass2());
                            MonthyearofPurchasetv.setText(identifyLists.get(0).getPurchaseDate());
                            Itassetscodetv.setText(identifyLists.get(0).getItAssetCode());
                            helperFunctions.hideProgress();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Available", Toast.LENGTH_SHORT).show();
                        helperFunctions.hideProgress();
                    }
                }

                @Override
                public void onFailure(Call<IdentifyData> call, Throwable t) {
                    checkprocess = true;
                    Toast.makeText(getApplicationContext(), ""+t.toString(), Toast.LENGTH_SHORT).show();
                    helperFunctions.hideProgress();
                }
            });
        }
        catch(Exception e) {
            Log.d("IdentifyException",e.getMessage().toString());
            helperFunctions.hideProgress();
        }
    }
    public void reset(){
        Tagidtv.setText("");
        Costcentertv.setText("");
        Assetclass1tv.setText("");
        Plannttv.setText("");
        Departmenttv.setText("");
        Usernametv.setText("");
        Locationtv.setText("");
        Descriptiontv.setText("");
        Assetsubclasstv.setText("");
        Sbutv.setText("");
        Capitalizedtv.setText("");
        Modeltv.setText("");
        Serialnotv.setText("");
        Assettypetv.setText("");
        Statustv.setText("");
        Makeoemtv.setText("");
        Financeassettv.setText("");
        Quantitytv.setText("");
        AssetStatustv.setText("");
        MonthyearofPurchasetv.setText("");
        Itassetscodetv.setText("");
    }
}