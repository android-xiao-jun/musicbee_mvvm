package com.musichive.aop;


import com.blankj.utilcode.util.ToastUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * @Author Jun
 * Date 2021 年 04月 01 日 09:10
 * Description 检测当前是否登录 埋点
 */
@Aspect
public class CheckLoginAspect {

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.musichive.aop.CheckLogin * *(..))")
    public void method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(checkLogin)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, CheckLogin checkLogin) throws Throwable {
        if (true){
            ToastUtils.showShort("请先登录");
            return;
        }
        //执行原方法
        joinPoint.proceed();
    }
}