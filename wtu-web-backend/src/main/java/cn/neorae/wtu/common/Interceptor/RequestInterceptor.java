package cn.neorae.wtu.common.Interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.annotation.FreePass;
import cn.neorae.common.enums.Enums;
import cn.neorae.wtu.common.util.CookieUtil;
import cn.neorae.wtu.common.util.UserUtil;
import cn.neorae.wtu.common.util.Values;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.mapper.UserMapper;
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

    @Resource
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("请求路径：{}", request.getRequestURI());
        if (handler instanceof HandlerMethod method) {
            // 获取方法上有没有打FreePass注解
            FreePass freePass = method.getMethodAnnotation(FreePass.class);
            String uuid = CookieUtil.getUUID(request, Values.Fingerprint);
            // 为null表示该方法没打注解，需校验登录状态
            if (freePass == null) {
                if (StrUtil.isBlank(uuid)) {
                    response.sendRedirect("/redirect/unauthorized?route=login");
                    return false;
                }
                User user = userMapper.getUserByUUID(uuid, Enums.Polar.FALSE.getCode());
                if (BeanUtil.isEmpty(user)) {
                    response.sendRedirect("/redirect/unauthorized?route=login");
                    return false;
                }
                // 如果之前登陆过，就刷新登录状态
                if (!StpUtil.isLogin(uuid)){
                    StpUtil.login(uuid);
                }
                UserUtil.addUser(user);
                return true;
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
        String uuid = CookieUtil.getUUID(request, Values.Fingerprint);
        if (StrUtil.isNotBlank(uuid)) {
            UserUtil.removeUser(uuid);
        }
    }

}
