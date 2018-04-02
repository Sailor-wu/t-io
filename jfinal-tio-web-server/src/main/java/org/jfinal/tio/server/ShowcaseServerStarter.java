package org.jfinal.tio.server;

import java.io.IOException;

import org.tio.server.AioServer;
import org.tio.server.ServerGroupContext;
import org.tio.server.intf.ServerAioHandler;
import org.tio.server.intf.ServerAioListener;

import com.jfinal.plugin.IPlugin;

/**
 *
 * @author tanyaowu
 * 2017年3月27日 上午12:16:31
 */
public class ShowcaseServerStarter implements IPlugin {
	static ServerAioHandler aioHandler = new ShowcaseServerAioHandler();
	static ServerAioListener aioListener = new ShowcaseServerAioListener();
	public static ServerGroupContext serverGroupContext = new ServerGroupContext(aioHandler, aioListener);
	static AioServer aioServer = new AioServer(serverGroupContext); //可以为空
	static String serverIp = null;
	static int serverPort = org.jfinal.tio.commom.Const.PORT;

	public static void main(String[] args) throws IOException {
		aioServer.start(serverIp, serverPort);
	}

	@Override
	public boolean start() {
		aioHandler = new ShowcaseServerAioHandler();
		aioListener = null; // 可以为空
		serverGroupContext = new ServerGroupContext(aioHandler, aioListener);
		aioServer = new AioServer(serverGroupContext);
		try {
			aioServer.start(serverIp, serverPort);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean stop() {
		// TODO Auto-generated method stub
		return aioServer.stop();
	}
}