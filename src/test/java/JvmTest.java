import org.junit.Test;

/**
 * @author frank
 * @version 1.0
 * @date 2021/6/5 0005 上午 10:37
 */
public class JvmTest {
    @Test
    public void testJvmStack(){
        int fs =0;
        System.out.println("operation result="+math());
    }

    public int math(){
        int a = 7;
        int b = 8;
        int c = (a + b) * 10;
        return c;
    }

}
