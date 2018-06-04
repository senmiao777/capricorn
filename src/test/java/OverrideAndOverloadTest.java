import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.io.Serializable;

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

//    public void sayHello(int i) {
//        log.info("int sayHello i={}",i);
//    }

//    public void sayHello(char i) {
//        log.info("char sayHello i={}",i);
//    }

//    public void sayHello(long i) {
//        log.info("long sayHello i={}",i);
//    }

//    public void sayHello(Character i) {
//        log.info("Character sayHello i={}",i);
//    }

    public void sayHello(Serializable i) {
        log.info("Serializable sayHello i={}", i);
    }

    /**
     * 重载测试
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
         * 如果有接收char类型的sayHello,会输出char sayHello i=a
         * 去掉char类型的sayHello，如果有接收int类型的sayHello,会输出int sayHello i=97
         * 去掉char类型的sayHello，int类型的sayHello,如果有接收int类型的sayHello,会输出long sayHello i=97
         * 聪明的你已经发现规律了 char->int->long->float->double
         * 不会匹配到byte和short类型，因为char转byte或short类型不安全
         *
         * ...如果有接收Character类型的sayHello,会输出char sayHello i=a
         * 这时发生了自动装箱，'a'被包装为他的封装类型java.lang.Character
         *
         * ...如果有接收Serializable类型的sayHello,会输出Serializable sayHello i=a
         * 这个不知道是什么情况了吧，'a' 为什么会转成Serializable类型呢
         * 解释一波：当自动装箱之后找不到装箱类，但是找到了装箱类实现的接口类型，于是又发生了一次自动类型转换.
         * public final class Character implements java.io.Serializable, Comparable<Character>
         *
         * 这套测试代码演示了编译期间静态分派的过程，这个过程也是java实现方法重载的本质。
         */

        t.sayHello('a');
    }
}
