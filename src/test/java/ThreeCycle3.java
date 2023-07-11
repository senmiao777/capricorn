import java.util.concurrent.Semaphore;

public class ThreeCycle3 implements Runnable {
    Semaphore pre;
    Semaphore self;
    String name;
    static int count = 30;

    public ThreeCycle3(String name) {
        this.name = name;
    }


    public ThreeCycle3(Semaphore pre, Semaphore self, String name) {
        this.pre = pre;
        this.self = self;
        this.name = name;
    }

    @Override
    public void run() {

        for (int i = 0; i < 4; i++) {
            try {
                pre.acquire();
                System.out.println(name);
                self.release();

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
    public static void main(String[] args) throws InterruptedException {
        Semaphore semaphore1 = new Semaphore(0);
        Semaphore semaphore2 = new Semaphore(0);
        Semaphore semaphore3 = new Semaphore(1);
        new Thread(new ThreeCycle3(semaphore1, semaphore2, "B")).start();
        new Thread(new ThreeCycle3(semaphore2, semaphore3, "C")).start();
        new Thread(new ThreeCycle3(semaphore3, semaphore1, "A")).start();


    }
}
