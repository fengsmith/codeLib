/**
 * author:      shfq
 * description:
 * create date: 2016/6/20.
 */
public class CountWords {
    public static void main(String[] args) {
        System.out.println(countWords(null));
        System.out.println(countWords(""));
        System.out.println(countWords("    "));
        System.out.println(countWords("  sdfsf sd sdf sdf  sdf "));
        System.out.println(countWords("  sdf sdf"));
        System.out.println(countWords("sdf"));
    }

    /**
     * 一个字符串中只有空格或者是字母或者是二者的组合
     * @param originalString
     * @return
     */
    public static int countWords(String originalString) {
        StringBuilder stringBuilder = new StringBuilder();
        int count = 0;
        if (originalString != null) {
            originalString = originalString.trim();
            if (originalString.length() == 0) {
                return 0;
            }
            for (int i = 0; i < originalString.length(); i++) {
                if (originalString.charAt(i) != ' ') {
                    stringBuilder.append(originalString.charAt(i));
                } else {
                    if (stringBuilder.charAt(stringBuilder.length() - 1) == ' ') {
                        continue;
                    } else {
                        stringBuilder.append(' ');
                        count++;
                    }
                }
            }
            return ++count;
        } else {
            return 0;
        }
    }
}

