import org.apache.commons.lang.StringUtils;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author:shifengqiang
 * @Date:2021/6/9 9:16 上午
 */
public class TrimBlankBetweenChineseChar {
    public static void main(String[] args) {
        String s = " 啊 如图,正方形ABCD内的图形来自中国古代的太极图.正方形内切圆中的黑色部分和白         色部分位于正方形的中心成中心对称,在正方形内随机取一点,则此点取自黑色部分的 概率是() B D C 1 1  A.  B.  C.  D. 4 π8 2 π一4 ";
        String r = trimBlankBetweenChineseChar(s);
        System.out.println(r);
    }

    /**
     * 去掉中文字符之间的空格
     * ocr 识别完后，换行的时候会加一个空格，这样一来的话，把本来是一个词硬是拆成了单个的汉字。
     *
     * @return
     */
    public static String trimBlankBetweenChineseChar(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        if (content.length() > 3) {
            char[] chars = content.toCharArray();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < chars.length - 1; i++) {
                char current = chars[i];
                CharType currentType = identifyCharType1(current);

                char next = chars[i + 1];
                CharType nextType = identifyCharType1(next);
                // 去掉多个相邻的空格只保留一个，如果一个是汉字之间的空格的话，一个也不保留。
                if (currentType == CharType.BLANK &&
                        (nextType == CharType.BLANK || nextType == CharType.CHINESE)) {
                    // 当前是空白，跳过空白，相邻的汉字就合并在一起了
                    continue;
                } else {
                    sb.append(current);
                }

            }
            return sb.toString();
        }
        return content;

    }

    /**
     * 字符类型
     */
    private enum CharType {
        /**
         * 中文
         */
        CHINESE,
        /**
         * '
         * 空格
         */
        BLANK,
        /**
         * 其他
         */
        OTHERS
    }

    private static CharType identifyCharType1(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return CharType.OTHERS;
        } else if (c >= '0' && c <= '9') {
            return CharType.OTHERS;
        } else if (c == ' ') {
            return CharType.BLANK;
        } else if (isChineseChar(c)) {
            return CharType.CHINESE;
        } else {
            return CharType.OTHERS;
        }
    }


    /**
     * 判断一个字符是否是中文
     *
     * @param input
     * @return
     */
    public static boolean isChineseChar(char input) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(input);
        return chineseUnicodeBlockSet.contains(ub);
    }


    private static final Set<Character.UnicodeBlock> chineseUnicodeBlockSet = new HashSet<>(4);

    static {
        chineseUnicodeBlockSet.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS);
        chineseUnicodeBlockSet.add(Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS);
        chineseUnicodeBlockSet.add(Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A);
    }




}
