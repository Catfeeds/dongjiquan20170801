package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.GrammarListener;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by lenovo on 2017/2/8.
 */

public class VoiceUtil {
    private static String TAG = "语音";
    // 语音识别对象
    private SpeechRecognizer mAsr;
    private Toast mToast;
    // 缓存
    private SharedPreferences mSharedPreferences;
    // 云端语法文件
    private String mCloudGrammar = null;
    // 返回结果格式，支持：xml,json
    private String mResultType = "json";

    private final String KEY_GRAMMAR_ABNF_ID = "grammar_abnf_id";
    private final String GRAMMAR_TYPE_ABNF = "abnf";

    private String mEngineType = "cloud";
    int ret;

    Context context;

    public VoiceUtil(Context context,SharedPreferences mSharedPreferences) {
        this.context = context;
        // 初始化识别对象
        mAsr = SpeechRecognizer.createRecognizer(context, mInitListener);

        mCloudGrammar = FucUtil.readFile(context, "grammar_sample.abnf", "utf-8");

        this.mSharedPreferences = mSharedPreferences;
        mToast = Toast.makeText(context, "", Toast.LENGTH_SHORT);

        initAttri();
    }

    public void initAttri() {
        //showTip("上传预设关键词/语法文件");
        // 在线-构建语法文件，生成语法id
        String mContent = new String(mCloudGrammar);
        // 指定引擎类型
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        // 设置文本编码格式
        mAsr.setParameter(SpeechConstant.TEXT_ENCODING, "utf-8");
        ret = mAsr.buildGrammar(GRAMMAR_TYPE_ABNF, mContent, grammarListener);
        if (ret != ErrorCode.SUCCESS){
            log("语法构建失败,错误码：" + ret);
        }
    }

    //开启语音
    public void startVoice(){
        // 设置参数
        if (!setParam()) {
            return;
        };

        ret = mAsr.startListening(mRecognizerListener);
        if (ret != ErrorCode.SUCCESS) {
            log("识别失败,错误码: " + ret);
        }
    }
    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
                log("初始化失败,错误码：" + code);
            }
        }
    };

    /**
     * 构建语法监听器。
     */
    private GrammarListener grammarListener = new GrammarListener() {
        @Override
        public void onBuildFinish(String grammarId, SpeechError error) {
            if (error == null) {
                if (mEngineType.equals(SpeechConstant.TYPE_CLOUD)) {
                    SharedPreferences.Editor editor = mSharedPreferences.edit();
                    if (!TextUtils.isEmpty(grammarId))
                        editor.putString(KEY_GRAMMAR_ABNF_ID, grammarId);
                    editor.commit();
                }
                log("语法构建成功：" + grammarId);
                startVoice();
            } else {
                log("语法构建失败,错误码：" + error.getErrorCode());
            }
        }
    };
    /**
     * 识别监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            //log("当前正在说话，音量大小：" + volume + "--" + data.length);
        }

        @Override
        public void onResult(final RecognizerResult result, boolean isLast) {
            if (null != result && !TextUtils.isEmpty(result.getResultString())) {
                Log.d(TAG, "recognizer result：" + result.getResultString());
                String text = "";
                if (mResultType.equals("json")) {
                    text = JsonParser.parseGrammarResult(result.getResultString(), mEngineType);
                } else if (mResultType.equals("xml")) {
                    text = XmlParser.parseNluResult(result.getResultString());
                }
                log(text);
                if(text.equals("没有匹配结果.")){
                    mAsr.startListening(mRecognizerListener);
                    return;
                }
                Pattern p = Pattern.compile("】(.*)【");
                Matcher m = p.matcher(text);
                for(int i = 0;i < 1;i++){
                    m.find();
                    text = m.group(1);
                    log(text);
                }
                voiceListene.controlResult(text);
                /*while(m.find()){
                    text = m.group(1);
                    log(text);
                }*/

                if (text.equals("迈迈前进") || text.equals("迈迈走起来") || text.equals("迈迈跑起来") || text.equals("迈迈开始")) {
                    log("进入");
                    voiceListene.controlStopStart(0);
                } else if (text.equals("迈迈加速") || text.equals("迈迈加快速度") || text.equals("迈迈再快点") || text.equals("迈迈快") || text.equals("迈迈快快的")) {
                    voiceListene.controlSpeedSlope(1);
                } else if (text.equals("迈迈减速") || text.equals("迈迈减慢速度") || text.equals("迈迈再慢点") || text.equals("迈迈慢慢的")) {
                    voiceListene.controlSpeedSlope(2);
                } else if (text.equals("迈迈停下") || text.equals("迈迈停下来") || text.equals("迈迈停")) {
                    voiceListene.controlStopStart(1);
                } else if (text.equals("迈迈高一点") || text.equals("迈迈再高一点") || text.equals("迈迈抬高") || text.equals("迈迈增加坡度")) {
                    voiceListene.controlSpeedSlope(3);
                } else if (text.equals("迈迈低一点") || text.equals("迈迈在低一点") || text.equals("迈迈降低") || text.equals("迈迈降低坡度")) {
                    voiceListene.controlSpeedSlope(4);
                } else if (text.equals("迈迈速度0") || text.equals("迈迈速度1") || text.equals("迈迈速度2") || text.equals("迈迈速度3") || text.equals("迈迈速度4")
                        || text.equals("迈迈速度5") || text.equals("迈迈速度6") || text.equals("迈迈速度7") || text.equals("迈迈速度8") || text.equals("迈迈速度9")
                        || text.equals("迈迈速度10") || text.equals("迈迈速度11") || text.equals("迈迈速度12") || text.equals("迈迈速度13") || text.equals("迈迈速度14")
                        || text.equals("迈迈速度15") || text.equals("迈迈速度16")) {
                    voiceListene.controlSpeedSlopeData(1,text);
                } else if (text.equals("迈迈坡度0") || text.equals("迈迈坡度1") || text.equals("迈迈坡度2") || text.equals("迈迈坡度3") || text.equals("迈迈坡度4")
                        || text.equals("迈迈坡度5") || text.equals("迈迈坡度6") || text.equals("迈迈坡度7") || text.equals("迈迈坡度8") || text.equals("迈迈坡度9")
                        || text.equals("迈迈坡度10") || text.equals("迈迈坡度11") || text.equals("迈迈坡度12")) {
                    voiceListene.controlSpeedSlopeData(2,text);
                }

                log("返回结果");
                mAsr.startListening(mRecognizerListener);
            } else {
                Log.d(TAG, "recognizer result : null");
            }
        }
            @Override
            public void onEndOfSpeech () {
                // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
                voiceListene.controlStyle(1);
                log("结束说话");
                //mAsr.startListening(mRecognizerListener);
            }

            @Override
            public void onBeginOfSpeech () {
                // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
                log("开始说话");
                voiceListene.controlStyle(0);
            }

            @Override
            public void onError (SpeechError error){
                log("出现错误" + error.getErrorCode());
                if(error.getErrorCode() != 11201){
                    mAsr.startListening(mRecognizerListener);
                }
            }

            @Override
            public void onEvent ( int eventType, int arg1, int arg2, Bundle obj){
                // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
                log("出现onEvent ----" + arg1 + "----" + arg1 + "----");
                //mAsr.startListening(mRecognizerListener);
                // 若使用本地能力，会话id为null
                //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
                //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
                //		Log.d(TAG, "session id =" + sid);
                //	}
            }
    };

    /*private void showTip(final String str) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mToast.setText(str);
                mToast.show();
            }
        });
    }*/

    /**
     * 参数设置
     * @return
     */
    public boolean setParam(){
        boolean result = false;
        // 清空参数
        mAsr.setParameter(SpeechConstant.PARAMS, null);
        // 设置识别引擎
        mAsr.setParameter(SpeechConstant.ENGINE_TYPE, mEngineType);
        String grammarId = mSharedPreferences.getString(KEY_GRAMMAR_ABNF_ID, null);
        if(TextUtils.isEmpty(grammarId))
        {
            result =  false;
        }else {
            // 设置返回结果格式
            mAsr.setParameter(SpeechConstant.RESULT_TYPE, mResultType);
            // 设置云端识别使用的语法id
            mAsr.setParameter(SpeechConstant.CLOUD_GRAMMAR, grammarId);
            result =  true;
        }

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
        mAsr.setParameter(SpeechConstant.AUDIO_FORMAT,"wav");
        mAsr.setParameter(SpeechConstant.ASR_AUDIO_PATH, Environment.getExternalStorageDirectory()+"/msc/asr.wav");
        log(result+"");
        return result;
    }

    public void log(String str){
        Log.i("测试","--------"+str+"---------");
    }

    public interface VoiceListene{
        void controlStopStart(int n);         //启动停止
        void controlSpeedSlope(int n);        //速度坡度增减
        void controlSpeedSlopeData(int n,String str);    //速度坡度具体值

        void controlStyle(int n);           //语音状态监测
        void controlResult(String str);      //返回结果监听
    }


    public VoiceListene voiceListene;
    //定义接口入住对象
    public void setGetIntentDataListener(VoiceListene voiceListene){
        this.voiceListene = voiceListene;
    }
}
