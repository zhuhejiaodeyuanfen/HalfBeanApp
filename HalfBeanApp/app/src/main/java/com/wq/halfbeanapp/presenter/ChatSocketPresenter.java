package com.wq.halfbeanapp.presenter;

import android.content.Context;

import com.rabtman.wsmanager.WsManager;
import com.rabtman.wsmanager.listener.WsStatusListener;
import com.wq.halfbeanapp.bean.SocketModel;
import com.wq.halfbeanapp.bean.UserSocketBean;
import com.wq.halfbeanapp.constants.SocketParams;
import com.wq.halfbeanapp.net.response.JsonTools;
import com.wq.halfbeanapp.util.AppLogUtil;
import com.wq.halfbeanapp.util.user.UserInfoUtil;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Response;
import okio.ByteString;

/**
 * Created by vivianWQ on 2017/9/13
 * Mail: wangqi_vivian@sina.com
 * desc: 聊天服务 与服务器对接presenter
 * Version: 1.0
 */
public class ChatSocketPresenter {

    private WsManager wsManager;
    private Context context;
    private String userUuid;
    private String clientId;

    public ChatSocketPresenter(Context context) {
        this.context = context;
    }

    public void disConnect() {
        if (wsManager != null) {
            wsManager.stopConnect();
        }
    }

    public void disConnectServer() {

    }


    /**
     * 连接服务器
     */
    public void connectService() {
        OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                .pingInterval(15, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        wsManager = new WsManager.Builder(context)
                .wsUrl("ws://192.168.10.154:8080/wq/echo")
                .needReconnect(false)
                .client(okHttpClient)
                .build();
        wsManager.startConnect();
        AppLogUtil.i("尝试重新连接");
        wsManager.setWsStatusListener(new WsStatusListener() {
            @Override
            public void onOpen(Response response) {
                AppLogUtil.i("服务器onOpen");
                super.onOpen(response);


            }

            @Override
            public void onMessage(String text) {
                super.onMessage(text);
                if (text.equals("ServerConnect")) {
                    AppLogUtil.i("接收到消息" + text);

                    UserSocketBean userSocketBean = new UserSocketBean();
                    userSocketBean.setUserId(UserInfoUtil.getUserInfo(context).getUserId());
                    SocketModel socketModel = new SocketModel();
                    socketModel.setSocketType(SocketParams.SERVER_CONNECT);
                    socketModel.setData(userSocketBean);
                    sendMsg(socketModel);
                }

            }

            @Override
            public void onMessage(ByteString bytes) {
                super.onMessage(bytes);
                AppLogUtil.i("接收到字节流消息");

            }

            @Override
            public void onReconnect() {
                super.onReconnect();
                AppLogUtil.i("onReconnect");
            }

            @Override
            public void onClosing(int code, String reason) {
                super.onClosing(code, reason);
                AppLogUtil.i("onClosing");
                //在socket断掉后发送消息post给服务器,这里服务器应该是立马感知的,不应该客户端再告诉服务器
                disConnectServer();
            }

            @Override
            public void onClosed(int code, String reason) {
                super.onClosed(code, reason);

                disConnectServer();
            }

            @Override
            public void onFailure(Throwable t, Response response) {
                AppLogUtil.i("onFailure");
                super.onFailure(t, response);
            }
        });
    }

    public boolean sendMsg(SocketModel socketModel) {
        //开启连接的消息
        boolean result = wsManager.sendMessage(JsonTools.getJsonString(socketModel));
        AppLogUtil.i("是否发送成功" + result);
        return result;
    }

    public void sendHeartBeat() {
//        AppLogUtil.i("发送心跳包");
//        ChatConnectBean chatConnectBean = new ChatConnectBean();
//        chatConnectBean.setRole(1);
//        chatConnectBean.setUuid(userUuid);
//        chatConnectBean.setUrl("/chat/heart-beat");
//        chatConnectBean.setChat_token(BuildConfig.CLIENT_CHAT_TOKEN);
//        wsManager.sendMessage(JsonTools.getJsonString(chatConnectBean));

    }
}
