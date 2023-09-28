package cn.neorae.wtu.module.netty.module.en;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class EnChannelMap {


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

    public static Stream<ChannelGroup> init() {
        return Stream.of(EN_TEAM_ORIGIN, EN_TEAM_EVENT, EN_TEAM_ALARM, EN_TEAM_STEEL_PATH, EN_TEAM_INVASION, EN_TEAM_SYNDICATE, EN_TEAM_FISSURE, EN_TEAM_SORTIE, EN_TEAM_HUNT, EN_TEAM_DURIVI, EN_TEAM_EMPYREAN);
    }
}
