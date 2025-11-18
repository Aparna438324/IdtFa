package com.proj8.idt_fa.ApiHandling;

import com.google.gson.JsonObject;
import com.proj8.idt_fa.Model.DeptSpin.DeptSpin;
import com.proj8.idt_fa.Model.Identify.IdentifyData;
import com.proj8.idt_fa.Model.Login.LoginModel;
import com.proj8.idt_fa.Model.PlantAssetList.PAList;
import com.proj8.idt_fa.Model.PlantSpin.PlantSpin;
import com.proj8.idt_fa.Model.RegisterSend.JORegisteration;
import com.proj8.idt_fa.Model.Registeration.RegPlantVal;
import com.proj8.idt_fa.Model.ScanPost.PostArray;
import com.proj8.idt_fa.Model.ScanPost.ScanPostList;
import com.proj8.idt_fa.Model.Transfer.FinalTransfer;
import com.proj8.idt_fa.Model.Transfer.TagTransferModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface API {

    @FormUrlEncoded
    @POST("Login")
    Call<LoginModel> getloginmodels(
            @Field("username") String email,
            @Field("password") String password
    );

    @GET("Plant_Master")
    Call<PlantSpin> getPlant();

    @GET("Dept_Master")
    Call<DeptSpin> getDept();
    @GET("Dept_Master")
    Call<DeptSpin> getDeptSpin();

    @FormUrlEncoded
    @POST("Get_Assets_Data")
    Call<PAList> getdatapdlist(
            @Field("plant") String id1,
            @Field("department") String id2
    );

    @FormUrlEncoded
    @POST("Get_Assets_Data2")
    Call<PAList> getAssetDataList(
            @Field("plant") String plantId
    );

    @POST("Post_Inventry_Data")
    Call<PostArray> postinventorydata(@Body ScanPostList postA );

    @GET("Scan_Data")
    Call<IdentifyData> getIdentifyList(@Query("tagid") String tagid);

    @GET("Get_Tagdata")
    Call<TagTransferModel> getTransferList(@Query("tagid") String tagid);

    @POST("Post_Temp_TransferData")
    Call<FinalTransfer> postdatatransfer(@Body JsonObject jsonObject );

    @GET("Get_Temp_Tagdata")
    Call<RegPlantVal> getPlantregdata(@Query("plant") String plantid);

    @POST("Send_Asset_Temp_to_trans")
    Call<FinalTransfer> postdatareg(@Body JORegisteration joRegisteration );

}
