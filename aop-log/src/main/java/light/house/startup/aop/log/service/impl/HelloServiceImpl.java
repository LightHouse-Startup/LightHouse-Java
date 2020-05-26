package light.house.startup.aop.log.service.impl;

import light.house.startup.aop.log.service.IHelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements IHelloService {

    @Override
    public String sayHello(String name) {
        return "hello," + name;
    }
}
