package com.mcsy.blog.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.ServletContextAttributeFactoryBean;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author 15199
 * 使用Aspect处理日志
 */
@Component
@Aspect
public class AspectLog {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 定义切点 使用execution表达式 第一个行表示的是所有权限修饰符 第二个*表示所有类 第三个表示所有方法
     * （..）表示所有带参数的方法和不带参数的方法
     */
    @Pointcut("execution(* com.mcsy.blog.controller.*.*(..))")
    public void log() {}

    /**
     * 执行方法之前
     */
    @Before("log()")
    public void doBefore(JoinPoint joinPoint) {
        //使用RequestContextHolder得到ServletRequestAttributes；
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        //得到HttpServletRequest
        HttpServletRequest request;
        if (attributes != null) {
            request = attributes.getRequest();
        } else {
            throw new RuntimeException("没有获取到Http的request");
        }
        //获取url ip
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        //获取方法中的参数
        Object[] args = joinPoint.getArgs();
        //获取方法的类名+方法名字
        String classMethod = joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName();
        BlogLog blogLog = new BlogLog(url, ip, args, classMethod);
        //日志输出
        logger.info("访客访问信息 : {}",blogLog);
    }

    /**
     * 执行方法之后
     */
    @After("log()")
    public void doAfter() {
        logger.info("*--------doAfter 方法-------");
    }
    @AfterReturning(value = "log()",returning = "result")
    public void doAfterReturn(Object result){
        logger.info("result : {}",result);
    }
    /**
     * 内部类用于封装接收到的信息
     */
    private static class BlogLog {
        private String url;
        private String ip;
        private Object[] args;
        private String classMethod;

        public BlogLog(String url, String ip, Object[] args, String classMethod) {
            this.url = url;
            this.ip = ip;
            this.args = args;
            this.classMethod = classMethod;
        }

        @Override
        public String toString() {
            return "接收到的参数{" +
                    "url='" + url + '\'' +
                    ", ip='" + ip + '\'' +
                    ", args=" + Arrays.toString(args) +
                    ", classMethod='" + classMethod + '\'' +
                    '}';
        }
    }
}
