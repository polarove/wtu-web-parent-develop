package cn.neorae.wtu.common.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.neorae.wtu.common.Interceptor.RequestInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    @Resource
    private RequestInterceptor requestInterceptor;

    private static final long MAX_AGE = 259200L;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 登录校验
        // @FreePass 注解表示不需要登录校验
        registry.addInterceptor(requestInterceptor).addPathPatterns("/**");

        // @SaIgnore 注解表示不需要权限校验
        registry.addInterceptor(new SaInterceptor(handler -> {
            /// 权限校验：
        })).addPathPatterns("/**");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://localhost:67","https://192.168.0.102:67","https://www.neorae.cn")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(MAX_AGE);
    }
}
