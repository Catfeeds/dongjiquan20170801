package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceApkVersionListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceIntIdListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceSinerListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceStringListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.bean.AddCollectionResult;
import com.weiaibenpao.demo.chislim.bean.AddCommentResult;
import com.weiaibenpao.demo.chislim.bean.AddSportResult;
import com.weiaibenpao.demo.chislim.bean.ApkVersionBean;
import com.weiaibenpao.demo.chislim.bean.BooleanResult;
import com.weiaibenpao.demo.chislim.bean.CommentResult;
import com.weiaibenpao.demo.chislim.bean.GetCollectionResult;
import com.weiaibenpao.demo.chislim.bean.GetIntId;
import com.weiaibenpao.demo.chislim.bean.GetUserMarkResult;
import com.weiaibenpao.demo.chislim.bean.HumorResult;
import com.weiaibenpao.demo.chislim.bean.Li_MusicResult;
import com.weiaibenpao.demo.chislim.bean.Li_Praise_Result;
import com.weiaibenpao.demo.chislim.bean.Li_ReportComment_Result;
import com.weiaibenpao.demo.chislim.bean.LunBoTuBeanResult;
import com.weiaibenpao.demo.chislim.bean.MainBeanResult;
import com.weiaibenpao.demo.chislim.bean.NewTeachGifImageResult;
import com.weiaibenpao.demo.chislim.bean.NewTeachPlanResut;
import com.weiaibenpao.demo.chislim.bean.NewTeachResult;
import com.weiaibenpao.demo.chislim.bean.NotesMessageResult;
import com.weiaibenpao.demo.chislim.bean.PraiseResult;
import com.weiaibenpao.demo.chislim.bean.RegistResult;
import com.weiaibenpao.demo.chislim.bean.SendHumorResult;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResultBean;
import com.weiaibenpao.demo.chislim.bean.SuccessResult;
import com.weiaibenpao.demo.chislim.bean.TeachResult;
import com.weiaibenpao.demo.chislim.bean.ThemeResule;
import com.weiaibenpao.demo.chislim.bean.TravelInfoResult;
import com.weiaibenpao.demo.chislim.bean.TravelNotesResult;
import com.weiaibenpao.demo.chislim.bean.TravelNotesUserItemResult;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.bean.UploadResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.model.AddCollectionModel;
import com.weiaibenpao.demo.chislim.model.AddCommentModel;
import com.weiaibenpao.demo.chislim.model.AddNotesThemeUpLoadModel;
import com.weiaibenpao.demo.chislim.model.AddNotesUpLoadModel;
import com.weiaibenpao.demo.chislim.model.AddUserBackModel;
import com.weiaibenpao.demo.chislim.model.ApkVersionModel;
import com.weiaibenpao.demo.chislim.model.CommetModel;
import com.weiaibenpao.demo.chislim.model.GetCollectionModel;
import com.weiaibenpao.demo.chislim.model.GetDisModel;
import com.weiaibenpao.demo.chislim.model.GetStartModel;
import com.weiaibenpao.demo.chislim.model.GetUserMarkModel;
import com.weiaibenpao.demo.chislim.model.HumorModel;
import com.weiaibenpao.demo.chislim.model.LunBoTuModel;
import com.weiaibenpao.demo.chislim.model.MainBeanModel;
import com.weiaibenpao.demo.chislim.model.MusicSinerModel;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.model.NewTeachGifImageModel;
import com.weiaibenpao.demo.chislim.model.NewTeachModel;
import com.weiaibenpao.demo.chislim.model.NewTeachPlanModel;
import com.weiaibenpao.demo.chislim.model.NewTeachTab1Model;
import com.weiaibenpao.demo.chislim.model.NewTeachTab2Model;
import com.weiaibenpao.demo.chislim.model.NewTeachTabModel;
import com.weiaibenpao.demo.chislim.model.NotesMessageModel;
import com.weiaibenpao.demo.chislim.model.PraiseModel;
import com.weiaibenpao.demo.chislim.model.SendHumorModel;
import com.weiaibenpao.demo.chislim.model.SuccessModel;
import com.weiaibenpao.demo.chislim.model.SupportModel;
import com.weiaibenpao.demo.chislim.model.TeachModel;
import com.weiaibenpao.demo.chislim.model.ThemeModel;
import com.weiaibenpao.demo.chislim.model.TravelInfoModel;
import com.weiaibenpao.demo.chislim.model.TravelMessageModel;
import com.weiaibenpao.demo.chislim.model.TravelModel;
import com.weiaibenpao.demo.chislim.model.TravelNotesModel;
import com.weiaibenpao.demo.chislim.model.TravelNotesUserItemModel;
import com.weiaibenpao.demo.chislim.model.UpdateMarkModel;
import com.weiaibenpao.demo.chislim.model.UploadModel;
import com.weiaibenpao.demo.chislim.music.bean.MusicSinerResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.loopj.android.http.AsyncHttpClient.log;
import static com.umeng.socialize.utils.DeviceConfig.context;

/**
 * Created by lenovo on 2016/9/27.
 */

public class GetIntentData {
   boolean flag = false;
    private ACache mCache;

    private GetInterfaceSinerListener sinerListener;             //music对象
    private GetInterfaceVideoListener interfaceVideo;             //list
    private GetInterfaceBooleanListener interfaceBoolean;          //boolean
    private GetInterfaceIntIdListener interfaceIntId;              //int
    private GetInterfaceApkVersionListener apkVersionListener;
    private GetObjectListener getObjectListener;                        //object
    private GetInterfaceStringListener getInterfaceStringListener;     //字符串

    //定义接口入住对象
    public void setGetIntentDataListener(GetInterfaceVideoListener interfaceVideo){
        this.interfaceVideo = interfaceVideo;
    }

    //定义接口入住对象
    public void setGetInterfaceBooleanListener(GetInterfaceBooleanListener interfaceBoolean){
        this.interfaceBoolean = interfaceBoolean;
    }

    //定义接口入住对象
    public void setGetInterfaceIntIdListener(GetInterfaceIntIdListener interfaceIntId){
        this.interfaceIntId = interfaceIntId;
    }

    //定义接口入住对象
    public void setGetInterfaceSinerListener(GetInterfaceSinerListener sinerListener){
        this.sinerListener = sinerListener;
    }


    //定义接口入住对象
    public void setGetInterfaceApkVersionListener(GetInterfaceApkVersionListener apkVersionListener){
        this.apkVersionListener = apkVersionListener;
    }

    //定义接口入住对象
    public void setGetObjectListener(GetObjectListener getObjectListener){
        this.getObjectListener = getObjectListener;
    }

    //定义接口入住对象
    public void setGetStringListener(GetInterfaceStringListener getInterfaceStringListener){
        this.getInterfaceStringListener = getInterfaceStringListener;
    }

    /**
     * 获取教程
     * @param context
     */
    public void getTeach(final Context context){
        mCache = ACache.get(context);    //实例化缓存
        Call<TeachResult> call = TeachModel.getModel().getService().getResult("getAll");

        call.enqueue(new Callback<TeachResult>() {
            @Override
            public void onResponse(Call<TeachResult> call, Response<TeachResult> response) {
                if (response.isSuccessful()) {
                    TeachResult result = response.body();

                    if (result.getError() == 0) {
                        mCache.put("teachResult",result);
                        interfaceVideo.getDateList((ArrayList) result.getTeach());
                    }else{
                        Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TeachResult> call, Throwable t) {
               // Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拉去小众景点
     * @param context
     * @param dowhat
     * @param tab
     * @param i
     * @param num
     */
    public void getTravel(final Context context, String dowhat, final String tab, int i, int num){
        mCache = ACache.get(context);    //实例化缓存
        Call<TravelResult> call = TravelModel.getModel().getService().getResult(dowhat,tab,i,num);

        call.enqueue(new Callback<TravelResult>() {
            @Override
            public void onResponse(Call<TravelResult> call, Response<TravelResult> response) {
                if (response.isSuccessful()) {
                    TravelResult result = response.body();
                    if(result.getError() == 0 && result.getTravel().size() > 0){
                        if(tab.equals("location") && result.getTravel().size() > 0){
                            mCache.put("travelNotesResultlocation",result);
                            interfaceVideo.getDateList((ArrayList) result.getTravel());
                        }else if(tab.equals("foreign") && result.getTravel().size() > 0){
                            mCache.put("travelNotesResultforeign",result);
                            interfaceVideo.getDateList((ArrayList) result.getTravel());
                        }else if(tab.equals("taiwan") && result.getTravel().size() > 0){
                            mCache.put("travelNotesResulttaiwan",result);
                            interfaceVideo.getDateList((ArrayList) result.getTravel());
                        }else if(tab.equals("favotite") && result.getTravel().size() > 0){
                            mCache.put("travelNotesResultfavotite",result);
                            interfaceVideo.getDateList((ArrayList) result.getTravel());
                        }else{
                         //   Toast.makeText(context,"暂无更多数据...",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        interfaceVideo.getDateList(new ArrayList());
                    }
                }
            }

            @Override
            public void onFailure(Call<TravelResult> call, Throwable t) {
                interfaceVideo.getDateList(new ArrayList());
              //Toast.makeText(context,"检查网络连接...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拉去小众景点详情
     * @param context
     * @param dowhat
     */
    public void getTravelInfo(final Context context, String dowhat, final int t_id){
        mCache = ACache.get(context);    //实例化缓存
        Call<TravelInfoResult> call = TravelInfoModel.getModel().getService().getResult(dowhat,t_id);

        call.enqueue(new Callback<TravelInfoResult>() {
            @Override
            public void onResponse(Call<TravelInfoResult> call, Response<TravelInfoResult> response) {
                if (response.isSuccessful()) {
                    TravelInfoResult result = response.body();

                    if (result.getError() == 0 && result.getTravel_info().size() > 0) {
                        mCache.put("travelInfoResult" + t_id,result);
                        interfaceVideo.getDateList((ArrayList) result.getTravel_info());
                    }else{
                       // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TravelInfoResult> call, Throwable t) {
              //  Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 拉取游记
     * @param context
     * @param dowhat
     */
    public void getTravelNotes(final Context context,String dowhat,int i,int num){
        mCache = ACache.get(context);    //实例化缓存

        Call<TravelNotesResult> call = TravelNotesModel.getModel().getService().getResult(dowhat,i,num);

        call.enqueue(new Callback<TravelNotesResult>() {
            @Override
            public void onResponse(Call<TravelNotesResult> call, Response<TravelNotesResult> response) {
                if (response.isSuccessful()) {
                    TravelNotesResult result = response.body();
                    if( result.getTravel_notes().size() > 0){
                        mCache.put("travelNotesResult",result);
                    }
                    interfaceVideo.getDateList((ArrayList) result.getTravel_notes());
                }
            }

            @Override
            public void onFailure(Call<TravelNotesResult> call, Throwable t) {
                interfaceVideo.getDateList(new ArrayList());
            }
        });
    }

    /**
     * 点赞
     * @param context
     * @param dowhat
     */
    public void Support(final Context context,String dowhat,int userID,int tn_id,int tab1){
        Log.i("赞","点赞  "+userID+"----"+tn_id + "======" +tab1);
        //Call<BooleanResult> call = SupportModel.getModel().getService().getResult("addOneSupport",17,3,1);
        Call<BooleanResult> call = SupportModel.getModel().getService().getResult("delOneSupportNotes",17,3,0);

        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                    //interfaceBoolean.getBoolean(result.isFlag());
                }
            }
            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {
                Toast.makeText(context,"请检查网络...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询留言
     * @param context
     * @param dowhat
     */
    public void getMessage(final Context context,String dowhat,int tn_id){
        Call<NotesMessageResult> call = NotesMessageModel.getModel().getService().getResult(dowhat,tn_id);

        call.enqueue(new Callback<NotesMessageResult>() {
            @Override
            public void onResponse(Call<NotesMessageResult> call, Response<NotesMessageResult> response) {
                if (response.isSuccessful()) {
                    NotesMessageResult result = response.body();
                    if (result.getError() == 0) {
                        interfaceVideo.getDateList((ArrayList) result.getTravel_message());
                    }else{
                      //  Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NotesMessageResult> call, Throwable t) {
               // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询某个用户发表的所有游记
     * @param context
     * @param dowhat
     */
    public void getNotesUserItemMessage(final Context context, String dowhat, final int tn_id){
        mCache = ACache.get(context);    //实例化缓存
        Call<TravelNotesUserItemResult> call = TravelNotesUserItemModel.getModel().getService().getResult(dowhat,tn_id);

        call.enqueue(new Callback<TravelNotesUserItemResult>() {
            @Override
            public void onResponse(Call<TravelNotesUserItemResult> call, Response<TravelNotesUserItemResult> response) {
                if (response.isSuccessful()) {
                    TravelNotesUserItemResult result = response.body();
                    if(result.getTravel_notes_item().size() > 0){
                        mCache.put("travelNotesUserItemResult" + tn_id,result);
                    }
                    interfaceVideo.getDateList((ArrayList) result.getTravel_notes_item());
                }
            }
            @Override
            public void onFailure(Call<TravelNotesUserItemResult> call, Throwable t) {
                interfaceVideo.getDateList(new ArrayList());
            }
        });
    }

    /**
     * 添加一个游记收藏
     * @param context
     * @param dowhat
     */
    public void getNotesStart(final Context context,String dowhat,int userID,int tn_id,int tab2){
        Call<BooleanResult> call = GetStartModel.getModel().getService().getResult(dowhat,userID,tn_id,tab2);

        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                    interfaceBoolean.getBoolean(result.isFlag());
                }
            }

            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {
               // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 查询所有教程
     * @param context
     * @param dowhat
     */
    public void getAllTeach (final Context context,String dowhat,int i,int n){
        mCache = ACache.get(context);    //实例化缓存
        Call<NewTeachResult> call = NewTeachModel.getModel().getService().getResult(dowhat,i,n);

        call.enqueue(new Callback<NewTeachResult>() {
            @Override
            public void onResponse(Call<NewTeachResult> call, Response<NewTeachResult> response) {
                if (response.isSuccessful()) {
                    NewTeachResult result = response.body();
                    if(result.getNewTeachBean().size() > 0){
                        mCache.put("newTeachResult",result);
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachBean());
                    }else{
                        interfaceVideo.getDateList(new ArrayList());
                       // Toast.makeText(context,"暂无更多数据...",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewTeachResult> call, Throwable t) {
                interfaceVideo.getDateList(new ArrayList());
                Toast.makeText(context,"请检查网络了解...",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询器械或非器械所有教程
     * @param context
     * @param dowhat
     */
    public void getAllTeachTab1 (final Context context,String dowhat,int tab1){
        Call<NewTeachResult> call = NewTeachTab1Model.getModel().getService().getResult(dowhat,tab1);

        call.enqueue(new Callback<NewTeachResult>() {
            @Override
            public void onResponse(Call<NewTeachResult> call, Response<NewTeachResult> response) {
                if (response.isSuccessful()) {
                    NewTeachResult result = response.body();
                    if (result.getError() == 0) {
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachBean());
                    }else{
                     //   Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewTeachResult> call, Throwable t) {
               // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询身体不同部位所有教程
     * @param context
     * @param dowhat
     */
    public void getAllTeachTab2 (final Context context, String dowhat, final int tab2){
        mCache = ACache.get(context);    //实例化缓存
        Call<NewTeachResult> call = NewTeachTab2Model.getModel().getService().getResult(dowhat,tab2);

        call.enqueue(new Callback<NewTeachResult>() {
            @Override
            public void onResponse(Call<NewTeachResult> call, Response<NewTeachResult> response) {
                if (response.isSuccessful()) {
                    NewTeachResult result = response.body();
                    if (result.getError() == 0 && result.getNewTeachBean().size() > 0) {
                        mCache.put("newTeachResult" + tab2,result);
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachBean());
                    }else{
                       // Toast.makeText(context,"暂无更多数据...",Toast.LENGTH_SHORT).show();
                        interfaceVideo.getDateList(new ArrayList());
                    }
                }
            }

            @Override
            public void onFailure(Call<NewTeachResult> call, Throwable t) {
                Toast.makeText(context,"请检查您的网络...",Toast.LENGTH_SHORT).show();
                interfaceVideo.getDateList(new ArrayList());
            }
        });
    }

    /**
     * 查询身体不同部位所有教程
     * @param context
     * @param dowhat
     */
    public void getAllTeachTab (final Context context,String dowhat,int tab1,int tab2){
        Call<NewTeachResult> call = NewTeachTabModel.getModel().getService().getResult(dowhat,tab1,tab2);

        call.enqueue(new Callback<NewTeachResult>() {
            @Override
            public void onResponse(Call<NewTeachResult> call, Response<NewTeachResult> response) {
                if (response.isSuccessful()) {
                    NewTeachResult result = response.body();
                    if (result.getError() == 0) {
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachBean());
                    }else{
                    //    Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<NewTeachResult> call, Throwable t) {
               // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询训练计划
     * @param context
     * @param dowhat
     */
    public void getAllTeachPlan (final Context context,String dowhat,int teachID){
        Call<NewTeachPlanResut> call = NewTeachPlanModel.getModel().getService().getResult(dowhat,teachID);

        call.enqueue(new Callback<NewTeachPlanResut>() {
            @Override
            public void onResponse(Call<NewTeachPlanResut> call, Response<NewTeachPlanResut> response) {
                if (response.isSuccessful()) {
                    NewTeachPlanResut result = response.body();
                    if (result.getError() == 0) {
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachMenu());
                    }else{
                       // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<NewTeachPlanResut> call, Throwable t) {
               // Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 查询gif动画
     * @param dowhat
     */
    public void getAllTeachGifIGifImage (final Context context, String dowhat, final int teachID){
        mCache = ACache.get(context);    //实例化缓存
        Call<NewTeachGifImageResult> call = NewTeachGifImageModel.getModel().getService().getResult(dowhat,teachID);

        call.enqueue(new Callback<NewTeachGifImageResult>() {
            @Override
            public void onResponse(Call<NewTeachGifImageResult> call, Response<NewTeachGifImageResult> response) {
                if (response.isSuccessful()) {
                    NewTeachGifImageResult result = response.body();
                    if (result.getError() == 0 && result.getNewTeachGifImageBean().size() != 0) {
                        mCache.put("newTeachGifImageResult" + String.valueOf(teachID),result);
                        interfaceVideo.getDateList((ArrayList) result.getNewTeachGifImageBean());
                    }else{
                     //   Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<NewTeachGifImageResult> call, Throwable t) {
              //  Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发布游记
     * @param dowhat
     */
    public void AddNotesUpLoad (final Context context,String dowhat,int tn_id,String tn_name,StringBuffer tn_image,String tn_text){
        Call<BooleanResult> call = AddNotesUpLoadModel.getModel().getService().getResult(dowhat,tn_id,tn_name,tn_image,tn_text);

        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                        //发布成功
                        interfaceBoolean.getBoolean(result.isFlag());
                    Toast.makeText(context,"成功",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {
             //   Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 发布主题
     * @param dowhat
     */
    public void AddNotesThemeUpLoad (final Context context,String dowhat,String tn_title,String tn_address,int userID){
        Call<GetIntId> call = AddNotesThemeUpLoadModel.getModel().getService().getResult(dowhat,tn_title,tn_address,userID);

        call.enqueue(new Callback<GetIntId>() {
            @Override
            public void onResponse(Call<GetIntId> call, Response<GetIntId> response) {
                if (response.isSuccessful()) {
                    GetIntId result = response.body();
                    //发布成功
                    interfaceIntId.getDateIntId(result.getIntId());
                    Toast.makeText(context,"成功" + result.getIntId(),Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<GetIntId> call, Throwable t) {
                Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取歌手图片
     */
    public void getMusicSiner (String appiKey,String userName){
        Call<MusicSinerResult> call = MusicSinerModel.getModel().getService().getResult(appiKey,userName);

        call.enqueue(new Callback<MusicSinerResult>() {
            @Override
            public void onResponse(Call<MusicSinerResult> call, Response<MusicSinerResult> response) {
                if (response.isSuccessful()) {
                    MusicSinerResult result = response.body();
                    if(result.getCode() == 0){
                        sinerListener.getDateList(result.getData());
                    }else{
                        Log.i("错误码","---------" + result.getCode() + "---------");
                    }

                }
            }

            @Override
            public void onFailure(Call<MusicSinerResult> call, Throwable t) {
                Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取轮播图
     */
    public void getLunBoTu (Context context, String dowhat, final int tab, int i, int n){
        mCache = ACache.get(context);    //实例化缓存
        Call<LunBoTuBeanResult> call = LunBoTuModel.getModel().getService().getResult(dowhat,tab,i,n);

        call.enqueue(new Callback<LunBoTuBeanResult>() {
            @Override
            public void onResponse(Call<LunBoTuBeanResult> call, Response<LunBoTuBeanResult> response) {
                if (response.isSuccessful()) {
                    LunBoTuBeanResult result = response.body();
                    if(result.getError() == 0){
                        if(tab == 1){
                            mCache.put("lunBoTuBeanResultHappy",result);
                        }else if(tab == 2){
                            mCache.put("lunBoTuBeanResultTravel",result);
                        }else if(tab == 3){
                            mCache.put("lunBoTuBeanResultBuilding",result);
                        }

                        interfaceVideo.getDateList((ArrayList) result.getLunboTuBean());

                    }else{
                        Log.i("错误码","---------" + result.getError() + "---------");
                    }

                }
            }

            @Override
            public void onFailure(Call<LunBoTuBeanResult> call, Throwable t) {
            }
        });
    }

    /**
     * 获取apk版本
     */
    public void getApkVersion (String dowhat){
        Call<ApkVersionBean> call = ApkVersionModel.getModel().getService().getResult(dowhat,"chislim");

        call.enqueue(new Callback<ApkVersionBean>() {
            @Override
            public void onResponse(Call<ApkVersionBean> call, Response<ApkVersionBean> response) {
                if (response.isSuccessful()) {
                    ApkVersionBean result = response.body();
                    apkVersionListener.getDateApkVersion(result.getApkVersion(),result.getApkText());

                }else{

                }
            }
            @Override
            public void onFailure(Call<ApkVersionBean> call, Throwable t) {
            }
        });
    }


    /**
     * 游记留言
     */
    public void TravelMessageSend (String dowhat,int tra_tm_id,int tn_id,int userID,String tm_tex){
        Call<BooleanResult> call = TravelMessageModel.getModel().getService().getResult(dowhat,tra_tm_id,tn_id,userID,tm_tex);

        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                    interfaceBoolean.getBoolean(result.isFlag());
                }else{

                }
            }

            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {
             //   Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取首页数据
     */
    public void GetMainData (final Context context, String dowhat, int talk, int talkNum, int activity, int activityNum){
        mCache = ACache.get(context);    //实例化缓存
        Call<MainBeanResult> call = MainBeanModel.getModel().getService().getResult(dowhat,talk,talkNum,activity,activityNum);

        call.enqueue(new Callback<MainBeanResult>() {
            @Override
            public void onResponse(Call<MainBeanResult> call, Response<MainBeanResult> response) {
                if (response.isSuccessful()) {
                    MainBeanResult result = response.body();
                    if(result.getTalk().size() > 0){
                        mCache.put("mainData",result);
                    }
                    getObjectListener.getObject(result);
                }else{

                }
            }
            @Override
            public void onFailure(Call<MainBeanResult> call, Throwable t) {
              //  Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 上传运动记录
     */
    public void  GetSportData (int userId, int sportType, String altitudeHigh, String average,String boolPressure,String calories,String heartRate,String matchNumber,
                               String sportDate,String sportImgUrl,float totalDistance,String totalShin,String stepFrequency,String totalTime,String totalStep){
        Call<AddSportResult> call = MyModel.getModel().getService().addSports(userId,sportType,altitudeHigh,average,boolPressure,calories,heartRate,matchNumber,sportDate,
                sportImgUrl,totalDistance,totalShin,stepFrequency,totalTime,totalStep);

        call.enqueue(new Callback<AddSportResult>() {
            @Override
            public void onResponse(Call<AddSportResult> call, Response<AddSportResult> response) {
                if (response.isSuccessful()) {
                    AddSportResult result = response.body();
                    if(result.getCode() == 0){
                        interfaceBoolean.getBoolean(true);
                    }
                }
            }
            @Override
            public void onFailure(Call<AddSportResult> call, Throwable t) {
                //Toast.makeText(context,"暂无更多",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 获取运动记录
     */
    public void GetSportHsitoryData (int userID,  int monthCount){
        Call<SportHistoryResultBean> call = MyModel.getModel().getService().getUserSport(userID,monthCount);

        call.enqueue(new Callback<SportHistoryResultBean>() {
            @Override
            public void onResponse(Call<SportHistoryResultBean> call, Response<SportHistoryResultBean> response) {
                if (response.isSuccessful()) {
                    SportHistoryResultBean result = response.body();
                    Log.e("GetSportHsitoryData","result is "+new Gson().toJson(result));
                    if(result.getCode() == 0 ){
                        if(result.getData()!= null && result.getData().getSports() != null&& result.getData().getSports().size()>0)
                        interfaceVideo.getDateList((ArrayList) result.getData().getSports());
                    }else{

                    }
                }else{

                }
            }
            @Override
            public void onFailure(Call<SportHistoryResultBean> call, Throwable t) {

            }
        });
    }

    /**
     * 从服务器获取距离
     */
    public void getDis(int userId) {
        Call<RegistResult> call = GetDisModel.getModel().getService().getResult(Default.countDis, userId);

        call.enqueue(new Callback<RegistResult>() {
            @Override
            public void onResponse(Call<RegistResult> call, Response<RegistResult> response) {
                if (response.isSuccessful()) {
                    RegistResult result = response.body();
                    if (result.getNum() != -1) {

                        interfaceIntId.getDateIntId(result.getNum());

                    } else {

                    }
                }
            }

            @Override
            public void onFailure(Call<RegistResult> call, Throwable t) {

            }
        });
    }

    /**
     * 上传用户的积分
     * @param userId     用户ID
     * @param userMark   本次获得积分
     */
    public void updateMark(int userId,int userMark){
        Call<BooleanResult> call = UpdateMarkModel.getModel().getService().getResult(Default.updateMark,userId,userMark);

        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                    if (result.isFlag()) {

                    }else{

                    }
                }
            }

            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {

            }
        });
    }

    /**
     * 积分查询
     * @param userId     用户ID
     */
    public void getUserMark(int userId){
        Call<GetUserMarkResult> call = GetUserMarkModel.getModel().getService().getResult("getUserMark",userId);

        call.enqueue(new Callback<GetUserMarkResult>() {
            @Override
            public void onResponse(Call<GetUserMarkResult> call, Response<GetUserMarkResult> response) {
                if (response.isSuccessful()) {
                    GetUserMarkResult result = response.body();
                    interfaceIntId.getDateIntId(result.getNum());
                }
            }

            @Override
            public void onFailure(Call<GetUserMarkResult> call, Throwable t) {

            }
        });
    }


    /**
     * 获取勋章
     * @param userId     用户ID
     */
    public void getSuccess(int userId){
        Call<SuccessResult> call = SuccessModel.getModel().getService().getResult("getSuccess",userId);

        call.enqueue(new Callback<SuccessResult>() {
            @Override
            public void onResponse(Call<SuccessResult> call, Response<SuccessResult> response) {
                if (response.isSuccessful()) {
                    SuccessResult result = response.body();
                    getInterfaceStringListener.getDateString(result.getString());
                }
            }

            @Override
            public void onFailure(Call<SuccessResult> call, Throwable t) {

            }
        });
    }

    /**
     * 获取话题列表
     */
    public void getTheme(Context context, final String themeTitle, int nowUserId, final int page, int size){
        mCache = ACache.get(context);    //实例化缓存
        Call<ThemeResule> call = ThemeModel.getModel().getService().getResult(themeTitle,nowUserId,page,size);

        call.enqueue(new Callback<ThemeResule>() {
            @Override
            public void onResponse(Call<ThemeResule> call, Response<ThemeResule> response) {
                if (response.isSuccessful()) {
                    ThemeResule result = response.body();
                    if(result.getData().getThemelist().size() > 0){
                        mCache.put("theme" + page,result);
                    }
                    interfaceVideo.getDateList((ArrayList) result.getData().getThemelist());

                }else {
                    Log.i("话题","---");
                }
            }

            @Override
            public void onFailure(Call<ThemeResule> call, Throwable t) {

            }
        });
    }

    /**
     * 获取话题中的心情列表
     */
    public void getHumor(Context context, final String themeTitleStr, int nowUserId, int page, int size, final int id){
        mCache = ACache.get(context);    //实例化缓存
        Call<HumorResult> call = HumorModel.getModel().getService().getResult(themeTitleStr,nowUserId,page,size);

        call.enqueue(new Callback<HumorResult>() {
            @Override
            public void onResponse(Call<HumorResult> call, Response<HumorResult> response) {
                if (response.isSuccessful()) {
                    HumorResult result = response.body();
                    if(result.getCode() == 0){
                        mCache.put("humor" + id,result.getData());
                    }

                    Log.i("话题",result.getCode() +"---");
                    interfaceVideo.getDateList((ArrayList) result.getData().getHumorlist());

                }else {
                    Log.i("话题","---");
                }
            }

            @Override
            public void onFailure(Call<HumorResult> call, Throwable t) {

            }
        });
    }


    /**
     * 上传运动记录
     *
     *  userId  用户id
     *  altitudeHigh  海拔高度
     *  average   平均
     *  boolPressure  血压
     *  calories       消耗卡路里
     *  heartRate      心率
     *  matchNumber      配步数
     *  sportDate   运动日期
     *  sportImgUrl  运动轨迹图片(七牛返回的图片地址)
     *  totalDistance     //全程距离
     *  totalShin         //累计攀爬
     *  stepFrequency       //步频率
     *  totalTime         //运动时长
     *  totalStep   //总步数
     */
    public void getUploadSport(int userId,String altitudeHigh,String average,String boolPressure,
                               String calories,String heartRate,String matchNumber,String sportDate,
                               String sportImgUrl,String totalDistance,String totalShin,String stepFrequency,String totalTime,String totalStep){
        Call<UploadResult> call = UploadModel.getModel().getService().getResult(userId,altitudeHigh,average,boolPressure,calories,
                heartRate,matchNumber,sportDate,sportImgUrl,totalDistance,totalShin,stepFrequency,totalTime,totalStep);

        call.enqueue(new Callback<UploadResult>() {
            @Override
            public void onResponse(Call<UploadResult> call, Response<UploadResult> response) {
                if (response.isSuccessful()) {
                    UploadResult result = response.body();
                    if(result.getCode() == 0){
                        Log.i("上传记录","成功");
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<UploadResult> call, Throwable t) {

            }
        });
    }

    /**
     * 点赞
     */
    public void getPraise(Context context,int userId,int humorId){
        Call<PraiseResult> call = PraiseModel.getModel().getService().getResult(userId,humorId);
        call.enqueue(new Callback<PraiseResult>() {
            @Override
            public void onResponse(Call<PraiseResult> call, Response<PraiseResult> response) {
                if (response.isSuccessful()) {
                    PraiseResult result = response.body();
                    if(result.getCode() == 0){
                       Log.i("话题点赞","成功");
                        interfaceBoolean.getBoolean(true);
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<PraiseResult> call, Throwable t) {

            }
        });
    }

    /**
     * 获取评论列表
     */
    public void getComment(Context context,int humorId){
        Call<CommentResult> call = CommetModel.getModel().getService().getResult(humorId);
        call.enqueue(new Callback<CommentResult>() {
            @Override
            public void onResponse(Call<CommentResult> call, Response<CommentResult> response) {
                if (response.isSuccessful()) {
                    CommentResult result = response.body();
                    if(result.getCode() == 0){
                        interfaceVideo.getDateList((ArrayList) result.getData().getThemelist());
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<CommentResult> call, Throwable t) {

            }
        });
    }

    /**
     * 评论
     */
    public void setComment(Context context,int userId,int humorId,String con){
        Call<AddCommentResult> call = AddCommentModel.getModel().getService().getResult(userId,humorId,con);
        call.enqueue(new Callback<AddCommentResult>() {
            @Override
            public void onResponse(Call<AddCommentResult> call, Response<AddCommentResult> response) {
                if (response.isSuccessful()) {
                    AddCommentResult result = response.body();
                    if(result.getCode() == 0){
                        getObjectListener.getObject(result.getData());
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<AddCommentResult> call, Throwable t) {

            }
        });
    }

    /**
     * 发表心情
     */
    public void sentHumor(Context context,int userId,String themeTitleStr,String humorContent,String humorImgUrl){
        Call<SendHumorResult> call = SendHumorModel.getModel().getService().getResult(userId,themeTitleStr,humorContent,humorImgUrl);
        call.enqueue(new Callback<SendHumorResult>() {
            @Override
            public void onResponse(Call<SendHumorResult> call, Response<SendHumorResult> response) {
                if (response.isSuccessful()) {
                    SendHumorResult result = response.body();
                    Log.i("发表心情","00000");
                    if(result.getCode() == 0){
                        getObjectListener.getObject(result.getData());
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<SendHumorResult> call, Throwable t) {

            }
        });
    }


    /**
     * 收藏和取消收藏
     */
    public void addCollection(Context context,String collectionType,int userId,int objectId){
        Call<AddCollectionResult> call = AddCollectionModel.getModel().getService().getResult(collectionType,userId,objectId);
        call.enqueue(new Callback<AddCollectionResult>() {
            @Override
            public void onResponse(Call<AddCollectionResult> call, Response<AddCollectionResult> response) {
                if (response.isSuccessful()) {
                    AddCollectionResult result = response.body();
                    if(result.getCode() == 0){
                        log.i("收藏",result.getMsg());
                        interfaceIntId.getDateIntId(result.getData());
                    }
                }else {
                }
            }

            @Override
            public void onFailure(Call<AddCollectionResult> call, Throwable t) {

            }
        });
    }

    /**
     * 查询收藏
     */
    public void getCollection(Context context,int userId){
        mCache = ACache.get(context);    //实例化缓存
        Call<GetCollectionResult> call = GetCollectionModel.getModel().getService().getResult(userId);
        call.enqueue(new Callback<GetCollectionResult>() {
            @Override
            public void onResponse(Call<GetCollectionResult> call, Response<GetCollectionResult> response) {
                if (response.isSuccessful()) {
                    GetCollectionResult result = response.body();
                    if(result.getCode() == 0){
                        mCache.put("collection",result.getData());
                        getObjectListener.getObject(result.getData());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<GetCollectionResult> call, Throwable t) {

            }
        });
    }


    /**
     * 上传用户反馈
     */
    public void addUserBack(Context context,int userId,String cont){
        Call<BooleanResult> call = AddUserBackModel.getModel().getService().getResult("addUserBack",userId,cont);
        call.enqueue(new Callback<BooleanResult>() {
            @Override
            public void onResponse(Call<BooleanResult> call, Response<BooleanResult> response) {
                if (response.isSuccessful()) {
                    BooleanResult result = response.body();
                    if(result.isFlag()){
                        interfaceBoolean.getBoolean(result.isFlag());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<BooleanResult> call, Throwable t) {

            }
        });
    }


    /**
     * 获取歌曲列表
     */
    public void getMusicList(Context context){
        mCache = ACache.get(context);    //实例化缓存
        Call<Li_MusicResult> call = MyModel.getModel().getService().getMusicList();
        call.enqueue(new Callback<Li_MusicResult>() {
            @Override
            public void onResponse(Call<Li_MusicResult> call, Response<Li_MusicResult> response) {
                if (response.isSuccessful()) {
                    Li_MusicResult result = response.body();
                    if(result.getCode() == 0){
                        mCache.put("music",result.getData());
                        getObjectListener.getObject(result.getData());
                    }
                }else {

                }
            }

            @Override
            public void onFailure(Call<Li_MusicResult> call, Throwable t) {

            }
        });
    }

    /**
     * 获取点赞列表
     */
    public void getPraiseList(final Context context, int humorId, String page, String size){
        Call<Li_Praise_Result> call = MyModel.getModel().getService().getPraiseList(humorId, page,size);

        call.enqueue(new Callback<Li_Praise_Result>() {
            @Override
            public void onResponse(Call<Li_Praise_Result> call, Response<Li_Praise_Result> response) {
                if (response.isSuccessful()) {
                    final Li_Praise_Result result = response.body();
                    if(result.getCode() == 0){
                        getObjectListener.getObject(result.getData());
                        //Toast.makeText(context,"成功",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_Praise_Result> call, Throwable t) {

            }
        });
    }


    /**
     * 删除说说
     */
    public void getDelectComment(final Context context, int humorId){
        Call<Li_ReportComment_Result> call = MyModel.getModel().getService().geDelectComment(humorId);

        call.enqueue(new Callback<Li_ReportComment_Result>() {
            @Override
            public void onResponse(Call<Li_ReportComment_Result> call, Response<Li_ReportComment_Result> response) {
                if (response.isSuccessful()) {
                    final Li_ReportComment_Result result = response.body();
                    if(result.getCode() == 0){
                        getObjectListener.getObject(result);

                    }
                }
            }

            @Override
            public void onFailure(Call<Li_ReportComment_Result> call, Throwable t) {

            }
        });
    }


    /**
     * 举报说说
     */
    public void getReportComment(final Context context, int humorId, String str){
        //类型 0 代表说说, 1代表用户
        Call<Li_ReportComment_Result> call = MyModel.getModel().getService().getReportComment(UserBean.getUserBean().userId,humorId,0,str);

        call.enqueue(new Callback<Li_ReportComment_Result>() {
            @Override
            public void onResponse(Call<Li_ReportComment_Result> call, Response<Li_ReportComment_Result> response) {
                if (response.isSuccessful()) {
                    final Li_ReportComment_Result result = response.body();
                    if(result.getCode() == 0){
                        Toast.makeText(context,"成功",Toast.LENGTH_SHORT).show();
                        //getObjectListener.getObject(result.getData());
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_ReportComment_Result> call, Throwable t) {

            }
        });
    }


}
