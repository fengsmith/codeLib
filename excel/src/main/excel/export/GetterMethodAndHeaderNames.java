package excel.export;

import lombok.Data;

import java.lang.reflect.Method;

/**
 * @Author:shifengqiang
 * @Date:19/6/28 上午10:47
 */
@Data
public class GetterMethodAndHeaderNames {
    /**
     * 字段对应的 get 方法
     */
    private Method getterMethod;
    /**
     * 字段值对应的表头,字段有可能是集合,所以 headerNames 是数组
     */
    private String[] headerNames;

    public GetterMethodAndHeaderNames(Method getterMethod, String[] headerNames) {
        this.getterMethod = getterMethod;
        this.headerNames = headerNames;
    }
}
