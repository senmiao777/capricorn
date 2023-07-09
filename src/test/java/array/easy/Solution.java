package array.easy;

import com.frank.model.leetcode.ListNode;

import java.util.*;

public class Solution {

    public static void main(String[] args) {
        ListNode six = new ListNode(6, null);
        ListNode five = new ListNode(5, six);
        ListNode four = new ListNode(4, five);
        ListNode three = new ListNode(3, four);
        ListNode two = new ListNode(2, three);
        ListNode one = new ListNode(1, two);
        ListNode listNode = oddEvenList(one);

    }


    /**
     * 删除有序链表中重复的元素-II
     * @param head
     * @return
     */
    public ListNode deleteDuplicates22 (ListNode head) {
        // write code here
        if (head == null || head.next == null) {
            return head;
        }

        // 建立一个哨兵节点
        ListNode dummy = new ListNode(-1);

        ListNode cur = dummy;
        dummy.next = head;
        int v;
        while (cur.next != null && cur.next.next != null) {
            if ((v = cur.next.val) == cur.next.next.val) {
                while (cur.next != null && cur.next.val == v) {
                    //靠这步下标后移
                    cur.next = cur.next.next;
                }
            } else {
                cur = cur.next;
            }
        }
        return dummy.next;
    }

    /**
     * 删除有序链表中重复的元素-I
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates2(ListNode head) {
        // write code here
        if (head == null) {
            return head;
        }
        ListNode cur = head;
        while ((cur != null) && (cur.next != null)) {
            if (cur.val == cur.next.val) {
                cur.next = cur.next.next;
            } else {
                cur = cur.next;
            }

        }
        return head;
    }


    /**
     * 删除有序链表中重复的元素-I
     *
     * @param head
     * @return
     */
    public ListNode deleteDuplicates(ListNode head) {
        // write code here
        if (head == null) {
            return head;
        }
        ListNode pre = head;
        ListNode h = head.next;
        while (h != null) {
            if (pre.val == h.val) {
                pre.next = h.next;
            } else {
                pre = pre.next;
            }
            h = h.next;
        }
        return head;
    }


    public static ListNode oddEvenList(ListNode head) {
        // write code here
        if (head == null) {
            return head;
        }


        ListNode odd = head;
        ListNode even = head.next;
        ListNode evenHead = even;
        while (even != null && even.next != null) {
            odd.next = even.next;
            odd = odd.next;
            even.next = odd.next;
            even = even.next;
        }

        odd.next = evenHead;
        return head;

    }

    /**
     * 判断一个链表是否为回文结构
     * 也可以用栈 出栈 和 链表从头遍历
     * 也可以找到链表的后半部分，反转
     *
     * @param head
     * @return
     */
    public boolean isPail(ListNode head) {
        // write code here
        if (head == null) {
            return false;
        }

        ListNode fast = head;


        ListNode cur = head;
        ListNode curNext = null;
        ListNode pre = null;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            curNext = cur.next;
            cur.next = pre;
            pre = cur;
            cur = curNext;

        }
        // fast 不为空，说明是奇数个 注意：这里是关键，奇数还需要在往前走一步
        if (fast != null) {
            cur = cur.next;
        }
        while (cur != null && pre != null) {
            if (cur.val != pre.val) {
                return false;
            }
            cur = cur.next;
            pre = pre.next;
        }
        return cur == null && pre == null;

    }

    /**
     * 链表排序-递归
     *
     * @param head
     * @return
     */
    public ListNode sortInList(ListNode head) {
        // write code here

        if (head == null || head.next == null) {
            return head;
        }
        ListNode slow = head;
        ListNode mid = head.next;
        ListNode fast = head.next.next;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            mid = mid.next;
        }
        slow.next = null;
        return merge(sortInList(head), sortInList(mid));
    }

    /**
     * 合并两个排序链表
     *
     * @param h1
     * @param h2
     * @return
     */
    public ListNode merge(ListNode h1, ListNode h2) {
        if (h1 == null) {
            return h2;
        }
        if (h2 == null) {
            return h1;
        }

        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        while (h1 != null && h2 != null) {
            if (h1.val <= h2.val) {
                head.next = h1;
                h1 = h1.next;
            } else {
                head.next = h2;
                h2 = h2.next;
            }
            head = head.next;
        }

        if (h1 != null) {
            head.next = h1;
        }
        if (h2 != null) {
            head.next = h2;
        }
        return dummy.next;
    }


    public ListNode addInList3(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        Stack<ListNode> s1 = new Stack<>();
        Stack<ListNode> s2 = new Stack<>();
        while (head1 != null) {
            s1.push(head1);
            head1 = head1.next;
        }
        while (head2 != null) {
            s2.push(head2);
            head2 = head2.next;
        }
        int flag = 0;

        ListNode head = null;
        while (!s1.isEmpty() || !s2.isEmpty() || flag > 0) {
            int v1 = s1.isEmpty() ? 0 : s1.pop().val;
            int v2 = s2.isEmpty() ? 0 : s2.pop().val;
            int t = v1 + v2 + flag;
            flag = t / 10;
            ListNode cur = new ListNode(t % 10);
            cur.next = head;
            head = cur;
        }
        return head;

    }

    public ListNode addInList2(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        // write code here
        ListNode h1 = reverse(head1);
        ListNode h2 = reverse(head2);

        ListNode head = null;
        int flag = 0;
        while (h1 != null || h2 != null || flag > 0) {
            int v1 = h1 == null ? 0 : h1.val;
            int v2 = h2 == null ? 0 : h2.val;
            int t = v1 + v2 + flag;
            flag = t / 10;
            t = t % 10;
            ListNode cur = new ListNode(t);
            cur.next = head;
            head = cur;
            if (h1 != null) {
                h1 = h1.next;
            }
            if (h2 != null) {
                h2 = h2.next;
            }
        }
        return head;

    }

    /**
     * 两个链表相加
     *
     * @param head1
     * @param head2
     * @return
     */
    public ListNode addInList(ListNode head1, ListNode head2) {
        if (head1 == null) {
            return head2;
        }
        if (head2 == null) {
            return head1;
        }
        // write code here
        ListNode h1 = reverse(head1);
        ListNode h2 = reverse(head2);
        ListNode dummy = new ListNode(-1);
        ListNode head = dummy;
        int flag = 0;
        while (h1 != null || h2 != null || flag > 0) {
            int v1 = h1 == null ? 0 : h1.val;
            int v2 = h2 == null ? 0 : h2.val;
            int t = v1 + v2 + flag;
            flag = t / 10;
            t = t % 10;
            head.next = new ListNode(t);
            head = head.next;
            if (h1 != null) {
                h1 = h1.next;
            }
            if (h2 != null) {
                h2 = h2.next;
            }
        }
        return reverse(dummy.next);

    }

    public ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }
        ListNode pre = null;
        ListNode cur = head;
        ListNode cur_next;
        while (cur != null) {
            cur_next = cur.next;
            cur.next = pre;
            pre = cur;
            cur = cur_next;
        }
        return pre;
    }


    /**
     * 找到两个链表的公共节点
     *
     * @param pHead1
     * @param pHead2
     * @return
     */
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        ListNode p1 = pHead1;
        ListNode p2 = pHead2;
        while (p1 != p2) {

            p1 = p1 != null ? p1.next : pHead2;
            p2 = p2 != null ? p2.next : pHead1;
        }
        return p1;

    }

    /**
     * 删除倒数第K个节点
     *
     * @param head
     * @param n
     * @return
     */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        // write code here
        ListNode fast = head;
        // 这里n是多少，fast就往后走多少步
        for (int i = 0; i < n; i++) {
            fast = fast.next;
        }
        // fast == null说明要删除的是头节点
        if (fast == null) {
            return head.next;
        }
        ListNode slow = head;
        // 注意，这里是用fast.next做的非空判断
        while (fast.next != null) {
            fast = fast.next;
            slow = slow.next;
        }

        slow.next = slow.next.next;
        return head;

    }

    /**
     * 找到倒数第K个节点
     *
     * @param pHead
     * @param k
     * @return
     */
    public ListNode FindKthToTail(ListNode pHead, int k) {
        // write code here

        if (pHead == null) {
            return null;
        }

        ListNode fast = pHead;

        while (fast != null && k > 0) {
            fast = fast.next;
            k--;
        }
        if (fast == null) {
            // 注意，这里如果k等于0，那么说明倒数第K个节点，是头节点
            if (k == 0) {
                return pHead;
            }
            return null;
        }
        ListNode slow = pHead;
        while (fast != null) {
            fast = fast.next;
            slow = slow.next;
        }
        return slow;
    }

    /**
     * 第一次相遇之后，在走X步到达相遇节点
     * 从头出发，也是在走X步到达节点
     *
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop2(ListNode pHead) {

        ListNode fast = pHead;
        ListNode slow = pHead;
        int count = 0;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            count++;
            if (fast == slow) {
                fast = pHead;
                while (fast != slow) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }

        }
        return null;

    }

    /**
     * 找到入口节点
     *
     * @param pHead
     * @return
     */
    public ListNode EntryNodeOfLoop(ListNode pHead) {

        ListNode fast = pHead;
        ListNode slow = pHead;


        Set<ListNode> set = new HashSet<>();

        while (fast != null) {
            if (set.contains(fast)) {
                return fast;
            }

            set.add(fast);
            fast = fast.next;
        }
        return null;

    }

    public boolean hasCycle2(ListNode head) {
        if (head == null) {
            return false;
        }
        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (fast == slow) {
                return true;
            }
        }
        return false;
    }

    /**
     * 代码中的类名、方法名、参数名已经指定，请勿修改，直接返回方法规定的值即可
     *
     * @param head ListNode类
     * @return ListNode类
     */
    public ListNode ReverseList(ListNode head) {
        // write code here
        ListNode pre = null;
        ListNode cur = head;
        ListNode t;
        while (cur != null) {
            t = cur.next;
            cur.next = pre;
            pre = cur;
            cur = t;
        }
        return pre;

    }

    /**
     * 链表中的节点每k个一组翻转
     * https://www.nowcoder.com/practice/b49c3dc907814e9bbfa8437c251b028e?tpId=295&tqId=722&ru=/exam/oj&qru=/ta/format-top101/question-ranking&sourceUrl=%2Fexam%2Foj
     *
     * @param head
     * @param k
     * @return
     */
    public ListNode reverseKGroup(ListNode head, int k) {
        // write code here
        ListNode tail = head;
        for (int i = 0; i < k; i++) {
            if (tail == null) {
                return head;
            }
            tail = tail.next;
        }
        ListNode pre = null;
        ListNode cur = head;

        ListNode t;
        while (cur != tail) {
            t = cur.next;
            cur.next = pre;
            pre = cur;
            cur = t;
        }
        head.next = reverseKGroup(tail, k);
        return pre;


    }


    public ListNode mergeKLists(ArrayList<ListNode> lists) {
        // write code here
        //构造小顶堆
        PriorityQueue<ListNode> queue = new PriorityQueue<>((a, b) -> a.val - b.val);
        for (int i = 0; i < lists.size(); i++) {
            if (lists.get(i) != null) {
                queue.add(lists.get(i));
            }
        }
        ListNode dummy = new ListNode(-1);
        ListNode cur = dummy;
        ListNode t;
        while (!queue.isEmpty()) {
            t = queue.poll();
            cur.next = t;
            cur = cur.next;
            if ((t = t.next) != null) {
                queue.add(t);
            }
        }
        return dummy.next;
    }
}
