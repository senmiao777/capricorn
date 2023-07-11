import java.util.concurrent.locks.Condition;

public class Test {

    public static void main(String[] args) {


        Thread one = new Thread(() -> {
            System.out.println("one exec");
        });
        one.start();

    }
}
