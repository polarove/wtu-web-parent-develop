package cn.neorae.wtu.module.netty.module;

import cn.neorae.wtu.module.netty.NettyApplication;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import com.alibaba.fastjson.JSON;
import io.netty.channel.Channel;
import io.netty.channel.group.ChannelGroup;

import java.util.NoSuchElementException;
import java.util.Optional;

public class ChannelUtil {



    public static ChannelGroup getEnChannelGroupByRoute (String route) {
        return NettyApplication.EN_CHANNEL_GROUP_LIST.stream().filter(channelGroup -> channelGroup.name().equals(route)).findFirst().orElseThrow();
    }

    public static ChannelGroup getCnChannelGroupByRoute (String route) {
        return NettyApplication.CN_CHANNEL_GROUP_LIST.stream().filter(channelGroup1 -> channelGroup1.name().equals(route)).findFirst().orElseThrow();
    }

    public static Channel getEnUserChannelByUuid (String uuid) {
        return Optional.ofNullable(NettyApplication.EN_PUBLIC_CHANNEL_POOL.get(uuid)).orElseThrow();
    }

    public static Channel getCnUserChannelByUuid (String uuid) {
        return Optional.ofNullable(NettyApplication.CN_PUBLIC_CHANNEL_POOL.get(uuid)).orElseThrow();
    }
}
