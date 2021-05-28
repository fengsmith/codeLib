package latex.search.shfq;

import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;


/**
 * @Author:shifengqiang
 * @Date:2021/5/6 4:22 下午
 */
public class DictNode {
    /**
     *
     */
    private static final int ARRAY_LENGTH_LIMIT = 3;

    /**
     * Map存储结构
     */
    private Map<Character, DictNode> childrenMap;

    /**
     * 当前节点上存储的字符
     */
    private Character nodeChar;

    /**
     * 当前DictSegment状态
     * 默认 0
     * 1表示从根节点到当前节点的路径表示一个词，1 的话实际上是叶子节点
     */
    private int nodeState = 0;


    DictNode(Character nodeChar) {
        if (nodeChar == null) {
            throw new IllegalArgumentException("node char cannot be empty");
        }
        this.nodeChar = nodeChar;
    }


    /**
     * 加载填充词典片段
     *
     * @param charArray
     */
    void fillSegment(char[] charArray) {
        this.fillSegment(charArray, 0, charArray.length, 1);
    }


    /**
     * 加载填充词典片段
     *
     * @param charArray
     * @param begin
     * @param length
     * @param enabled
     */
    private synchronized void fillSegment(char[] charArray, int begin, int length, int enabled) {
        // 获取字典表中的对象
        Character beginChar = Character.valueOf(charArray[begin]);
        Character keyChar = beginChar;

        // 搜索当前节点的存储，查询对应keyChar的keyChar，如果没有则创建
        DictNode ds = lookforSegment(keyChar, enabled);
        if (ds != null) {
            // 处理keyChar对应的segment
            if (length > 1) {
                // 词元还没有完全加入词典树
                ds.fillSegment(charArray, begin + 1, length - 1, enabled);
            } else if (length == 1) {
                // 已经是词元的最后一个char,设置当前节点状态为enabled，
                // enabled=1表明一个完整的词，enabled=0表示从词典中屏蔽当前词
                ds.nodeState = enabled;
            }
        }

    }

    /**
     * 查找本节点下对应的keyChar的segment	 *
     *
     * @param keyChar
     * @param create  =1如果没有找到，则创建新的segment ; =0如果没有找到，不创建，返回null
     * @return
     */
    private DictNode lookforSegment(Character keyChar, int create) {
        DictNode ds = null;

        //获取Map容器，如果Map未创建,则创建Map
        Map<Character, DictNode> segmentMap = getChildrenMap();
        //搜索Map
        ds = segmentMap.get(keyChar);
        if (ds == null && create == 1) {
            //构造新的segment
            ds = new DictNode(keyChar);
            segmentMap.put(keyChar, ds);
        }

        return ds;
    }

    /**
     * 获取Map容器
     */
    private Map<Character, DictNode> getChildrenMap() {
        if (this.childrenMap == null) {
            this.childrenMap = new HashMap<>(ARRAY_LENGTH_LIMIT * 2, 0.8f);
        }
        return this.childrenMap;
    }

    Hit match(char[] charArray, int begin, int length, Hit searchHit) {
        //设置hit的当前处理位置
        searchHit.setEnd(begin);

        Character keyChar = Character.valueOf(charArray[begin]);
        DictNode ds = null;

        //引用实例变量为本地变量，避免查询时遇到更新的同步问题
        Map<Character, DictNode> segmentMap = this.childrenMap;

        if (segmentMap != null) {
            ds = segmentMap.get(keyChar);
        }

        if (ds != null) {
            if (ds.nodeState == 1) {
                //添加HIT状态为完全匹配
                searchHit.setMatched(true);
                return searchHit;
            } else {
                // 没有匹配到，继续递归
                if (length > 1) {
                    return ds.match(charArray, begin + 1 , length - 1 , searchHit);
                }
            }
        }
        return searchHit;
    }


    public static DictNode build(Collection<String> dic) {
        DictNode root = new DictNode((char) 0);
        for (String words : dic) {
            if (StringUtils.isNotBlank(words)) {
                root.fillSegment(words.toCharArray());
            }
        }
        return root;
    }

}
