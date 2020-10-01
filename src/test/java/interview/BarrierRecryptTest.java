package interview;

import org.junit.Test;

public class BarrierRecryptTest {

    @Test
    public void testBarrierEncrypt() {
        String plaintext = "helloworldencrypttestabcd";
        String ciphertext = encode1(plaintext, 5);
        System.out.println("密文=" + ciphertext);

        System.out.println("明文=" + decode1(ciphertext, 5));


     /*   long l = System.currentTimeMillis();
        for (int i = 0; i < 10000000; i++) {
            String ciphertext2 = encode1(plaintext, 5);
            decode1(ciphertext2, 5);
        }
        System.out.println("耗时=" + (System.currentTimeMillis() - l));*/
    }

    /**
     * 栅栏解密
     */
    private String decode1(String ciphertext, int columnNumber) {
        int length = ciphertext.length();
        //char[] chars = ciphertext.toCharArray();
        char[] result = new char[length];
        int line = length / columnNumber;

        int index = 0;
        int columnMinusOne = columnNumber - 1;
        int i1 = length % columnNumber;
        for (int j = 0; j < columnNumber; j++) {
            for (int i = 0; i <= line; i++) {
                if (i1 > 0) {
                    if (i <= i1) {

                        //result[index] = chars[j + columnNumber * i];
                        result[index] = ciphertext.charAt(j + columnNumber * i);
                    } else {
                        // result[index] = chars[j + columnMinusOne * i + i1];
                        result[index] =ciphertext.charAt(j + columnMinusOne * i + i1);
                    }
                } else {
                    if (i == line) {
                        continue;
                    }
                    //result[index] = chars[j + columnNumber * i];
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
     */
    private String encode1(String plaintext, int columnNumber) {
        int length = plaintext.length();
        //char[] chars = plaintext.toCharArray();
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
                //result[index] = chars[j + columnNumber * i];
                result[index] = plaintext.charAt(j + columnNumber * i);
                index++;
            }
        }
        return new String(result);
    }
}
