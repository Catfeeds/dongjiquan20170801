package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.ChatMessageAdapter2;
import com.weiaibenpao.demo.chislim.bean.ChatMessage;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.HttpUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by zhangxing on 2017/3/10.
 */


public class RobotActivity extends Activity {
    private RecyclerView listView;
    private TextView sendBtn;
    private EditText InputMsg;
    private UserBean user;
    private ImageView robot_left;
    private List<ChatMessage> msgList;
    private ChatMessageAdapter2 chatMessageAdapter;

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {

            ChatMessage fromMessage = (ChatMessage) msg.obj;
            msgList.add(fromMessage);
            chatMessageAdapter.notifyDataSetChanged();
            // listView.setSelection(msgList.size()); // 将ListView定位到最后一行
            // listView.scrollToPosition(msgList.size());
            listView.smoothScrollToPosition(chatMessageAdapter.getItemCount()-1);
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.robot_item);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        initView();
        initData();
        initListerner();
    }

    private void initListerner() {

        robot_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String to_msg = InputMsg.getText().toString();
                if(TextUtils.isEmpty(to_msg)){
                    Toast.makeText(RobotActivity.this,"发送消息不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }

                ChatMessage toMessage = new ChatMessage();
                toMessage.setName(user.userName);
                toMessage.setImgStr(user.userImage);
                toMessage.setDate(new Date());
                toMessage.setMsg(to_msg);
                toMessage.setType(ChatMessage.Type.OUTCOMING);
                msgList.add(toMessage);
                chatMessageAdapter.notifyDataSetChanged();

                InputMsg.setText("");

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // ChatMessage fromMessage = HttpUtils.sengMessage(to_msg);
                        ChatMessage fromMessage = HttpUtils.sengMessage(to_msg);
                        try {
                            Thread.sleep(800);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        Log.i("json",fromMessage.getMsg()+"");
                        Message m = Message.obtain();
                        m.obj = fromMessage;
                        handler.sendMessage(m);
                    }
                }).start();
            }
        });
    }

    private void initView() {
        listView = (RecyclerView) findViewById(R.id.listview);
        listView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        InputMsg = (EditText) findViewById(R.id.input_msg);
        sendBtn = (TextView) findViewById(R.id.sendBtn);
        robot_left = (ImageView) findViewById(R.id.back);
    }

    private void initData() {
        user = UserBean.getUserBean();
        msgList = new ArrayList<ChatMessage>();
        msgList.add(new ChatMessage("你好，小迈为您服务!", ChatMessage.Type.INCOMING,new Date()));
        chatMessageAdapter = new ChatMessageAdapter2(this,msgList);
        listView.setAdapter(chatMessageAdapter);
    }
}
