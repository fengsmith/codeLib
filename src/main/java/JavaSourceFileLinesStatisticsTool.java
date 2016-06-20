import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * author:      shfq
 * description:
 * create date: 2016/6/16.
 */

/**
 * 统计一个工程下的 java 源代码的行数（不包括空行）
 */
public class JavaSourceFileLinesStatisticsTool {
    public static void main(String[] args) {
        String path = "C:\\Users\\Administrator\\Desktop\\netty-4.1";
        System.out.println(recsiveStatisticsLines(new File(path)));
    }

    /**
     * 统计一个文件的代码行数 但不统计空行
     * @param file
     * @return
     */
    private static int statisticsLinesOfOneFile(File file) {
        BufferedReader reader = null;
        int count = 0;

        try {
            reader = new BufferedReader(new FileReader(file));
            String s;
            while ((s = reader.readLine()) != null) {
                // 不统计空行
                if (s.length() != 0) {
                    count++;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }

        return count;
    }


    /**
     * 统计一个文件或一个目录下的 java 文件（不包括测试文件）的代码行数
     * @param file
     * @return
     */
    public static int recsiveStatisticsLines(File file) {
        if (file.isFile()) {
            // 判断是否是 java 类但不包括测试类
            if (isJavaFile(file) && !isJavaTestFile(file)) {
                return statisticsLinesOfOneFile(file);
            } else {
                return 0;
            }
        }
        int sum = 0;
        for (File temp : file.listFiles()) {
            sum += recsiveStatisticsLines(temp);
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
