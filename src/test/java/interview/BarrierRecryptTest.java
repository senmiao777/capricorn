package interview;

import com.alibaba.fastjson.JSON;
import com.frank.entity.mysql.User;
import com.frank.util.RandomNum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Test;

import java.util.Base64;

@Slf4j
public class BarrierRecryptTest {
    /**
     * 字符串压缩，也可以作为一种加密方式
     * 原理，每两个字符压缩成一个字符
     * 比如原字符串是，ae，获取a的ASCII码值，97,转换为二进制1100001
     * 获取e的ASCII码值，101,转换为二进制1100101(不够8位前边补0)
     * 1100001  + 01100101 -> 110000101100101
     * 将110000101100101转换为十进制得到24933，获取24933的ascii码值，得到字符XXXX
     */
    @Test
    public void ys(){
        String mnw = "12j3jhk1s-12h3-12j3h712-as2h-23Oas";
        System.out.println("压缩前：" + mnw + "，长度： " + mnw.length());
        String miw = yasuo(mnw);
        System.out.println("压缩后："+miw +"，长度："+ miw.length());
        String hy = jem(miw);
        System.out.println("还原后："+hy +"，长度："+ hy.length());
    }

    private String yasuo(String mnw) {
        StringBuilder sb = new StringBuilder();
        int index = 0;
        for (index = 1; index < mnw.length(); index += 2) {
            char c1 = mnw.charAt(index - 1);
            char c2 = mnw.charAt(index);
            sb.append((char) (((int) c1 << 8) + (int) c2));
        }
        if (index == mnw.length()) {
            sb.append(mnw.charAt(index - 1));
        }
        return sb.toString();
    }

    private String jem(String miw) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < miw.length(); i++) {
            int c = miw.charAt(i);
            String binaryString = Integer.toBinaryString(c);
            if (binaryString.length() < 8) {
                sb.append((char) c);
                continue;
            }
            int mnwi1 = Integer.parseInt(binaryString.substring(0, binaryString.length() - 8), 2);
            int mnwi2 = Integer.parseInt(binaryString.substring(binaryString.length() - 8), 2);
            sb.append((char) mnwi1);
            sb.append((char) mnwi2);
        }
        return sb.toString();
    }



    @Test
    public void findSingleNumber() {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 3, 4, 5, 6, 7, 8, 1};
        int i = singleNumber(nums);
        log.info("单数为{}", i);
    }

    private int singleNumber(int[] nums) {

        int result = 0;
        for (int i = 0; i < nums.length; i++) {
            result = result ^ nums[i];
        }
        return result;
    }

    /**
     * 根据一个数抑或另一个数操两次得到本身，进行简单的加密
     * Exclusive-OR
     * <p>
     * N^0 = N ，N^N = 0 （想想为什么）
     * <p>
     * <p>
     * 异或满足结合率 A^B^C = A^(B^C) 和交换律A^B = B^A
     * 所以A^B^B = A^(B^B) = A^0 = A
     */
    @Test
    public void testXOR() {
        char two = 'a';
        char e = 'e';
        log.info("ascii 值为{},{}",Integer.valueOf(two),Integer.valueOf(e));
        //String s = "https://blog.csdn.net/qq_30054961/article/details/82456069?utm_medium=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param&depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-1.channel_param";
        String s = JSON.toJSONString(User.generateUser());
        System.out.println("明文是=" + s);

        char[] chars = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            chars[i] = (char) (s.charAt(i) ^ (i<<2));
        }
        String s3 = new String(chars);
        System.out.println("异或之后=" + s3);

        char[] chars2 = new char[s3.length()];
        for (int i = 0; i < s3.length(); i++) {
            chars2[i] = (char)(s3.charAt(i) ^ (i<<2));
        }
        String s4 = new String(chars2);
        System.out.println("两次异或之后=" + s4);
        log.info("两次异或之后 equals={}", s4.equals(s));

        String afterBase64 = Base64.getEncoder().withoutPadding().encodeToString(s3.getBytes());
        log.info("afterBase64={}", afterBase64);
        byte[] decode = Base64.getDecoder().decode(afterBase64.getBytes());

        String s5 = new String(decode);
        log.info("s5={},抑或之后的密文eq={}", s5, s5.equals(s3));
        char[] chars5 = new char[s5.length()];
        for (int i = 0; i < s5.length(); i++) {
            chars5[i] = (char) (s5.charAt(i) ^ (i<<2));
        }
        String s6 = new String(chars5);

        log.info("两次异或之后 equals={}", s6.equals(s));

    }

    @Test
    public void testBarrierEncrypt() throws Exception {
        String plain = "helloworldencrypttestabcdefghijk";
        String ciphertext2 = encode2(plain, 11);
        System.out.println("密文=" + ciphertext2);
        System.out.println("明文=" + decode2(ciphertext2, 11));

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
     * 共columnNumber列,例子中columnNumber=10
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
