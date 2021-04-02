import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author:shifengqiang
 * @Date:2021/3/25 11:53 上午
 */
public class StatLog {
    public static void main(String[] args) {
        try {
            List<String> webLogList = Files.readAllLines(Paths.get("/Users/shifengqiang/Desktop/newWeb.log"));
            List<String> dubboList = Files.readAllLines(Paths.get("/Users/shifengqiang/Desktop/newDubbo.log"));
            System.out.println("web total:" + webLogList.size());
            System.out.println("dubbo total:" + dubboList.size());

            String seperator = " \\[";
            Map<String, List<String>> webMap = new HashMap<>();
            Map<String, List<String>> dubboMap = new HashMap<>();
            for (String line : webLogList) {
                String[] ss = line.split(seperator);
                String key = format(ss[0]);
                List<String> tempList = webMap.getOrDefault(key, new ArrayList<>());
                tempList.add(line);
                webMap.put(key, tempList);
            }

            for (String line : dubboList) {
                String[] ss = line.split(seperator);
                String key = format(ss[0]);
                List<String> tempList = dubboMap.getOrDefault(key, new ArrayList<>());
                tempList.add(line);
                webMap.put(key, tempList);

            }

            Map<String, List<String>> fullMap = new HashMap<>(webMap);
            dubboMap.forEach((k, v) -> {
                List<String> temp = fullMap.getOrDefault(k, new ArrayList<>());
                temp.addAll(v);
                fullMap.put(k, temp);
            });
            int max = 0;
            for (List<String> list : fullMap.values()) {
                if (list.size() > max) {
                    max = list.size();
                }
            }
            System.out.println("最大并发:" + max);



        } catch (Exception e) {

        }
    }

    private static String format(String date) {
        return date.split("\\.")[0];
    }
}
