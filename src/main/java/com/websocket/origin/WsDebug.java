package com.websocket.origin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 *                 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket")
public class WsDebug {

	public static List<Session> websocketSession = new ArrayList<Session>();

	// 与某个客户端的连接会话，需要通过它来给客户端发送数据

	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * 
	 * @param session
	 *            可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		websocketSession.add(session);

	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose() {

		websocketSession.remove(session);

	}

	/**
	 * 发生错误时调用
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {

		error.printStackTrace();
	}

	/**
	 * 发送数据
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessage(Session session, String message) throws IOException {
		session.getBasicRemote().sendText(message);

	}

	/**
	 * 收到客户端消息后调用的方法
	 * 
	 * @param message
	 *            客户端发送过来的消息
	 * @param session
	 *            可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {

		SignalMsg sm = JSON.parseObject(message, new TypeReference<SignalMsg>() {
		});

		System.out.println(sm.toString());

		for (int i = 0; i < websocketSession.size(); i++) {

			try {

				if (session != websocketSession.get(i))
					websocketSession.get(i).getBasicRemote().sendText((JSONObject.toJSONString(sm)));
				
			 

			} catch (IOException e) {
				// TODO Auto-generated catch block
				// e.printStackTrace();
			}
		}

	}

	/**
	 * 群发消息
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessage(List<Session> websocketSession, String message) throws IOException {
		// 群发消息
		for (int i = 0; i < websocketSession.size(); i++) {

			websocketSession.get(i).getBasicRemote().sendText(message);
		}
	}

}