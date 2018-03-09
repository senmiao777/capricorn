package interview;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author frank
 * @version 1.0
 * @date 2018/3/9 0004 下午 18:50
 */
@Slf4j
public class TopInterviewQuestions {
    @Test
    public void addTwoNumbers() {
        log.info(Common.LOG_BEGIN.getValue());
        List<Integer> firstList = new ArrayList<>();
        List<Integer> secondList = Lists.newArrayList(5,6,4);

        StringBuilder firstNumberString = new StringBuilder();

        ListNode l1 = new ListNode(2);

        ListNode l2 = new ListNode(4);
        ListNode l3 = new ListNode(3);
        l1.setNext(l2);
        l2.setNext(l3);

        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(4);
        l4.setNext(l5);
        l5.setNext(l6);

        ListNode currentNode;
        ListNode nextNode;

        int sum = getNumber(l1)+getNumber(l4);
//        currentNode = l1;
//
//        do{
//            nextNode = currentNode.getNext();
//
//            firstNumberString.append(currentNode.getVal());
//            currentNode = nextNode;
//
//        }while(nextNode!=null);
//
//        Integer firstNumber = Integer.valueOf(firstNumberString.toString());
//
//
//
//
//
//
//        StringBuilder secondNumberString = new StringBuilder();
//        currentNode = l4;
//        do{
//            nextNode = currentNode.getNext();
//
//            secondNumberString.append(currentNode.getVal());
//            currentNode = nextNode;
//
//        }while(nextNode!=null);
//
//        Integer secondNumber = Integer.valueOf(secondNumberString.toString());
//
//        int sum = firstNumber + secondNumber;
        log.info("sum={}",sum);

        log.info(Common.LOG_END.getValue());
    }

    private int getNumber(ListNode currentNode){
        StringBuilder numberString = new StringBuilder();
       // ListNode currentNode = node;
        ListNode nextNode;
        do{
            nextNode = currentNode.getNext();
            numberString.append(currentNode.getVal());
            currentNode = nextNode;
        }while(nextNode!=null);
        return Integer.valueOf(numberString.toString());
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

//      public int getVal(){
//          return this.val;
//      }
//
//      public ListNode getNext(){
//          return this.next;
//      }

        public void setVal(int val) {
            this.val = val;
        }

        public void setNext(ListNode next) {
            this.next = next;
        }

        public int getVal() {
            return val;
        }

        public ListNode getNext() {
            return next;
        }
    }

}

