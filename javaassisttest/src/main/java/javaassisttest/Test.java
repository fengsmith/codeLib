package javaassisttest;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;

/**
 * @Author:shifengqiang
 * @Date:19/12/26 上午11:42
 */
public class Test {
    public static void main(String[] args) {
        try {
            ClassPool classPool = ClassPool.getDefault();

            CtClass ctClass = classPool.get("javaassisttest.MyTest");
            ctClass.setSuperclass(classPool.get("javaassisttest.Parent"));
            CtMethod m = ctClass.getDeclaredMethod("test");
            m.insertBefore("{ System.out.println($ + $);}");
            ctClass.writeFile();



            MyTest test = new MyTest();
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
