package com.musichive.common.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Author Jun
 * Date 2021 年 04月 01 日 09:10
 * Description 检测当前是否登录 注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CheckLogin {

    /**
     * 是否显示对话框--默认不显示直接跳转
     */
    boolean value() default false;
}
