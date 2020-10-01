package interview;

import com.frank.util.RandomNum;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

public class BarrierRecryptTest {

    @Test
    public void testBarrierEncrypt() throws Exception {

     /*   String ciphertext = encode1(plaintext, 11);
        System.out.println("密文=" + ciphertext);
        String ciphertext2 = encode2(plaintext, 11);
        System.out.println("密文=" + ciphertext2);
        System.out.println("明文=" + decode2(ciphertext, 11));*/

        for (int i = 3; i < 20; i++) {
            String plaintext = RandomNum.createRandomString(RandomUtils.nextInt(10, 200));
            System.out.println("明文=" + plaintext);


            String ciphertext3 = encode2(plaintext, i);
            // System.out.println("密文=" + ciphertext3);
            String s = decode2(ciphertext3, i);
            System.out.println("equals=" + s.equals(plaintext));
        }


        long l = System.currentTimeMillis();
        String plaintext = "CL1L9AJTTgC9Hjms1Xx0cadNlslKQf5ExVJSh7Cpvtr2DBlHvuYEJrtEWaqnIiQikEWlEc5fWwlmFsgYRt4NdXpxApw1JjEDTmDC1MBfNVkGJ2tngY539MuWNJGv1i3sQ1bK59ez9tdxvvFGH7WlhB";
        String s = encode1(plaintext, 15);
        for (int i = 0; i < 1000000; i++) {
            decode2(s, 15);
        }
        long l2 = System.currentTimeMillis();

        System.out.println("decode2耗时=" + (l2 - l));


    }

    /**
     * 栅栏解密
     * 加密的时候有几列，解密的时候就有几行
     * 竖着读
     * <p>
     * hetj
     * enak
     * lcb
     * lrc
     * oyd
     * wpe
     * otf
     * rtg
     * leh
     * dsi
     */
    private String decode2(String ciphertext, int lineNumber) {
        int length = ciphertext.length();
        char[] result = new char[length];
        int columnNumber = length / lineNumber;
        /**
         * 如果余数大于零，即为能填满列数的元素个数
         */
        int mod = length % lineNumber;
        if (mod > 0) {
            columnNumber = columnNumber + 1;
        }
        int columnMinusOne = columnNumber - 1;
        int currentPosition = 0;
        int currentColumn = 0;
        int currnetLine = 0;

        for (int i = 0; i < length; i++) {
            if (mod == 0) {
                result[currentPosition] = ciphertext.charAt(currentColumn + currnetLine * columnNumber);
            } else {
                if (currnetLine <= mod) {
                    result[currentPosition] = ciphertext.charAt(currentColumn + currnetLine * columnNumber);
                } else {
                    result[currentPosition] = ciphertext.charAt(currentColumn + currnetLine * columnMinusOne + mod);
                }
            }
            currentPosition++;
            currnetLine++;
            if (currnetLine == lineNumber) {
                currnetLine = 0;
                currentColumn++;
            }
        }
        return new String(result);
    }

    /**
     * 栅栏解密
     * 此方法经过测试改进之后还是有漏洞，仅用于记录错误思路
     * 在错误的思路上不断的打补丁，本身就是错误的，正确的做法是换思路。
     */
    private String decode1(String ciphertext, int columnNumber) {
        int length = ciphertext.length();
        char[] result = new char[length];
        int line = length / columnNumber;

        int index = 0;
        int columnMinusOne = columnNumber - 1;
        int i1 = length % columnNumber;
        for (int j = 0; j < columnNumber; j++) {
            for (int i = 0; i <= line; i++) {
                if (i1 > 0) {
                    if (i <= i1) {
                        result[index] = ciphertext.charAt(j + columnNumber * i);
                    } else {
                        result[index] = ciphertext.charAt(j + columnMinusOne * i + i1);
                    }
                } else {
                    if (i == line) {
                        continue;
                    }
                    result[index] = ciphertext.charAt(j + columnNumber * i);
                }

                index++;
                if (index == length) {
                    return new String(result);
                }
            }
        }
        return new String(result);
    }

    /**
     * 栅栏加密
     * 共columnNumber列
     * 逻辑上：读取明文字符，依次填充第一行第一列，第一行第二列，直到达到第一行第columnNumber列
     * 字符串还有剩余字符，则跳到下一行，重复上一步操作，然后竖着读取,得到的结果即为密文
     * 根据这个规律， 计算出每个字符在密文中的位置
     * <p>
     * helloworld
     * encrypttes
     * tabcdefghi
     * jk
     * 这个方法好理解
     */
    private String encode2(String plaintext, int columnNumber) {
        int length = plaintext.length();
        char[] result = new char[length];
        /**
         * 明文下标
         */
        int index;
        /**
         * 密文下标
         */
        int currentPosition = 0;
        for (int i = 0; i < columnNumber; i++) {
            index = i;
            while (index < length) {
                result[currentPosition] = plaintext.charAt(index);
                index = index + columnNumber;
                currentPosition++;
            }
        }
        return new String(result);
    }


    /**
     * 栅栏加密
     */
    private String encode1(String plaintext, int columnNumber) {
        int length = plaintext.length();
        char[] result = new char[length];
        // 行数
        int line = length / columnNumber;

        /**
         * 余数，不需要补位的个数
         */
        int i1 = length % columnNumber;
        int index = 0;

        for (int j = 0; j < columnNumber; j++) {
            for (int i = 0; i <= line; i++) {
                /**
                 * 最后一行需要判断是否空位
                 * 有空位直接跳过
                 */
                if (i == line) {
                    if (i1 > 0) {
                        i1--;
                    } else {
                        continue;
                    }
                }
                result[index] = plaintext.charAt(j + columnNumber * i);
                index++;
            }
        }
        return new String(result);
    }
}
