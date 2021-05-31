package interview.math;

import org.junit.Test;

public class PowerOfFour {
    @Test
    public void isPowerOfFour() {
        int n = 1073741824;
        System.out.println("res=" +(1<<6));
    }

    public boolean powerOfFour(int number) {
        if (number < 0) {
            return false;
        }
        int temp;
        while ((number >= 4) && (number % 4 == 0)) {
            number = number / 4;
        }
        if (number > 4) {
            return false;
        }
        return number == 1;
    }


    public boolean powerOfFour2(int number) {
        if (number <= 0) {
            return false;
        }
        int temp = 4;
        while (number % temp == 0 && number > temp) {
            temp *= 4;
        }
        return (number == 1) || (number == temp);
    }

    /**
     * 可以构造一个整数 mask，它的所有偶数二进制位都是 0，所有奇数二进制位都是 1。
     * 这样一来，我们将 n 和 mask 进行按位与运算，如果结果为 0，说明 n 二进制表示中的 1 出现在偶数的位置，否则说明其出现在奇数的位置。
     * <p>
     * 根据上面的思路，\textit{mask}mask 的二进制表示为：
     * mask=(10101010101010101010101010101010)  java中是0b开头表示二进制0b10101010101010101010101010101010
     * <p>
     * 我们也可以将其表示成 1616 进制的形式，使其更加美观：
     * mask=(AAAAAAAA) 16 Java中是0x开头表示16进制0xaaaaaaaa
     * 链接：https://leetcode-cn.com/problems/power-of-four/solution/4de-mi-by-leetcode-solution-b3ya/
     */
    public boolean powerOf4(int n) {
        //return (n > 0) && ((n & n - 1) == 0) && ((n & 0xaaaaaaaa) == 0);
        return (n > 0) && ((n & n - 1) == 0) && ((n & 0b10101010101010101010101010101010) == 0);

    }
}
