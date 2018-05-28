import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by shifengqiang on 18/5/28.
 */
public class JavaRegex {
    public static void main(String[] args) {
        String className = "高三8班";
        int classNo = getClassNo(className);
        System.out.println(classNo);
    }

    /**
     * 提取出班级中的数字
     * @param className
     * @return
     */
    public static int getClassNo(String className) {
        String pattern = "(\\D*)(\\d+)(.*)";

        // 创建 Pattern 对象
        Pattern r = Pattern.compile(pattern);

        // 现在创建 matcher 对象
        Matcher m = r.matcher(className);
        int no = 1;
        if (m.find()) {
            no = Integer.parseInt(m.group(2));
        }
        return no;
    }
}
