import java.util.Stack;

/**
 * author:      shfq
 * description:
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
        ListNode listNode1 = new ListNode(1);
        listNode1.next = new ListNode(8);

        ListNode listNode2 = new ListNode(0);

        Object o = addTwoNumbers(listNode1, listNode2);
        System.out.println("");
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        Stack<Integer> numbers1 = new Stack<Integer>();
        Stack<Integer> numbers2 = new Stack<Integer>();
        reversePut(l1, numbers1);
        reversePut(l2, numbers2);

        int n = 0;
        ListNode listNode = null;
        ListNode headNode = listNode;

        while (!numbers1.empty() && !numbers2.empty()) {
            int number1 = numbers1.pop();
            int number2 = numbers2.pop();
            int sum = number1 + number2 + n;
            if (sum >= 10) {
                sum = sum - 10;
                n = 1;
            } else {
                n = 0;
            }
            if (listNode == null) {
                listNode = new ListNode(sum);
                headNode = listNode;
            } else {
                listNode.next = new ListNode(sum);
                listNode = listNode.next;

            }
        }

        n = add(l1, headNode, n);
        n = add(l2, headNode, n);
        if (n == 1) {
            listNode.next = new ListNode(1);
        }
        return headNode;
    }

    public static void reversePut(ListNode listNode, Stack<Integer> integers) {
        while (listNode != null) {
            integers.push(listNode.val);
            listNode = listNode.next;
        }
    }

    public static int add(ListNode listNode, ListNode sumListNode, int n) {
        while (listNode != null) {
            int sum = listNode.val + n;
            if (sum >= 10) {
                sum = sum - 10;
                n = 1;
            } else {
                n = 0;
            }
            sumListNode.next = new ListNode(sum);
        }
        return n;
    }

}
