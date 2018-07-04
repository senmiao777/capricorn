import com.frank.designpattern.*;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 10:08
 */
@Slf4j
public class DesignPatternTest {

    /**
     * 模板模式
     */
    @Test
    public void test() {
        Ford ford = new Ford();
        ford.run();

        Hummer hummer = new Hummer();
        hummer.run();

        hummer.setAlarm(true);
        hummer.run();

    }

    /**
     * 观察者模式也叫发布/订阅模式
     * 优点：
     * 1、观察者和监听者解耦，易扩展
     * 2、触发的方式，完美的形成触发链
     * 少了一个铁钉，掉了一个马掌；
     * 掉了一个马掌，失了一匹战马；
     * 失了一匹战马，丢了一个国王；
     * 丢了一个国王，输了一场战争；
     * 输了一场战争，亡了一个国家。
     * <p>
     * 缺点：
     * 1、通知观察者的时候，如果是顺序的，一个观察者卡了，会影响后边的观察者，存在效率问题
     * 所以一般将观察者的处理设置成异步的
     * 2、多级触发效率和卡壳的问题可能更明显
     * <p>
     * 使用场景：
     * 关联行为
     * 多级触发
     * <p>
     * 观察者模式和责任连模式的最大区别就是，观察者模式消息在传递过程中可以修改，
     * 而责任链模式传递的消息原则上是不进行修改的
     */
    @Test
    public void test2() {

        Observer police = new Police();

        Observable criminal = new Criminal();

        criminal.addObserver(police);


        criminal.eat("apple");
        criminal.sleep("十分钟");

        /**
         * spring 提供的观察者和监听者
         */
        Demon demon = new Demon();
        demon.addObserver(new CIA());
        demon.eat("banana");


    }

}
