package org.jfinal.tio.server;

import java.io.IOException;

import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

/**
 *
 * @author tanyaowu
 * 2017年3月27日 上午12:16:31
 */
public class ShowcaseServerStarter {
	static ServerAioHandler aioHandler = new ShowcaseServerAioHandler();
	static ServerAioListener aioListener = new ShowcaseServerAioListener();
	static ServerGroupContext serverGroupContext = new ServerGroupContext(aioHandler, aioListener);
	static AioServer aioServer = new AioServer(serverGroupContext); //可以为空

	static String serverIp = null;
	static int serverPort = org.jfinal.tio.commom.Const.PORT;

	public static void main(String[] args) throws IOException {
		aioServer.start(serverIp, serverPort);
	}
}