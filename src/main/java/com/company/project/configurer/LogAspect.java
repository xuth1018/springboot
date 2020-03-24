package com.company.project.configurer;

import com.alibaba.fastjson.JSON;
import com.company.project.annotation.Log;
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
        Object result = null;
        long beginTime = System.currentTimeMillis();
        try{//执行方法
            getMethodInfo(point);
            result = point.proceed();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        long endTime = System.currentTimeMillis();
        logger.info("执行时间：{}",endTime-beginTime);
        return result;
    }

    private void getMethodInfo(ProceedingJoinPoint point){
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
        logger.info("==="+className+"."+methodName+"()===info:{}", JSON.toJSONString(args));
    }




}
