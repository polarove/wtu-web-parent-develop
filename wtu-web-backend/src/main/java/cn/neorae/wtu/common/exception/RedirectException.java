package cn.neorae.wtu.common.exception;

import lombok.Getter;

@Getter
public class RedirectException extends RuntimeException {

        private final String route;

        public RedirectException(String route) {
            this.route = route;
        }

        public static void throwException(String route) throws RedirectException {
            throw new RedirectException(route);
        }
}
