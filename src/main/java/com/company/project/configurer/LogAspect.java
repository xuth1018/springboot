package com.company.project.configurer;

import com.alibaba.fastjson.JSON;
import com.company.project.annotation.Log;
import org.apache.tomcat.util.http.fileupload.RequestContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.xml.stream.events.StartDocument;
import java.lang.reflect.Method;

@Aspect
@Component
public class LogAspect {

    private static Logger logger = LoggerFactory.getLogger(LogAspect.class);

    /**
     * 定义切入点  注解为log的
     */
    @Pointcut("@annotation(com.company.project.annotation.Log)")
    public void pointcut(){

    }

/*    @Before("")//连接点执行之前的通知
    public void doBefore(){
    }

    @After("")//连接点执行之后的通知（返回通知和异常返回的通知）
    public void doAfter(){

    }*/

    /**
     * 环绕通知
     * @param point
     * @return
     */
    @Around("pointcut()")
    public Object doAround(ProceedingJoinPoint point){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String str = getMethodInfo(point);
        Object result = null;
        try{//执行方法
            result =  point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        stopWatch.stop();
        logger.info(str+"---执行时间：{}",stopWatch.prettyPrint());
        return result;
    }

    private String getMethodInfo(ProceedingJoinPoint point){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String url = request.getRequestURI();

        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log log = method.getAnnotation(Log.class);
        String value;
        if(null != log) {
            value   = log.value();
        }
        //方法名
        String className = point.getTarget().getClass().getName();
        String methodName = signature.getName();
        //参数值
        Object[] args = point.getArgs();
        logger.info(url+"---info:{}", JSON.toJSONString(args));
        return url;
    }




}
