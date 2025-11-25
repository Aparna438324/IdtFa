package com.proj8.idt_fa.UI;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.proj8.idt_fa.Adapter.InventoryAdapter;
import com.proj8.idt_fa.ApiHandling.API;
import com.proj8.idt_fa.Basic.BaseActivity;
import com.proj8.idt_fa.Basic.HelperFunctions;
import com.proj8.idt_fa.Model.DeptSpin.DataDept;
import com.proj8.idt_fa.Model.PlantAssetList.DataPAList;
import com.proj8.idt_fa.Model.PlantAssetList.PAList;
import com.proj8.idt_fa.Model.PlantSpin.DataPlant;
import com.proj8.idt_fa.Model.ScanPost.PostArray;
import com.proj8.idt_fa.Model.ScanPost.ScanPostData;
import com.proj8.idt_fa.Model.ScanPost.ScanPostList;
import com.proj8.idt_fa.R;
import com.proj8.idt_fa.RfidReader.ContinuousTagCallback;
import com.proj8.idt_fa.RfidReader.ReaderManager;
import com.proj8.idt_fa.RfidReader.TagReadCallback;
import com.rscja.deviceapi.RFIDWithUHFUART;
import com.rscja.deviceapi.exception.ConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InventoryActivity extends BaseActivity {

    boolean allowed = false;
    ScanPostList scanPostList;
    JSONObject finaljsonBody;
    JSONArray dlArray;
    TextView PlantText, DeptText, Counttext;
    int totalval = 0;
    Spinner spinnerPlant, spinnerDept;
    Button ScanBtn,SaveBtn;
    String plantid, deptid;
    ArrayList<DataPAList> productList;
    private boolean loopFlag = false;
    InventoryAdapter inventoryAdapter;
    private static final String TAG = "InventoryActivity";
    private RFIDWithUHFUART mReader;
    static HashSet<String> serverEPCmSet, foundEpcSet,uniqueSet;
    private ArrayList<HashMap<String, String>> tagList;
    private HashMap<String,Integer> serverMap;
    private HashMap<String, String> map;
    int count=1;
    int scanCount=0;
    ArrayList<ScanPostData> postInventoryLists;
    RecyclerView Scantablerv;
    HelperFunctions helperFunctions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        setHeaderTitle(true,"Inventory");


        /*updateConnectionStatus(ReaderManager.getInstance().isConnected());

        ReaderManager.getInstance().setStatusListener(isConnected -> {
            runOnUiThread(() -> updateConnectionStatus(isConnected));
        });*/

        helperFunctions=new HelperFunctions();

        ReaderManager.getInstance().setReaderPower(30);
        uniqueSet = new HashSet<>();
        serverEPCmSet = new HashSet<>();
        foundEpcSet = new HashSet<>();
        serverMap = new HashMap<>();
        productList =new ArrayList<>();
        tagList = new ArrayList<HashMap<String, String>>();

        ScanBtn = (Button) findViewById(R.id.scanBtn);
        SaveBtn = (Button) findViewById(R.id.saveBtn);
        PlantText = (TextView) findViewById(R.id.spinplanttext);
        DeptText = (TextView) findViewById(R.id.spindepttext);
        Counttext = (TextView) findViewById(R.id.counttext);
        spinnerPlant = (Spinner) findViewById(R.id.spinplant);
        spinnerDept = (Spinner) findViewById(R.id.spindept);

        Scantablerv = (RecyclerView) findViewById(R.id.scantablerv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        Scantablerv.setLayoutManager(layoutManager);

        SharedPreferences prefs = getSharedPreferences("IdtUser", MODE_PRIVATE);
        String UserPlantN = prefs.getString("UPlantName","");
        String UserPlantID = prefs.getString("UPlantID","0");

        PlantText.setText(UserPlantN);
        plantid = UserPlantID;
//        PlantText.setText("1");
//        plantid = "1";
        DeptText.setText("1");
        deptid = "1";

        finaljsonBody = new JSONObject();

        ScanBtn.setEnabled(false);
        ScanBtn.setBackgroundResource(R.drawable.bgstyle4);
        SaveBtn.setEnabled(false);
        SaveBtn.setBackgroundResource(R.drawable.bgstyle4);

        getInventoryDataFromServer(plantid,deptid);
        ScanBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readTag();
            }
        });
        SaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postdatatoServer();
            }
        });
    }
    @Override
    protected void onPause() {
        super.onPause();
        stopInventory();
    }
    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == 293) {
            if (allowed) {
                if (event.getRepeatCount() == 0) {
                    readTag();
                    Log.d("AUTOERROR", "TriggeredPressed TrueeE");
                }
            }else{
                Toast.makeText(getApplicationContext(), "No Data Found to Start Inventory", Toast.LENGTH_LONG).show();
            }
        }
        return super.onKeyDown(keyCode, event);
    }
    private void readTag() {
        if (ScanBtn.getText().equals("Scan")&&ScanBtn.isEnabled()) {

            ScanBtn.setText("Stop");
            ScanBtn.setBackgroundResource(R.drawable.bgstyle2);
            ReaderManager.getInstance(this)
                    .startContinuousRead(new ContinuousTagCallback() {

                        @Override
                        public void onTagRead(String epc, String rssi) {
                            runOnUiThread(() -> handleContinuousTag(epc, rssi));
                        }

                        @Override
                        public void onError(String reason) {
                            runOnUiThread(() ->
                                    helperFunctions.showToastMessage(getApplicationContext(), reason));
                        }
                        @Override
                        public void onStopped() {
                            runOnUiThread(() -> {
                                Log.d("RFID", "Continuous Reading Stopped");
                            });
                        }
                    });
        }
        else {
            stopInventory();
        }

    }
    private void handleContinuousTag(String epc, String rssi) {
        if (!uniqueSet.contains(epc)) {
            if (serverEPCmSet.contains(epc)) {
                foundEpcSet.add(epc);
                addEPCToList(epc, rssi);
                ReaderManager.getInstance().initSound();
            }
            Log.e("EPCFOUND", "Size=" + foundEpcSet.size());
        }
        uniqueSet.add(epc);
    }
    private void stopInventory() {
        if (ReaderManager.getInstance().isContinuousReading()) {
            ReaderManager.getInstance().stopContinuousRead();
                ScanBtn.setText("Scan");
                ScanBtn.setBackgroundResource(R.drawable.bgstyle2);
        }
        else {
//            HelperFunctions.showToastMessage(this,"Inventory Stop");
            Log.d("StopInv","Inventory Stop");
        }
    }
    private void addEPCToList(String epc, String rssi) {
        Log.d("AddEPCToList",":::EPC CHECK:::"+epc);
        if (!TextUtils.isEmpty(epc)) {
            int index = checkIsExist(epc);

            map = new HashMap<String, String>();
            map.put("tagUii", epc);
            map.put("tagCount", String.valueOf(1));
            map.put("tagRssi", rssi);
            String sa1 = epc;
            Log.d("EPC", sa1);

            if (index == -1) {

                tagList.add(map);
                Log.d("HEYYY",epc);
                if (serverMap.toString().contains(epc)){
                    try {
                        productList.get(serverMap.get(epc)).setMatchTag(true);
                        productList.get(serverMap.get(epc)).setStatus("Found");
                        setDatatomyobject();
                        inventoryAdapter = new InventoryAdapter(InventoryActivity.this, productList);
                        Scantablerv.setAdapter(inventoryAdapter);
                        inventoryAdapter.notifyDataSetChanged();
                        scanCount= count++;
                        Counttext.setText(scanCount+"/"+totalval);

                        Log.e("MyCountVal","::"+scanCount);
                    }catch (IndexOutOfBoundsException e){
                        Log.d("AddEpcToListError", Objects.requireNonNull(e.getMessage()));
                    }
                }
            } else {
                int tagcount = Integer.parseInt(
                        tagList.get(index).get("tagCount"), 10) + 1;

                map.put("tagCount", String.valueOf(tagcount));

                tagList.set(index, map);
            }

        }
    }
    public int checkIsExist(String strEPC) {
        int existFlag = -1;
        if (isEmpty(strEPC)) {
            return existFlag;
        }
        String tempStr = "";
        for (int i = 0; i < tagList.size(); i++) {
            HashMap<String, String> temp = new HashMap<String, String>();
            temp = tagList.get(i);
            tempStr = temp.get("tagUii");
            if (strEPC.equals(tempStr)) {
                existFlag = i;
                break;
            }
        }
        return existFlag;
    }
    public static boolean isEmpty(CharSequence cs) {

        return cs == null || cs.length() == 0;

    }
    private void setDatatomyobject() {
        dlArray = new JSONArray();
        JSONObject jsonBody = new JSONObject();
        try {
            int a = productList.size();
            for (int i=0;i<a;i++){
                createDocument(productList.get(i).getId(),productList.get(i).getTagId(),productList.get(i).getAssetPrimaryCode(),productList.get(i).getStatus());
            }
            jsonBody.put("scan_data", dlArray);
            jsonBody.put("user_name","admin");
            jsonBody.put("scan_date_time",
                    HelperFunctions.getTodaysDate(HelperFunctions.DateFormats.DATETIME_FORMAT_DB));
            finaljsonBody = jsonBody;

            String ABBCD = jsonBody.toString();

            Log.i("MyObject", "Hello: mmmmmmmmmmm: Data: " + jsonBody);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private JSONArray createDocument(String id, String tagid, String asset_primary_code, String scan_status) {
        int a = productList.size();

        JSONObject dlObject = new JSONObject();
        try {
            dlObject.put("id", id);
            dlObject.put("tag_id", tagid);
            dlObject.put("asset_primary_code", asset_primary_code);
            dlObject.put("scan_status", scan_status);

            dlArray.put(dlObject);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return dlArray;
    }
    private void getInventoryDataFromServer(String plantid,String deptid) {
        try{

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(HelperFunctions.BaseURL)
//                .client(SSLCertificate.getUnsafeOkHttpClient())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            API api = retrofit.create(API.class);
            Call<PAList> call = api.getdatapdlist(plantid,deptid);
            call.enqueue(new Callback<PAList>() {
                @Override
                public void onResponse(Call<PAList> call, Response<PAList> response) {
                    PAList data = response.body();
                    if (data.getMsg().equals("Found")) {
                        productList = data.getData();
                        int a = productList.size();
                        totalval = productList.size();
                        Counttext.setText("0/"+totalval);
                        postInventoryLists = new ArrayList<>();
                        for (int i=0;i<a;i++){
                            postInventoryLists.add(new ScanPostData(productList.get(i).getId(),
                                    productList.get(i).getTagId(), "Not Found"));
                            productList.get(i).setStatus("Not Found");
                            serverEPCmSet.add(productList.get(i).getTagId());
                            serverMap.put(productList.get(i).getTagId(),i);
                        }

                        inventoryAdapter = new InventoryAdapter(getApplicationContext(), productList);
                        Scantablerv.setAdapter(inventoryAdapter);
                        inventoryAdapter.notifyDataSetChanged();
                        allowed = true;
                        ScanBtn.setEnabled(true);
                        ScanBtn.setBackgroundResource(R.drawable.bgstyle2);
                        SaveBtn.setEnabled(true);
                        SaveBtn.setBackgroundResource(R.drawable.bgstyle2);
                    } else {

                        allowed = false;
                        ScanBtn.setEnabled(false);
                        ScanBtn.setBackgroundResource(R.drawable.bgstyle4);
                        SaveBtn.setEnabled(false);
                        SaveBtn.setBackgroundResource(R.drawable.bgstyle4);
                        Counttext.setText(0+"/"+0);
                        helperFunctions.showToastMessage(InventoryActivity.this, data.getMsg());
                        if (productList!=null){
                            productList.clear();
                            inventoryAdapter = new InventoryAdapter(getApplicationContext(), productList);
                            Scantablerv.setAdapter(inventoryAdapter);
                            inventoryAdapter.notifyDataSetChanged();
                        }

                    }
                }
                @Override
                public void onFailure(Call<PAList> call, Throwable t) {
                    HelperFunctions.showToastMessage(InventoryActivity.this, t.toString());
                    Log.d("GetInventoryData Failure",t.toString());
                }
            });
        }
        catch(Exception e){
            Log.d("Exception",e.getMessage().toString());
        }

    }
    private void postdatatoServer(){
        try{
            if(postInventoryLists.isEmpty()){
                Toast.makeText(InventoryActivity.this, "No tags found, Scan before saving.",
                        Toast.LENGTH_LONG).show();
            }
            else{
                stopInventory();
                Date currentTime = Calendar.getInstance().getTime();
                String Date = currentTime.toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                String currentDateandTime = sdf.format(new Date());
                Log.d("TIMING"," :: "+Date+" :: "+currentDateandTime);
                scanPostList = new ScanPostList();
                scanPostList.setScanData(postInventoryLists);
                scanPostList.setUserName("Admin");
                scanPostList.setScanDateTime(currentDateandTime);

                Gson gson = new GsonBuilder()
                        .setLenient()
                        .create();
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(HelperFunctions.BaseURL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                API api = retrofit.create(API.class);
                Call<PostArray> call = api.postinventorydata(scanPostList);
//        Call<PostInventory> call = api.postinventorydata(postInventoryLists,"android",roomid,currentDateandTime);
                call.enqueue(new Callback<PostArray>() {
                    @Override
                    public void onResponse(Call<PostArray> call, Response<PostArray> response) {
                        if (response.body() != null) {
                            PostArray data = response.body();
                            Toast.makeText(InventoryActivity.this, data.getData(), Toast.LENGTH_LONG).show();
                            if(data.getData().equals(" Sucess!")){
                                scanCount=0;
                                getInventoryDataFromServer(plantid,deptid);
                            }
                        } else {
                            Toast.makeText(InventoryActivity.this, "Something went wrong, Please try " +
                                    "again.", Toast.LENGTH_LONG).show();
                            Log.e(TAG, "Failed to post data. Response code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<PostArray> call, Throwable t) {

                        String errorMessage = t.getMessage();
                        if (t instanceof UnknownHostException) {
                            errorMessage = "Please check your internet connection.";
                        }
                        Toast.makeText(InventoryActivity.this, errorMessage, Toast.LENGTH_LONG).show();
                        Log.d("PostInventoryList", "onFailure: "+t.toString());

                    }
                });
            }
        }
        catch(Exception e){
            Log.d("Exception",e.getMessage().toString());
        }
    }
}