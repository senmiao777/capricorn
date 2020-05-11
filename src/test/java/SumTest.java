import org.apache.commons.lang3.math.NumberUtils;

import java.math.BigDecimal;
import java.util.Scanner;

public class SumTest {

    public static void main(String[] args) {
        System.out.println("请输入两个数字，每个输入完按回车");
        Scanner scan = new Scanner(System.in);
        BigDecimal number1 = getNumber(scan, 1);
        BigDecimal number2 = getNumber(scan, 2);
        System.out.println("两个数的和为" + number1.add(number2));
    }

    private static BigDecimal getNumber(Scanner scan, int i) {
        System.out.println("请输入第" + i + "个数字:");
        while (scan.hasNextLine()) {
            String value = scan.nextLine();
            if(NumberUtils.isParsable(value)){
                return new BigDecimal(value);
            } else {
                System.out.println("请输入正确的数字后按回车键");
            }
        }
        return null;
    }

}
