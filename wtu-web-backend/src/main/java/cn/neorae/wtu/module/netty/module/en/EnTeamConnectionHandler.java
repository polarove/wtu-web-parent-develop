package cn.neorae.wtu.module.netty.module.en;


import cn.neorae.wtu.module.netty.NettyApplication;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.AfterConnectionVO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.exceptions.ChannelNotFoundException;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class EnTeamConnectionHandler {




    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame socketFrame) throws ChannelNotFoundException {
        WebsocketConnectionDTO dto = JSON.parseObject(socketFrame.text(), WebsocketConnectionDTO.class);
        NettyApplication.EN_PUBLIC_CHANNEL_POOL.putIfAbsent(dto.getUuid(), channelHandlerContext.channel());
        AfterConnectionVO afterConnectionVO = new AfterConnectionVO();
        afterConnectionVO.setTotal(NettyApplication.EN_PUBLIC_CHANNEL_POOL.size());
        try {
            EnChannelMap.channelGroupStream.forEach(channelGroup ->{
                if (channelGroup.name().equals(dto.getRoute())){
                    channelGroup.add(channelHandlerContext.channel());
                    afterConnectionVO.setClients(channelGroup.size());
                    channelGroup.writeAndFlush(WssResponseVO.connect(JSON.toJSONString(afterConnectionVO)));
                }
            });
//            switch (NettyServerEnum.TeamRoutes.match(dto.getRoute())) {
//                case ORIGIN -> {
//                    NettyApplication.EN_TEAM_ORIGIN.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_ORIGIN.size());
//                    NettyApplication.EN_TEAM_ORIGIN.writeAndFlush(WssResponseVO.connect("en_已连接至始源星系组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case EVENT -> {
//                    NettyApplication.EN_TEAM_EVENT.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_EVENT.size());
//                    NettyApplication.EN_TEAM_EVENT.writeAndFlush(WssResponseVO.connect("en_已连接至事件组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case ALARM -> {
//                    NettyApplication.EN_TEAM_ALARM.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_ALARM.size());
//                    NettyApplication.EN_TEAM_ALARM.writeAndFlush(WssResponseVO.connect("en_已连接至警报组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case STEEL_PATH -> {
//                    NettyApplication.EN_TEAM_STEEL_PATH.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_STEEL_PATH.size());
//                    NettyApplication.EN_TEAM_STEEL_PATH.writeAndFlush(WssResponseVO.connect("en_已连接至钢铁之路组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case INVASION -> {
//                    NettyApplication.EN_TEAM_INVASION.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_INVASION.size());
//                    NettyApplication.EN_TEAM_INVASION.writeAndFlush(WssResponseVO.connect("en_已连接至入侵组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case SYNDICATE -> {
//                    NettyApplication.EN_TEAM_SYNDICATE.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_SYNDICATE.size());
//                    NettyApplication.EN_TEAM_SYNDICATE.writeAndFlush(WssResponseVO.connect("en_已连接集团组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case FISSURE -> {
//                    NettyApplication.EN_TEAM_FISSURE.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_FISSURE.size());
//                    NettyApplication.EN_TEAM_FISSURE.writeAndFlush(WssResponseVO.connect("en_已连接虚空遗物组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case SORTIE -> {
//                    NettyApplication.EN_TEAM_SORTIE.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_SORTIE.size());
//                    NettyApplication.EN_TEAM_SORTIE.writeAndFlush(WssResponseVO.connect("en_已连接至突击组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case HUNT -> {
//                    NettyApplication.EN_TEAM_HUNT.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_HUNT.size());
//                    NettyApplication.EN_TEAM_HUNT.writeAndFlush(WssResponseVO.connect("en_已连接猎杀执行官组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case DURIVI -> {
//                    NettyApplication.EN_TEAM_DURIVI.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_DURIVI.size());
//                    NettyApplication.EN_TEAM_DURIVI.writeAndFlush(WssResponseVO.connect("en_已连接双衍王境组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                case EMPYREAN -> {
//                    NettyApplication.EN_TEAM_EMPYREAN.add(channelHandlerContext.channel());
//                    afterConnectionVO.setClients(NettyApplication.EN_TEAM_EMPYREAN.size());
//                    NettyApplication.EN_TEAM_EMPYREAN.writeAndFlush(WssResponseVO.connect("en_已连接九重天组队频道", JSON.toJSONString(afterConnectionVO)));
//                }
//                default -> throw new ChannelNotFoundException(ResponseEnum.CHANNEL_NOT_FOUND);
//            }
        } catch (ChannelNotFoundException e) {
            channelHandlerContext.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum(), dto.getRoute()));
        } catch (Exception e) {
            log.info("error:{}",e.getMessage());
        }
    }

}
