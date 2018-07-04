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
     * 观察者模式
     */
    @Test
    public void test2() {

        Observer police = new Police();
        Observer cia = new CIA();
        Observable criminal = new Criminal();

        criminal.addObserver(police);
        criminal.addObserver(cia);

        criminal.eat("apple");
        criminal.sleep("十分钟");

        Observable demon = new Demon();
        demon.addObserver(cia);
        demon.sleep("一年");


    }

}
