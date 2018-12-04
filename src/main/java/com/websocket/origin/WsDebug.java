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
 * @ServerEndpoint ע����һ�����ε�ע�⣬���Ĺ�����Ҫ�ǽ�Ŀǰ���ඨ���һ��websocket��������,
 *                 ע���ֵ�������ڼ����û����ӵ��ն˷���URL��ַ,�ͻ��˿���ͨ�����URL�����ӵ�WebSocket��������
 */
@ServerEndpoint("/websocket")
public class WsDebug {

	public static List<Session> websocketSession = new ArrayList<Session>();

	// ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������

	private Session session;

	/**
	 * ���ӽ����ɹ����õķ���
	 * 
	 * @param session
	 *            ��ѡ�Ĳ�����sessionΪ��ĳ���ͻ��˵����ӻỰ����Ҫͨ���������ͻ��˷�������
	 */
	@OnOpen
	public void onOpen(Session session) {
		this.session = session;
		websocketSession.add(session);

	}

	/**
	 * ���ӹرյ��õķ���
	 */
	@OnClose
	public void onClose() {

		websocketSession.remove(session);

	}

	/**
	 * ��������ʱ����
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {

		error.printStackTrace();
	}

	/**
	 * ��������
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessage(Session session, String message) throws IOException {
		session.getBasicRemote().sendText(message);

	}

	/**
	 * �յ��ͻ�����Ϣ����õķ���
	 * 
	 * @param message
	 *            �ͻ��˷��͹�������Ϣ
	 * @param session
	 *            ��ѡ�Ĳ���
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
	 * Ⱥ����Ϣ
	 * 
	 * @param message
	 * @throws IOException
	 */
	public static void sendMessage(List<Session> websocketSession, String message) throws IOException {
		// Ⱥ����Ϣ
		for (int i = 0; i < websocketSession.size(); i++) {

			websocketSession.get(i).getBasicRemote().sendText(message);
		}
	}

}