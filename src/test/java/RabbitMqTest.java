import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author frank
 * @version 1.0
 * @date 2018/1/21 0021 下午 4:18
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages = "com.frank")
@SpringBootTest(classes = RabbitMqTest.class)
@Rollback(false)
@Slf4j
public class RabbitMqTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Test
    public void testt2() {
        log.info("RabbitMqTest start");
        rabbitTemplate.convertAndSend("假装是个json串");
        log.info("RabbitMqTest end");
    }

    @Test
    public void testt3() {
        log.info("RabbitMqTest start rabbitTemplate={}", rabbitTemplate);
        Message msg = rabbitTemplate.receive();
        log.info("RabbitMqTest end msg={}", msg);
    }


}
