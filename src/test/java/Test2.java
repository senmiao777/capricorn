public class Test2 extends Test{

    static {
        System.out.println("子类 static 静态代码块类加载时执行 test ");
    }

    {
        System.out.println("子类 构造代码块 创建类时执行 test ");
    }

    public Test2() {
        System.out.println("子类 构造方法 test ");

    }

    public static void main(String[] args) {


        Thread one = new Thread(() -> {
            System.out.println("one exec");
        });
        one.start();
        new Test2();
        new Test2();
        new Test2();
    }
}
