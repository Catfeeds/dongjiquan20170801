package com.weiaibenpao.demo.chislim.util;

import com.weiaibenpao.demo.chislim.bean.ChatMessage;
import com.weiaibenpao.demo.chislim.bean.ResultBean;
import com.weiaibenpao.demo.chislim.service.PostService;

import java.util.Date;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhangxing on 2017/3/9.
 */

public class HttpUtils {
    private static final String BaseURL = "http://www.tuling123.com/openapi/";
    private static final String API_KEY = "d4ea669ba7f9482787f11852692da2f5";


    /**
     * 发送一个消息，得到一个消息返回
     * @param msg
     * @return
     */
    public static ChatMessage sengMessage(String msg){
        final ChatMessage chatMessage = new ChatMessage();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PostService service = retrofit.create(PostService.class);
        Call<ResultBean> result = service.getResult(API_KEY,msg);
        result.enqueue(new Callback<ResultBean>() {
            @Override
            public void onResponse(Call<ResultBean> call, Response<ResultBean> response) {
                String talk = response.body().getText();
                chatMessage.setMsg(talk);
            }

            @Override
            public void onFailure(Call<ResultBean> call, Throwable t) {
                chatMessage.setMsg("网络异常，请检查您的网络");
            }
        });

        chatMessage.setDate(new Date());
        chatMessage.setType(ChatMessage.Type.INCOMING);

        return chatMessage;
    }
}
