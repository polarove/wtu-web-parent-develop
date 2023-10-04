package cn.neorae.wtu.module.netty.module.en;

import cn.neorae.wtu.module.netty.NettyApplication;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.domain.vo.connection.AfterConnectionBO;
import cn.neorae.wtu.module.netty.exceptions.ChannelException;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnTeamDisconnectionHandler {


    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg)  {
        WebsocketConnectionDTO dto = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        NettyApplication.EN_PUBLIC_CHANNEL_POOL.remove(dto.getUuid());
        AfterConnectionBO afterConnectionBO = new AfterConnectionBO();
        afterConnectionBO.setTotal(NettyApplication.EN_PUBLIC_CHANNEL_POOL.size());
        log.info("total:{}", afterConnectionBO.getTotal());
        Channel channel = channelHandlerContext.channel();
        try {
            NettyApplication.EN_CHANNEL_GROUP_LIST.forEach(channelGroup ->{
                if (channelGroup.name().equals(dto.getRoute()) && channelGroup.contains(channel)){
                    afterConnectionBO.setClients(channelGroup.size() - 1);
                    channelGroup.writeAndFlush(WssResponseVO.CONNECT_SUCCEED(JSON.toJSONString(afterConnectionBO)));
                    channelGroup.remove(channelHandlerContext.channel());
                }
            });
//
//            switch (NettyServerEnum.TeamRoutes.match(dto.getRoute())) {
//                case ORIGIN -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_ORIGIN.size() - 1);
//                    NettyApplication.EN_TEAM_ORIGIN.writeAndFlush(WssResponseVO.connect("en_已离开始源星系组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_ORIGIN.remove(channelHandlerContext.channel());
//                }
//                case EVENT -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_EVENT.size() - 1);
//                    NettyApplication.EN_TEAM_EVENT.writeAndFlush(WssResponseVO.connect("en_已离开事件组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_EVENT.remove(channelHandlerContext.channel());
//                }
//                case ALARM -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_ALARM.size() - 1);
//                    NettyApplication.EN_TEAM_ALARM.writeAndFlush(WssResponseVO.connect("en_已离开警报组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_ALARM.remove(channelHandlerContext.channel());
//                }
//                case STEEL_PATH -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_STEEL_PATH.size() - 1);
//                    NettyApplication.EN_TEAM_STEEL_PATH.writeAndFlush(WssResponseVO.connect("en_已离开钢铁之路组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_STEEL_PATH.remove(channelHandlerContext.channel());
//                }
//                case INVASION -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_INVASION.size() - 1);
//                    NettyApplication.EN_TEAM_INVASION.writeAndFlush(WssResponseVO.connect("en_已离开入侵组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_INVASION.remove(channelHandlerContext.channel());
//                }
//                case SYNDICATE -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_SYNDICATE.size() - 1);
//                    NettyApplication.EN_TEAM_SYNDICATE.writeAndFlush(WssResponseVO.connect("en_已离开集团组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_SYNDICATE.remove(channelHandlerContext.channel());
//                }
//                case FISSURE -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_FISSURE.size() - 1);
//                    NettyApplication.EN_TEAM_FISSURE.writeAndFlush(WssResponseVO.connect("en_已离开虚空遗物组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_FISSURE.remove(channelHandlerContext.channel());
//                }
//                case SORTIE -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_SORTIE.size() - 1);
//                    NettyApplication.EN_TEAM_SORTIE.writeAndFlush(WssResponseVO.connect("en_已离开突击组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_SORTIE.remove(channelHandlerContext.channel());
//                }
//                case HUNT -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_HUNT.size() - 1);
//                    NettyApplication.EN_TEAM_HUNT.writeAndFlush(WssResponseVO.connect("en_已离开猎杀执行官组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_HUNT.remove(channelHandlerContext.channel());
//                }
//                case DURIVI -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_DURIVI.size() - 1);
//                    NettyApplication.EN_TEAM_DURIVI.writeAndFlush(WssResponseVO.connect("en_已离开双衍王境组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_DURIVI.remove(channelHandlerContext.channel());
//                }
//                case EMPYREAN -> {
//
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_EMPYREAN.size() - 1);
//                    NettyApplication.EN_TEAM_EMPYREAN.writeAndFlush(WssResponseVO.connect("en_已离开九重天组队频道", JSON.toJSONString(afterConnectionVO)));
//                    NettyApplication.EN_TEAM_EMPYREAN.remove(channelHandlerContext.channel());
//                }
//                default -> throw new ChannelNotFoundException(ResponseEnum.CHANNEL_NOT_FOUND);
//            }
        } catch (ChannelException e) {
            channelHandlerContext.channel().writeAndFlush(WssResponseVO.CONNECT_FAIL(e.getResponseEnum(), dto.getRoute()));
        } catch (Exception e) {
            log.info("error:{}",e.getMessage());
        }


    }
}
