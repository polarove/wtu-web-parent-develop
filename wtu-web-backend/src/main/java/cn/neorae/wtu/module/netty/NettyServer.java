package cn.neorae.wtu.module.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class NettyServer {

    public static void start(){
        EventLoopGroup primitiveGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(primitiveGroup, workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加http编码解码器
                        pipeline.addLast(new HttpServerCodec())
                                //支持大数据流
                                .addLast(new ChunkedWriteHandler())
                                //对http消息做聚合操作，会产生FullHttpRequest、FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                //websocket支持
                                .addLast(new WebSocketServerProtocolHandler("/")); //websocket的根路径
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(8080);
        log.info("Netty Server started at port 8080");
    }
}
