package light.house.startup.aop.log.annotation;

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
