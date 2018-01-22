package concurrent;

import com.frank.entity.mysql.User;
import com.frank.repository.mysql.BenefitRepository;
import com.frank.repository.mysql.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

//import org.springframework.boot.test.SpringApplicationConfiguration;

/**
 * Created by Administrator on 2017/4/25 0025.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootApplication
@ComponentScan(basePackages ="com.frank")
@SpringBootTest(classes= SpelTest.class)
@Rollback(false)
@Slf4j
public class SpelTest {

    @Autowired
    private BenefitRepository benefitRepository;


    @Autowired
    private UserRepository userRepository;

    @Value("#{'Hello World'.concat(' spel!')}")
    private String helloWorld;

    @Test
    public void ti() {
        final List<User> all = userRepository.findAll();
        log.info("helloWorld={}",helloWorld);



        Expression expression = new SpelExpressionParser().parseExpression(key);
        String value = expression.getValue(joinPoint.getArgs(), String.class);

    }



}
