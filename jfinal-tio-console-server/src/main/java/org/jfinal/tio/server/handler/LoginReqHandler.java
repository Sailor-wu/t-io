package org.jfinal.tio.server.handler;

import java.util.concurrent.atomic.AtomicLong;

import org.jfinal.tio.commom.ShowcasePacket;
import org.jfinal.tio.commom.ShowcaseSessionContext;
import org.jfinal.tio.commom.Type;
import org.jfinal.tio.commom.intf.AbsShowcaseBsHandler;
import org.jfinal.tio.commom.packets.JoinGroupRespBody;
import org.jfinal.tio.commom.packets.LoginReqBody;
import org.jfinal.tio.commom.packets.LoginRespBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tio.core.Aio;
import org.tio.core.ChannelContext;
import org.tio.utils.json.Json;

import com.alibaba.fastjson.JSON;

/**
 * @author tanyaowu
 * 2017年3月27日 下午9:51:28
 */
public class LoginReqHandler extends AbsShowcaseBsHandler<LoginReqBody> {
	private static Logger log = LoggerFactory.getLogger(LoginReqHandler.class);

	/**
	 * @param args
	 * @author tanyaowu
	 */
	public static void main(String[] args) {

	}

	java.util.concurrent.atomic.AtomicLong tokenSeq = new AtomicLong();

	/**
	 *
	 * @author tanyaowu
	 */
	public LoginReqHandler() {
	}

	/**
	 * @return
	 * @author tanyaowu
	 */
	@Override
	public Class<LoginReqBody> bodyClass() {
		return LoginReqBody.class;
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
	public Object handler(ShowcasePacket packet, LoginReqBody bsBody, ChannelContext channelContext) throws Exception {
		log.info("收到登录请求消息:{}", Json.toJson(bsBody));
		LoginRespBody loginRespBody = new LoginRespBody();
		loginRespBody.setCode(JoinGroupRespBody.Code.SUCCESS);
		loginRespBody.setToken(newToken());
		System.out.println("返回body信息："+JSON.toJSON(loginRespBody));
		String userid = bsBody.getLoginname();
		System.out.println("userid------------->"+userid);
		Aio.bindUser(channelContext, userid);

//		ShowcaseSessionContext showcaseSessionContext = (ShowcaseSessionContext) channelContext.getAttribute();
//		showcaseSessionContext.setUserid(userid);

		ShowcasePacket respPacket = new ShowcasePacket();
		respPacket.setType(Type.LOGIN_RESP);
		respPacket.setBody(Json.toJson(loginRespBody).getBytes(ShowcasePacket.CHARSET));
		Aio.send(channelContext, respPacket);

		return null;
	}

	private String newToken() {
		return System.currentTimeMillis() + "_" + tokenSeq.incrementAndGet();
	}
}
