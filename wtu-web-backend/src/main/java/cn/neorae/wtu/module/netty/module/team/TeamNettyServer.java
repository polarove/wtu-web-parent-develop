package cn.neorae.wtu.module.netty.module.team;

import cn.neorae.wtu.module.netty.module.team.connection.TeamServerHandler;
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
public class TeamNettyServer {

    public static final Map<String, Channel> TEAM_CHANNEL_MAP = new ConcurrentHashMap<>(1024);

    public static final ChannelGroup TEAM_ORIGIN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static final ChannelGroup TEAM_EVENT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_ALARM = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_INVASION = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_SYNDICATE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_FISSURE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_SORTIE = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_HUNT = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_DURIVI = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // public static final ChannelGroup TEAM_EMPYREAN = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    public static void start() throws Exception{
        EventLoopGroup primitiveGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();
        
        ClassPathResource pem = new ClassPathResource("ssl/dev/localhost+3.pem");
        ClassPathResource key = new ClassPathResource("ssl/dev/localhost+3-key.pem");

        SslContext sslContext = SslContextBuilder.forServer(pem.getInputStream(), key.getInputStream()).build();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(primitiveGroup, workGroup)
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
                                .addLast(new WebSocketServerProtocolHandler("/")) //websocket的根路径
                                .addLast(new TeamServerHandler());
                    }
                });
        ChannelFuture channelFuture = serverBootstrap.bind(7676);
        channelFuture.addListener(future -> {
            if (future.isSuccess()) {
                log.info("Team Netty Server started at port 7676");
            } else {
                log.error("Team Netty started failed at port 7676");
            }
        });
    }
}
