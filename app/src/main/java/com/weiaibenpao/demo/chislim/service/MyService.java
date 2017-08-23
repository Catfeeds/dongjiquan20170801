package com.weiaibenpao.demo.chislim.service;

import com.weiaibenpao.demo.chislim.bean.AddInterestSportResult;
import com.weiaibenpao.demo.chislim.bean.AddSportResult;
import com.weiaibenpao.demo.chislim.bean.CommentDetailListBean;
import com.weiaibenpao.demo.chislim.bean.FansBean;
import com.weiaibenpao.demo.chislim.bean.GetInterestSportResult;
import com.weiaibenpao.demo.chislim.bean.Li_MusicResult;
import com.weiaibenpao.demo.chislim.bean.Li_Praise;
import com.weiaibenpao.demo.chislim.bean.Li_Praise_Result;
import com.weiaibenpao.demo.chislim.bean.Li_ReportComment_Result;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.Li_UserInfoResult;
import com.weiaibenpao.demo.chislim.bean.Li_funsResult;
import com.weiaibenpao.demo.chislim.bean.MyMedal;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResultBean;
import com.weiaibenpao.demo.chislim.hurui.bean.CommentBean;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2017/4/14.
 */

public interface MyService {
    //获取音乐列表
    @GET("/chislim6/music/musictype")
    Call<Li_MusicResult> getMusicList();

    //电话注册
    @GET("/chislim5/user/phoneRegister")
    Call<Li_UserBeanResult> getPhoneRegistResult(
            @Query("userPhone") String userPhone,
            @Query("registerSource") String registerSource,
            @Query("userPass") String userPass);

    //第三方注册
    @GET("/chislim6/user/threePartRegister")
    Call<Li_UserBeanResult> getThreeRegistResult(
            @Query("openId") String openId,
            @Query("registerSource") String registerSource,
            @Query("userImage") String userImage,
            @Query("userName") String userName,
            @Query("sex") String sex,
            @Query("userBirth") String userBirth);

    //电话登录
    @GET("/chislim6/user/loginByPhone")
    Call<Li_UserBeanResult> getLoginByPhone(
            @Query("userPhone") String userPhone,
            @Query("userPass") String userPass);

    //第三方登录
    @GET("/chislim6/user/loginByThreeType")
    Call<Li_UserBeanResult> getLoginByThree(
            @Query("openId") String openId,
            @Query("registerSource") String registerSource);

    //头像修改
    @GET("/chislim6/user/updateUserImage")
    Call<Li_UserBeanResult> updateUserImage(
            @Query("userID") int userID,
            @Query("userImage") String userImage);

    //修改密码
    @GET("/chislim6/user/updateUserPass")
    Call<Li_UserBeanResult> updateUserPass(
            @Query("userID") int userID,
            @Query("userPass") String userPass);

    //修改用户信息
    @GET("/chislim5/user/updateUserInfo")
    Call<Li_UserBeanResult> updateUserInfo(
            @Query("userId") int userID,
            @Query("userName") String userName,
            @Query("sex") String sex,
            @Query("userBirth") String userBirth,
            @Query("userEmail") String userEmail,
            @Query("userIntru") String userIntru,
            @Query("hobby") String hobby,
            @Query("userWeight") float userWeight,
            @Query("userHeigh") int userHeigh);

    //@个人信息
    @GET("fans/mine")
    Call<FansBean> getFans(@Query("userId") int userId);

    //修改头像
    @GET("user/updateUserImage")
    Call<Li_UserBeanResult> getImage(@Query("userId") int id,@Query("userImage") String url);
    //查询个人中心信息
    @GET("fans/minelist")
    Call<Li_UserInfoResult> getUserInfo(
            @Query("userId") int userId,
            @Query("nowUserId") int nowUserId,
                @Query("page") String page,
            @Query("size") String size);

    //查询个人的粉丝和关注的人的数
    @GET("fans/list")
    Call<Li_funsResult> getUserFuns(
            @Query("userId") int userId,
            @Query("type") int type,
            @Query("page") String page,
            @Query("size") String size);

    //查询趣味跑信息
    @GET("/chislim6/interest/selectinterest")
    Call<GetInterestSportResult> GetInterestSportResult(
            @Query("userId") int userId);

    //添加趣味跑信息
    @GET("/chislim6/interest/addinterest")
    Call<AddInterestSportResult> AddInterestSportResult(
            @Query("userId") int userId,
            @Query("cityName") String cityName,
            @Query("finishPercent") String finishPercent,
            @Query("allDis") String allDis,
            @Query("nowDis") String nowDis,
            @Query("sportTime") String sportTime);

    //找回密码
    @GET("/chislim6/user/updatepassByPhone")
    Call<Li_UserBeanResult> FindUserPass(
            @Query("userPhone") String userPhone,
            @Query("userPass") String userPass);

    //关注和取消关注：fans/fan
    @GET("fans/fan")
    Call<Li_funsResult> isFans(@Query("userId") int userId,@Query("beUserId") int beUserId);

    //添加运动记录

    /**
     * userId=43&
     * sportType=1&
     * altitudeHigh=0
     * &average=0&
     * boolPressure=0&
     * calories=asd&
     * heartRate=0&
     * matchNumber=0&
     * sportDate=asdasd&
     * sportImgUrl=asda&
     * totalDistance=3.3&
     * totalShin=0&
     * stepFrequency=10&
     * totalTime=1010&
     * totalStep=10
     * @param userId
     * @param sportType
     * @param altitudeHigh
     * @param average
     * @param boolPressure
     * @param calories
     * @param heartRate
     * @param matchNumber
     * @param sportDate
     * @param sportImgUrl
     * @param totalDistance
     * @param totalShin
     * @param stepFrequency
     * @param totalTime
     * @param totalStep
     * @return
     */
    @GET("/chislim6/sports/addsports")
    Call<AddSportResult> addSports(
            @Query("userId") int userId,
            @Query("sportType") int sportType,
            @Query("altitudeHigh") String altitudeHigh,
            @Query("average") String average,
            @Query("boolPressure") String boolPressure,
            @Query("calories") String calories,
            @Query("heartRate") String heartRate,
            @Query("matchNumber") String matchNumber,
            @Query("sportDate") String sportDate,
            @Query("sportImgUrl") String sportImgUrl,
            @Query("totalDistance") float totalDistance,
            @Query("totalShin") String totalShin,
            @Query("stepFrequency") String stepFrequency,
            @Query("totalTime") String totalTime,
            @Query("totalStep") String totalStep);

//    //查询运动记录--------不要了，换成下面的
//    @GET("/chislim6/sports/list")
//    Call<Li_SportHistory_Result> getUserSport(
//            @Query("userId") int userId,
//            @Query("sportType") int sportType,
//            @Query("page") int page,
//            @Query("size") int size);

    @GET("/chislim5/sports/getSportHistoryMonth")
    Call<SportHistoryResultBean> getUserSport(
            @Query("userId") int userId,
            @Query("m") int monthCount);

    @GET("badge/list")
    Call<MyMedal> getMedals(@Query("userId") int id );

    @GET("praise/list")
    Call<Li_Praise_Result> getPraiseList(
            @Query("humorId") int humorId,
            @Query("page") String page,
            @Query("size") String size );

    //详情
    @GET("comment/detaillist")
    Call<CommentDetailListBean> getCommentDatail(@Query("humorId") int humorId, @Query("nowUserId") int nowUserId, @Query("page") String page , @Query("size") String size);

    //评论说说
    @GET("comment/addComment")
    Call<CommentBean> getAddComment(@Query("userId") int userId, @Query("humorId") int humorId, @Query("comText") String comText);

    //回复他人评论
    @GET("comment/replyComment")
    Call<CommentBean> getReplyComment(@Query("userId") int userId, @Query("humorId") int humorId, @Query("comText") String comText, @Query("parentId") int parentId);



    @GET("praise/makepraise")
    Call<Li_Praise> getPraiseList(
            @Query("userId") int userId,
            @Query("humorId") int humorId);

    //举报
    @GET("tips/addtips")
    Call<Li_ReportComment_Result> getReportComment(@Query("userId") int userId ,
                                                   @Query("type") int type,
                                                   @Query("objectId") int objectId,
                                                   @Query("content") String content);


    //删除
    @GET("humor/delete")
    Call<Li_ReportComment_Result> geDelectComment(@Query("humorId") int humorId );
}
