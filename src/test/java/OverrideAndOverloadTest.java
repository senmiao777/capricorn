import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2018/6/4 0004 下午 9:10
 */
@Slf4j
public class OverrideAndOverloadTest {
    abstract class Human {
    }

    class Man extends Human {
    }

    class Woman extends Human {
    }

    public void sayHello(Human human) {
        log.info("Human sayHello");
    }

    public void sayHello(Man man) {
        log.info("Man sayHello");
    }

    public void sayHello(Woman woman) {
        log.info("Woman sayHello");
    }


    @Test
    public void testHello() {
        Human man = new Man();
        Human woman = new Woman();
        OverrideAndOverloadTest t = new OverrideAndOverloadTest();

        // Human sayHello
        t.sayHello(man);
        // Human sayHello
        t.sayHello(woman);

        t.sayHello((Man) man);
        t.sayHello((Woman) woman);
    }
}
