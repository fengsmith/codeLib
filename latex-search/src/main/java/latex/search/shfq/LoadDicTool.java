package latex.search.shfq;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author:shifengqiang
 * @Date:2021/5/7 5:01 下午
 */
public class LoadDicTool {
    public static void main(String[] args) throws Exception {
        System.out.println("");
        generateExtraDic();
    }

    /**
     * 把 latex 替换为中文，产生的中文需要加到 ik 扩展字典中
     * @throws Exception
     */
    public static void generateExtraDic() throws Exception {
        DicContext dicContext = buildDicContext();
        for (String value : dicContext.getLatexMap().values()) {
            if (StringUtils.isNotBlank(value)) {
                System.out.println(value.trim());
            }
        }
    }

    public static DicContext buildDicContext() throws Exception {
        URL url = LoadDicTool.class.getResource("/");
        File file = new File(url.toURI());

        List<String> list = Files.readAllLines(Paths.get(file.toString() + "/公式符号.dic"));

        Map<String, String> latexMap = new HashMap(list.size() * 2);

        // 转换目标短语分隔符
        String separator = "-》";
        for (String line : list) {
            if (line.startsWith("###") || line.length() == 0 || !line.contains(separator)) {
                continue;
            }
            // 转换后的目标短语
            String[] tokens = line.split(separator, -1);
            String[] synonyms = tokens[0].split(" ");
            String target = tokens[1];
            target = target.trim();
            for (String synonym : synonyms) {
                if (StringUtils.isNotBlank(synonym)) {
                    synonym = synonym.trim();
                    latexMap.put(synonym, " " + target + " ");
                }
            }
        }
        Set<String> latexSet = latexMap.keySet();

        DicContext dicContext = new DicContext(latexMap, latexSet);
        return dicContext;
    }
}
