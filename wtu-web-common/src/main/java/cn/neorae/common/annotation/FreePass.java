package cn.neorae.common.annotation;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记不需要登录的接口
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FreePass {
    /**
     * 用来确定没有登录后跳到哪里
     * 如果有值，则使用returnUrl做为跳转，否则根据业务跳到指定url
     * @return
     */
    String redirectUrl() default "";
}