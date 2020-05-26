package light.house.startup.aop.log.controller;

import light.house.startup.aop.log.service.IHelloService;
import light.house.startup.aop.log.annotation.EagleEye;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final IHelloService helloService;

    public HelloController(IHelloService helloService) {
        this.helloService = helloService;
    }

    @EagleEye(desc = "调用hello接口")
    @GetMapping("/hello")
    public String hello(String name) {
        return helloService.sayHello(name);
    }
}
