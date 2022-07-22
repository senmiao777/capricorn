package interview.string;

import org.junit.Test;

/**
 * @author: A + RT
 * @description:
 * @date: 2022/7/22
 **/
public class IP2Long {

    @Test
    public void testIp2Long() {

        // 百度的ip地址
        String ip = "110.242.68.66";
        long value = ip2Long(ip);
        System.out.println(ip + "转换为long=" + value);

        String rawIP = long2Ip(value);
        System.out.println(value + "转换为String=" + rawIP);

    }

    /**
     * 将String类型ip转为long类型
     * IP的每个段都是0~255之间的数字，255用2的8次方，即8位就可以存下，四段总共需要32位
     * 因为java 里的int 是有符号的，第一位为符号位，实际的数据存储位只有31位，所以用int存不下32位，需要用long类型
     *
     * @param ip ip字符串
     * @return ip字符串转换后的long类型值
     */
    private long ip2Long(String ip) {
        // 不考虑IP字符串异常情况
        final String[] numbers = ip.split("\\.");
        return (Long.parseLong(numbers[0]) << 24)
                + (Long.parseLong(numbers[1]) << 16)
                + (Long.parseLong(numbers[2]) << 8)
                + Long.parseLong(numbers[3]);
    }

    /**
     * long类型ip转换为String类型字符串
     *
     * @param ip long类型ip值
     * @return String类型ip字符串
     */
    private String long2Ip(long ip) {
        return (ip >> 24) + "."
                + ((ip & 0xFF0000) >> 16) + "."
                + ((ip & 0xFF00) >> 8) + "."
                + (ip & 0xFF);

    }
}
