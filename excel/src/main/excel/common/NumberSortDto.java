package excel.common;

import lombok.Data;

/**
 * @Author:shifengqiang
 * @Date:19/6/28 上午10:57
 */
@Data
public class NumberSortDto<T> {
    private Integer sortNo;
    private T object;

    public NumberSortDto(Integer sortNo, T object) {
        this.sortNo = sortNo;
        this.object = object;
    }
}
