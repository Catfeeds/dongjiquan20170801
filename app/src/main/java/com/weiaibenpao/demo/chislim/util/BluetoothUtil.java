package com.weiaibenpao.demo.chislim.util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothServerSocket;
import android.bluetooth.BluetoothSocket;
import android.os.Handler;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 手机端：
 *  1.开始：{"type":100}
 *  2.停止: {"type":101}
 *  3.速度控制：{"type":102,"speed":123} //为实际速度乘以10
 *  4.坡度控制：{"type":103,"incline":12}
 *
 * <p/>
 * 跑步机端:
 * 连接之后每200ms发送一次数据：
 * {"status":1,  //跑步机当前状态：1-->正常跑步中、2-->停止中、3、安全锁脱落、4.跑步机故障
 * "maxSpeed":240, //跑步机最大速度   实际速度乘以了10
 * "minSpeed":"10", //跑步机最小速度  实际速度乘以了10
 * "maxIncline":12, //跑步机最大坡度 ，0代表坡度不可调节
 * "param":{ //跑步数据
 *      "speed":120, //实际速度乘以10
 *      "incline":5,
 *      "duration":12939948  //单位ms
 *      "distance":2.3  //公里
 *      "calorie":345.2 //千卡
 *      "heartBeat":87
 * }}
 *
 * <p/>
 * 跑步结束后发送结果：
 * {
 * "sumTime":10283080 //ms
 * "sumDist":4.5 //km
 * "sumCal":456.7 //kcal
 * }
 *
 * Created by tiger on 2017/5/5.
 */
public final class BluetoothUtil {

    static final String TAG = "BluetoothUtil";
    static final boolean D = true;

    private Handler mHandler;
    private BluetoothAdapter mAdapter;

    private int mState;

    public static final int STATE_NONE = 0;
    public static final int STATE_LISTEN = 1; //服务端正在监听
    public static final int STATE_CONNECTING = 2; //客户端正在连接
    public static final int STATE_CONNECTED = 3; //双端已连接
    public static final int STATE_CONNECT_FAIL = 4; //客户端连接失败
    public static final int STATE_CONNECTION_LOST = 5; //其中一端断开了连接，通信断开

    public static final int MSG_READ = 101;
    public static final int MSG_STATE_CHANGE = 200;

    private static final UUID UUID = java.util.UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private AcceptTask mAcceptTask;
    private ConnectTask mConnectTask;
    private ConnectedTask mConnectedTask;

    public BluetoothUtil(Handler handler) {
        mAdapter = BluetoothAdapter.getDefaultAdapter();
        mState = STATE_NONE;
        mHandler = handler;
    }

    /**
     * 服务端开始监听，启动接收线程
     */
    public synchronized void listen() {
        if (D)
            Log.e(TAG, "start to listen ");
        if (mConnectedTask != null) {
            mConnectedTask.cancel();
            mConnectedTask = null;
        }

        if (mAcceptTask != null) {
            mAcceptTask.cancel();
            mAcceptTask = null;
        }

        mAcceptTask = new AcceptTask();
        mAcceptTask.start();
        setState(STATE_LISTEN);
    }

    /**
     * 客户端开始连接, 启动连接线程
     *
     * @param device 远程设备、即服务端
     */
    public synchronized void connect(BluetoothDevice device) {
        if (D)
            Log.e(TAG, "connecting " + device.getName());
        if (mState == STATE_CONNECTING) {
            if (mConnectTask != null) {
                mConnectTask.cancel();
                mConnectTask = null;
            }
        }

        if (mConnectedTask != null) {
            mConnectedTask.cancel();
            mConnectedTask = null;
        }

        mConnectTask = new ConnectTask(device);
        mConnectTask.start();
        setState(STATE_CONNECTING);
    }

    /**
     * 表示已连接完成，可以通信了，启动通信线程读写数据
     */
    private synchronized void connected(BluetoothSocket socket, BluetoothDevice device) {
        if (D)
            Log.e(TAG, "connected");
        if (mConnectedTask != null) {
            mConnectedTask.cancel();
            mConnectedTask = null;
        }

        mConnectedTask = new ConnectedTask(socket);
        mConnectedTask.start();

        if (mHandler != null) {
            mHandler.sendMessage(mHandler.obtainMessage(STATE_CONNECTED, device.getName()));
        }
        setState(STATE_CONNECTED);
    }

    /**
     * 终止所有线程
     */
    public synchronized void disconnect() {
        if (D)
            Log.e(TAG, "disconnect");
        if (mConnectTask != null) {
            mConnectTask.cancel();
            mConnectTask = null;
        }
        if (mConnectedTask != null) {
            mConnectedTask.cancel();
            mConnectedTask = null;
        }
        if (mAcceptTask != null) {
            mAcceptTask.cancel();
            mAcceptTask = null;
        }
        mHandler = null;
    }

    /**
     * 客户端连接失败
     */
    private void connectionFail() {
        if (D)
            Log.e(TAG, "connection Fail");
        setState(STATE_CONNECT_FAIL);
    }

    /**
     * 连接断开
     */
    private void connectionLost() {
        if (D)
            Log.e(TAG, "connection Lost");
        setState(STATE_CONNECTION_LOST);
    }

    /**
     * 写数据
     */
    public void write(byte[] out) {
        ConnectedTask task;
        synchronized (this) {
            if (mState != STATE_CONNECTED) {
                return;
            }
            task = mConnectedTask;
        }
        task.write(out);
    }

    private synchronized void setState(int state) {
        mState = state;
        if (mHandler != null) {
            mHandler.sendMessage(mHandler.obtainMessage(MSG_STATE_CHANGE, mState, -1));
        }
    }

    class AcceptTask extends Thread {

        private final BluetoothServerSocket mServerSocket;

        public AcceptTask() {
            BluetoothServerSocket tmp = null;
            try {
                tmp = mAdapter.listenUsingRfcommWithServiceRecord("treadmill", UUID);
            } catch (IOException e) {
                Log.e(TAG, "AcceptTask: listen failed");
            }
            mServerSocket = tmp;
        }

        @Override
        public void run() {
            if (D)
                Log.e(TAG, "begin accept task");
            BluetoothSocket socket = null;
            while (mState != STATE_CONNECTED) {
                try {
                    // 这是一个阻塞调用 返回成功的连接
                    // mServerSocket.close()在另一个线程中调用，可以中止该阻塞
                    if (mServerSocket != null) {
                        socket = mServerSocket.accept();
                    }
                } catch (IOException e) {
                    Log.e(TAG, "accept() failed!");
                    break;
                }

                // accepted successfully
                if (socket != null) {
                    synchronized (BluetoothUtil.this) {
                        switch (mState) {
                            case STATE_LISTEN:
                            case STATE_CONNECTING:
                                //正常情况，启动ConnectedTask
                                if (D)
                                    Log.e(TAG, "Succeed ---> 启动ConnectedTask");
                                connected(socket, socket.getRemoteDevice());
                                break;
                            case STATE_NONE:
                            case STATE_CONNECTED:
                                //没有准备或已连接，新连接终止
                                try {
                                    socket.close();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                break;
                        }
                    }
                }

            }
            if (D)
                Log.e(TAG, "End AcceptTask");
        }

        public void cancel() {
            if (D)
                Log.e(TAG, "cancel: AcceptTask");
            try {
                mServerSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ConnectTask extends Thread {

        private BluetoothSocket mSocket;
        private final BluetoothDevice mDevice;

        public ConnectTask(BluetoothDevice device) {
            mDevice = device;
            BluetoothSocket tmp = null;
            try {
                tmp = mDevice.createRfcommSocketToServiceRecord(UUID);
            } catch (IOException e) {
                Log.e(TAG, "create() failed");
            }
            mSocket = tmp;
        }

        @Override
        public void run() {
            if (D)
                Log.e(TAG, "BEGIN connect task");
            try {
                if (mSocket != null) {
                    mSocket.connect();
                }
            } catch (IOException e) {
                connectionFail();
                try {
                    mSocket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                return;
            }

            connected(mSocket, mDevice);
            if (D)
                Log.e(TAG, "END connect task");
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    class ConnectedTask extends Thread {

        private final BluetoothSocket mSocket;
        private final InputStream mInputStream;
        private final OutputStream mOutputStream;

        public ConnectedTask(BluetoothSocket socket) {
            mSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;
            try {
                tmpIn = socket.getInputStream();
                tmpOut = socket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mInputStream = tmpIn;
            mOutputStream = tmpOut;
        }

        @Override
        public void run() {
            if (D)
                Log.e(TAG, "BEGIN connected task");
            while (true) {
                try {
                    byte[] bytes = new byte[2048];
                    int i = mInputStream.read(bytes);
                    String str = new String(bytes, 0, i, "UTF-8").trim();
                    Log.e(TAG, "READ: " + str);
                    if (mHandler != null) {
                        mHandler.sendMessage(mHandler.obtainMessage(MSG_READ, str));
                    }
                } catch (IOException e) {
                    connectionLost();
                    break;
                }
            }
            if (D)
                Log.e(TAG, "END connected task");
        }

        public void write(byte[] out) {
            try {
                mOutputStream.write(out);
                mOutputStream.flush();
                if (D)
                    Log.e(TAG, "write: " + new String(out, "UTF-8"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void cancel() {
            try {
                mSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
