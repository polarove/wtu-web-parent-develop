package cn.neorae.wtu.module.netty.module.en.connection;

import cn.neorae.common.enums.ResponseEnum;
import cn.neorae.wtu.module.netty.NettyApplication;
import cn.neorae.wtu.module.netty.domain.dto.WebsocketConnectionDTO;
import cn.neorae.wtu.module.netty.domain.vo.AfterConnectionVO;
import cn.neorae.wtu.module.netty.domain.vo.WssResponseVO;
import cn.neorae.wtu.module.netty.enums.NettyServerEnum;
import cn.neorae.wtu.module.netty.exceptions.ChannelNotFoundException;
import com.alibaba.fastjson2.JSON;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EnTeamDisconnectionHandler {


    public static void execute(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame msg)  {
        WebsocketConnectionDTO dto = JSON.parseObject(msg.text(), WebsocketConnectionDTO.class);
        NettyApplication.EN_PUBLIC_CHANNEL_POOL.remove(dto.getUuid());
        AfterConnectionVO afterConnectionVO = new AfterConnectionVO();
        afterConnectionVO.setTotal(NettyApplication.EN_PUBLIC_CHANNEL_POOL.size());
        try {
            switch (NettyServerEnum.TeamRoutes.match(dto.getRoute())) {
                case ORIGIN -> {
                    NettyApplication.EN_TEAM_ORIGIN.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_ORIGIN.size());
                    NettyApplication.EN_TEAM_ORIGIN.writeAndFlush(WssResponseVO.connect("已离开始源星系组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case EVENT -> {
                    NettyApplication.EN_TEAM_EVENT.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_EVENT.size());
                    NettyApplication.EN_TEAM_EVENT.writeAndFlush(WssResponseVO.connect("已离开事件组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case ALARM -> {
                    NettyApplication.EN_TEAM_ALARM.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_ALARM.size());
                    NettyApplication.EN_TEAM_ALARM.writeAndFlush(WssResponseVO.connect("已离开警报组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case STEEL_PATH -> {
                    NettyApplication.EN_TEAM_STEEL_PATH.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_STEEL_PATH.size());
                    NettyApplication.EN_TEAM_STEEL_PATH.writeAndFlush(WssResponseVO.connect("已离开钢铁之路组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case INVASION -> {
                    NettyApplication.EN_TEAM_INVASION.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_INVASION.size());
                    NettyApplication.EN_TEAM_INVASION.writeAndFlush(WssResponseVO.connect("已离开入侵组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case SYNDICATE -> {
                    NettyApplication.EN_TEAM_SYNDICATE.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_SYNDICATE.size());
                    NettyApplication.EN_TEAM_SYNDICATE.writeAndFlush(WssResponseVO.connect("已离开集团组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case FISSURE -> {
                    NettyApplication.EN_TEAM_FISSURE.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_FISSURE.size());
                    NettyApplication.EN_TEAM_FISSURE.writeAndFlush(WssResponseVO.connect("已离开虚空遗物组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case SORTIE -> {
                    NettyApplication.EN_TEAM_SORTIE.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_SORTIE.size());
                    NettyApplication.EN_TEAM_SORTIE.writeAndFlush(WssResponseVO.connect("已离开突击组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case HUNT -> {
                    NettyApplication.EN_TEAM_HUNT.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_HUNT.size());
                    NettyApplication.EN_TEAM_HUNT.writeAndFlush(WssResponseVO.connect("已离开猎杀执行官组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case DURIVI -> {
                    NettyApplication.EN_TEAM_DURIVI.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_DURIVI.size());
                    NettyApplication.EN_TEAM_DURIVI.writeAndFlush(WssResponseVO.connect("已离开双衍王境组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                case EMPYREAN -> {
                    NettyApplication.EN_TEAM_EMPYREAN.remove(channelHandlerContext.channel());
                    afterConnectionVO.setCurrentChannel(NettyApplication.EN_TEAM_EMPYREAN.size());
                    NettyApplication.EN_TEAM_EMPYREAN.writeAndFlush(WssResponseVO.connect("已离开九重天组队频道", JSON.toJSONString(afterConnectionVO)));
                }
                default -> throw new ChannelNotFoundException(ResponseEnum.CHANNEL_NOT_FOUND);
            }
        } catch (ChannelNotFoundException e) {
            channelHandlerContext.channel().writeAndFlush(WssResponseVO.fail(e.getResponseEnum(), dto.getRoute()));
        } catch (Exception e) {
            log.info("error:{}",e.getMessage());
        }
    }
}
