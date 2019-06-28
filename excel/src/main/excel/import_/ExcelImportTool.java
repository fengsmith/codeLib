package excel.import_;

import excel.common.ExcelColumnNo;
import excel.common.NumberSortDto;
import excel.common.ReflectUtil;
import lombok.Data;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:      shfq
 * create date: 2019/04/30.
 *
 * @param <T>
 */
public class ExcelImportTool<T> {
    private final Class<T> type;
    private final List<Method> setterMethodList;
    private final Sheet sheet;
    private List<T> list;

    public ExcelImportTool(InputStream inputStream, Class<T> type) throws Exception {
        if (inputStream == null) {
            throw new RuntimeException("inputStream can not been null");
        }
        if (type == null) {
            throw new RuntimeException("type can not been null");
        }
        this.type = type;

        Field[] fields = type.getDeclaredFields();
        List<NumberSortDto<Method>> numberSortDtoList = new ArrayList<>();
        for (Field field : fields) {
            ExcelColumnNo excelColumnNo = field.getAnnotation(ExcelColumnNo.class);
            if (excelColumnNo != null) {
                Method setterMethod = ReflectUtil.getSetterMethod(type, field.getName(), field.getType());
                numberSortDtoList.add(new NumberSortDto<>(excelColumnNo.value(), setterMethod));
            }
        }
        if (numberSortDtoList.size() == 0) {
            throw new RuntimeException(type.getName() + " 的字段没有 ExcelColumnNo 注解");
        }
        numberSortDtoList.sort(Comparator.comparing(NumberSortDto::getSortNo));
        setterMethodList = numberSortDtoList.stream().map(NumberSortDto::getObject).collect(Collectors.toList());
        try {
            Workbook workbook = WorkbookFactory.create(inputStream);
            sheet = workbook.getSheetAt(0);
        } catch (Exception e) {
            throw new RuntimeException("construct Workbook failed");
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("关闭流失败");
                }
            }
        }
    }

    public List<T> getList() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        if (list == null) {
            list = traverseSheet();
        }
        return list;
    }

    private List<T> traverseSheet() throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<T> list = new ArrayList<T>();
        int lastRowNum = sheet.getLastRowNum();
        // 忽略掉第一行表头记录
        for (int i = 1; i <= lastRowNum; i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            T t = parseRecord(row);
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    private T parseRecord(Row row) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        T targetObject = null;
        targetObject = type.newInstance();
        boolean blankRow = true;
        for (int i = 0; i < setterMethodList.size(); i++) {
            Cell cell = row.getCell(i);
            String cellValue = "";
            if (cell != null) {
                cellValue = cell.toString();
            }
            if (!"".equals(cellValue)) {
                blankRow = false;
            }
            Object value = null;
            Method method = setterMethodList.get(i);
            Class parameterType = method.getParameterTypes()[0];
            if (cell != null && StringUtils.isNotEmpty(cell.toString())) {
                CellType cellType = cell.getCellTypeEnum();
                if (cellType == CellType.STRING && String.class == parameterType) {
                    value = cell.getStringCellValue().trim();
                }
                if (cellType == CellType.NUMERIC) {
                    BigDecimal bigDecimal = new BigDecimal(cell.getNumericCellValue());
                    if (parameterType == Integer.class || parameterType == int.class ||
                            parameterType == Long.class || parameterType == long.class ||
                            parameterType == Short.class || parameterType == short.class ||
                            parameterType == Float.class || parameterType == float.class ||
                            parameterType == Double.class || parameterType == double.class) {
                        if (parameterType == Integer.class || parameterType == int.class) {
                            value = bigDecimal.intValue();
                        }
                        if (parameterType == Long.class || parameterType == long.class) {
                            value = bigDecimal.longValue();
                        }
                        if (parameterType == Short.class || parameterType == short.class) {
                            value = bigDecimal.shortValue();
                        }
                        if (parameterType == Float.class || parameterType == float.class) {
                            value = bigDecimal.floatValue();
                        }
                        if (parameterType == Double.class || parameterType == double.class) {
                            value = bigDecimal.doubleValue();
                        }
                    } else if (parameterType == String.class) {
                        value = bigDecimal.toString();
                    }
                }
                if (value != null) {
                    method.invoke(targetObject, value);
                }

            }
        }
        if (blankRow) {
            return null;
        }
        return targetObject;
    }

    public static void main(String[] args) {
        try {
            FileInputStream fileInputStream = new FileInputStream("./excel/src/main/resources/导入模板.xlsx");
            ExcelImportTool excelImportTool = new ExcelImportTool(fileInputStream, TempDto.class);
            List<TempDto> tempDtoList = excelImportTool.getList();
            System.out.println("");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Data
    public static class TempDto {
        @ExcelColumnNo(2)
        private String phone;
        @ExcelColumnNo(1)
        private Long accountId;
    }
}
