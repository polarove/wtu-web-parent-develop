package cn.neorae.wtu.common.Interceptor;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.neorae.common.annotation.FreePass;
import cn.neorae.wtu.common.util.CookieUtil;
import cn.neorae.wtu.common.util.UserUtil;
import cn.neorae.wtu.common.util.Values;
import cn.neorae.wtu.module.account.domain.User;
import cn.neorae.wtu.module.account.mapper.UserMapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
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
    private UserMapper userMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception {
        log.info("请求路径：{}", request.getRequestURI());
        if (handler instanceof HandlerMethod method) {
            // 获取方法上有没有打FreePass注解
            FreePass freePass = method.getMethodAnnotation(FreePass.class);
            String uuid = CookieUtil.getUUID(request, Values.Fingerprint);
            // 为null表示该方法没打注解，需校验登录状态
            if (freePass == null) {
                if (StrUtil.isBlank(uuid)) {
                    response.sendRedirect("/redirect/login?route=login");
                    return false;
                }
                User user = userMapper.selectOne(new LambdaQueryWrapper<User>().eq(User::getUuid,uuid));
                if (BeanUtil.isEmpty(user)) {
                    response.sendRedirect("/redirect/login?route=login");
                    return false;
                }
                UserUtil.addUser(user);
                return true;
            }
            return true;
        }
        return true;
    }

    @Override
    public void postHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable ModelAndView modelAndView) {
    }

    @Override
    public void afterCompletion(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler, @Nullable Exception ex) {
        String uuid = CookieUtil.getUUID(request, Values.Fingerprint);
        if (StrUtil.isNotBlank(uuid)) {
            UserUtil.removeUser(uuid);
        }
    }

}
