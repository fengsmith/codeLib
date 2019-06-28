package excel.import_;

import lombok.Data;

/**
 * @Author:shifengqiang
 * @Date:19/4/30 下午8:16
 */
@Data
public class ExcelColumnNoFieldMapping {
    /**
     * excel 列号
     */
    private Integer columnNo;
    /**
     * VO 字段名称
     */
    private String fieldName;
    /**
     * 字段类型
     */
    private Class fieldType;

    public ExcelColumnNoFieldMapping(Integer columnNo, String fieldName, Class fieldType) {
        this.columnNo = columnNo;
        this.fieldName = fieldName;
        this.fieldType = fieldType;
    }
}
