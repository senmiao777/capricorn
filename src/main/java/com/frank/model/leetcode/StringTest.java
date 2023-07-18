package com.frank.model.leetcode;

public class StringTest {

    /**
     * BM84 最长公共前缀
     *
     * @param strs
     * @return
     */
    public String longestCommonPrefix(String[] strs) {
        // write code here
        int length = strs.length;
        if (length == 0) {
            return "";
        }

        for (int i = 0; i < strs[0].length(); i++) {
            char temp = strs[0].charAt(i);
            for (int j = 1; j < length; j++) {
                /**
                 * 找到其他字符串的边界 或者 和当前位置的字符不相同，返回
                 */
                if (i == strs[j].length() || temp != strs[j].charAt(i)) {
                    return strs[0].substring(0, i);
                }

            }
        }
        return strs[0];

    }
}
