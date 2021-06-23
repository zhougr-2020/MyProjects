package com.nanhang.mybatis_plus.aspect;

import com.nanhang.mybatis_plus.annotation.LoginCheck;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Vector;

@Aspect  //把当前类标识为一个切面供容器读取
@Component
public class LogAspect {
    //切点
    @Pointcut("execution(public * com.nanhang.mybatis_plus.controller.*.*(..))")
    public void webLog() {
    }

    //前置通知
    @Before("webLog()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {
        // 接收到请求，记录请求内容
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        // 记录请求内容
        System.out.println("URL : " + request.getRequestURL().toString());
        System.out.println("HTTP_METHOD : " + request.getMethod());
        System.out.println("IP : " + request.getRemoteAddr());
        System.out.println("CLASS_METHOD : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        System.out.println("ARGS : " + Arrays.toString(joinPoint.getArgs()));

    }

    @Before("webLog()")
    public void before(JoinPoint joinPoint) {
        System.out.println("前置通知2.........");
    }

    //后置通知
    @AfterReturning(returning = "ret", pointcut = "webLog()")
    public void doAfterReturning(Object ret) throws Throwable {
        // 处理完请求，返回内容
        System.out.println("方法的返回值 : " + ret);
    }

    //后置异常通知
    @AfterThrowing("webLog()")
    public void throwss(JoinPoint jp) {
        System.out.println("方法异常时执行.....");
        Vector<Object> vector = new Vector<>();

    }

    //后置最终通知,final增强，抛出异常或者正常退出都会执行
    @After("webLog()")
    public void after(JoinPoint jp) {
        System.out.println("方法最后执行.....");
    }

    //环绕通知,环绕增强，相当于MethodInterceptor
    @Around("webLog() && @annotation(loginCheck)")
    public Object arround(ProceedingJoinPoint pjp, LoginCheck loginCheck) {
        System.out.println("方法环绕start.....");
        try {
            if (loginCheck.check()) {
                HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
                this.loginCheck(request);
            }
            Object o = pjp.proceed();
            System.out.println("方法环绕proceed，结果是 :" + o);
            return o;
        } catch (Throwable e) {
            e.printStackTrace();
            return null;
        }
    }

    //登录效验
    private void loginCheck(HttpServletRequest request) {
        String token = request.getHeader("Accept-Language");
        if (StringUtils.isEmpty(token)) {
            throw new RuntimeException("请先登录");
        }
        //用token去缓存里拿到用户登录信息,login==null就代表过期了 用token去拿登录接口的信息,再放到缓存中30分钟
        //LoginInfo login = redisService.get(LOGIN_INFO_PREFIX+token, LoginInfo.class);
    }
}