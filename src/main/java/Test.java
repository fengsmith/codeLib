import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.WildcardType;
import java.util.List;

/**
 * @Author:shifengqiang
 * @Date:19/12/24 下午3:32
 */
public class Test {
    public static void main(String[] args) {
        try {
            print("getStringList");
            print("getList1");
            print("getList2");
            print("getList3");
            print("getList4");

            System.out.println("");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void print(String methodName) throws Exception {
        Method method = MyClass.class.getMethod(methodName, null);

        Type returnType = method.getGenericReturnType();

        if(returnType instanceof ParameterizedType){
            ParameterizedType type = (ParameterizedType) returnType;
            Type[] typeArguments = type.getActualTypeArguments();
            for(Type typeArgument : typeArguments){
                if (typeArgument instanceof Class) {
                    Class typeArgClass = (Class) typeArgument;
                    System.out.println("typeArgClass = " + typeArgClass);
                }
                if (typeArgument instanceof ParameterizedType) {
                    System.out.println("");
                }
                if (typeArgument instanceof WildcardType) {
                    System.out.println("");
                }
            }
        }

    }


    /**
     * Generates the field signiture for a field of the given type
     *
     * @param type
     * @return the signiture, or null if no signure is required (e.g. for Class types)
     */
    public static String fieldAttribute(Type type) {
        StringBuilder builder = new StringBuilder();
        fieldAttribute(type, builder);
        return builder.toString();

    }

    private static void fieldAttribute(Type type, StringBuilder builder) {
        if (type instanceof Class<?>) {
            classType((Class<?>) type, builder);
        }
        if (type instanceof ParameterizedType) {
            ParameterizedType ptype = (ParameterizedType) type;
            parametizedType(ptype, builder);
        }
        if (type instanceof WildcardType) {
            WildcardType ptype = (WildcardType) type;
            wildcardType(ptype, builder);

        }
    }

    private static void wildcardType(WildcardType type, StringBuilder builder) {
        // WRONG
        builder.append('*');
    }

    public static void parametizedType(ParameterizedType type, StringBuilder builder) {
        fieldAttribute(type.getRawType(), builder); //write the owner type
        //now write the type arguments
        builder.append('<');
        for(Type t : type.getActualTypeArguments()) {
            fieldAttribute(t, builder);
            builder.append(';');
        }
        builder.append(">;");
    }

    private static void classType(Class<?> clazz, StringBuilder builder) {
        if (clazz.isMemberClass()) {
            classType(clazz.getDeclaringClass(), builder);
            builder.append('.');
            builder.append(clazz.getSimpleName());
        } else {
            builder.append("L");
            builder.append(clazz.getName().replace('.', '/'));
        }

    }

    public class MyClass {
        private List<String> stringList;

        private List<?> list1;

        private List<List<String>> list2;

        private List<? extends Number> list3;

        private List<? super Long> list4;

        public List<String> getStringList() {
            return stringList;
        }

        public List<?> getList1() {
            return list1;
        }

        public List<List<String>> getList2() {
            return list2;
        }

        public List<? extends Number> getList3() {
            return list3;
        }

        public List<? super Long> getList4() {
            return list4;
        }
    }
}
