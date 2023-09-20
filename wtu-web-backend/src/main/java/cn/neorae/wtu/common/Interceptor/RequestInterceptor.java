package cn.neorae.wtu.common.Interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.annotation.FreePass;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class RequestInterceptor implements HandlerInterceptor {

    // HttpServlet 请求拦截器，只要是经过config/InterceptorConfig.java配置的请求，都会经过这里
    // 拦截器可以有多个
    // 然后在config/MvcConfig中     registry().addInterceptor(requestInterceptor).order(顺序)  即可添加

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求路径：{}", request.getRequestURI());
        if (handler instanceof HandlerMethod method) {
            String uuid = request.getHeader("uuid");
            // 获取方法上有没有打FreePass注解
            FreePass freePass = method.getMethodAnnotation(FreePass.class);
            // 为null表示该方法没打注解，需校验登录状态
            if (freePass == null) {
                if (StrUtil.isBlank(uuid)) {
                    response.sendRedirect("/redirect/unauthorized?route=login");
                    return false;
                }
                if (!StpUtil.isLogin(uuid)) {
                    // uuid不为空，但不匹配，说明token过期了，重新登录，并放行
                    log.info("{}的会话token过期，重新登录，予以放行",uuid);
                    StpUtil.login(uuid);
                }
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {

    }

}
