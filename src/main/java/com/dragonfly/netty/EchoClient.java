package com.dragonfly.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class EchoClient {


    private final static String host = "127.0.0.1";
    private final static int port = 6666;

    public static void main(String[] args) {

        int tmpPort = port;
        if (args != null && args.length > 0) {
            try {
                tmpPort = Integer.parseInt(args[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        new EchoClient().connect(host, tmpPort);
    }

    public void connect(String host, int port) {
        //创建NIO处理线程
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        //实例化Bootstrap
        Bootstrap bootstrap = new Bootstrap();
        //配置NIO处理线程
        bootstrap.group(eventLoopGroup)
                //设置并绑定客户端Channel
                .channel(NioSocketChannel.class)
                //TCP参数配置，TCP_NODELAY为低延迟
                .option(ChannelOption.TCP_NODELAY, true)
                //绑定事件处理类
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //绑定处理事件Handler
                        socketChannel.pipeline().addLast(new EchoClientHandler());
                    }
                });

        try {
            //发起连接，将异步操作转为同步阻塞
            ChannelFuture cf = bootstrap.connect(host, port).sync();
            //同步阻塞至channel关闭后退出
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程资源
            eventLoopGroup.shutdownGracefully();
        }
    }
}
