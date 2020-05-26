package light.house.book;

import light.house.rest.starter.HelloService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookApplicationTests {

    @Autowired
    HelloService helloService;

    @Test
    public void contextLoads() {
        System.out.println(helloService.sayHello());
    }
}
