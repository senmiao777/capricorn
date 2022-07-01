package interview;

import com.frank.model.leetcode.stack.MinStack;
import com.frank.model.leetcode.stack.MyQueue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author: sb
 * @time: 2018-10-11 10:25
 */
@Slf4j
public class Stack {


    public List<Integer> minIndex = Lists.newArrayList();
    public List<Integer> data = Lists.newArrayList();

    @Test
    public void testMyQueue(){
        MyQueue myQueue = new MyQueue();
        myQueue.push(1); // queue is: [1]
        myQueue.push(2); // queue is: [1, 2] (leftmost is front of the queue)
        myQueue.peek(); // return 1
        myQueue.pop(); // return 1, queue is [2]
        myQueue.empty(); // return false
    }


    @Test
    public void testMinStack() {
        MinStack minStack = new MinStack();
        minStack.push(-1);
      /*  minStack.push(0);
        minStack.push(-3);

       int res =  minStack.getMin(); // return -3
        minStack.pop();*/
        minStack.top();    // return 0
        int min = minStack.getMin();// return -2
        System.out.println("min ="+min);
    }



    @Test
    public void testDetermineValid() {
        int a = 0;
        int b = 0;
        int c = 10;
        int d = 10;
        if ((c = a--) < 0) {
            System.out.println("11");
        }
        if ((d = --b) < 0) {
            System.out.println("222");
        }

        System.out.println(a + " , " + b + "," + c + "," + d);
        String str = "()[]{}";
        System.out.println("determineValid res= " + determineValid(str));
    }

    /**
     * Given a string s containing just the characters '(', ')', '{', '}', '[' and ']', determine if the input string is valid.
     * An input string is valid if:
     * Open brackets must be closed by the same type of brackets.
     * Open brackets must be closed in the correct order.
     *
     * @param str
     * @return true:if valid ; false: if not
     */
    private boolean determineValid(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        int length = str.length();
        // 奇数，肯定不符合规则
        if (length % 2 != 0) {
            return false;
        }
        int len = length / 2;
        char[] stack = new char[len];
        // 当前下标
        int index = 0;
        char c;
        for (int i = 0; i < length; i++) {
            c = str.charAt(i);
            if (isLeft(c)) {
                // 索引过半，出现左括号，一定不符合规则
                if (index >= len) {
                    return false;
                }
                // 注意a ++ 和 ++ a 区别
                stack[index++] = c;
            } else {
                // 如果给其他变量赋值的话，这里用index-- ,--index 都有问题，因为都已经改变了index的值
                // 数组前一个下标
                if (--index < 0 || stack[index] != getLeft(c)) {
                    return false;
                }
            }
        }
        return index == 0;
    }

    /**
     * 是否是左括号
     *
     * @param c 字符
     * @return true: 是左括号；false: 不是左括号；
     */
    private boolean isLeft(char c) {
        return c == '{' || c == '[' || c == '(';
    }


    private char getLeft(char c) {
        if (c == ')') {
            return '(';
        } else if (c == '}') {
            return '{';
        } else {
            return '[';
        }
    }


    /**
     * 设计一个栈结构，有获取栈的最小值功能
     * 思路：一个数据栈，一个最小值位置索引栈
     */
    @Test
    public void test() {
        for (int i = 0; i < 15; i++) {
            final int element = RandomUtils.nextInt(0, 100);
            push(element);
        }
        log.info("data={}", data);
        log.info("minIndex={}", minIndex);
        for (int i = 0; i < 5; i++) {
            try {
                final Integer min = getMin();
                log.info("min={}", min);
                final Integer pop = pop();
                log.info("pop={}", pop);
                log.info("after pop data={}", data);
                log.info("after pop minIndex={}", minIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Integer pop() throws Exception {
        if (data.size() == 0) {
            throw new Exception("栈为空");
        }

        // 栈顶元素坐标
        final int topIndex = data.size() - 1;

        final Integer remove = data.remove(topIndex);
        // 栈顶元素为最小值，索引出栈
        if (topIndex == minIndex.get(minIndex.size() - 1).intValue()) {
            minIndex.remove(minIndex.size() - 1);
        }
        return remove;
    }

    public void push(Integer member) throws NullPointerException {
        if (member == null) {
            throw new NullPointerException("入栈元素不能为空");
        }


        // 第一个元素入栈，最小值索引位置为0
        if (data.size() == 0) {
            data.add(member);
            minIndex.add(0);
        } else {
            data.add(member);
            final Integer preMinMember = data.get(minIndex.get(minIndex.size() - 1));
            // 当前值小于之前的最小值，索引栈保存当前数据项索引位置
            if (preMinMember > member) {
                minIndex.add(data.size() - 1);
            }
        }

    }

    public Integer getMin() throws Exception {
        if (data.size() == 0) {
            throw new Exception("栈为空");
        }
        return data.get(minIndex.get(minIndex.size() - 1));
    }
}
