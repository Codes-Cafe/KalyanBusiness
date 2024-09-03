package com.app.kalyanbusiness.baseapp.networking;

import com.app.kalyanbusiness.baseapp.model.BaseResponse;
import com.app.kalyanbusiness.models.SliderModel;
import com.app.kalyanbusiness.models.UserModel;
import com.app.kalyanbusiness.requestModel.LoginRequestModel;
import com.app.kalyanbusiness.requestModel.RegisterRequestModel;
import com.app.kalyanbusiness.requestModel.SupplierRequestModel;
import com.app.kalyanbusiness.responseModel.CategoriesResponseModel;
import com.app.kalyanbusiness.responseModel.RegisterResponseModel;
import com.app.kalyanbusiness.responseModel.SupplierRequestResponseModel;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface WebServices {

    //auth
    @POST("vendor_portal/login.php")
    Single<BaseResponse<UserModel>> login(@Body LoginRequestModel requestLogin);

    @POST("vendor_portal/register.php")
    Single<BaseResponse<RegisterResponseModel>> register(@Body RegisterRequestModel registerRequestModel);

    //user
    @POST("vendor_portal/vendor_request.php")
    Single<BaseResponse<UserModel>> updateUser(@Body String userid);

    @POST("vendor_portal/vendor_request.php")
    Single<BaseResponse<SupplierRequestResponseModel>> supplierRequest(@Body SupplierRequestModel requestResponseModel);


    //products
    @GET("vendor_portal/Products/getCategory.php")
    Single<BaseResponse<ArrayList<CategoriesResponseModel>>> getCategories();

}
