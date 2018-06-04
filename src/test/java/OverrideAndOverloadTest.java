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


    /**
     * 重载测试
     *
     */
    @Test
    public void testHello() {
        /**
         * Human 称为 外观类型 或者 静态类型
         * Man 称为 实际类型
         */
        Human man = new Man();
        Human woman = new Woman();
        OverrideAndOverloadTest t = new OverrideAndOverloadTest();

        /**
         * 方法的接受者在确定调用方法的对象的前提下，使用哪个重载（overload）版本，取决于传入参数的数据量和类型
         * 虚拟机，准确的说是编译器，在重载的时候是通过参数的静态类型而不是实际类型作为判定依据的
         * 所以，下边的代码，输出的是 Human sayHello
         */
        // Human sayHello
        t.sayHello(man);
        // Human sayHello
        t.sayHello(woman);

        t.sayHello((Man) man);
        t.sayHello((Woman) woman);

        /**
         * 编译器虽然能确定出方法的重载版本，但在很多情况下这个重载的版本并不是唯一的，往往只能确定一个更加合适的版本。
         */
    }
}
