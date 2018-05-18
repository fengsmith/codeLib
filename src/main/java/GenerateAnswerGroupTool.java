import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by shifengqiang on 18/5/18.
 */
public class GenerateAnswerGroupTool {

    public static void main(String[] args) {
        // 输出组合
        String s = "ABCD";
        List<String> result = recursive(s);

        Collections.sort(result, new Comparator<String>() {
            public int compare(String o1, String o2) {
                if (o1.length() == o2.length()) {
                    return o1.compareTo(o2);
                } else {
                    return o1.length() - o2.length();
                }
            }

        });

        for (String s1 : result) {
            System.out.println(s1);
        }


    }

    public static List<String> recursive(String answer) {
        if (answer.length() == 1) {
            List<String> list = new ArrayList<String>();
            list.add(answer);
            return list;
        } else {
            List<String> list1 = recursive(answer.substring(0, answer.length() - 1));
            List<String> list2 = new ArrayList<String>(list1);
            String last = answer.substring(answer.length() - 1);
            for (String s : list1) {
                list2.add(s + last);
            }
            list2.add(last);
            return list2;
        }
    }

    public static class Answer {
        private boolean isDeducted = false;
        private List<NameValuePair> answerGroup;

        public boolean getIsDeducted() {
            return isDeducted;
        }

        public void setIsDeducted(boolean isDeducted) {
            this.isDeducted = isDeducted;
        }

        public List<NameValuePair> getAnswerGroup() {
            return answerGroup;
        }

        public void setAnswerGroup(List<NameValuePair> answerGroup) {
            this.answerGroup = answerGroup;
        }

        @Override
        public String toString() {
            return "{\"Answer\":{"
                    + "                        \"isDeducted\":\"" + isDeducted + "\""
                    + ",                         \"answerGroup\":" + answerGroup
                    + "}}";
        }

        public static class NameValuePair {
            private String answer;
            private int score;

            public String getAnswer() {
                return answer;
            }

            public void setAnswer(String answer) {
                this.answer = answer;
            }

            public int getScore() {
                return score;
            }

            public void setScore(int score) {
                this.score = score;
            }

            @Override
            public String toString() {
                return "{\"NameValuePair\":{"
                        + "                        \"answer\":\"" + answer + "\""
                        + ",                         \"score\":\"" + score + "\""
                        + "}}";
            }
        }

    }

}
