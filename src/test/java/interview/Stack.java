package interview;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.assertj.core.util.Lists;
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
