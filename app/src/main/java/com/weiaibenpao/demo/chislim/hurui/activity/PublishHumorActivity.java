package com.weiaibenpao.demo.chislim.hurui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.DeleteEventBus;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.adapte.HumorImgAndVideoAdpter;
import com.weiaibenpao.demo.chislim.hurui.bean.HumorImgAndVideoBean;
import com.weiaibenpao.demo.chislim.hurui.bean.PulishHumorBean;
import com.weiaibenpao.demo.chislim.hurui.luban.Luban;
import com.weiaibenpao.demo.chislim.hurui.luban.OnCompressListener;
import com.weiaibenpao.demo.chislim.util.DateUtil;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import jameson.io.library.util.ToastUtils;
import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PublishHumorActivity extends HBaseActivity {
    private String token; //七牛的token
    public static String RELEASE="release";
    public final static int CHECK_IMAGE_REQUEST = 23;
    public final static int CHECK_VIDEO_REQUEST = 24;
    public final int YAOSUO_SUCCESS=122;
    private final static int SHANG_SUCCESS = 12;
    private String imgUrl;
    private List<String> listImgUrl=new ArrayList<>();
    private StringBuffer imgUrlBuff=new StringBuffer();
    private List<String> imgPathlist=new ArrayList<>();

    //高德定位
    public AMapLocationClientOption mLocationOption = null;
    public AMapLocationClient mlocationClient = null;

    //popwindow
    private View view;

    //位置显示
    @BindView(R.id.pos_tv)
    TextView pos_tv;

    @BindView(R.id.precleView)
    RecyclerView precleView;

    @BindView(R.id.add_content)
    EditText add_content;
    @BindView(R.id.tv_content_num)
    TextView content_num;

    private int num;
    //关闭界面
    @OnClick(R.id.close_iv)
    void clickClose() {
        finish();
    }

    //发送说说
    @OnClick(R.id.send_btn)
    void send_Hurmor() {
        if (TextUtils.isEmpty(add_content.getText().toString()) && imgPathlist.size() == 0) {
            ToastUtils.show(mActivity,"请输入发布的内容");
            return;
        }
        if (!TextUtils.isEmpty(add_content.getText().toString())||imgPathlist.size()!=0) {
            showProgressDialog();
            if (imgPathlist.size()!=0){

                lubanCompress(imgPathlist);
            }else
                upQzone();

        }

    }



    public ArrayList<HumorImgAndVideoBean> lists;

    private HumorImgAndVideoAdpter adapter;

    private MyHandler myHandler = new MyHandler();

    private class MyHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case YAOSUO_SUCCESS:
                    getUPing2(lists);
                    break;
                case SHANG_SUCCESS:
                    String url= (String) msg.obj;
//
                        listImgUrl.add(url);
                        if (listImgUrl.size()==lists.size()){
                            upQzone();
                        }
//                    }



                    break;
            }
        }
    }

    private void upQzone() {
        if (listImgUrl.size()!=0 && listImgUrl.size()>1){
            for (int i = 0; i < listImgUrl.size(); i++) {

                imgUrlBuff.append(listImgUrl.get(i)+",");
            }
        }else if (listImgUrl.size()==1){
                imgUrlBuff.append(listImgUrl.get(0));
            }

        pulishHumor(add_content.getText().toString(),imgUrlBuff.toString(), "", pos_tv.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_humor);
        //注册eventBUS
        EventBus.getDefault().register(this);
        initMap();

        token = getIntent().getStringExtra("token");

        lists = new ArrayList<>();

        precleView.setLayoutManager(new GridLayoutManager(this, 3));

        adapter = new HumorImgAndVideoAdpter(this);

        precleView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        add_content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    num+=count;
            }

            @Override
            public void afterTextChanged(Editable s) {

                    if (s.length()>250){
                        s.delete(250,s.length());
                        ToastUtils.show(mActivity,"字数已够");

                    }
                content_num.setText(s.length()+"/250");
                }


        });
        adapter.setOnMyItemClick(new HumorImgAndVideoAdpter.OnMyItemClick() {
            @Override
            public void addData() {
               /* Toast.makeText(mActivity, "没有数据加载数据", Toast.LENGTH_SHORT).show();
                PhotoPickerIntent intentPhoto = new PhotoPickerIntent(mActivity);
                intentPhoto.setPhotoCount(9 - lists.size());
                intentPhoto.setShowCamera(true);
                startActivityForResult(intentPhoto,CHECK_IMAGE_REQUEST );*/
                /*Intent intent = new Intent();
                intent.setType("video*//*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CHECK_VIDEO_REQUEST);*/
                showPop();
            }

            /**
             * 查看大图
             * @param position
             */
            @Override
            public void ImageClick(int position) {
                Intent intent = new Intent(mActivity, PhotoActivity.class);
                intent.putExtra(PhotoActivity.URL_LIST, (Serializable) imgPathlist);
                intent.putExtra(PhotoActivity.POSITION, position);
                intent.putExtra(PhotoActivity.TYPE, 0);
                startActivity(intent);
            }

            @Override
            public void VideoClick() {
//                Toast.makeText(mActivity, "查看视频", Toast.LENGTH_SHORT).show();
//                Intent intent = new Intent(mActivity, ShowVideoActivity.class);
//                intent.putExtra("url", lists.get(0).path);
//                startActivity(intent);
            }

            @Override
            public void addDataNoVideo() {
//                Toast.makeText(mActivity, "添加过了图像，不能添加视频了", Toast.LENGTH_SHORT).show();
                PhotoPickerIntent intentPhoto = new PhotoPickerIntent(mActivity);
                intentPhoto.setPhotoCount(9 - lists.size());
                intentPhoto.setShowCamera(true);
                startActivityForResult(intentPhoto, CHECK_IMAGE_REQUEST);
            }

            @Override
            public void deleteItem(int position) {
//                lists.remove(position);
//                adapter.refreshData(lists);
            }
        });

    }

    /**
     * 显示popwindow
     */
    public void showPop() {
        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(R.layout.layout_popupwindow, null);
        }
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(mActivity).inflate(R.layout.activity_publish_humor
                , null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到调用系统相机
                Intent intent = new Intent();
                intent.setType("video/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, CHECK_VIDEO_REQUEST);
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到调用系统图库
                PhotoPickerIntent intentPhoto = new PhotoPickerIntent(mActivity);
                intentPhoto.setPhotoCount(9 - imgPathlist.size());
                intentPhoto.setShowCamera(true);
                startActivityForResult(intentPhoto, CHECK_IMAGE_REQUEST);
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

    }

    @Subscribe
    public void onMessageEvent(DeleteEventBus deleteEventBus) {
        List<Integer> index = deleteEventBus.getIndex();
        int integer = index.get(0);
        lists.remove(integer);
        imgPathlist.remove(integer);
        adapter.refreshData(imgPathlist);


    }

    //初始化定位信息
    private void initMap() {
        mlocationClient = new AMapLocationClient(this);
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置返回地址信息，默认为true


        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation amapLocation) {
                if (amapLocation != null) {
                    if (amapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息

                        String pro = amapLocation.getProvince();//省信息
                        String city = amapLocation.getCity();//城市信息
                        String dist = amapLocation.getDistrict();//城区信息

                        pos_tv.setText(pro + city + dist);
                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + amapLocation.getErrorCode() + ", errInfo:"
                                + amapLocation.getErrorInfo());
                    }
                }
            }
        });
//设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
//设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        //设置是否只定位一次,默认为false

        mlocationClient.startLocation();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case CHECK_IMAGE_REQUEST:

                   final List<String> mResultsPath = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
                    imgPathlist.addAll(mResultsPath);
                    adapter.refreshData(imgPathlist);
//                    lubanCompress(imgPathlist);
//
//                    for (int i = 0; i < mResultsPath.size(); i++) {
//
//                        index=i;
//                        //得到图片的path
//                         path = mResultsPath.get(i);
//                        Log.e("wlx", "onActivityResult: "+ path);
//                        //压缩
//                        lubanCompress();
//
//                    }

//
                    break;
                case CHECK_VIDEO_REQUEST:
//                    HumorImgAndVideoBean bean = new HumorImgAndVideoBean();
//                    bean.isVideo = true;
//                    Uri uri = data.getData();
//                    Cursor cursor = getContentResolver().query(uri, null, null,
//                            null, null);
//                    cursor.moveToFirst();
//                    // String imgNo = cursor.getString(0); // 图片编号
//                    String v_path = cursor.getString(1); // 图片文件路径
//                    bean.path = v_path;
//                    lists.add(bean);
//                    adapter.refreshData(lists);
//                    lists.get(0).compresPath = lists.get(0).path;
//                    getUpimg(lists.get(0).path);
                    break;
            }
        }
    }

    private void lubanCompress(final List<String> photosList ) {
        Luban.with(mActivity).load(photosList).setCompressListener(new OnCompressListener() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(File file) {
                lists.add(new HumorImgAndVideoBean(file.getPath(),file.getPath()));
                Log.e("wlx", "lubanCompress: "+lists.size() );
                myHandler.sendEmptyMessage(YAOSUO_SUCCESS);

            }

            @Override
            public void onError(Throwable e) {
                ToastUtils.show(mActivity,"数据提交失败");
                dismissProgressDialog();
            }
        }).launch();
//
    }


    public void getUPing2(List<HumorImgAndVideoBean> list){
        //压缩

       for (int i = 0; i < list.size(); i++) {
                new MyThread2(list.get(i).compresPath).start();
            }
    }


    public void pulishHumor(String contentstr, String imageuil, String videouil, String palceStr) {
        int id = UserBean.getUserBean().userId;
        Call<PulishHumorBean> call = apiStores.getPulishHumorService(
                UserBean.getUserBean().userId + "",
                DateUtil.getNowDate(), contentstr, imageuil, videouil, palceStr
        );

        call.enqueue(new Callback<PulishHumorBean>() {
            @Override
            public void onResponse(Call<PulishHumorBean> call, Response<PulishHumorBean> response) {
                if (response.isSuccessful()) {
                    PulishHumorBean pulishHumorbean = response.body();
                    if (pulishHumorbean.getCode() == 0) {
                        dismissProgressDialog();
                        setResult(RESULT_OK,new Intent().putExtra(RELEASE,true));
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<PulishHumorBean> call, Throwable t) {
                dismissProgressDialog();
            }
        });
        addCalls(call);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mlocationClient.onDestroy();//销毁定位客户端。
        //接触注册
        EventBus.getDefault().unregister(this);
    }

    public class MyThread2 extends Thread {
        String imgePath;

        public MyThread2(String imagePath) {
            this.imgePath = imagePath;
        }

        @Override
        public void run() {
            // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
            UploadManager uploadManager = new UploadManager();
            uploadManager.put(imgePath, imgePath, token,
                    new UpCompletionHandler() {
                        @Override
                        public void complete(String key, ResponseInfo info, JSONObject res) {
                            try {
                                // 七牛返回的文件名
                                if (res != null) {
                                    String upimg = res.getString("key");
                                   String url= "http://ofplk6att.bkt.clouddn.com/" +upimg;

                                    Message message = myHandler.obtainMessage();
                                    message.obj=url;
                                    message.what=SHANG_SUCCESS;
                                    myHandler.sendMessage(message);
                                    Log.e("wlx", "complete: "+url );

                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }, new UploadOptions(null, null, false,             //上传进度
                            new UpProgressHandler() {
                                public void progress(String key, double percent) {
                                    double progress = percent * 100;
                                    Log.e("wlx", "progress: "+progress );
//
                                }
                            }, null));
        }
    }

}
