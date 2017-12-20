package com.dragonfly.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class EchoServer {

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
        new EchoServer().bind(tmpPort);
    }

    private void bind(int port) {

        //配置reactor线程池
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建ServerBootstrap实例
        ServerBootstrap bootstrap = new ServerBootstrap();

        //绑定reactor线程池
        bootstrap.group(bossGroup, workerGroup)
                //设置并绑定服务端Channel
                .channel(NioServerSocketChannel.class)
                //设置TCP参数，backlog表示等待队列长度
                .option(ChannelOption.SO_BACKLOG, 1024)
                //绑定事件处理类
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //绑定处理事件的Handler，这里可以设置多个
                        socketChannel.pipeline().addLast(new EchoServerHandler());
                    }
                });

        try {
            //绑定本是异步操作,这里将其变为同步阻塞
            ChannelFuture cf = bootstrap.bind(port).sync();
            //promise模式，阻塞至channel关闭后才退出
            cf.channel().closeFuture().sync();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //优雅退出，释放线程资源
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
