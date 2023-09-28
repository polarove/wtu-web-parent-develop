package cn.neorae.wtu.module.netty;

import cn.neorae.wtu.module.netty.module.cn.CnTeamServerHandler;
import cn.neorae.wtu.module.netty.module.en.EnTeamServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.stream.ChunkedWriteHandler;
import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
@Component
public class NettyApplication implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware{

    public static final Map<String, Channel> CN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);


    public static final Map<String, Channel> EN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);


    private static EventLoopGroup en_primitiveGroup;
    private static EventLoopGroup en_workGroup;

    private static EventLoopGroup cn_primitiveGroup;
    private static EventLoopGroup cn_workGroup;

    private ApplicationContext applicationContext;

    
    @Value("${netty.en.port}")
    private int EnPort;

    @Value("${netty.en.root}")
    private String EnRoot;

    @Value("${netty.cn.port}")
    private int CnPort;

    @Value("${netty.cn.root}")
    private String CnRoot;

    @Value("${netty.ssl.certificate}")
    private String pem;

    @Value("${netty.ssl.certificate-private-key}")
    private String key;


    @PreDestroy
    public void destroy() throws InterruptedException {
        en_primitiveGroup.shutdownGracefully().sync();
        en_workGroup.shutdownGracefully().sync();
        cn_primitiveGroup.shutdownGracefully().sync();
        cn_workGroup.shutdownGracefully().sync();
    }

    @Override
    public void run(ApplicationArguments args) throws Exception{

        ClassPathResource key = new ClassPathResource(this.key);
        ClassPathResource pem = new ClassPathResource(this.pem);
        SslContext sslContext = SslContextBuilder.forServer(pem.getInputStream(), key.getInputStream()).build();

        en_primitiveGroup = new NioEventLoopGroup();
        en_workGroup = new NioEventLoopGroup();

        cn_primitiveGroup = new NioEventLoopGroup();
        cn_workGroup = new NioEventLoopGroup();

        ServerBootstrap EnServer = new ServerBootstrap();
        EnServer.group(en_primitiveGroup, en_workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws IOException {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加http编码解码器
                        pipeline.addFirst(sslContext.newHandler(socketChannel.alloc()))
                                .addLast(new HttpServerCodec())
                                //支持大数据流
                                .addLast(new ChunkedWriteHandler())
                                //对http消息做聚合操作，会产生FullHttpRequest、FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                //websocket支持
                                .addLast(new WebSocketServerProtocolHandler(EnRoot)) //websocket的根路径
                                .addLast(new EnTeamServerHandler());
                    }
                });

        ServerBootstrap CnServer = new ServerBootstrap();
        CnServer.group(cn_primitiveGroup, cn_workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws IOException {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加http编码解码器
                        pipeline.addFirst(sslContext.newHandler(socketChannel.alloc()))
                                .addLast(new HttpServerCodec())
                                //支持大数据流
                                .addLast(new ChunkedWriteHandler())
                                //对http消息做聚合操作，会产生FullHttpRequest、FullHttpResponse
                                .addLast(new HttpObjectAggregator(1024 * 64))
                                //websocket支持
                                .addLast(new WebSocketServerProtocolHandler(CnRoot)) //websocket的根路径
                                .addLast(new CnTeamServerHandler());
                    }
                });

        ChannelFuture EnServerChannelFuture = EnServer.bind(EnPort).sync();
        EnServerChannelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("En Team Netty Server started at port:{}", EnPort);
            } else {
                log.error("En Team Netty started failed at port:{}", EnPort);
            }
        });

        ChannelFuture CnServerChannelFuture = CnServer.bind(CnPort).sync();
        CnServerChannelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("Cn Team Netty Server started at port:{}", CnPort);
            } else {
                log.error("Cn Team Netty started failed at port:{}", CnPort);
            }
        });


    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        if (event.getApplicationContext().equals(this.applicationContext)) {
            try {
                destroy();
            } catch (InterruptedException e) {
                log.info("NettyApplication destroy failed: ", e.fillInStackTrace());
            }
        }
    }

}
