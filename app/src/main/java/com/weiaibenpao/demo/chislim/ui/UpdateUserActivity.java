package com.weiaibenpao.demo.chislim.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.customs.WheelView;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.CircleImg;
import com.weiaibenpao.demo.chislim.util.CircleTransform;
import com.weiaibenpao.demo.chislim.util.FileUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import org.greenrobot.eventbus.EventBus;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Calendar;

import cz.msebera.android.httpclient.Header;
import jameson.io.library.util.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.weiaibenpao.demo.chislim.bean.UserBean.getUserBean;


public class UpdateUserActivity extends Activity {

    private EditText updateUserName;
    private TextView updateSex;
    private TextView updateHeigh;
    private TextView updateWeight;
    private TextView updateBirth;
    /*    private TextView updatePhone;
        private TextView updateEmail;*/
    private TextView okUpdate;
    private EditText updateHobby,userIntro;

    private ImageView myImage;
    private ImageView back;

    private Calendar cal;
    private int year;
    private int month;
    private int day;

    //用于图片的上传
    private Context context;
  //  Bitmap icon;
    private CircleImg avatarImg;// 头像图片
    //登录用户
    UserBean user;
    public static final String PREFS_NAME = "UserInfo";
    private SharedPreferences settings;
    private boolean flag=true;

    // 自定义的头像编辑弹出框
    private SelectPicPopupWindow menuWindow;
    private static final String IMAGE_FILE_NAME = "avatarImage.jpg";// 头像文件名称
    private static final int REQUESTCODE_PICK = 0;		// 相册选图标记
    private static final int REQUESTCODE_TAKE = 1;		// 相机拍照标记
    private static final int REQUESTCODE_CUTTING = 2;	// 图片裁切标记

    private String urlpath;			// 图片本地路径


    //--------修改用户数据后重新赋值user
    String name;

    private BottomSheetDialog mBottomSheetDialog;
    private float data;
    private String dateTime;
    private String sexSelect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        settings = getSharedPreferences(PREFS_NAME, 0);

        context = UpdateUserActivity.this;
       // icon = BitmapFactory.decodeResource(context.getResources(),R.mipmap.zhanwei);

        //获取一个日历对象
        cal = Calendar.getInstance();
        //获取年月日时分秒的信息
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH)+1;
        day = cal.get(Calendar.DAY_OF_MONTH);

        initdata();
        initView();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initSetData();
    }

    public void initdata() {
        user = getUserBean();
    }


    public void initView(){
        //new 一个BottomSheetDialog对象
        mBottomSheetDialog = new BottomSheetDialog(this);
        //完成
        okUpdate = (TextView) findViewById(R.id.okUpdate);
        okUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (flag){
                   updateUser();
               }else
                   ToastUtils.show(context,"头像上传失败");
            }
        });
        //用户头像
        myImage = (ImageView) findViewById(R.id.myImage);
        Log.i("显示","111**************");
        if(user != null && !TextUtils.isEmpty(user.userImage)){
            Picasso.with(this)
                    .load(user.userImage)
                    .resize(200, 200)
                    .placeholder(R.drawable.logo1)
                    .error(R.drawable.logo1)
                    .transform(new CircleTransform())
                    .into(myImage);
        }

        //用户昵称
        updateUserName = (EditText) findViewById(R.id.updateUserName);


        //用户性别
        updateSex = (TextView) findViewById(R.id.updateSex);
        updateSex.setText(user.userSex);

        //用户身高
        updateHeigh = (TextView) findViewById(R.id.updateHeigh);
        updateHeigh.setText(String.valueOf(user.userHeight));

        //用户体重
        updateWeight = (TextView) findViewById(R.id.updateWeight);
        updateWeight.setText(String.valueOf(user.userWeight));

        //用户生日
        updateBirth = (TextView) findViewById(R.id.updateBirth);
        updateBirth.setText(user.userBirth);

        //用户的爱好
        updateHobby = (EditText) findViewById(R.id.updateHobby);



        userIntro = (EditText) findViewById(R.id.userIntro);


        userIntro.setText(user.userTntru);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void initSetData(){
        updateUserName.setText(user.userName);
        updateHobby.setText(user.userHobby);
        userIntro.setText(user.userTntru);
    }


    /**
     *
     * @param tv
     * @param type 1 是签名 2是昵称
     */
    private void modifyNick_address(final EditText tv, final int type) {
        tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {



            }

            @Override
            public void afterTextChanged(Editable s) {

//
                    if (type==1){
                        if (s.length()>21){
                            s.delete(20,s.length());
                        }
                    }
                    if (type==2){
                        if (s.length()>11){
                            s.delete(11,s.length());
                        }
                    }

            }
        });

    }

    /**
     * 生日弹框
     * @param v
     */
    public void updateBirth(View v){

        View view = LayoutInflater.from(context).inflate(R.layout.date_layout,null,false);

       DatePicker datePicker = (DatePicker) view.findViewById(R.id.update_datePicker);
        //初始化DatePicker组件，初始化时指定监听器

        datePicker.init(1992, 01, 01, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                // 显示当前日期、时间
                month = month+1;
                 dateTime = year + "." + month + "." + day;
                Log.i("日期",dateTime);


            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
               if (dateTime != null){
                   updateBirth.setText(dateTime);
                   user.userBirth = dateTime;
               }
            }
        });
        createBottomSheetDialog(view);
    }

    /**
     * 点击性别触发
     * @param v
     */
    public void updateSex(View v){
        sexDialog();
    }

    //点击身高触发
    public void userHeight(View v){
        heighDialog(2);
    }
    //点击体重触发
    public void userWeight(View v){
        heighDialog(1);
    }
    //点击爱好触发
    public void updateHobby(View v){
        modifyNick_address(updateHobby,1);
//        updateHobby.addTextChangedListener(new MaxLimitTextWatcher(updateHobby,20));

    }

    //点击个性签名触发
    public void updateUserIntro(View v){
       modifyNick_address(userIntro,1);
//        userIntro.addTextChangedListener(new MaxLimitTextWatcher(userIntro,20));
    }

    //触发昵称
    public void updateName(View v){
        modifyNick_address(updateUserName,2);
//        updateUserName.addTextChangedListener(new MaxLimitTextWatcher(updateUserName,10));
    }

    /**
     * 点击更换头像
     * @param v
     */
    public void updateImage(View v){

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},
                    1);}

        menuWindow = new SelectPicPopupWindow(UpdateUserActivity.this, itemsOnClick);
        menuWindow.showAtLocation(findViewById(R.id.activity_update_user),
                Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0);

    }

    //为弹出窗口实现监听类
    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                // 拍照
                case R.id.takePhotoBtn:
                    Intent takeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    //下面这句指定调用相机拍照后的照片存储的路径
                    takeIntent.putExtra(MediaStore.EXTRA_OUTPUT,
                            Uri.fromFile(new File(Environment.getExternalStorageDirectory(), IMAGE_FILE_NAME)));
                    startActivityForResult(takeIntent, REQUESTCODE_TAKE);
                    break;
                // 相册选择图片
                case R.id.pickPhotoBtn:
                    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
                    // 如果朋友们要限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型"
                    pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                    startActivityForResult(pickIntent, REQUESTCODE_PICK);
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:// 直接从相册获取
                try {
                    startPhotoZoom(data.getData());
                } catch (NullPointerException e) {
                    e.printStackTrace();// 用户点击取消操作
                }
                break;
            case REQUESTCODE_TAKE:// 调用相机拍照
                File temp = new File(Environment.getExternalStorageDirectory() + "/" + IMAGE_FILE_NAME);
                startPhotoZoom(Uri.fromFile(temp));
                break;
            case REQUESTCODE_CUTTING:// 取得裁剪后的图片
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 裁剪图片方法实现
     * @param uri
     */
    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    /**
     * 保存裁剪之后的图片数据
     * @param picdata
     */
    private void setPicToView(Intent picdata) {
        Bundle extras = picdata.getExtras();
        if (extras != null) {
            // 取得SDCard图片路径做显示
            Bitmap photo = extras.getParcelable("data");
            Drawable drawable = new BitmapDrawable(null, photo);
            urlpath = FileUtil.saveFile(UpdateUserActivity.this, "temphead.jpg", photo);

            reg(user.userId,context,photo,"图片名称");
           /* myImage.setImageBitmap(photo);*/
            Log.i("地址",urlpath);
            /*Picasso.with(this)
                    .load("file://"+ urlpath)
                    .placeholder(R.mipmap.zw1)
                    .error(R.mipmap.zw2)
                    .transform(new CircleTransform())
                    .into(myImage);*/
        }
    }

    /**
     * 上传图片
     * @param userId
     * @param cont
     * @param photodata
     * @param regData
     */
    public void reg(int userId, final Context cont, Bitmap photodata, String regData) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //将bitmap一字节流输出 Bitmap.CompressFormat.PNG 压缩格式，100：压缩率，baos：字节流
            photodata.compress(Bitmap.CompressFormat.PNG, 100, baos);
            baos.close();
            byte[] buffer = baos.toByteArray();
            System.out.println("图片的大小："+buffer.length);

            //将图片的字节流数据加密成base64字符输出
            String photo = Base64.encodeToString(buffer, 0, buffer.length,Base64.DEFAULT);

            //photo=URLEncoder.encode(photo,"UTF-8");
            RequestParams params = new RequestParams();
            //params.put("dowhat", "updateImage");
            params.put("userId", userId);
            params.put("photo", photo);
            params.put("name", regData);//传输的字符数据
            String url = "http://112.74.28.179:8080/Chislim/UserServlet?dowhat=updateImg";


            AsyncHttpClient client = new AsyncHttpClient();
            client.post(url, params, new AsyncHttpResponseHandler() {

                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String imageUrl = new String(responseBody);
                    UserBean user = getUserBean();
                    user.userImage = imageUrl;
                    Log.i("图片上传成功：",imageUrl);
                    //展示图片
                    Picasso.with(UpdateUserActivity.this)
                            .load(imageUrl)
                            .resize(200, 200)
                            .transform(new CircleTransform())
                            .error(R.drawable.logo1)
                            .into(myImage);
                    UpdateUserActivity.this.changeImage();
                    updateImg(imageUrl);
                    Log.i("显示","222**************");
//                    Picasso.with(UpdateUserActivity.this)
//                            .load(user.userImage)
//                            .resize(200, 200)
//                            .transform(new CircleTransform())
//                            .error(R.drawable.logo1)
//                            .into(myImage);
                }
                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    Toast.makeText(cont,statusCode+"图片上传失败",Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //提交头像
    private void updateImg(String imageUrl) {
        MyModel.getModel().getService().getImage(user.userId,imageUrl).enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.body().getMsg().equals("成功")){
                    flag=true;
                }else
                    flag=false;
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {

            }
        });
    }

    //修改图片本地存放链接
    public void changeImage(){
        //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
        SharedPreferences.Editor edit = settings.edit();
        edit.putString("userImage",user.userImage);

        //提交
        edit.commit();
    }




    /**
     * 性别弹出框
     */
    public void sexDialog(){

        final String sex[]={"男","女"};
        sexSelect=null;
        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.sex_layout,null);
        final WheelView wheelView = (WheelView) view.findViewById(R.id.wheel_view_wv);

        wheelView.setOffset(1);
        wheelView.setItems(Arrays.asList(sex));
        wheelView.setOnWheelViewListener(new WheelView.OnWheelViewListener(){
            @Override
            public void onSelected(int selectedIndex, String item) {
                super.onSelected(selectedIndex, item);
                sexSelect=item;

            }
        });
        view.findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBottomSheetDialog.dismiss();
            }
        });
        view.findViewById(R.id.commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if ( sexSelect!=null){
                  updateSex.setText(sexSelect);
                  user.userSex=sexSelect;
              }else {
                  updateSex.setText(sex[0]);
                  user.userSex=sex[0];

              }

                mBottomSheetDialog.dismiss();
            }
        });
        createBottomSheetDialog(view);
    }


    /**
     * 身高或者体重弹框
     * @param style 1是体重 2是身高
     */
    public void heighDialog(final int style){

        View view = LayoutInflater.from(context).inflate(R.layout.layout_height_weight, null, false);
        final SeekBar seekBar = (SeekBar) view.findViewById(R.id.seekBar);

        TextView cancel = (TextView) view.findViewById(R.id.cancel);
        TextView commit = (TextView) view.findViewById(R.id.commit);
        final TextView allWeight = (TextView) view.findViewById(R.id.allWeight);
           if (style==1){
               seekBar.setMax(150);
               allWeight.setText(Math.round(getUserBean().userWeight) + "kg");
               seekBar.setProgress(Math.round(getUserBean().userWeight));
           }
           if (style==2){
               seekBar.setMax(230);
               allWeight.setText(Math.round(getUserBean().userHeight) + "cm");
               seekBar.setProgress(Math.round(getUserBean().userHeight));
           }


        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
            }
        });
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mBottomSheetDialog.dismiss();
                if (style == 1) {
                  getUserBean().userWeight=data;
                    updateWeight.setText(data+"");

                } else if (style == 2) {
                   UserBean.getUserBean().userHeight= (int) data;

                    updateHeigh.setText((int)data+"");
                }
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                    data=i;
                if (style == 1) {
                    allWeight.setText(data+"kg");
                } else if (style == 2) {
                    allWeight.setText(i+"cm");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        createBottomSheetDialog(view);
    }



    /**
     * 底部滑动显示
     *
     */
    private void createBottomSheetDialog(View view) {

        mBottomSheetDialog.setContentView(view);
        //显示
        mBottomSheetDialog.show();
    }

    /**
     * 修改用户信息
     */
    public void updateUser(){

        user.userName = updateUserName.getText().toString().trim();
        user.userSex = updateSex.getText().toString().trim();
        user.userHeight = Integer.parseInt(updateHeigh.getText().toString().trim());
        user.userWeight =  Float.valueOf(updateWeight.getText().toString().trim());
        user.userBirth = updateBirth.getText().toString().trim();
        // email = updateEmail.getText().toString().trim();
        user.userHobby = updateHobby.getText().toString().trim();
        user.userTntru = userIntro.getText().toString().trim();

        Call<Li_UserBeanResult> call = MyModel.getModel().getService().updateUserInfo(user.userId,
                user.userName,user.userSex,user.userBirth,user.userEmail,user.userTntru,user.userHobby,user.userWeight,user.userHeight);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    Li_UserBeanResult result = response.body();
                    if (result.getCode() == 0) {

                        WriteReadSharedPrefs.writeUser(context,result.getData());
                        WriteReadSharedPrefs.readUser(context, getUserBean());
                        Log.e("wlx000", "onResponse: "+ getUserBean());
                        EventBus.getDefault().post(true);

                    }else{

                    }
                }else{

                }
            }
            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {

            }
        });
        finish();
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode, grantResults);
    }

    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted

            } else {
                // Permission Denied
                //  displayFrameworkBugMessageAndExit();
                Toast.makeText(this, "请在应用管理中打开“相机”访问权限！", Toast.LENGTH_LONG).show();
                finish();
            }
        }
    }

}
