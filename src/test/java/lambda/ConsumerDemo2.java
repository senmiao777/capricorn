package lambda;

import org.junit.Test;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsumerDemo2 {

    public static void printInfo(String[] strArr, Consumer<String> con1, Consumer<String> con2) {

        for (int i = 0; i < strArr.length; i++) {
            //con1.andThen(con2).accept(strArr[i]);
            con1.andThen(con2).accept(strArr[i]);

        }

    }

    public static void main(String[] args) {
        String[] strArr = {"迪丽热巴,女", "郑爽,女", "杨紫,女"};
        printInfo(strArr, (message) -> {
            System.out.print("姓名:" + message.split(",")[0]);
        }, (message) -> {
            System.out.println(",性别:" + message.split(",")[1]);

        });
    }

    @Test
    public void test(){
        StringBuilder sb = new StringBuilder("Hello ");
        Consumer<StringBuilder> consumer = (str) -> str.append("lambda");
        /*consumer.accept(sb);
        System.out.println(sb);*/

        Consumer<StringBuilder> consumerAppend = s -> s.append(" and then operate");
        consumer.andThen(consumerAppend).accept(sb);
        System.out.println(sb);
    }

    @Test
    public void testBiConsumer(){

        StringBuilder sb = new StringBuilder();
        BiConsumer<String, String> biConsumer = (a, b) -> {
            sb.append(a);
            sb.append(b);
            a = a + "789";
            System.out.println("a="+a+",b="+b);

        };
//        biConsumer.accept("Hello ", "Jack!");
//        System.out.println(sb);

        BiConsumer<String,String> biConsumer1 = (x,y) ->{
            sb.append("testst");
            x = x + "123";
            y = y + "456";
            System.out.println("x="+x+",y="+y+",sb="+sb);
        };

        biConsumer.andThen(biConsumer1).accept("hello","lambda");


    }

}
