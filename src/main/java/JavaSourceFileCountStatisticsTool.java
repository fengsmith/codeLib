import java.io.File;

/**
 * author:      shfq
 * description:
 * create date: 2016/6/16.
 */

/**
 * 统计一个目录下的所有 java 文件的个数（不包括测试文件）
 */
public class JavaSourceFileCountStatisticsTool {
    public static void main(String[] args) {
        System.out.println("hello");
        String path = "/Users/shifengqiang/projects/nacos";
        System.out.println(recursiveStatisticsFileCount(new File(path)));
    }


    /**
     * 统计一个文件或一个目录下的 java 文件（不包括测试文件）的个数
     * @param file
     * @return
     */
    public static int recursiveStatisticsFileCount(File file) {
        if (file.isFile()) {
            // 判断是否是 java 类但不包括测试类
            if (isJavaFile(file) && !isJavaTestFile(file)) {
                return 1;
            } else {
                return 0;
            }
        }
        int sum = 0;
        for (File temp : file.listFiles()) {
            sum += recursiveStatisticsFileCount(temp);
        }
        return sum;
    }

    /**
     * 判断一个文件是不是 java 类
     * @param file
     * @return
     */
    private static boolean isJavaFile(File file) {
        return file.exists() && file.isFile() && file.getName().endsWith(".java");
    }

    /**
     * 判断一个文件是不是 java 测试类
     * @param file
     * @return
     */
    private static boolean isJavaTestFile(File file) {
        return isJavaFile(file) && file.getName().endsWith("Test.java");
    }
}
