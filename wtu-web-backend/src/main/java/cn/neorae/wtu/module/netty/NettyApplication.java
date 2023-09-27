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
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


@Slf4j
public class NettyApplication {

    public static final Map<String, Channel> CN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);

    public static final ChannelGroup CN_TEAM_ORIGIN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_EVENT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup CN_TEAM_ALARM = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_STEEL_PATH = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_INVASION = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_SYNDICATE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_FISSURE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_SORTIE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_HUNT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_DURIVI = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

     public static final ChannelGroup CN_TEAM_EMPYREAN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static final Map<String, Channel> EN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);

    public static final ChannelGroup EN_TEAM_ORIGIN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_EVENT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_ALARM = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_STEEL_PATH = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_INVASION = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_SYNDICATE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_FISSURE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_SORTIE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_HUNT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_DURIVI = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup EN_TEAM_EMPYREAN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);


    public static void start(Integer EnPort, String EnRoot, Integer CnPort, String  CnRoot) throws Exception{
        EventLoopGroup en_primitiveGroup = new NioEventLoopGroup();
        EventLoopGroup en_workGroup = new NioEventLoopGroup();
        
        ClassPathResource pem = new ClassPathResource("ssl/pro/cert.crt");
        ClassPathResource key = new ClassPathResource("ssl/pro/cert-key.key");

        SslContext sslContext = SslContextBuilder.forServer(pem.getInputStream(), key.getInputStream()).build();

        ServerBootstrap EnServer = new ServerBootstrap();
        EnServer.group(en_primitiveGroup, en_workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加http编码解码器
                        pipeline.addLast(sslContext.newHandler(socketChannel.alloc()))
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
        EventLoopGroup cn_primitiveGroup = new NioEventLoopGroup();
        EventLoopGroup cn_workGroup = new NioEventLoopGroup();
        CnServer.group(cn_primitiveGroup, cn_workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel){
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //添加http编码解码器
                        pipeline.addLast(sslContext.newHandler(socketChannel.alloc()))
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
}
