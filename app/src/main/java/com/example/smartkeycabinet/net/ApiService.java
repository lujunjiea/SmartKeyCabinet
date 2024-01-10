package com.example.smartkeycabinet.net;

import com.example.smartkeycabinet.model.BoxsModel;
import com.example.smartkeycabinet.model.CarListBean;
import com.example.smartkeycabinet.model.OperatorRecordBean;
import com.example.smartkeycabinet.model.OperatorRecordModel;
import com.example.smartkeycabinet.model.ReturnCarModel;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import io.reactivex.rxjava3.core.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface ApiService {
    /**
     * 校验取车码
     * @param code 取车码
     */
    @GET("carBoxRecord/checkCode")
    Observable<BaseResponse<String>> checkGetCarCode(@Query("code") String code);

    /**
     * 管理员登录
     */
    @POST("carBoxRecord/boxAdminLogin")
    Observable<BaseResponse<String>> checkAdminPw(@Body RequestBody body);

    /**
     * 操作记录列表
     */
    @POST("carBoxRecord/pageList")
    Observable<BaseResponse<OperatorRecordBean>> getOperatorRecordList(@Body RequestBody body);

    /**
     * 获取柜子列表
     */
    @GET("carBoxRecord/getBoxList")
    Observable<BaseResponse<List<BoxsModel>>> getBoxList();

    /**
     * 上传操作记录
     */
    @POST("carBoxRecord/save")
    Observable<BaseResponse<JSONObject>> saveOperatorRecord(@Body RequestBody body);

    /**
     * 获取车位列表
     */
    @POST("carPlace/carPlaceList")
    Observable<BaseResponse<CarListBean>> getCarPositionList(@Body RequestBody body);

    /**
     * 还车
     */
    @POST("carBoxRecord/returnTheCar")
    Observable<BaseResponse<ReturnCarModel>> returnCar(@Body RequestBody body);

    /**
     * 更新箱格状态
     */
    @POST("carBoxRecord/updateBoxStatus")
    Observable<BaseResponse<String>> updateBoxStatus(@Body RequestBody body);
}
