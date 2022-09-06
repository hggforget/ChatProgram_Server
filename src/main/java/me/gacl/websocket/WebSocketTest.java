package me.gacl.websocket;

import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

/**
 * @ServerEndpoint 注解是一个类层次的注解，它的功能主要是将目前的类定义成一个websocket服务器端,
 * 注解的值将被用于监听用户连接的终端访问URL地址,客户端可以通过这个URL来连接到WebSocket服务器端
 */
@ServerEndpoint("/websocket/{userID}")
public class WebSocketTest {
	//静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
	private static int onlineCount = 0;

	//concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
	private static Map<Integer,WebSocketTest> webSocketMap = new HashMap<Integer, WebSocketTest>();

	//与某个客户端的连接会话，需要通过它来给客户端发送数据
	private Session session;

	/**
	 * 连接建立成功调用的方法
	 * @param session  可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
	 */
	@OnOpen
	public void onOpen(@PathParam("userID")Integer userID,Session session){
		this.session = session;
		System.out.println("userID:"+userID.toString()+" websocketID:"+this.toString());
		webSocketMap.put(userID,this);     //加入Map中
		addOnlineCount();           //在线数加1
		System.out.println("有新连接加入！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 连接关闭调用的方法
	 */
	@OnClose
	public void onClose(@PathParam("userID") Integer userID){
		System.out.println("userID:"+userID.toString()+" websocketID:"+this.toString());
		webSocketMap.remove(userID); //从Map中删除
		subOnlineCount();           //在线数减1
		System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
	}

	/**
	 * 收到客户端消息后调用的方法
	 * @param message 客户端发送过来的消息
	 * @param session 可选的参数
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println("来自客户端的消息:" + message);
		JSONObject object=JSONObject.parseObject(message);
		String cmd=object.getString("type");
		WebSocketUtils utils=new WebSocketUtils();
		if(cmd.equals("query_Friendlist"))
		{
			Integer userID=Integer.valueOf(object.getString("userID"));
			utils.query_Friendlist(session,userID);
		}
		else if(cmd.equals("query_mydata"))
		{
			Integer userID=Integer.valueOf(object.getString("userID"));
			utils.query_MyData(session,userID);
		}

		else if(cmd.equals("sendMsg")) {
			utils.sendMsg2Friend(session,object);
		}
		else if(cmd.equals("QueryAlluser")){
			utils.queryAlluser(session);
		}
		else if(cmd.equals("addFriend")){
			utils.addFriend(session,object);
		}
		else if(cmd.equals("createGroup")){
			utils.createGroup(object);
		}
		else if(cmd.equals("query_group")){
			Integer userID=Integer.valueOf(object.getString("userID"));
			utils.queryGroup(session,userID);
		}
		else if(cmd.equals("groupTalk")){
			utils.groupTalk(this,object);
		}
		else if(cmd.equals("queryCon")){
			utils.queryCon(session,object);
		}
		else if(cmd.equals("queryGroupMsg")){
			utils.queryGroupMsg(session,object);
		}
		else if(cmd.equals("register")){
			utils.register(session,object);
		}

		//群发消息
	}

	/**
	 * 发生错误时调用
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error){
		System.out.println("发生错误");

		error.printStackTrace();
	}

	/**
	 * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
	 * @param message
	 * @throws IOException
	 */
	public void sendMessage(String message) throws IOException{
		this.session.getBasicRemote().sendText(message);
		//this.session.getAsyncRemote().sendText(message);
	}

	public static synchronized int getOnlineCount() {
		return onlineCount;
	}

	public static synchronized void addOnlineCount() {
		WebSocketTest.onlineCount++;
	}

	public static synchronized void subOnlineCount() {
		WebSocketTest.onlineCount--;
	}

	public static synchronized WebSocketTest queryWs(Integer userID){
		WebSocketTest ws=webSocketMap.get(userID);
		System.out.println(userID.toString()+"   "+ws);
		return ws;
	}
}
