package cn.neorae.wtu.module.netty.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

public interface TeamServerEnum {


    @AllArgsConstructor
    @Getter
    enum TeamRoutes implements TeamServerEnum {

        ORIGIN("/origin"),

        EVENT("/event"),

        ALARM("/alarm"),

        INVASION("/invasion"),

        SYNDICATE("/syndicate"),

        FISSURE("/fissure"),

        SORTIE("/sortie"),

        HUNT("/HUNT"),

        DURIVI("/durivi"),

        EMPYREAN("/empyrean"),

        ROUTE_NOT_FOUNT("/route_not_found");

        private final String route;

        public static TeamRoutes match(String route) {
            for (TeamRoutes teamRoutes :TeamRoutes.values()) {
                if (teamRoutes.getRoute().equals(route)) {
                    return teamRoutes;
                }
            }
            return ROUTE_NOT_FOUNT;
        }
    }

    @AllArgsConstructor
    @Getter
    enum ConnectionEnum implements TeamServerEnum {

        CONNECT(1),

        DISCONNECT(2),

        NOT_SUPPORTED(0);

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
    enum ActionEnum implements TeamServerEnum {

        INSERT(1),

        UPDATE(2),

        DELETE(3),

        SELECT(4),

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
}
