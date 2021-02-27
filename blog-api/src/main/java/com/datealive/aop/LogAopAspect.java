package com.datealive.aop;

import java.lang.reflect.Method;
import java.sql.SQLException;
import java.util.Date;
import java.util.Map;

import com.datealive.annotation.LogAnno;
import com.datealive.pojo.Log;
import com.datealive.pojo.User;
import com.datealive.service.LogService;
import com.datealive.utils.StringUtils;
import com.datealive.utils.UserAgentUtils;
import com.datealive.utils.UserUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;


/**
 * @Classname LogAopAspect
 * @Description AOP实现日志
 * @Date 2021/2/17 15:06
 * @author 冰羽
 */
@Component
@Aspect
public class LogAopAspect {
    // 日志Service
    @Autowired
    private LogService logService;

    @Autowired
    UserAgentUtils userAgentUtils;

    /**
     * 环绕通知记录日志通过注解匹配到需要增加日志功能的方法
     *
     * @param pjp
     * @return
     * @throws Throwable
     */
    @Around("@annotation(com.datealive.annotation.LogAnno)")
    public Object aroundAdvice(ProceedingJoinPoint pjp) throws Throwable {
        // 1.方法执行前的处理，相当于前置通知
        // 获取方法签名
        MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
        // 获取方法
        Method method = methodSignature.getMethod();
        // 获取方法上面的注解
        LogAnno logAnno = method.getAnnotation(LogAnno.class);
        // 获取操作类型的属性值
        String operateType = logAnno.operateType();
        //获取操作描述值
        String description=logAnno.description();
        //获取日志类别
        Integer classification= logAnno.classification();

        // 创建一个日志对象(准备记录日志)
        Log log = new Log();


        // 操作方式get put...
        log.setOperatetype(operateType);
        //操作描述
        log.setDescription(description);
        //日志类别
        log.setClassification(classification);

        if(classification!=3){
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String userAgent = request.getHeader("User-Agent");
            String ip = UserUtils.getIpAddress();
            String url = request.getRequestURL().toString(); //请求的url
            log.setUrl(url);
            log.setIp(ip);
            //设置ip地址来源
            log.setIpSource(UserUtils.getCityInfo(ip));
            Map<String, String> userAgentMap = userAgentUtils.parseOsAndBrowser(userAgent);
            String os = userAgentMap.get("os");
            String browser = userAgentMap.get("browser");
            log.setOs(os);
            log.setBrowser(browser);
        }else{
            log.setIp("127.0.0.1");
            log.setIpSource(UserUtils.getCityInfo("127.0.0.1"));
        }
        Object result = null;
        try {
            // 让代理方法执行
            long start = System.currentTimeMillis() ;
            result = pjp.proceed();
            long end = System.currentTimeMillis() ;
            // 2.相当于后置通知(方法成功执行之后走这里)
           // System.out.println(start-end);
            log.setSpendTime((int)(end-start)); // 请求该接口花费的时间
            log.setOperateresult("正常");// 设置操作结果
            log.setError(null);
        } catch (Exception  e) {
            // 3.相当于异常通知部分
            log.setOperateresult("异常");// 设置操作结果
            log.setError(StringUtils.getStackTrace(e));
            //4是异常日志
            log.setClassification(4);
        } finally {
            // 4.相当于最终通知
            //如果是操作 登录日志
            if(classification==1){
                log.setOperateor(UserUtils.getSubjectUser().getNick_name());
            }
            log.setOperatedate(new Date());// 设置操作日期
            logService.addLog(log);// 添加日志记录
        }
        return result;
    }


}