package com.weiaibenpao.demo.chislim.hurui.weight;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.ms.square.android.expandabletextview.ExpandableTextView;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.hurui.activity.PhotoActivity;
import com.weiaibenpao.demo.chislim.hurui.activity.UserDetailActivity;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;
import com.weiaibenpao.demo.chislim.ui.MomentDetailActivity;
import com.weiaibenpao.demo.chislim.util.Utils;
import com.weiaibenpao.demo.chislim.video.VideoActivity;
import com.zhy.view.flowlayout.FlowLayout;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by ZJL on 2017/8/15.
 */

public class MomentView extends RelativeLayout {
    private static final String VIDEO_COVER_SUFFIX = "?vframe/jpg/offset/4";//视频地址后缀   视频地址拼接一个url?vframe/jpg/offset/0 就是视频第一帧图片，一般选用第五帧即url?vframe/jpg/offset/4
    private static final String SEPARATOR = ",";
    private static final String YES = "YES";
    private static final String NO = "NO";
    private static final int IMAGE_PADDING = 4;
    private static final float ASPECT_RATIO = 9 / 16f;
    public static final String TYPE_RUNNING_CIRCLE_LIST ="running_circle_list";
    public static final String TYPE_USER_DETAIL_LIST ="user_detail_list";
    public static final String TYPE_MOMENT_DETAIL ="moment_detail";
    private String applyType;

    private OnMomentClickListener onMomentClickListener;
    @BindView(R.id.root)
    LinearLayout root;
    @BindView(R.id.header)
    CircleImageView header;
    @BindView(R.id.nickname)
    TextView nickname;
    @BindView(R.id.post_time)
    DateTextView postTime;
    @BindView(R.id.post_location)
    TextView postLocation;
    @BindView(R.id.likeNum)
    TextView likeNum;
    @BindView(R.id.commentNum)
    TextView commentNum;

    @BindView(R.id.content)
    ExpandableTextView content;

    @BindView(R.id.img_container)
    FlowLayout imageContainer;

    @BindView(R.id.video_cover_container)
    FrameLayout videoContainer;

    @BindView(R.id.video_cover)
    ImageView videoCover;

    @BindView(R.id.iv_play)
    ImageView imagePlay;

    @BindView(R.id.more)
    ImageView more;

    @BindView(R.id.num_footer)
    LinearLayout numFooter;

    @BindView(R.id.divider_footer)
    View dividerFooter;


    public MomentView(Context context) {
        super(context);
        init(context);
    }

    public MomentView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MomentView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public MomentView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    /**
     * 发布，个人详情，跑圈界面，评论详情，全部点赞者，视频播放单独界面，
     * 点赞，取消赞，删除，举报 ，封装统一dialog
     */


    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_moment_view, this);
        ButterKnife.bind(this, view);
    }

    public void setOnMomentClickListener(OnMomentClickListener listener) {
        onMomentClickListener = listener;
    }

    public void setApplyType(String type){
        applyType = type;
    }


    public void setView(Moment data) {
        if (data == null) {
            return;
        }
        root.setTag(data);
        header.setTag(R.id.glide_image_tag, data);
        more.setTag(data);
        likeNum.setTag(data);
        commentNum.setTag(data);
        loadImage(data.getUserIcon(), header);
        nickname.setText(data.getUserNickName());
        postTime.setText(Utils.getFormattedTime(data.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
        if (!TextUtils.isEmpty(data.getHumorContent())) {
            content.setVisibility(VISIBLE);
            content.setText(data.getHumorContent());
        } else {
            content.setVisibility(GONE);
        }
        postLocation.setText(data.getHumorpalce());
        likeNum.setText(String.valueOf(data.getLikeNum()));
        likeNum.setSelected(YES.equals(data.getIsLike()));
        commentNum.setText(String.valueOf(data.getComentNum()));
        if (!TextUtils.isEmpty(data.getHumorImgUrlSmall()) && !TextUtils.isEmpty(data.getHumorImgUrl())) {
            showImages(data);
        } else {
            imageContainer.setVisibility(GONE);
        }
        if (!TextUtils.isEmpty(data.getHumorVideoImgUrl()) && !TextUtils.isEmpty(data.getHumorVideoUrl())) {
            showVideoCover(data);
        } else {
            videoContainer.setVisibility(GONE);
        }
    }

    public void hideMore() {
        more.setVisibility(GONE);
    }

    public void hideFooter() {
        numFooter.setVisibility(GONE);
        dividerFooter.setVisibility(GONE);
    }


    private void showVideoCover(Moment data) {
        videoContainer.setVisibility(VISIBLE);
        videoContainer.setTag(data);
        String videoCoverUrl = data.getHumorVideoImgUrl();
        int maxSize = Utils.getScreenWidth() - imageContainer.getPaddingLeft() * 2;
        int minSize = maxSize / 2;
        int imageWidth = minSize;
        int imageHeight = minSize;
        if (data.getWidth() > 0 && data.getHeight() > 0) {
            double sampleSize = getSampleSize(data.getWidth(), data.getHeight(), maxSize);
            imageWidth = (int) (data.getWidth() / sampleSize);
            imageHeight = (int) (data.getHeight() / sampleSize);
            if (imageHeight > imageWidth) {
                imageWidth = getOptimalSize(imageHeight, imageWidth);
            } else if (imageWidth > imageHeight) {
                imageHeight = getOptimalSize(imageWidth, imageHeight);
            }
        }
        Log.e("videoCover", "width is " + imageWidth + "\nheight is " + imageHeight);
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) videoCover.getLayoutParams();
        params.width = imageWidth;
        params.height = imageHeight;
        videoCover.setLayoutParams(params);
        loadImage(videoCoverUrl, videoCover);
        int imagePlaySize = Math.min(imageWidth, imageHeight) / 2;
        Log.e("showCover", "image play size " + imagePlaySize);
        FrameLayout.LayoutParams playParams = (FrameLayout.LayoutParams) imagePlay.getLayoutParams();
        playParams.width = imagePlaySize;
        playParams.height = imagePlaySize;
        imagePlay.setLayoutParams(playParams);
    }

    private void showImages(Moment data) {
        String[] imageThumbnailUrls = data.getHumorImgUrlSmall().split(SEPARATOR);
        String[] imageOriginalUrls = data.getHumorImgUrl().split(SEPARATOR);
        if (imageThumbnailUrls.length > 0) {
            imageContainer.removeAllViews();
            imageContainer.setVisibility(VISIBLE);
            int maxSize = Utils.getScreenWidth() - imageContainer.getPaddingLeft() * 2;
            int minSize = maxSize / 2;
            int imageWidth = minSize;
            int imageHeight = minSize;
            if (imageThumbnailUrls.length == 1) { //单图  按服务端返回的尺寸设置
                if (data.getWidth() > 0 && data.getHeight() > 0) {
                    double sampleSize = getSampleSize(data.getWidth(), data.getHeight(), maxSize);
                    imageWidth = (int) (data.getWidth() / sampleSize);
                    imageHeight = (int) (data.getHeight() / sampleSize);
                    if (imageHeight > imageWidth) {
                        imageWidth = getOptimalSize(imageHeight, imageWidth);
                    } else if (imageWidth > imageHeight) {
                        imageHeight = getOptimalSize(imageWidth, imageHeight);
                    }
                }
                Log.e("showImages", "image width is " + imageWidth + "\nimage height is " + imageHeight);
                createImage(imageThumbnailUrls[0], imageOriginalUrls, 0, imageWidth, imageHeight);
            } else {//多图   按每行三张布局
                imageWidth = (Utils.getScreenWidth() - imageContainer.getPaddingLeft() - imageContainer.getPaddingRight()) / 3;
                imageHeight = imageWidth;
                for (int i = 0; i < imageThumbnailUrls.length; i++) {
                    createImage(imageThumbnailUrls[i], imageOriginalUrls, i, imageWidth, imageHeight);
                }
            }
        }
    }

    private double getSampleSize(int width, int height, int maxSize) {
        double sampleSize = 1;
        if (maxSize <= 0) {
            return sampleSize;
        }
        Log.e("getSampleSize", "width is " + width + "--height is " + height + "--maxSize is " + maxSize);
        while (width / sampleSize > maxSize || height / sampleSize > maxSize) {
            sampleSize = sampleSize * 1.25;
        }
        Log.e("getSampleSize", "sample size is " + sampleSize);
        return sampleSize;
    }

    private int getOptimalSize(int maxSize, int originalSize) {
        int minSize = (int) (maxSize * ASPECT_RATIO);
        if (originalSize < minSize) {
            originalSize = minSize;
        }
        return originalSize;
    }

    private void createImage(String imageUrl, final String[] originalUrls, final int position, int imageWidth, int imageHeight) {
        ImageView image = new ImageView(getContext());
        image.setPadding(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING);
        image.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageContainer.addView(image);
        ViewGroup.LayoutParams params = image.getLayoutParams();
        params.width = imageWidth;
        params.height = imageHeight;
        image.setLayoutParams(params);
        loadImage(imageUrl, image);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkOriginalImage(position, originalUrls);
            }
        });
    }

    private void checkOriginalImage(int position, String[] urlArray) {
        Intent intent = new Intent(getContext(), PhotoActivity.class);
        intent.putExtra(PhotoActivity.URL_LIST, (Serializable) PhotoActivity.transformData(urlArray));
        intent.putExtra(PhotoActivity.POSITION, position);
        intent.putExtra(PhotoActivity.TYPE, 1);
        getContext().startActivity(intent);
    }


    private void loadImage(String url, ImageView imageView) {
        if (imageView == null) {
            return;
        }
        Glide
                .with(getContext())
                .load(url)
                .into(imageView);
    }

    @OnClick({R.id.header, R.id.more, R.id.likeNum, R.id.root, R.id.video_cover_container})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.header:
                if(TYPE_USER_DETAIL_LIST.equals(applyType)) return;
                Moment data = (Moment) view.getTag(R.id.glide_image_tag);
                if (data != null) UserDetailActivity.openActivity(getContext(), data.getUserId());
                break;
            case R.id.more:
                if (onMomentClickListener != null) {
                    Moment moment = (Moment) more.getTag();
                    onMomentClickListener.onClickMore(moment);
                }
                break;
            case R.id.video_cover_container:
                Moment moment = (Moment) videoContainer.getTag();
                Intent videoIntent = new Intent(getContext(), VideoActivity.class);
                videoIntent.putExtra(VideoActivity.VIDEO_URL, moment.getHumorVideoUrl());
                videoIntent.putExtra(VideoActivity.VIDEO_COVER_URL, moment.getHumorVideoImgUrl());
                videoIntent.putExtra(VideoActivity.VIDEO_WIDTH, moment.getWidth());
                videoIntent.putExtra(VideoActivity.VIDEO_HEIGHT, moment.getHeight());
                getContext().startActivity(videoIntent);
                break;

            case R.id.likeNum:
                if (onMomentClickListener != null) {
                    Moment likeData = (Moment) likeNum.getTag();
                    onMomentClickListener.onClickLike(likeData);
                }
//                int num = 0;
//                try {
//                    num = Integer.parseInt(String.valueOf(likeNum.getText()));
//                } catch (NumberFormatException e) {
//                    e.printStackTrace();
//                }
//                if (likeNum.isSelected()) {
//                    likeNum.setSelected(false);
//                    likeNum.setText(String.valueOf(num - 1));
//                } else {
//                    likeNum.setSelected(true);
//                    likeNum.setText(String.valueOf(num + 1));
//                }
                break;
            case R.id.root:
                if(TYPE_RUNNING_CIRCLE_LIST.equals(applyType) || TYPE_USER_DETAIL_LIST.equals(applyType)){
                    Moment momentData = (Moment) root.getTag();
                    MomentDetailActivity.openActivity((Activity) getContext(),momentData);
                    Log.e("MomentView","getContext is "+getContext());
                }
                break;
        }
    }

//    private void like(){
//        Call<MakePraiseBean> call = ApiClient.getModel().getService().getMakePraiseService(UserBean.getUserBean().userId+"" , humorLists.get(position).getId()+"");
//        call.enqueue(new Callback<MakePraiseBean>() {
//            @Override
//            public void onResponse(Call<MakePraiseBean> call, Response<MakePraiseBean> response) {
//                if(response.isSuccessful()){
//                    MakePraiseBean bean = response.body() ;
//                    if(bean.getCode() == 0){
//                        if(bean.getMsg().equals("点赞成功")) {
//                            humorLists.get(position).setIsLike("YES");
//                        }else{
//                            humorLists.get(position).setIsLike("NO");
//                        }
//                        humorAdapter.notifyItemChanged(position+1);
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<MakePraiseBean> call, Throwable t) {
//
//            }
//        });
//    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
//        if(hasWindowFocus){
//            Log.e("onWindowFocusChanged","video cover width is "+videoCover.getMeasuredWidth()+"\n height is "+videoCover.getMeasuredHeight());
//        }
    }


    public interface OnMomentClickListener {

        void onClickLike(Moment moment);

        void onClickMore(Moment moment);

    }
}
