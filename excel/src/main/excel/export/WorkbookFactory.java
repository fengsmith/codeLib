package excel.export;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * author:      shfq
 * description:
 * create date: 2018/09/12.
 */
public class WorkbookFactory {
    private WorkbookFactory() {

    }

    /**
     *
     * @param excelType excel 表格类型 后缀是 xls 或者是 xlsx
     * @return
     * @throws Exception
     */
    public static Workbook create(ExcelType excelType) throws Exception {
        if (excelType == null) {
            throw new Exception("excel 表格类型不能为空");
        }
        if (ExcelType.XLS == excelType) {
            return new HSSFWorkbook();
        } else {
            return new XSSFWorkbook();

        }
    }
}
