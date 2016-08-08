/**
 * author:      shfq
 * description: 两个链表都是低位在前高位在后 相加后的和也是低位在前高位在后
 * create date: 2016/8/5.
 */
public class AddTwoNumbers {
    static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }
    }

    public static void main(String[] args) {
        ListNode listNode1 = new ListNode(9);
        listNode1.next = new ListNode(8);

        ListNode listNode2 = new ListNode(1);

        Object o = addTwoNumbers(listNode1, listNode2);
        System.out.println("");
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Integer n = 0;
        ListNode tailNode = null;
        ListNode headNode = tailNode;

        while (l1 != null && l2 != null) {
            int sum = l1.val + l2.val + n;
            n = sum / 10;
            sum = sum % 10;
            if (tailNode == null) {
                tailNode = new ListNode(sum);
                headNode = tailNode;
            } else {
                tailNode.next = new ListNode(sum);
                tailNode = tailNode.next;

            }
            l1 = l1.next;
            l2 = l2.next;
        }
        while (l1 != null) {
            int sum = l1.val + n;
            n = sum / 10;
            sum = sum % 10;
            tailNode.next = new ListNode(sum);
            tailNode = tailNode.next;
            l1 = l1.next;
        }

        while (l2 != null) {
            int sum = l2.val + n;
            n = sum / 10;
            sum = sum % 10;
            tailNode.next = new ListNode(sum);
            tailNode = tailNode.next;
            l2 = l2.next;
        }
        if (n == 1) {
            tailNode.next = new ListNode(1);
        }
        return headNode;
    }

}
