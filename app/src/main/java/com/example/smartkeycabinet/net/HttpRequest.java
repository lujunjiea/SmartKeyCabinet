package com.example.smartkeycabinet.net;

import static com.example.smartkeycabinet.net.util.RequestBodyUtils.getBody;
import static com.example.smartkeycabinet.net.util.RxBindUtils.setMainSubscribe;

import com.example.smartkeycabinet.model.AdminLoginModel;
import com.example.smartkeycabinet.model.BoxsModel;
import com.example.smartkeycabinet.model.CarListBean;
import com.example.smartkeycabinet.model.GetCarListBodyModel;
import com.example.smartkeycabinet.model.GetOperatorRecordBodyModel;
import com.example.smartkeycabinet.model.OperatorRecordBean;
import com.example.smartkeycabinet.model.OperatorRecordModel;
import com.example.smartkeycabinet.model.ReturnCarBody;
import com.example.smartkeycabinet.model.ReturnCarModel;
import com.example.smartkeycabinet.model.SaveRecordModel;
import com.example.smartkeycabinet.model.UpdateBoxStatusBody;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import io.reactivex.rxjava3.core.Observer;

public class HttpRequest {

    public static final ApiService apiService = ApiServiceManager.getInstance().create(ApiService.class);

    /**
     * 校验取车码
     */
    public static void checkCarCode(String code,
                                      Observer<BaseResponse<String>> observer) {
        setMainSubscribe(apiService.checkGetCarCode(code), observer);
    }

    /**
     * 管理员登录
     */
    public static void checkAdminPw(String pw,
                                    Observer<BaseResponse<String>> observer) {
        setMainSubscribe(apiService.checkAdminPw(getBody(new AdminLoginModel(pw))), observer);
    }

    /**
     * 操作记录列表
     */
    public static void getOperatorRecordList(GetOperatorRecordBodyModel bodyModel,
                                    Observer<BaseResponse<OperatorRecordBean>> observer) {
        setMainSubscribe(apiService.getOperatorRecordList(getBody(bodyModel)), observer);
    }

    /**
     * 获取柜子列表
     */
    public static void getBoxList(Observer<BaseResponse<List<BoxsModel>>> observer) {
        setMainSubscribe(apiService.getBoxList(), observer);
    }

    /**
     * 上传操作记录
     */
    public static void saveOperatorRecord(SaveRecordModel bodyModel,
                                          Observer<BaseResponse<JSONObject>> observer) {
        setMainSubscribe(apiService.saveOperatorRecord(getBody(bodyModel)), observer);
    }

    /**
     * 上传操作记录
     */
    public static void getCarPositionList(GetCarListBodyModel bodyModel,
                                          Observer<BaseResponse<CarListBean>> observer) {
        setMainSubscribe(apiService.getCarPositionList(getBody(bodyModel)), observer);
    }

    /**
     * 还车
     */
    public static void returnCar(ReturnCarBody bodyModel,
                                 Observer<BaseResponse<ReturnCarModel>> observer) {
        setMainSubscribe(apiService.returnCar(getBody(bodyModel)), observer);
    }

    /**
     * 更新箱格状态
     */
    public static void updateBoxStatus(UpdateBoxStatusBody bodyModel,
                                       Observer<BaseResponse<String>> observer) {
        setMainSubscribe(apiService.updateBoxStatus(getBody(bodyModel)), observer);
    }
}
