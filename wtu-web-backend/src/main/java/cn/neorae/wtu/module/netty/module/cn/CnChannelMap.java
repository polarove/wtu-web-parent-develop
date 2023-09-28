package cn.neorae.wtu.module.netty.module.cn;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class CnChannelMap {
    public static final Map<String, Channel> CN_PUBLIC_CHANNEL_POOL = new ConcurrentHashMap<>(1024);

    public static final ChannelGroup CN_TEAM_ORIGIN = new DefaultChannelGroup("origin", GlobalEventExecutor.INSTANCE);

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


    public static Stream<ChannelGroup> init(){
        return Stream.of(CN_TEAM_ORIGIN, CN_TEAM_EVENT, CN_TEAM_ALARM, CN_TEAM_STEEL_PATH, CN_TEAM_INVASION, CN_TEAM_SYNDICATE, CN_TEAM_FISSURE, CN_TEAM_SORTIE, CN_TEAM_HUNT, CN_TEAM_DURIVI, CN_TEAM_EMPYREAN);
    }

    public static Channel getChannelByUUID(String uuid) {
        return CN_PUBLIC_CHANNEL_POOL.get(uuid);
    }

}
