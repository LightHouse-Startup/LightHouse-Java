# 自定义Aop、Annotation实现接口请求日志记录

## 添加依赖项
pom.xml
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-aop</artifactId>
</dependency>
```

## 自定义Annotation
```java
import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface EagleEye {

    /**
     * 接口描述
     * @return
     */
    String desc() default "";
}
```
## 自定义Aop
```java
@Aspect
@Component
public class RequestLogAspect {

    private final static Logger logger = LoggerFactory.getLogger(RequestLogAspect.class);

    /**
     * 切入所有EagleEye注解修饰的方法
     */
    @Pointcut("@annotation(EagleEye)")
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
```
## 接口标注新注解
```java
@EagleEye(desc = "调用hello接口")
@GetMapping("/hello")
public String hello(String name) {
    return helloService.sayHello(name);
}
```
