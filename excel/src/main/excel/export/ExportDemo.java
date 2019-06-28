package excel.export;

import excel.common.ExcelColumnNo;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:shifengqiang
 * @Date:19/6/28 下午8:17
 */
public class ExportDemo {
    public static void main(String[] args) throws Exception {
        testWithList();
        testWithSimpleObject();
    }


    private static void testWithList() throws Exception {
        File file = new File("./excel/src/main/resources/testWithList.xls");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        List<String> extraList = new ArrayList<>();
        extraList.add("你好吗?");
        extraList.add("我很好");


        TestObjectWithList testObject = new TestObjectWithList();
        testObject.setStudentId("110");
        testObject.setStudentName("石锋强");
        testObject.setExtraList(extraList);

        List<TestObjectWithList> testObjectWithLists = new ArrayList<>();
        testObjectWithLists.add(testObject);

        ExcelExportTool<TestObjectWithList> excelExportTool = new ExcelExportTool<>(TestObjectWithList.class, testObjectWithLists, ExcelType.XLS, fileOutputStream);

        excelExportTool.write();

    }

    private static void testWithSimpleObject() throws Exception {
        File file = new File("./excel/src/main/resources/testWithSimpleObject.xls");
        FileOutputStream fileOutputStream = new FileOutputStream(file);

        List<SimpleObject> simpleObjectList = new ArrayList<>();
        simpleObjectList.add(new SimpleObject("11111111111", 1));
        simpleObjectList.add(new SimpleObject("22222222222", 2));
        simpleObjectList.add(new SimpleObject("", 3));
        simpleObjectList.add(new SimpleObject(null, 4));


        ExcelExportTool<SimpleObject> excelExportTool = new ExcelExportTool<>(SimpleObject.class, simpleObjectList, ExcelType.XLS, fileOutputStream);

        excelExportTool.write();

    }


    @Data
    public static class TestObjectWithList {
        @ExcelColumnNo(3)
        @ExcelHeaderName({"问题1", "问题2"})
        private List<String> extraList;
        @ExcelColumnNo(1)
        @ExcelHeaderName({"学生 id"})
        private String studentId;
        @ExcelColumnNo(2)
        @ExcelHeaderName({"学生姓名"})
        private String studentName;
    }

    @Data
    public static class SimpleObject {
        @ExcelHeaderName({"手机号"})
        @ExcelColumnNo(2)
        private String phoneNo;
        @ExcelHeaderName({"用户 id"})
        @ExcelColumnNo(1)
        private Integer userId;

        public SimpleObject(String phoneNo, Integer userId) {
            this.phoneNo = phoneNo;
            this.userId = userId;
        }
    }
}
