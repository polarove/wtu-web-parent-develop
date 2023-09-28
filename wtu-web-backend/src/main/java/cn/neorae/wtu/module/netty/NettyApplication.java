package cn.neorae.wtu.module.netty;

import cn.neorae.wtu.module.netty.module.cn.CnTeamServerHandler;
import cn.neorae.wtu.module.netty.module.en.EnTeamServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.GlobalEventExecutor;
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
import java.util.stream.Stream;


@Slf4j
@Component
public class NettyApplication implements ApplicationRunner, ApplicationListener<ContextClosedEvent>, ApplicationContextAware{

    public static final Map<String, Channel> CN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);


    public static final ChannelGroup CN_TEAM_ORIGIN  = new DefaultChannelGroup("origin", GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_EVENT  = new DefaultChannelGroup("event",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_ALARM = new DefaultChannelGroup("alarm",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_STEEL_PATH =  new DefaultChannelGroup("steelpath",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_INVASION =   new DefaultChannelGroup("invasion",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_SYNDICATE =  new DefaultChannelGroup("syndicate",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_FISSURE =   new DefaultChannelGroup("fissure",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_SORTIE  = new DefaultChannelGroup("sortie",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_HUNT  = new DefaultChannelGroup("hunt",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_DURIVI = new DefaultChannelGroup("durivi",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_EMPYREAN   = new DefaultChannelGroup("empyrean",GlobalEventExecutor.INSTANCE);

    public static Stream<ChannelGroup> CN_Channel_Group_Stream = Stream.of(CN_TEAM_ORIGIN, CN_TEAM_EVENT, CN_TEAM_ALARM, CN_TEAM_STEEL_PATH, CN_TEAM_INVASION, CN_TEAM_SYNDICATE, CN_TEAM_FISSURE, CN_TEAM_SORTIE, CN_TEAM_HUNT, CN_TEAM_DURIVI, CN_TEAM_EMPYREAN);

    public static final Map<String, Channel> EN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);


    public static final ChannelGroup EN_TEAM_ORIGIN = new DefaultChannelGroup("origin", GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_EVENT = new DefaultChannelGroup("event",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_ALARM = new DefaultChannelGroup("alarm",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_STEEL_PATH = new DefaultChannelGroup("steelpath",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_INVASION = new DefaultChannelGroup("invasion",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_SYNDICATE = new DefaultChannelGroup("syndicate",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_FISSURE = new DefaultChannelGroup("fissure",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_SORTIE = new DefaultChannelGroup("sortie",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_HUNT = new DefaultChannelGroup("hunt",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_DURIVI = new DefaultChannelGroup("durivi",GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_EMPYREAN = new DefaultChannelGroup("empyrean",GlobalEventExecutor.INSTANCE);

    public static Stream<ChannelGroup> EN_CHANNEL_GROUP_STREAM = Stream.of(EN_TEAM_ORIGIN, EN_TEAM_EVENT, EN_TEAM_ALARM, EN_TEAM_STEEL_PATH, EN_TEAM_INVASION, EN_TEAM_SYNDICATE, EN_TEAM_FISSURE, EN_TEAM_SORTIE, EN_TEAM_HUNT, EN_TEAM_DURIVI, EN_TEAM_EMPYREAN);

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
                        log.info("新连接加入国际服");
                    }
                });

        ServerBootstrap CnServer = new ServerBootstrap();
        CnServer.group(cn_primitiveGroup, cn_workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) {
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
                        log.info("新连接加入国服");
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
