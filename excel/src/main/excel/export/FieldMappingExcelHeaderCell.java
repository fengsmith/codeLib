package excel.export;

/**
 * author:      shfq
 * description:
 * create date: 2018/09/12.
 */
public class FieldMappingExcelHeaderCell {
    private String fieldName;
    private String headerCellValue;

    public FieldMappingExcelHeaderCell(String fieldName, String headerCellValue) {
        this.fieldName = fieldName;
        this.headerCellValue = headerCellValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getHeaderCellValue() {
        return headerCellValue;
    }

    public void setHeaderCellValue(String headerCellValue) {
        this.headerCellValue = headerCellValue;
    }
}
