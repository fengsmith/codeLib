package javaassisttest;

/**
 * @Author:shifengqiang
 * @Date:19/12/26 上午11:56
 */
public class MyTest {
    public static void main(String[] args) {
        MyTest myTest = new MyTest();
        myTest.test("hello s1", "hello s2");
    }
    public void test(String s1, String s2) {
        System.out.println("hello test");
    }
}
