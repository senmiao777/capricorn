import lombok.extern.slf4j.Slf4j;
        import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2018/2/4 0021 下午 4:18
 */

@Slf4j
public class ClassTest {
    @Test
    public void testClazzCode() {
        int a = 100;
        int b = 110;
        int c = 120;
        int d = (a + b) * c;
        log.info("a={},b={},c={},d={}", a, b, c, d);
    }
}
