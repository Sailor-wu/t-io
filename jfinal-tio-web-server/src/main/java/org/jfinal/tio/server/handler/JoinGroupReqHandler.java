package org.jfinal.tio.server.handler;

import org.jfinal.tio.commom.ShowcasePacket;
import org.jfinal.tio.commom.Type;
import org.jfinal.tio.commom.intf.AbsShowcaseBsHandler;
import org.jfinal.tio.commom.packets.JoinGroupReqBody;
import org.jfinal.tio.commom.packets.JoinGroupRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class JoinGroupReqHandler extends AbsShowcaseBsHandler<JoinGroupReqBody> {
	private static Logger log = LoggerFactory.getLogger(JoinGroupReqHandler.class);

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
	public JoinGroupReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<JoinGroupReqBody> bodyClass() {
		return JoinGroupReqBody.class;
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
	public Object handler(ShowcasePacket packet, JoinGroupReqBody bsBody, ChannelContext channelContext) throws Exception {
		log.info("收到进群请求消息:{}", Json.toJson(bsBody));
		JoinGroupRespBody joinGroupRespBody = new JoinGroupRespBody();
		joinGroupRespBody.setCode(JoinGroupRespBody.Code.SUCCESS);
		joinGroupRespBody.setGroup(bsBody.getGroup());

		Aio.bindGroup(channelContext, bsBody.getGroup());

		ShowcasePacket respPacket = new ShowcasePacket();
		respPacket.setType(Type.JOIN_GROUP_RESP);
		respPacket.setBody(Json.toJson(joinGroupRespBody).getBytes(ShowcasePacket.CHARSET));
		Aio.send(channelContext, respPacket);
		return null;
	}
}
