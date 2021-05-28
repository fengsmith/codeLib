package latex.search.shfq;

import java.util.Map;
import java.util.Set;

/**
 * @Author:shifengqiang
 * @Date:2021/5/8 9:51 上午
 */
public class DicContext {
    private Map<String, String> latexMap;
    private Set<String> latexSet;

    public DicContext(Map<String, String> latexMap, Set<String> latexSet) {
        this.latexMap = latexMap;
        this.latexSet = latexSet;
    }

    public Map<String, String> getLatexMap() {
        return latexMap;
    }

    public void setLatexMap(Map<String, String> latexMap) {
        this.latexMap = latexMap;
    }

    public Set<String> getLatexSet() {
        return latexSet;
    }

    public void setLatexSet(Set<String> latexSet) {
        this.latexSet = latexSet;
    }
}
