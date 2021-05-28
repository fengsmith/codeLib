package latex.search.shfq;

/**
 * @Author:shifengqiang
 * @Date:2021/5/6 5:59 下午
 */
public class Hit {
    /**
     * 是否找到字典中的一个单词
     */
    private boolean matched = false;
    /**
     * 当前正在查找的节点
     */
    private DictNode dictNode;
    private int start = -1;
    private int end = -1;
    private int previousStart = -1;

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public DictNode getMyDictSegment() {
        return dictNode;
    }

    public void setMyDictSegment(DictNode dictNode) {
        this.dictNode = dictNode;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getPreviousStart() {
        return previousStart;
    }

    public void setPreviousStart(int previousStart) {
        this.previousStart = previousStart;
    }
}
