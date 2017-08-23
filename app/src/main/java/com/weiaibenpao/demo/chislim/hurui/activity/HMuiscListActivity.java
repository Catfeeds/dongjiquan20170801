package com.weiaibenpao.demo.chislim.hurui.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.MusicListAdapter;
import com.weiaibenpao.demo.chislim.bean.Li_MusicResult;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;
import com.weiaibenpao.demo.chislim.music.MusicManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class HMuiscListActivity extends HBaseActivity implements MusicListAdapter.OnItemClickListener, MusicManager.OnPlayingListener {
    @BindView(R.id.back)
    ImageView backImg;
    @BindView(R.id.topImg)
    ImageView topImg;
    @BindView(R.id.titleImg)
    CircleImageView titleImg;
    @BindView(R.id.musicType)
    TextView musicType;
    @BindView(R.id.total_songs)
    TextView totalSongs;
    @BindView(R.id.localMusicListView)
    RecyclerView musicListView;

    private MusicListAdapter musicListAdapter;
    private Li_MusicResult.DataBean musicResult;
    private ArrayList<Li_MusicResult.DataBean.MusicTypelistBean> musicTypeList;
    private ArrayList<Li_MusicResult.DataBean.MusicTypelistBean.MusicListBean> musicList;
    private int type;
    private int musicCount;

    /**
     * 模糊效果使用Alpha就行
     * 电话监听转移到MusicManager里面
     * 自动销毁什么鬼----------先不继承 HBaseActivity试试 ，如果还出现那么单独拎到一个项目里测试一下。。
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hmuisc_list);
        ButterKnife.bind(this);
        initData();
        initView();
//        Log.e("HMuiscListActivity", "getLevel()---> 向上取整 " + getLevel(250));
//        Log.e("HMuiscListActivity", "2的3次方 " + Math.pow(2,3));
    }

    private double getLevel(double input) {
        double y = input / 50;
        double divisor = Math.log(y);
        double dividend = Math.log(2);
        double quotient  = divisor/dividend;
        double result = Math.ceil(quotient);
        Log.e("getLevel","y is "+y+"\ndivisor is "+divisor+"\ndividend is "+dividend+"\nquotient is "+quotient+"\nresult  is "+result);
        if(result < 0 ){
            return  0 ;
        }
        return result;
    }

    private void initData() {
//        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        telephonyManager.listen(new MyPhoneListener(), PhoneStateListener.LISTEN_CALL_STATE);
        Bundle bundle = getIntent().getExtras();
        type = bundle.getInt("num", 1);
        Log.e("initData ", "type is " + type);
        musicResult = (Li_MusicResult.DataBean) bundle.getSerializable("musicResult");
        if (musicResult != null) {
            musicTypeList = (ArrayList<Li_MusicResult.DataBean.MusicTypelistBean>) musicResult.getMusicTypelist();
        }
        if (musicTypeList != null && musicTypeList.size() > 0) {
            musicList = (ArrayList<Li_MusicResult.DataBean.MusicTypelistBean.MusicListBean>) musicTypeList.get(type - 1).getMusicList();
        }
        if (musicList != null && musicList.size() > 0) {
            musicCount = musicList.size();
            MusicManager.getInstance().registerOnPlayingListener(this);
            MusicManager.getInstance().setIsLoop(true);
            List<String> musicUrlList = new ArrayList<>();
            for (int i = 0; i < musicCount; i++) {
                musicUrlList.add(musicList.get(i).getMusicUrl());
            }
            MusicManager.getInstance().setMusicUrlList(musicUrlList);
        }
    }

    private void initView() {
        if (musicTypeList != null && musicTypeList.size() > 0)
            musicType.setText(musicTypeList.get(type - 1).getMusicType());
        totalSongs.setText(musicCount + "首");
        musicListAdapter = new MusicListAdapter(this, musicList);
        musicListAdapter.registerOnItemClickListener(this);
        musicListView.setLayoutManager(new LinearLayoutManager(this));
        musicListView.setAdapter(musicListAdapter);
        setTopView();
        Log.e("MusicList", "playing type is " + MusicManager.getInstance().getPlayingType());
        if (MusicManager.getInstance().isPlaying() && type == MusicManager.getInstance().getPlayingType()) {
            restorePlayingState();
        }
    }

    private void setTopView() {
        Picasso.with(this)
                .load((musicTypeList.get(type - 1)).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(topImg);
        Picasso.with(this)
                .load((musicTypeList.get(type - 1)).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(titleImg);
    }

    private void restorePlayingState() {
        int position = MusicManager.getInstance().getPlayingPosition();
        musicListAdapter.clickItem(position);
    }

    @Override
    public void onItemClick(int position) {
        if(position <0 || position >= musicCount){
            return;
        }
//        Log.e("onItemClick", "position is " + position);
        musicListAdapter.clickItem(position);
        if (type == MusicManager.getInstance().getPlayingType() && position == MusicManager.getInstance().getPlayingPosition()) {
            MusicManager.getInstance().stop();
//            Log.e("onItemClick", "stop " + position);
        } else {
            MusicManager.getInstance().play(musicList.get(position).getMusicUrl(), position, type);
//            Log.e("onItemClick", "play " + position);
        }
    }

    @OnClick(R.id.back)
    public void onClickBack(View v){
        finish();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
//        Log.e("MusicList", "onSaveInstanceState");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("MusicList", "onDestroy");
        if (musicListAdapter != null) {
            musicListAdapter.unregisterOnItemClickListener();
        }
        MusicManager.getInstance().unregisterOnPlayingListener();
    }

    @Override
    public void onCompleted(int position) {
        if(position < 0 ){
            return;
        }
        Log.e("onCompleted", " position is " + position);
        int newPosition = ++position % musicCount;
        if (musicListAdapter != null) {
            musicListAdapter.clickItem(newPosition);
        }
//        onItemClick(pos);
    }

    @Override
    public void onError(int position, int what, int extra) {
        if (position >0 && musicList != null && musicList.size() >0 && position<musicList.size() && musicList.get(position) != null) {
            Toast.makeText(this, "歌曲 《" + musicList.get(position).getMusicName() + "》播放失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 只有电话来了之后才暂停音乐的播放
     */
    private final class MyPhoneListener extends PhoneStateListener {
        @Override
        public void onCallStateChanged(int state, String incomingNumber) {
            switch (state) {
                case TelephonyManager.CALL_STATE_RINGING://电话来了


                    break;
                case TelephonyManager.CALL_STATE_IDLE: //通话结束

                    break;
            }
        }
    }


}
