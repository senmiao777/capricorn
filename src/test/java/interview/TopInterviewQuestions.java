package interview;

import com.frank.enums.Common;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

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
        ListNode l1 = new ListNode(2);

        ListNode l2 = new ListNode(4);
       // ListNode l3 = new ListNode(3);
        l1.setNext(l2);
     //   l2.setNext(l3);

        ListNode l4 = new ListNode(5);
        ListNode l5 = new ListNode(6);
        ListNode l6 = new ListNode(4);
        l4.setNext(l5);
        l5.setNext(l6);

        int sum = getNumber(l1) + getNumber(l4);
        log.info("sum={}", sum);
        ListNode head = new ListNode(sum % 10);
        sum = sum / 10;
        ListNode current = head;
        //ListNode t;
        ListNode currentNode = head ;
        while (sum / 10 > 0 || sum % 10 >0) {
            ListNode t = new ListNode(sum % 10);
            currentNode.setNext(t);
            currentNode = t;
            sum = sum /10;
        }
        ListNode next = head;
       do{

           log.info("{}->",next.val);
           next = next.getNext();
       }while (next !=null);
        log.info(Common.LOG_END.getValue());
    }

    private int getNumber(ListNode currentNode) {
        StringBuilder numberString = new StringBuilder();
        ListNode nextNode;
        do {
            nextNode = currentNode.getNext();
            numberString.append(currentNode.getVal());
            currentNode = nextNode;
        } while (nextNode != null);
        return Integer.valueOf(numberString.toString());
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

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

