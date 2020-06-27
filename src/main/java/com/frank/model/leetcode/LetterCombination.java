package com.frank.model.leetcode;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author frank
 * @version 1.0
 * @date 2020/6/27 0027 下午 8:42
 */
@Slf4j
@NoArgsConstructor
public class LetterCombination {
    public static Map<String, String> config;

    public List<String> letterCombinations(String digits) {
        List<String> result = new ArrayList<>();
        recursion(result,digits,new StringBuilder());
        return result;
    }

    /**
     * 怎么知道到了最后一层了
     *
     * @param result
     * @param remain
     * @param sb
     */
    private void recursion(List result, String remain, StringBuilder sb) {

        if (remain.length() == 0) {
            result.add(sb.toString());
            return;
        }

        String currentNumber = remain.substring(0, 1);
        String numberString = getNumberString(currentNumber);
        for(int i = 0;i< numberString.length();i++){
//            sb.append(numberString.substring(i,i+1));
//            result.add(sb.toString());
//            sb.deleteCharAt(sb.length() -1);
            recursion(result,remain.substring(1),new StringBuilder(sb).append(numberString.charAt(i)));
        }



    }


    static {
        config = new HashMap<>(16);
        config.put("2", "abc");
        config.put("3", "def");
        config.put("4", "ghi");
        config.put("5", "jkl");
        config.put("6", "mno");
        config.put("7", "pqrs");
        config.put("8", "tuv");
        config.put("9", "wxyz");
    }

    private String getNumberString(String number) {
        return config.get(number);
    }
}
