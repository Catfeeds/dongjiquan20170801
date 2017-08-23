package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;


/**
 * Created by zhangxing on 2017/2/15.
 */

public class VoicePlayerUtil {
    // 语记安装助手类
    ApkInstaller mInstaller ;
    Context mContext;
    String voiceText;
    private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;
    // 语音合成对象
    private SpeechSynthesizer mTts;

    // 默认发音人(henry,vimary,catherine可选择)
    private String voicer = "xiaoyan";
    //构造器
    public VoicePlayerUtil(Context context, SharedPreferences sharedPreferences){
        mContext = context;
       // mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        mSharedPreferences = sharedPreferences;

        initVoice();
    }


    private void initVoice() {
//        // 初始化合成对象
//        mTts = SpeechSynthesizer.createSynthesizer(mContext, mTtsInitListener);
//        mInstaller = new  ApkInstaller( mContext.getApplicationContext());
    }


    public void startVoice(String str) {
//       if(SharedPrefsUtil.getValue(mContext,"MySetting","voices",1) == 0){      //如果语音开关关闭
//         return;
//       }else {
//            // 移动数据分析，收集开始合成事件
//            FlowerCollector.onEvent(mContext, "tts_play");
//
//            // 设置参数
//            setParam();
//            int code = mTts.startSpeaking(str, mTtsListener);
////			/**
////			 * 只保存音频不进行播放接口,调用此接口请注释startSpeaking接口
////			 * text:要合成的文本，uri:需要保存的音频全路径，listener:回调接口
////			*/
////			String path = Environment.getExternalStorageDirectory()+"/tts.pcm";
////			int code = mTts.synthesizeToUri(text, path, mTtsListener);
//
//            if (code != ErrorCode.SUCCESS) {
//                if(code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED){
//                    //未安装则跳转到提示安装页面
//                    mInstaller.install();
//                }else {
//                    //showTip("语音合成失败,错误码: " + code);
//                    Log.i("VoiceUtil","语音合成失败,错误码："+code);
//                }
//            }
//        }
    }

    /**
     * 参数设置
     * @param
     * @return
     */
    private void setParam(){

        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);
        // 根据合成引擎设置相应参数
        if(mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);
            // 设置在线合成发音人
            mTts.setParameter(SpeechConstant.VOICE_NAME, voicer);
            //设置合成语速
            mTts.setParameter(SpeechConstant.SPEED, mSharedPreferences.getString("speed_preference", "50"));
            //设置合成音调
            mTts.setParameter(SpeechConstant.PITCH, mSharedPreferences.getString("pitch_preference", "50"));
            //设置合成音量
            mTts.setParameter(SpeechConstant.VOLUME, mSharedPreferences.getString("volume_preference", "150"));
        }else {
            mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_LOCAL);
            // 设置本地合成发音人 voicer为空，默认通过语记界面指定发音人。
            mTts.setParameter(SpeechConstant.VOICE_NAME, "");
            /**
             * TODO 本地合成不设置语速、音调、音量，默认使用语记设置
             * 开发者如需自定义参数，请参考在线合成参数设置
             */
        }
        //设置播放器音频流类型
        mTts.setParameter(SpeechConstant.STREAM_TYPE, mSharedPreferences.getString("stream_preference", "3"));
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
        mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/tts.wav");
    }


    /**
     * 初始化监听。
     */
    private InitListener mTtsInitListener = new InitListener() {
        @Override
        public void onInit(int code) {
            Log.d("Voice", "InitListener init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                //showTip("初始化失败,错误码："+code);
                Log.i("VoiceUtil","初始化失败,错误码："+code);
            } else {
                // 初始化成功，之后可以调用startSpeaking方法
                // 注：有的开发者在onCreate方法中创建完合成对象之后马上就调用startSpeaking进行合成，
                // 正确的做法是将onCreate中的startSpeaking调用移至这里
            }
        }
    };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener() {

        @Override
        public void onSpeakBegin() {
           // showTip("开始播放");
            Log.i("VoiceUtil","开始播放");
        }

        @Override
        public void onSpeakPaused() {
            //showTip("暂停播放");
            Log.i("VoiceUtil","暂停播放");
        }

        @Override
        public void onSpeakResumed() {
            //showTip("继续播放");
            Log.i("VoiceUtil","继续播放");
        }

        @Override
        public void onBufferProgress(int percent, int beginPos, int endPos,
                                     String info) {
            // 合成进度
            //showTip("正在为你语音播报！");
            Log.i("VoiceUtil","正在为你语音播报");
        }

        @Override
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
            // 播放进度
        }

        @Override
        public void onCompleted(SpeechError error) {
            if (error == null) {
                Log.i("VoiceUtil","播放完成");
            } else if (error != null) {
               // showTip(error.getPlainDescription(true));
                Log.i("VoiceUtil",error.getPlainDescription(true));
            }
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };



}
