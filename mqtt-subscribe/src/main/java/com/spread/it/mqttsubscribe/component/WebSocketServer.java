package com.spread.it.mqttsubscribe.component;

import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
@ServerEndpoint("/websocket/{sid}")
public class WebSocketServer {
    private static int onlineCount = 0;
    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    private static CopyOnWriteArraySet<WebSocketServer> webSocketSet = new CopyOnWriteArraySet<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;
    //接收sid
    private String sid = "";

    /**
     * 鏈接建立成功后調用的方法
     *
     * @param session
     * @param sid
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        this.session = session;
        webSocketSet.add(this);
        addOnlineCount();
        System.out.println(String.format("有新窗口开始监听: %s,当前在线人数为: %s", sid, getOnlineCount()));
        this.sid = sid;
    }

    /**
     * @return
     */
    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }

    /**
     * 服務器的推送方法
     * @param message
     * @throws IOException
     */

    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    /**
     * 自定義群發信息到客戶端
     * @param message
     */
    public static void sendInfo(String message) {
        for (WebSocketServer server : webSocketSet) {
            try {
                server.sendMessage(message);
            } catch (IOException e) {
                continue;
            } finally {
            }

        }
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    @OnClose
    public void onClose() {

            webSocketSet.remove(this);  //从set中删除
            subOnlineCount();           //在线数减1
            System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());

    }

    /**
     * 收到客戶端發回的信息
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void OnMessage(String message, Session session) {
        System.out.println(String.format("收到了來自頁面 %s 的信息內容：%s", sid, message));
    }


}
