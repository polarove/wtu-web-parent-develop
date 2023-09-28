package cn.neorae.wtu.module.netty.module.cn;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.stream.Stream;

public class CnChannelMap {

    public static Stream<ChannelGroup> CnChannelGroupStream;

    private static final ChannelGroup CN_TEAM_ORIGIN  = new DefaultChannelGroup("origin", GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_EVENT  = new DefaultChannelGroup("event",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_ALARM = new DefaultChannelGroup("alarm",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_STEEL_PATH =  new DefaultChannelGroup("steelpath",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_INVASION =   new DefaultChannelGroup("invasion",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_SYNDICATE =  new DefaultChannelGroup("syndicate",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_FISSURE =   new DefaultChannelGroup("fissure",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_SORTIE  = new DefaultChannelGroup("sortie",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_HUNT  = new DefaultChannelGroup("hunt",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_DURIVI = new DefaultChannelGroup("durivi",GlobalEventExecutor.INSTANCE);

    private static final ChannelGroup CN_TEAM_EMPYREAN   = new DefaultChannelGroup("empyrean",GlobalEventExecutor.INSTANCE);


    public static void init(){
        CnChannelGroupStream = Stream.of(CN_TEAM_ORIGIN, CN_TEAM_EVENT, CN_TEAM_ALARM, CN_TEAM_STEEL_PATH, CN_TEAM_INVASION, CN_TEAM_SYNDICATE, CN_TEAM_FISSURE, CN_TEAM_SORTIE, CN_TEAM_HUNT, CN_TEAM_DURIVI, CN_TEAM_EMPYREAN);
    }
}
