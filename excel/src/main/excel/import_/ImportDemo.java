package excel.import_;

import excel.common.ExcelColumnNo;
import lombok.Data;

import java.io.FileInputStream;
import java.util.List;

/**
 * @Author:shifengqiang
 * @Date:19/6/28 下午8:05
 */
public class ImportDemo {

    public static void main(String[] args) throws Exception {
        FileInputStream fileInputStream = new FileInputStream("./excel/src/main/resources/导入模板.xlsx");
        ExcelImportTool excelImportTool = new ExcelImportTool(fileInputStream, TempDto.class);
        List<TempDto> tempDtoList = excelImportTool.getList();
        System.out.println("");
    }

    /**
     * excel 行映射的对象
     */
    @Data
    public static class TempDto {
        /**
         * 解析出 excel 的第二列
         */
        @ExcelColumnNo(2)
        private String phone;
        /**
         * 解析出 excel 的第一列
         */
        @ExcelColumnNo(1)
        private Long accountId;
    }
}
