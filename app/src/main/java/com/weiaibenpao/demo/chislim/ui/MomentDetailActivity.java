package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Comment;
import com.weiaibenpao.demo.chislim.bean.CommentDetailListBean;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.activity.UserDetailActivity;
import com.weiaibenpao.demo.chislim.hurui.bean.CommentBean;
import com.weiaibenpao.demo.chislim.hurui.bean.MakePraiseBean;
import com.weiaibenpao.demo.chislim.hurui.httpClient.ApiClient;
import com.weiaibenpao.demo.chislim.hurui.weight.MomentView;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Administrator on 2017/8/19.
 */

public class MomentDetailActivity extends AppCompatActivity {

    public static final String MOMENT = "moment";
    public static final String REPLY = "回复";
    public static final String YES = "YES";
    public static final String NO = "NO";
    public static final String DEFAULT_HINT = "我也说一句...";
    public static final int MAX_DISPLAY_COUNT = 5;
    public static final int REQUEST_MOMENT_DETAIL = 101;
    private Moment moment;
    private Context context = this;
    @BindView(R.id.content)
    ScrollView content;

    @BindView(R.id.momentView)
    MomentView momentView;

    @BindView(R.id.user_header_container)
    LinearLayout headerContainer;

    @BindView(R.id.comment_container)
    LinearLayout commentContainer;

    @BindView(R.id.send_et)
    EditText editText;

    @BindView(R.id.send_tv)
    TextView send;

    @BindView(R.id.tv_like_num)
    TextView tvLikeNum;
    private UserBean userBean;
    private int momentId;
    private int parentId = -1;
    private int totalLikes;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_moment_detail_layout);
        ButterKnife.bind(this);
        initView();
        loadData();
        // 回传点赞 和评论数 数据给上级界面-----暂时不做
        // 昵称上色  样式调整
        //  测试设置item点击后 点赞是否还可以，因为本地版本点赞没反应
        /**
         *   QQ:
         *   1.当前正准备回复某人，然后按物理返回键软键盘隐藏，此行为视为放弃回复某人-- 此时应该把editText的hint设为默认。。
         *   2.点击某个人的评论弹出软键盘，再点某个人的评论就是隐藏软键盘
         */

    }

    private void initView() {
        if (getIntent() != null) moment = getIntent().getParcelableExtra(MOMENT);
        userBean = UserBean.getUserBean();
        momentId = moment.getId();
        editText.setHint(DEFAULT_HINT);//必须手动设置hint，如果在XML设置hint结果是回复他人时：例如hint为:“回复小川：”如果清空输入框（打算重输）发现hint变成了XML里设置的，而不是原来的“回复小川：”
        setMoment(moment);
        content.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Utils.hideSoftInput(MomentDetailActivity.this, content);
                editText.setHint(DEFAULT_HINT);
                parentId = -1;
//                getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
                Log.e("MomentDetailActivity", "onTouch");
                return false;
            }
        });
    }

    private void setMoment(Moment data) {
        if (data != null) {
            momentView.setView(data);
            momentView.hideFooter();
            momentView.hideMore();
        }
    }

    private void loadData() {
        Log.e("loadData", "userId " + userBean.userId + "\nmomentId " + momentId);
        MyModel.getModel().getService().getCommentDatail(momentId, userBean.userId, "0", "100").enqueue(new Callback<CommentDetailListBean>() {
            @Override
            public void onResponse(Call<CommentDetailListBean> call, Response<CommentDetailListBean> response) {
                CommentDetailListBean bean = response.body();
                Log.e("onResponse", "json result is " + new Gson().toJson(response.body()));
                if (bean != null && bean.getCode() == 0) {
                    Log.e("loadData", "onResponse: success");
                    setLikeState(bean.getData());
                    showLiker(bean.getData().getPraiseList());
                    showComment(response.body().getData().getCommentList());
                } else {
                    Log.e("loadData", "onResponse: failed ");
                }
            }

            @Override
            public void onFailure(Call<CommentDetailListBean> call, Throwable t) {

            }
        });
    }

    private void setLikeState(CommentDetailListBean.DataBean dataBean) {
        if (dataBean != null) {
            Log.e("setLikeState", "---" + dataBean.getIsLike() + "----num is " + dataBean.getLikeNum());
            tvLikeNum.setSelected(YES.equals(dataBean.getIsLike()));
            totalLikes = dataBean.getLikeNum();
            tvLikeNum.setText(String.valueOf(totalLikes) + "人已赞");

        }
    }

    private void showLiker(final List<CommentDetailListBean.DataBean.PraiseListBean> praiseListBean) {
        if (praiseListBean == null) {
            return;
        }
        int displayCount = praiseListBean.size() >= MAX_DISPLAY_COUNT ? MAX_DISPLAY_COUNT : praiseListBean.size();
        for (int i = 0; i < displayCount; i++) {
            final int userId = praiseListBean.get(i).getUserId();
            View view = LayoutInflater.from(this).inflate(R.layout.item_praise_usericon, headerContainer, false);
            ImageView header = (ImageView) view.findViewById(R.id.user_praise_icon);
            headerContainer.addView(view);
            loadImage(praiseListBean.get(i).getUserIcon(), header);
            header.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    UserDetailActivity.openActivity(MomentDetailActivity.this, userId);
                }
            });
        }
        if (displayCount == MAX_DISPLAY_COUNT) {
            ImageView proceed = new ImageView(this);
            proceed.setImageResource(R.drawable.ic_proceed);
            headerContainer.addView(proceed);
            proceed.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //TODO 跳转全部点赞者列表
                }
            });
        }
    }

    private void loadImage(String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(this)
                .load(url)
                .into(imageView);
    }


    private void showComment(List<Comment> commentList) {
        if (commentList == null) {
            return;
        }
        for (int i = 0; i < commentList.size(); i++) {
            insertComment(commentList.get(i));
        }
    }


    private void insertComment(final Comment commentEntity) {
        final TextView comment = (TextView) LayoutInflater.from(this).inflate(R.layout.item_user_comment, commentContainer, false);
        String commentStr;
        final String userNickName = commentEntity.getUserNickName();
        String otherUsername = commentEntity.getPreUserNickName();
        if (TextUtils.isEmpty(otherUsername)) { //这是一条评论
            commentStr = userNickName + ":" + commentEntity.getComText();
        } else {//这是回复其他评论者的评论
            commentStr = userNickName + REPLY + otherUsername + ":" + commentEntity.getComText();
        }
        comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userBean.userId != commentEntity.getUserId()) {
                    Utils.showSoftInput(comment.getContext());
                    editText.setHint(REPLY + userNickName + ":");
                    editText.requestFocus();
                    parentId = commentEntity.getId();
                    Log.e("onClick","parent id "+parentId);
                } else {
                    Log.e("onClickComment", "不能回复自己的评论");
                }
            }
        });
        comment.setText(commentStr);
        commentContainer.addView(comment);
    }


    @OnClick({R.id.send_tv, R.id.tv_like_num, R.id.close_iv})
    public void onSend(View view) {
        switch (view.getId()) {
            case R.id.send_tv:
                String inputText = String.valueOf(editText.getText());
                if (!TextUtils.isEmpty(inputText)) {
                    //TODO 这里加一个网络判断 如果没网就提示用户，不用请求接口
                    Toast.makeText(this, "发送中...", Toast.LENGTH_SHORT).show();
                    editText.setText("");
                    if (parentId >=0) {
                        replyComment(inputText);
                    } else {
                        addComment(inputText);
                    }
//                    if(String.valueOf(editText.getHint()).startsWith(REPLY)){
//                        replyComment(inputText);
//                    }else {
//                        addComment(inputText);
//                    }

                }
                break;
            case R.id.tv_like_num:
                clickLike();
//                String isLike = moment.getIsLike();
//                setResult(isLike);
                break;
            case R.id.close_iv:
                finish();
                break;
        }

    }

    private void addComment(String content) {
        Log.e("addComment", "content is " + content);
        MyModel.getModel().getService().getAddComment(userBean.userId, momentId, content).enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(Call<CommentBean> call, Response<CommentBean> response) {
                CommentBean bean = response.body();
                if (bean != null) {
                    if (bean.getCode() == 0) {
                        Comment comment = bean.getData();
                        Log.e("addComment", "onResponse: " + comment.toString());
                        insertComment(comment);
                        Utils.hideSoftInput(MomentDetailActivity.this, editText);
                    }
                } else {
                    Log.e("addComment", "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(Call<CommentBean> call, Throwable t) {
                Log.e("addComment", "onFailure");
            }
        });

    }


    private void replyComment(String content) {
        Log.e("replyComment", "content is " + content);
        MyModel.getModel().getService().getReplyComment(userBean.userId, momentId, content, parentId).enqueue(new Callback<CommentBean>() {
            @Override
            public void onResponse(Call<CommentBean> call, Response<CommentBean> response) {
                if (response.body() != null) {
                    if (response.body().getCode() == 0) {
                        Comment comment = response.body().getData();
                        Log.e("replyComment", "onResponse: " + comment.toString());
                        insertComment(comment);
                        Utils.hideSoftInput(MomentDetailActivity.this, editText);
                    }
                } else {
                    Log.e("replyComment", "onResponse: body is null");
                }
            }

            @Override
            public void onFailure(Call<CommentBean> call, Throwable t) {
                Log.e("replyComment", "onFailure");
            }
        });

    }


    private void clickLike() {
        Call<MakePraiseBean> call = ApiClient.getModel().getService().getMakePraiseService(userBean.userId, momentId);
        call.enqueue(new Callback<MakePraiseBean>() {
            @Override
            public void onResponse(Call<MakePraiseBean> call, Response<MakePraiseBean> response) {
                if (response.isSuccessful()) {
                    MakePraiseBean bean = response.body();
                    if (bean.getCode() == 0) {
                        Log.e("clickLike","onResponse "+bean.getMsg());
                        if ("点赞成功".equals(bean.getMsg())) {
                            tvLikeNum.setSelected(true);
                            totalLikes = totalLikes+1;
                            tvLikeNum.setText(String.valueOf(totalLikes )+"人已赞");
                        } else {
                            tvLikeNum.setSelected(false);
                            totalLikes = totalLikes -1 ;
                            tvLikeNum.setText(String.valueOf(totalLikes )+"人已赞");
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<MakePraiseBean> call, Throwable t) {

            }
        });
    }

    public static void openActivity(Activity context, Moment moment) {
        Intent intent = new Intent(context, MomentDetailActivity.class);
        intent.putExtra(MOMENT, moment);
        context.startActivityForResult(intent, REQUEST_MOMENT_DETAIL);
    }

    public void setResult(String likeOrNot) {
        Log.e("setResult", "setResult: ");
        Intent intent = new Intent();
        setResult(RESULT_OK, intent);
        finish();
    }
}
