package cn.neorae.common.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public interface Enums<T> {

    T getType();

    Integer getCode();

    @AllArgsConstructor
    enum Polar implements Enums<String> {
        TRUE(1, "是"),
        FALSE(0, "否");

        private final Integer code;

        private final String type;
        @Getter
        private static final Map<Integer, String> typeMap;

        static {
            typeMap = Arrays.stream(Polar.values()).collect(Collectors.toMap(Polar::getCode, Polar::getType));
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public static Boolean isValidType (Integer code) {
            return typeMap.containsKey(code);
        }
    }

    @AllArgsConstructor
    enum OnlineStatus implements Enums<String>{
        OFFLINE(0, "离线"),

        ONLINE(1, "在线"),

        ONLINE_IN_GAME(2, "游戏中");

        private final Integer code;

        private final String type;

        @Getter
        private static final Map<Integer, String> typeMap;

        static {
            typeMap = Arrays.stream(OnlineStatus.values()).collect(Collectors.toMap(OnlineStatus::getCode, OnlineStatus::getType));
        }

        @Override
        public String getType() {
            return type;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public static Boolean isValidType (Integer code) {
            return typeMap.containsKey(code);
        }
    }

    @AllArgsConstructor
    enum Bool implements Enums<Boolean> {
        TRUE(1, true),
        FALSE(0, false);

        private final Integer code;

        private final Boolean type;
        
        @Getter
        private static final Map<Integer, String> typeMap;

        static {
            typeMap = Arrays.stream(Polar.values()).collect(Collectors.toMap(Polar::getCode, Polar::getType));
        }

        @Override
        public Boolean getType() {
            return type;
        }

        @Override
        public Integer getCode() {
            return code;
        }

        public static Boolean isValidType (Integer code) {
            return typeMap.containsKey(code);
        }
    }
}