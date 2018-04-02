package com.jfinal.controller;

import java.io.UnsupportedEncodingException;

import org.jfinal.tio.commom.ShowcasePacket;
import org.jfinal.tio.commom.Type;
import org.jfinal.tio.commom.packets.P2PRespBody;
import org.jfinal.tio.server.ShowcaseServerStarter;
import org.tio.core.Aio;
import org.tio.server.ServerGroupContext;
import org.tio.utils.json.Json;

import com.jfinal.commom.TestRedis;
import com.jfinal.core.Controller;
import com.jfinal.json.FastJson;

public class HelloController extends Controller {

	public void index(){
		System.out.println("服务器ID：-------------"+TestRedis.getKey("serverId"));
	}
	/**
	 * 多个 sendToAll
	 *    获取serverGroupContext  所有的用户组   
	 */
	public void group() throws UnsupportedEncodingException {
		String type=getPara("inputName");
		String mess=getPara("inputname");
		ShowcasePacket packet=new ShowcasePacket();
		packet.setType((byte) 1);
		packet.setBody(Json.toJson(mess).getBytes(ShowcasePacket.CHARSET));
		ServerGroupContext group=ShowcaseServerStarter.serverGroupContext;
		Aio.sendToAll(group,packet );
	}
	/**
	 * 单个   user token
	 * @throws InterruptedException 
	 */
	public void One() throws UnsupportedEncodingException, InterruptedException {
		String userId=getPara("username");
		String mess=getPara("message");
//		ShowcasePacket packet=new ShowcasePacket();
//		packet.setType(Type.P2P_RESP);
//		packet.setBody(Json.toJson(mess).getBytes(ShowcasePacket.CHARSET));
//		ServerGroupContext group=ShowcaseServerStarter.serverGroupContext;
//		Aio.sendToUser(group, userId, packet);
//		-------
		ShowcasePacket respPacket = new ShowcasePacket();
		P2PRespBody respBody =new P2PRespBody();
		respBody.setFromUserid(TestRedis.SERVERID);
		respBody.setText(mess);
		respPacket.setType(Type.P2P_RESP);
		respPacket.setBody(Json.toJson(respBody).getBytes(ShowcasePacket.CHARSET));
		ServerGroupContext channelContext=ShowcaseServerStarter.serverGroupContext;
	    Aio.sendToUser(channelContext, userId, respPacket);
//	    Thread.sleep(15000);
	    System.out.println("消息："+TestRedis.getKey(TestRedis.SERVERID));
	    renderJson("{\"successMsg\":\"success\"}");
	}
	
	/**
	 * 刷新页面
	 */
	public void plushData() throws UnsupportedEncodingException {
		System.out.println("进来读取数据");
//		System.out.println(TestRedis.jedis.get(TestRedis.SERVERID));
		System.out.println(TestRedis.getKey(TestRedis.SERVERID));
//		System.out.println(TestRedis.jedis.hmget(TestRedis.SERVERID,"text"));
//		renderJson(TestRedis.jedis.hmget(TestRedis.SERVERID));
	}
	
}
