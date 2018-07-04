import com.frank.designpattern.Ford;
import com.frank.designpattern.Hummer;
import com.frank.designpattern.MotorVehicle;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2018/7/2 0002 下午 10:08
 */
@Slf4j
public class DesignPatternTest {
    @Test
    public void test() {
        MotorVehicle ford = new Ford();
        ford.run();

        MotorVehicle hummer = new Hummer();
        hummer.run();

        hummer.setAlarm(true);
        hummer.run();

    }
}
