import com.frank.designpattern.adapter.Adaptee;
import com.frank.designpattern.adapter.Adapter;
import com.frank.designpattern.adapter.ConcreteTarget;
import com.frank.designpattern.adapter.Target;
import com.frank.designpattern.command.*;
import com.frank.designpattern.decorator.*;
import com.frank.designpattern.facade.Facade;
import com.frank.designpattern.observer.*;
import com.frank.designpattern.observer.Police;
import com.frank.designpattern.observer.Soldier;
import com.frank.designpattern.strategy.*;
import com.frank.designpattern.template.Ford;
import com.frank.designpattern.template.Hummer;
import com.frank.designpattern.template.MotorVehicle;
import lombok.extern.slf4j.Slf4j;
import ognl.EnumerationIterator;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.*;
import java.util.Enumeration;
import java.util.Vector;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 10:08
 */
@Slf4j
public class DesignPatternTest {


    @Test
    public void testCommand() {
        RemoteControl control = new RemoteControl();
        final Light parlourLight = new ParlourLight();
        final Command parlourLightOnCommand = new ParlourLightOnCommand(parlourLight);
        control.setCommand(parlourLightOnCommand);
        control.openParlourLight();

        final ParlourLightOffCommand parlourLightOffCommand = new ParlourLightOffCommand(parlourLight);

        control.setCommand(parlourLightOffCommand);
        control.closeParlourLight();

        control.setCommand(new AirconditionerOnCommand(new Airconditioner("美的")));
        control.openAirconditioner();
    }

    /**
     * 策略模式
     */
    @Test
    public void testStrategy() {
        Person police = new com.frank.designpattern.strategy.Police();
        police.eat();
        police.fight();
        police.drive();

        Person soldier = new com.frank.designpattern.strategy.Soldier();
        soldier.eat();
        soldier.fight();

        Person moneyCarrier = new MoneyCarrier();
        moneyCarrier.eat();
        moneyCarrier.fight();

        Person securityGuard = new SecurityGuard();
        securityGuard.eat();
        securityGuard.fight();

        /**
         * 策略模式的另一种实现
         * 调用类对象不变，设置不同的策略
         */
        ConcretePerson police2 = new ConcretePerson(new Pistol());
        police2.strategyMethod();

        ConcretePerson police3 = new ConcretePerson(new MachineGun());
        police3.strategyMethod();
    }

    @Test
    public void testFacade() {
        Airconditioner airconditioner = new Airconditioner("格力");
        Light light = new ParlourLight();
        Facade facade = new Facade(light, airconditioner);
        facade.open();

        //搜索CLASSPATH路径，以classpath路径下的bean.xml、service.xml文件创建applicationContext
        ApplicationContext ctx = new ClassPathXmlApplicationContext(new String[]{"bean.xml", "service.xml"});

        //以指定路径下的bean.xml、service.xml文件创建applicationContext
        ApplicationContext ctx1 = new FileSystemXmlApplicationContext(new String[]{"bean.xml", "service.xml"});
    }

    @Test
    public void testAdapter() {
        Target t = new Adapter(new Adaptee());
        t.targetOperation();

        Target t2 = new ConcreteTarget();
        t2.targetOperation();

        Vector vector = new Vector();
        Enumeration elements = vector.elements();
        /**
         * EnumerationIterator作为一个适配器，使Enumeration具有Iterator的遍历方法
         */
        EnumerationIterator enumerationIterator = new EnumerationIterator(elements);

        String path = "D://test/123.txt";
        try {
            final InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(path));

            log.info("inputStreamReader.read()={}", inputStreamReader.read());

            final FileReader fileReader = new FileReader(path);
            log.info("fileReader.read()={}", fileReader.read());
        } catch (Exception e) {


        }
    }


    /**
     * 模板模式
     */
    @Test
    public void test() {
        MotorVehicle ford = new Ford();
        ford.run(15);

        MotorVehicle hummer = new Hummer();
        hummer.run(36);

        hummer.setAlarm(true);
        hummer.run(22);


    }

    @Test
    public void decorator() {
        Beverage tea = new Tea();
        tea = new Sugar(tea);
        tea = new Milk(tea);
        log.info("tea cost={}", tea.cost());

        Beverage coffe = new Coffe();
        coffe = new Sugar(coffe);
        coffe = new Milk(coffe);
        coffe = new Whip(coffe);
        log.info("coffe cost={}", coffe.cost());
        String path = "D://test/123.txt";
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream("test".getBytes());
        try {
            FilterInputStream filterInputStream = new BufferedInputStream(byteArrayInputStream);

            final BufferedInputStream bufferedInputStream = new BufferedInputStream(new FileInputStream(path));
            log.info("BufferedInputStream支持mark和reset,mark={}", bufferedInputStream.markSupported());
            bufferedInputStream.mark(0);
            final char read = (char) bufferedInputStream.read();
            log.info("read={}", read);
            bufferedInputStream.reset();

            bufferedInputStream.mark(0);
            final char read2 = (char) bufferedInputStream.read();
            log.info("read2={}", read2);
            //ByteArrayInputStream
        } catch (Exception e) {
            e.printStackTrace();
        }
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
    public void testObserveStrategy() {


        Criminal criminal = new Criminal();
        Police police = new Police(criminal);
        /**
         * 被观察者添加需要监听自己的观察者
         *  criminal.addObserver(police);
         criminal.addObserver(new Soldier());
         */
        new Soldier();

        criminal.sleep("十分钟");
        criminal.setAge(19);
        /**
         * spring 提供的观察者和监听者
         */
        Demon demon = new Demon();
        demon.addObserver(new CIA());
        demon.eat("banana");


    }

    /**
     * 工厂模式优点：
     * 1、良好的封装，屏蔽了创建对象的细节，不需要关心对象是怎么创建出来的
     * 2、解耦
     * 3、扩展性好，要产生新的对象，扩展一下工厂就可以了
     *
     * 一个工厂有多个车间，一个车间有多条生产线，每一条生产线生产的产品是固定的
     * 可以理解为，（工厂+车间）是一个细粒度的工厂
     */

}
