package com.frank.model;

/**
 * 位图
 **/
public class BitMap {
    private char[] bytes;

    /**
     * 位图具有的位数
     */
    private int count;

    public BitMap(int count) {
        this.count = count;
        bytes = new char[count / 8 + 1];
    }

    /**
     * 将数字k存入位图
     *
     * @param k 待存入位图的数字
     */
    public void set(int k) {
        if (k > count) {
            throw new RuntimeException("out of range");
        }
        /**
         * char类型，每一个元素占1字节，8位
         * 求数字k在数组的哪个下标，固定对8取整
         * 求数字k在当前下标的哪一位，固定对8取余
         */
        int byteIndex = k / 8;
        int bitIndex = k % 8;
        /**
         *  bytes[byteIndex] = (bytes[byteIndex]) | (1 << bitIndex);
         *  这么写有类型提升，需要强转 (char) ((bytes[byteIndex])
         *
         *  bytes[byteIndex] |= (1 << bitIndex) 没有问题
         *
         */
        bytes[byteIndex] |= (1 << bitIndex);
    }

    /**
     * 判断数字K是否存在于位图内
     *
     * @param k 待判断数字
     * @return true:存在;false:不存在
     */
    public boolean exist(int k) {
        if (k > count) {
            throw new RuntimeException("out of range");
        }

        int byteIndex = k / 8;
        int bitIndex = k % 8;
        return (bytes[byteIndex] & (1 << bitIndex)) != 0;
    }
}
