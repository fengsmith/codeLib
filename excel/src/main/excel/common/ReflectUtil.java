package excel.common;

import java.lang.reflect.Method;

/**
 * author:      shfq
 * description:
 * create date: 2018/09/12.
 */
public class ReflectUtil {
    private ReflectUtil() {

    }

    public static Method getGetterMethod(Class clazz, String fieldName) throws Exception {
        if (fieldName == null || fieldName.length() == 0) {
            throw new IllegalArgumentException("字段名称不能为空");
        }
        return clazz.getMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
    }

    public static Method getSetterMethod(Class clazz, String fieldName, Class parameterType) throws Exception {
        if (fieldName == null || fieldName.length() == 0) {
            throw new IllegalArgumentException("字段名称不能为空");
        }
        return clazz.getMethod("set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), parameterType);
    }

    public static Object callMethod(Object object, Method method) throws Exception {
        return method.invoke(object);
    }

    public static void main(String[] args) {
        Object b = 1;
        double d = 1;
        System.out.println(d);
    }


}
