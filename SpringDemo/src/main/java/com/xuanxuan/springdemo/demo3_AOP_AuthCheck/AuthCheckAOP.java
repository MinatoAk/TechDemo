package com.xuanxuan.springdemo.demo3_AOP_AuthCheck;

import com.xuanxuan.springdemo.annotation.AuthCheck;
import com.xuanxuan.springdemo.demo2_GlobalExceptionHandler.BusinessException;
import com.xuanxuan.springdemo.demo2_GlobalExceptionHandler.ErrorCode;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthCheckAOP {

    /**
     * AOP 实现权限校验
     * 具体使用时直接在 Controller 方法前打上 @AuthCheck(mustRole = "xxx") 注解
     * @param joinPoint
     * @param authCheck
     * @return
     * @throws Throwable
     */
    @Around("@annotation(authCheck)")
    public Object authCheck(ProceedingJoinPoint joinPoint, AuthCheck authCheck) throws Throwable {
        String mustRole = authCheck.mustRole();

        // 1. 不需要权限，直接放行
        if (mustRole == null) return joinPoint.proceed();

        // 2. 通过 HttpServletRequest 获取当前登录用户，这一步直接省略掉
        String currentLoginUserRole = "admin";

        // 3. 如果当前用户被封号，直接拒绝
        if ("ban".equals(currentLoginUserRole)) {
            throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
        }

        // 4. 需要管理员身份访问的则判断是否具有管理员权限
        if ("admin".equals(mustRole)) {
            if (!"admin".equals(currentLoginUserRole)) {
                throw new BusinessException(ErrorCode.NO_AUTH_ERROR);
            }
        }

        // 5. 通过权限校验，放行
        return joinPoint.proceed();
    }
}
