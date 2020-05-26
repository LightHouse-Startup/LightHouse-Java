package light.house.startup.aop.log.aop;

import light.house.startup.aop.log.annotation.EagleEye;
import light.house.startup.aop.log.util.JsonUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;

@Aspect
@Component
public class RequestLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    /**
     * 切入所有EagleEye注解修饰的方法
     */
    @Pointcut("@annotation(light.house.startup.aop.log.annotation.EagleEye)")
    public void logPointCut() {

    }

    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint pjp) throws Throwable {
        //请求开始时间戳
        long begin = System.currentTimeMillis();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        Method method = methodSignature.getMethod();
        EagleEye eagleEye = method.getAnnotation(EagleEye.class);

        //接口描述
        String desc = eagleEye.desc();

        logger.info("==================请求开始==================");
        logger.info("请求链接：{}", request.getRequestURL());
        logger.info("接口描述：{}", desc);
        logger.info("请求类型：{}", request.getMethod());
        logger.info("请求方法：{}.{}", signature.getDeclaringTypeName(), signature.getName());
        logger.info("请求IP：{}", request.getRemoteAddr());
        logger.info("请求入参：{}", JsonUtil.toJson(pjp.getArgs()));

        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        logger.info("请求耗时：{}ms", end - begin);
        logger.info("请求返回：{}", JsonUtil.toJson(result));
        logger.info("==================请求结束==================");

        return result;
    }
}
