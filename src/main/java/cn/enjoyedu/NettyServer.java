package cn.enjoyedu;

import cn.enjoyedu.server.ServerInit;
import cn.enjoyedu.vo.NettyConstant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 服务端的主入口
 */
public class NettyServer {

	private static final Log LOG = LogFactory.getLog(NettyServer.class);

    public void bind() throws Exception {
        // 配置服务端的NIO线程组
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
            .option(ChannelOption.SO_BACKLOG, 1024)
            .childHandler(new ServerInit());

        // 绑定端口，同步等待成功
        b.bind(NettyConstant.REMOTE_PORT).sync();
            LOG.info("Netty server start : "
                + (NettyConstant.REMOTE_IP + " : " + NettyConstant.REMOTE_PORT));
    }

    public static void main(String[] args) throws Exception {
	    new NettyServer().bind();
    }
}
