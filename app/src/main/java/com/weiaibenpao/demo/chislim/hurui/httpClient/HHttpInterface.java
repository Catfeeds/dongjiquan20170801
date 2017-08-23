package com.weiaibenpao.demo.chislim.hurui.httpClient;

import com.weiaibenpao.demo.chislim.bean.Li_ReportComment_Result;
import com.weiaibenpao.demo.chislim.hurui.bean.BannerItemBean;
import com.weiaibenpao.demo.chislim.hurui.bean.FlexibleBean;
import com.weiaibenpao.demo.chislim.hurui.bean.FunsBean;
import com.weiaibenpao.demo.chislim.hurui.bean.HumorBean;
import com.weiaibenpao.demo.chislim.hurui.bean.MakePraiseBean;
import com.weiaibenpao.demo.chislim.hurui.bean.PulishHumorBean;
import com.weiaibenpao.demo.chislim.hurui.bean.ReplayCommentBean;
import com.weiaibenpao.demo.chislim.hurui.bean.RunMachineBean;
import com.weiaibenpao.demo.chislim.hurui.bean.TravelBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2017/4/10.
 */

public interface HHttpInterface {
    int DEFAULT_PAGE_SIZE =20;

    /**
     * 首页轮播图
     * @param imgCastingTitle
     * @param imgCastingType
     * @return
     */
    @GET("imgCasting/list")
    Call<BannerItemBean> getBannerImageService(@Query("imgCastingTitle") String imgCastingTitle ,
                                               @Query("imgCastingType") String imgCastingType);

    /**
     * 活动
     * @param nowUserId
     * @param activityTitle
     * @return
     */
    @GET("activity/list")
    Call<FlexibleBean> getFlexibleBeanService(@Query("nowUserId") String nowUserId ,
                                              @Query("activityTitle") String activityTitle);

    /**
     * 跑步机
     * @param page
     * @param size
     * @return
     */
    @GET("treadmilllcontrol/selecttreadmilll")
    Call<RunMachineBean> getToursimService(@Query("page") String page ,
                                           @Query("size") String size);

    /**
     * 旅游
     * @param nowUserId
     * @param travelType
     * @return
     */
    @GET("travel/findTravelListByType")
    Call<TravelBean> getTravelService(@Query("nowUserId") String nowUserId ,
                                      @Query("travelType") String travelType ,
                                      @Query("page") String page ,
                                      @Query("size") String size);


    /**
     * 跑圈
     * @param nowUserId
     * @return
     */
    @GET("humor/list")
    Call<HumorBean> getHoumService(@Query("nowUserId") String nowUserId ,
                                   @Query("page") String page ,
                                   @Query("size") String size);

    /**
     * 发布说说
     * @param userId
     * @param themeTitleStr
     * @param humorContent
     * @param humorImgUrl
     * @param humorVideoUrl
     * @param humorpalce
     * @return
     */
    @FormUrlEncoded
    @POST("humor/addHumor")
    Call<PulishHumorBean> getPulishHumorService(@Field("userId") String userId ,
                                                @Field("themeTitleStr") String themeTitleStr ,
                                                @Field("humorContent") String humorContent ,
                                                @Field("humorImgUrl") String humorImgUrl ,
                                                @Field("humorVideoUrl") String humorVideoUrl ,
                                                @Field("humorpalce") String humorpalce);

    //点赞
    @GET("praise/makepraise")
    Call<MakePraiseBean> getMakePraiseService(@Query("userId") int userId ,
                                              @Query("humorId") int humorId);

    //回复评论
    @GET("comment/replyComment")
    Call<ReplayCommentBean> getReplayCommentService(@Query("userId") String userId ,
                                                    @Query("humorId") String humorId ,
                                                    @Query("comText") String comText ,
                                                    @Query("parentId") String parentId);

    //添加评论
    @GET("comment/addComment")
    Call<ReplayCommentBean> getAddCommentService(@Query("userId") String userId ,
                                                    @Query("humorId") String humorId ,
                                                    @Query("comText") String comText );

    //点赞
    @GET("fans/fan")
    Call<FunsBean> getFunsService(@Query("userId") int userId ,
                                        @Query("nowUserId") int nowUserId);


    //举报
    @GET("tips/addtips")
    Call<Li_ReportComment_Result> getReportComment(@Query("userId") int userId ,
                                                 @Query("type") int type,
                                                 @Query("objectId") int objectId,
                                                 @Query("content") int content);


    //删除
    @GET("humor/delete")
    Call<Li_ReportComment_Result> geDelectComment(@Query("humorId") int humorId );
}
