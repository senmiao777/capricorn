package interview.math;

import org.junit.Test;

public class PowerOfFour {


    @Test
    public void testIsPowerOfTwo() {

    }


    public boolean isPowerOfTwo(int n) {
        if(n <=0){
            return false;
        }

        return true;

    }
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

    /**
     * n & (n-1) 直接将十进制数字 n 对应的二进制表示的最低位 1 移除
     *
     * 假设 n 的二进制表示为 (a10⋯0) 2
     * 其中 a 表示若干个高位，1 表示最低位的那个 1，00⋯0 表示后面的若干个 0，那么 n−1 的二进制表示为：(a01⋯1)2
     * 我们将(a10⋯0)2与 (a01⋯1)2 进行按位与运算，高位 a 不变，在这之后的所有位都会变为 0，这样我们就将最低位的那个 1 移除了。
     *
     * n & (-n) 该位运算技巧可以直接获取十进制数字 n 对应的二进制表示的最低位的 1。
     *
     * 由于负数是按照补码规则在计算机中存储的，−n 的二进制表示为 n 的二进制表示的每一位取反再加上 1，因此它的原理如下：
     *
     * 假设 n 的二进制表示为 (a10⋯0)2 ，其中 a 表示若干个高位，1 表示最低位的那个 1，00⋯0 表示后面的若干个 0
     * 则 - n的二进制表示为 (A01⋯1)2 + (1)2 = (A10⋯0)2
     * 将两者进行按位与运算，高位全部变为 0，最低位的 1 以及之后的所有 0 不变，这样我们就获取了 n 二进制表示的最低位的 1。
     * @param n
     * @return
     */
    public boolean powerOf2(int n) {
        return (n > 0) && ((n & n - 1) == 0);
    }

    /**
     * 把int范围内2的幂全量枚举一遍
     * @param n
     * @return
     */
    public boolean powerOf22(int n) {
        if(n<=0){
            return false;
        }
        for(int i=0;i<32;i++){
            if((1<<i) == n){
                return true;
            }
        }
        return false;
    }
}
