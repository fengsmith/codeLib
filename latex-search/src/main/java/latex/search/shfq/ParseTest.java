package latex.search.shfq;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @Author:shifengqiang
 * @Date:2021/5/8 9:57 上午
 */
public class ParseTest {
    public static void main(String[] args) throws Exception {
//        demo();
        Long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String s = "3已知数列{an}满足a1=0,an+1an-3 √3a1(n∈n*),则a20=() A.0 D B.-√3 A C.√3 D 2 ";
//            System.out.println(s);
            String result = removeExerciseNo(s);
//            System.out.println(result);
            result = removeOptions(result);
//            System.out.println(result);
        }
        Long end = System.currentTimeMillis();
        System.out.println(end - start);
    }

    private static void demo() throws Exception {
        DicContext dicContext = LoadDicTool.buildDicContext();
        DictNode root = DictNode.build(dicContext.getLatexSet());
        Map<String, String> latexMap = dicContext.getLatexMap();
//        String s = "(0,\\dfrac12,1)\n" +
//                "|\\overrightarrow{a}-\\overrightarrow{b}|=\\sqrt{13}\n" +
//                "2^{x}+\\frac{1}{2^{x}}\\geqslant2\n" +
//                "|\\frac {1} {x}-a|\\geqslant 1-|2-\\frac {1} {x}|\n" +
//                "m\\geq2\n" +
//                "\\frac{\\sqrt{e}}{2}\n" +
//                "N\\left( 6,0.8^{2}\\right) \n" +
//                "S_{n} S_{n+1}\n" +
//                "\\[S_{11} >0\\]_\n" +
//                "(x-2)^9=a_0+a_1(x+1)+a_2(x+1)^2+\\cdots+a_9(x+1)^9\n" +
//                "f(x)=|x^{3}-3x-2m|+m\n" +
//                "x^2-2mx-3m^2\\leqslant0";
        String s = "已知U=R,A={x|x>O},B={x|x≤-1},则[An(ubun等于(  A. B.{x|x≤0} C.{x|x>-1}D.{x|x>0或x≤-1} ";

        String[] ss = s.split("\n");
        for (String line : ss) {
            System.out.println(line);
            parse(line, root, latexMap);
            System.out.println();
        }
//        String line = "|\\overrightarrow{a}-\\overrightarrow{b}|=\\sqrt{13}";
//        parse(line, root);

        System.out.println("");
    }


    private static void parse(String line, DictNode root, Map<String, String> latexMap) {
        char[] chars = line.toCharArray();
        StringBuilder sb = new StringBuilder();
        Hit hit = new Hit();
        hit.setStart(0);
        // 包含 0
        hit.setEnd(0);
        hit.setPreviousStart(0);


        int position = 0;
        while (position < chars.length) {
            hit = root.match(chars, position, chars.length - position - 1, hit);

            if (hit.isMatched()) {
                // 判断需要忽略还是直接转换
                String matchedString = line.substring(position, hit.getEnd() + 1);
                String replaced = latexMap.getOrDefault(matchedString, "");
                sb.append(replaced);
            } else {
                sb.append(chars[position]);
            }
            if (hit.isMatched()) {
                position = hit.getEnd() + 1;
            } else {
                position = hit.getPreviousStart() + 1;
            }
            // 设置上下文
            hit.setMatched(false);
            hit.setPreviousStart(position);

        }
        String result = reduceMultipleBlankSpace(sb.toString());

        System.out.println(result);
    }

    /**
     * 字符串中相邻的空格只保留一个
     * @param source
     * @return
     */
    private static String reduceMultipleBlankSpace(String source) {
        if (StringUtils.isEmpty(source)) {
            return "";
        }
        char[] chars = source.toCharArray();
        char previous = '0';
        char bankSpace = ' ';
        StringBuilder sb = new StringBuilder();
        for (char current : chars) {
            // 不是空格，直接追加
            if (current != bankSpace) {
                sb.append(current);
            } else {
                // 之前的字符不是空格，即第一个空格
                if (previous != bankSpace) {
                    sb.append(current);
                }
            }
            previous = current;
        }
        // 最后再把首尾的空格也去掉
        return sb.toString().trim();
    }

    /**
     * 去掉 ocr 识别中的题号
     * @param content
     * @return
     */
    private static String removeExerciseNo(String content) {
        if (StringUtils.isEmpty(content)) {
            return content;
        }
        content = content.trim();
        // 题号最长两位
        if (content.length() > 2) {
            Character first = content.charAt(0);
            // 第一位是题号
            if (exerciseNoSet.contains(first.toString())) {
                Character second = content.charAt(1);
                if (exerciseNoSet.contains(second.toString())) {
                    return content.substring(2);
                } else {
                    return content.substring(1);
                }
            }
        }
        return content;
    }

    /**
     * 去掉选择题中的选项 A B C D
     * @param content
     * @return
     */
    private static String removeOptions(String content) {
        if (StringUtils.isEmpty(content)) {
            return "";
        }
        content = content.trim();
        if (content.length() > 3) {
            List<Character> optionList = new ArrayList<>(8);
            // 选项一般都是大写字母。
            // 不循环首尾，确保前一个字符和下一个字符不会发生空指针异常
            StringBuilder sb = new StringBuilder();
            // 符合前一个字符不是 字母 当前字符是字母 下一个字符不是字母 这样要求的字母才可能是选项。
            // 选项之间的顺序是 A B C D
            char[] chars = content.toCharArray();
            {
                Predicate<Character> isChar = c -> c >= 'a' && c <= 'z' || c >= 'A' && c <= 'Z';
                char previous = ' ';
                char next = ' ';
                sb.append(chars[0]);
                for (int i = 1; i < chars.length - 1; i++) {
                    char current = chars[i];
                    if (!isChar.test(previous) && current >= 'A' && current <= 'D' && !isChar.test(next)) {
                        optionList.add(current);
                        sb.append(' ');
                    } else {
                        sb.append(current);
                    }
                }
                sb.append(chars[chars.length - 1]);
            }
            // 4 个选项。
            // 4 个选项应该还是有顺序的，即 A -> B -> C -> D 。
            if (optionList.size() >= 4) {
                Map<Character, List<Integer>> charNoMap = new HashMap<>(4);
                for (int i = 0; i < optionList.size(); i++) {
                    Character c = optionList.get(i);
                    List<Integer> noList = charNoMap.getOrDefault(c, new ArrayList<>());
                    noList.add(i);
                    charNoMap.put(c, noList);
                }
                // 同时含有 A、B、C、D 四个选项。
                if (charNoMap.containsKey('A') && charNoMap.containsKey('B') && charNoMap.containsKey('C') && charNoMap.containsKey('D')) {
                    for (List<Integer> list : charNoMap.values()) {
                        list.sort(Integer::compare);
                    }
                }

                boolean found = true;
                for (int i = 'A'; i < 'D'; i++) {
                    char current = (char) i;
                    char next = (char) (i + 1);

                    List<Integer> currentList = charNoMap.get(current);
                    List<Integer> nextList = charNoMap.get(next);

                    if (currentList.get(0) > nextList.get(nextList.size() - 1)) {
                        found = false;
                        break;
                    }
                }
                // 是单选题，需要把 A B C D 剔除，如果某个字符有多个的话，需要保留一个。
                if (found) {
                    for (int i = 'A'; i <= 'D'; i++) {
                        char c = (char) i;
                        List<Integer> currentList = charNoMap.get(c);
                        if (currentList.size() > 1) {
                            sb.append(' ').append(c).append(' ');
                        }
                    }
                    return sb.toString();
                }
            }
        }


        return content;
    }

    /**
     * 题号
     */
    private static final Set<String> exerciseNoSet = new HashSet<>(32);
    static {
        exerciseNoSet.add("0");
        exerciseNoSet.add("1");
        exerciseNoSet.add("2");
        exerciseNoSet.add("3");
        exerciseNoSet.add("4");
        exerciseNoSet.add("5");
        exerciseNoSet.add("6");
        exerciseNoSet.add("7");
        exerciseNoSet.add("8");
        exerciseNoSet.add("9");


        exerciseNoSet.add("一");
        exerciseNoSet.add("二");
        exerciseNoSet.add("三");
        exerciseNoSet.add("四");
        exerciseNoSet.add("五");
        exerciseNoSet.add("六");
        exerciseNoSet.add("七");
        exerciseNoSet.add("八");
        exerciseNoSet.add("九");
        exerciseNoSet.add("十");
    }



}
