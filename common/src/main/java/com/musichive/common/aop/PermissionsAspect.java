package com.musichive.common.aop;

import android.app.Activity;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.PermissionUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.jetbrains.annotations.NotNull;

import java.util.List;


/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/06
 * desc   : 权限申请切面
 */
@Aspect
public class PermissionsAspect {


    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.musichive.musicbee.aop.Permissions * *(..))")
    public void method() {
    }

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(permissions)")
    public void aroundJoinPoint(final ProceedingJoinPoint joinPoint, Permissions permissions) {
        Activity activity = ActivityUtils.getTopActivity();
        if (activity == null || activity.isFinishing() || activity.isDestroyed()) {
            return;
        }
        PermissionUtils.permission(permissions.value())
                .callback((isAllGranted, granted, deniedForever, denied) -> {
                    if (isAllGranted) {
                        try {
                            // 获得权限，执行原方法
                            joinPoint.proceed();
                        } catch (Throwable e) {
                            e.printStackTrace();
                        }
                    }
                })
                .request();

    }
}