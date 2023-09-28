package cn.neorae.wtu.module.netty.module.en;

import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class EnChannelMap {

    public static Stream<ChannelGroup> channelGroupStream;

    private static final ChannelGroup EN_TEAM_ORIGIN = new DefaultChannelGroup("origin", GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_EVENT = new DefaultChannelGroup("event",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_ALARM = new DefaultChannelGroup("alarm",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_STEEL_PATH = new DefaultChannelGroup("steelpath",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_INVASION = new DefaultChannelGroup("invasion",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_SYNDICATE = new DefaultChannelGroup("syndicate",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_FISSURE = new DefaultChannelGroup("fissure",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_SORTIE = new DefaultChannelGroup("sortie",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_HUNT = new DefaultChannelGroup("hunt",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_DURIVI = new DefaultChannelGroup("durivi",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup EN_TEAM_EMPYREAN = new DefaultChannelGroup("empyrean",GlobalEventExecutor.INSTANCE);

    public static void init() {
        channelGroupStream = Stream.of(EN_TEAM_ORIGIN, EN_TEAM_EVENT, EN_TEAM_ALARM, EN_TEAM_STEEL_PATH, EN_TEAM_INVASION, EN_TEAM_SYNDICATE, EN_TEAM_FISSURE, EN_TEAM_SORTIE, EN_TEAM_HUNT, EN_TEAM_DURIVI, EN_TEAM_EMPYREAN);
    }
}
