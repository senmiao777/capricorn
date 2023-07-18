import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;

public class Test {

    static {
        System.out.println("父类 static test ");
    }

    {
        System.out.println("父类 构造代码块 创建类时执行 test ");
    }

    public Test() {
        System.out.println("父类 构造方法 test ");

    }





    public static void main(String[] args) throws InterruptedException {
        class Sub implements Runnable{
            String name;
            CountDownLatch latch;

            public Sub(String name, CountDownLatch latch) {
                this.name = name;
                this.latch = latch;
            }

            @Override
            public void run() {
                System.out.println(name);
                latch.countDown();
            }


        }
        CountDownLatch latch  =new CountDownLatch(2);

        new Thread(new Sub("a",latch)).start();
        new Thread(new Sub("b",latch)).start();
        latch.await();
        System.out.println("main");

    }




}
