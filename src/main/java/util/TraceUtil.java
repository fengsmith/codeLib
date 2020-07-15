package util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;

/**
 * @Author:shifengqiang
 * @Date:2020/4/23 6:33 下午
 */
public class TraceUtil {
    public static void main(String[] args) {
        String stackTrace = getStackTrace();
        System.out.println(stackTrace);
    }

    public static String getStackTrace() {
        // Need the exception in string form to prevent the retention of
        // references to classes in the stack trace that could trigger a memory
        // leak in a container environment.
        Exception e = new Exception();
        final Writer w = new StringWriter();
        final PrintWriter pw = new PrintWriter(w);
        e.printStackTrace(pw);
        return w.toString();
    }
}
