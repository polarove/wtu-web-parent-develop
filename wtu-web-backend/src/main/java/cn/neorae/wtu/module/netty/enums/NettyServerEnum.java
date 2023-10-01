package cn.neorae.wtu.module.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface NettyServerEnum {


    @AllArgsConstructor
    @Getter
    enum TeamRoutes implements NettyServerEnum {

        ORIGIN("origin"),

        EVENT("event"),

        ALARM("alarm"),

        STEEL_PATH("steelpath"),

        INVASION("invasion"),

        SYNDICATE("syndicate"),

        FISSURE("fissure"),

        SORTIE("sortie"),

        HUNT("hunt"),

        DURIVI("durivi"),

        EMPYREAN("empyrean"),

        CHANNEL_NOT_FOUND("channel_not_found");

        private final String route;

        public static TeamRoutes match(String route) {
            for (TeamRoutes teamRoutes :TeamRoutes.values()) {
                if (teamRoutes.getRoute().equals(route)) {
                    return teamRoutes;
                }
            }
            return CHANNEL_NOT_FOUND;
        }
    }


    @AllArgsConstructor
    @Getter
    enum GameServerEnum implements NettyServerEnum {

        EN(1),

        CN(0),

        NOT_SUPPORTED(-1);

        private final Integer type;

        public static GameServerEnum match (Integer type) {
            for (GameServerEnum gameServerEnum : GameServerEnum.values()) {
                if (gameServerEnum.getType().equals(type)) {
                    return gameServerEnum;
                }
            }
            return NOT_SUPPORTED;
        }
    }

    @AllArgsConstructor
    @Getter
    enum ConnectionEnum implements NettyServerEnum {

        PING(0),

        CONNECT(1),

        DISCONNECT(2),

        MESSAGE(3),

        NOT_SUPPORTED(-1);

        private final Integer type;

        public static ConnectionEnum match (Integer type) {
            for (ConnectionEnum connectionEnum : ConnectionEnum.values()) {
                if (connectionEnum.getType().equals(type)) {
                    return connectionEnum;
                }
            }
            return NOT_SUPPORTED;
        }
    }

    @AllArgsConstructor
    @Getter
    enum ActionEnum implements NettyServerEnum {

        CONNECTION(1),

        ADD_TEAM(2),

        REMOVE_TEAM(3),

        TOGGLE_STATUS(4),

        NOT_SUPPORTED(0);

        private final Integer type;

        public static ActionEnum match (Integer type) {
            for (ActionEnum actionEnum : ActionEnum.values()) {
                if (actionEnum.getType().equals(type)) {
                    return actionEnum;
                }
            }
            return NOT_SUPPORTED;
        }
    }

    @AllArgsConstructor
    @Getter
    enum TeamStatusEnum implements NettyServerEnum {

        PRIVATE(0),

        PUBLIC(1);

        private final Integer type;

    }
}
