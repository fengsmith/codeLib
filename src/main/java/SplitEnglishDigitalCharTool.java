/**
 * @Author:shifengqiang
 * @Date:2021/5/28 9:24 上午
 */
public class SplitEnglishDigitalCharTool {
    public static void main(String[] args) {
        String s = "阿发阿发撒旦法12312ssdfs431热污染safsfaf23asf234打发斯蒂芬发送";
        String r = splitEnglishNumberChar(s);
        System.out.println(r);
    }
    /**
     * 把相邻的 a-z 字母和数字分开
     *
     * @return
     */
    public static String splitEnglishNumberChar(String content) {
        if (content == null) {
            return "";
        }
        char[] chars = content.toCharArray();
        StringBuilder sb = new StringBuilder();

        CharTypeEnum currentType;
        CharTypeEnum previousType = identifyCharType(chars[0]);
        int previousIndex = 0;
        for (int i = 0; i < chars.length; i++) {
            char current = chars[i];
            currentType = identifyCharType(current);

            if (currentType != previousType &&
                    (previousType.isEnglishOrDigital() || currentType.isEnglishOrDigital())) {
                previousType = currentType;
                sb.append(content.substring(previousIndex, i)).append(" ");
                previousIndex = i;
            }
        }
        sb.append(content.substring(previousIndex));
        return sb.toString();
    }


    private static CharTypeEnum identifyCharType(char c) {
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            return CharTypeEnum.ENGLISH_CHAR;
        } else if (c >= '0' && c <= '9') {
            return CharTypeEnum.DIGITAL;
        } else {
            return CharTypeEnum.OTHERS;
        }
    }




    public enum CharTypeEnum {
        /**
         * 阿拉伯数字 0-9
         */
        DIGITAL,
        /**
         * 英文字母 a-z A-Z
         */
        ENGLISH_CHAR,
        OTHERS,
        ;

        public boolean isEnglishOrDigital() {
            return this == DIGITAL || this == ENGLISH_CHAR;
        }
    }
}
