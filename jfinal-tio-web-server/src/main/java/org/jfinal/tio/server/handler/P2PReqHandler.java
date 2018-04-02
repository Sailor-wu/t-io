package org.jfinal.tio.server.handler;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.jfinal.tio.commom.ShowcasePacket;
import org.jfinal.tio.commom.ShowcaseSessionContext;
import org.jfinal.tio.commom.Type;
import org.jfinal.tio.commom.intf.AbsShowcaseBsHandler;
import org.jfinal.tio.commom.packets.P2PReqBody;
import org.jfinal.tio.commom.packets.P2PRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import com.jfinal.commom.TestRedis;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class P2PReqHandler extends AbsShowcaseBsHandler<P2PReqBody> {
	private static Logger log = LoggerFactory.getLogger(P2PReqHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	/**
	 *
	 * @author tanyaowu
	 */
	public P2PReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<P2PReqBody> bodyClass() {
		return P2PReqBody.class;
	}

	/**
	 * @param packet
	 * @param bsBody
	 * @param channelContext
	 * @return
	 * @throws Exception
	 * @author tanyaowu
	 */
	@Override
	public Object handler(ShowcasePacket packet, P2PReqBody bsBody, ChannelContext channelContext) throws Exception {
		log.info("收到点对点请求消息:{}", Json.toJson(bsBody));

//		ShowcaseSessionContext showcaseSessionContext = (ShowcaseSessionContext) channelContext.getAttribute();
		System.out.println("from User-"+channelContext.getUserid());
		P2PRespBody p2pRespBody = new P2PRespBody();
		p2pRespBody.setFromUserid(channelContext.getUserid());
		p2pRespBody.setText(bsBody.getText());

		ShowcasePacket respPacket = new ShowcasePacket();
		respPacket.setType(Type.P2P_RESP);
		respPacket.setBody(Json.toJson(p2pRespBody).getBytes(ShowcasePacket.CHARSET));
		Aio.sendToUser(channelContext.getGroupContext(), bsBody.getToUserid(), respPacket);
//		TestRedis.jedis.set(TestRedis.SERVERID, p2pRespBody);
		Map<String, String> maps=new HashMap<String, String>();
		maps.put("fromUser", p2pRespBody.getFromUserid());
		maps.put("text",p2pRespBody.getText());
		TestRedis.jedis.hmset(TestRedis.SERVERID,maps);
		return null;
	}
}
