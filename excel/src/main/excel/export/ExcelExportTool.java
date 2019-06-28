package excel.export;

import excel.common.ExcelColumnNo;
import excel.common.NumberSortDto;
import excel.common.ReflectUtil;
import lombok.Data;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * author:      shfq
 * description:
 * create date: 2018/09/12.
 */
public class ExcelExportTool<T> {
    private final List<T> objectList;
    private List<GetterMethodAndHeaderNames> getterMethodAndHeaderNames;
    private final Workbook workbook;
    private final OutputStream outputStream;

    public ExcelExportTool(Class<T> myTClass, List<T> objectList, ExcelType excelType, OutputStream outputStream) throws Exception {
        this.objectList = objectList;
        this.workbook = WorkbookFactory.create(excelType);
        this.outputStream = outputStream;

        Field[] fields = myTClass.getDeclaredFields();
        List<NumberSortDto<GetterMethodAndHeaderNames>> sortDtoList = new ArrayList<>();
        for (Field field : fields) {
            ExcelHeaderName excelHeaderName = field.getAnnotation(ExcelHeaderName.class);
            ExcelColumnNo excelColumnNo = field.getAnnotation(ExcelColumnNo.class);
            if (excelHeaderName != null && excelColumnNo != null) {
                if (excelHeaderName.value() == null || excelHeaderName.value().length == 0) {
                    throw new RuntimeException("ExcelHeaderName value can not been null");
                }
                Method getterMethod = ReflectUtil.getGetterMethod(myTClass, field.getName());
                GetterMethodAndHeaderNames getterMethodAndHeaderNames = new GetterMethodAndHeaderNames(getterMethod, excelHeaderName.value());
                NumberSortDto sortDto = new NumberSortDto(excelColumnNo.value(), getterMethodAndHeaderNames);

                sortDtoList.add(sortDto);
            }
        }
        if (sortDtoList.size() == 0) {
            throw new RuntimeException("ExcelHeaderName 和 ExcelColumnNo 不能为空");
        }
        sortDtoList.sort(Comparator.comparing(NumberSortDto::getSortNo));
        getterMethodAndHeaderNames =  sortDtoList.stream().map(NumberSortDto::getObject).collect(Collectors.toList());

    }

    public void write() throws Exception {
        try {
            Sheet sheet = workbook.createSheet();
            setExcelTableHeader(sheet.createRow(0));

            // 在循环内部创建 style 的话,有限制,最多允许创建 4000 个 style ,超过 4000 的话,会报错,所以创建 style 放到循环的外部
            CellStyle style = workbook.createCellStyle();
            DataFormat dataFormat = workbook.createDataFormat();
            style.setDataFormat(dataFormat.getFormat("0"));

            if (objectList != null && objectList.size() != 0) {
                int rowId = 1;
                for (T object : objectList) {
                    if (object != null) {
                        Row row = sheet.createRow(rowId);
                        int cellId = 0;

                        for (GetterMethodAndHeaderNames getterMethodAndHeaderName : getterMethodAndHeaderNames) {
                            Method getterMethod = getterMethodAndHeaderName.getGetterMethod();
                            Object fieldValue = ReflectUtil.callMethod(object, getterMethod);

                            int i = 0;
                            for (String headerName : getterMethodAndHeaderName.getHeaderNames()) {
                                Cell cell = row.createCell(cellId++);
                                if (fieldValue != null) {
                                    if (fieldValue instanceof List) {
                                        List list = (List) fieldValue;
                                        if (i < list.size()) {
                                            setCellValue(cell, list.get(i), style);
                                        }

                                    } else {
                                        setCellValue(cell, fieldValue, style);
                                    }
                                }

                                i++;
                            }
                        }
                        rowId++;
                    }
                }
            }
            workbook.write(outputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                //outputStream.flush();
                outputStream.close();
            }

        }
    }

    private void setCellValue(Cell cell, Object o, CellStyle style) {
        if (o == null) {
            cell.setCellValue("");
            return;
        }
        if (o instanceof Date) {
            cell.setCellValue((Date) o);
        } else if (o instanceof Calendar) {
            cell.setCellValue((Calendar) o);
        } else if (o instanceof Boolean) {
            cell.setCellValue((Boolean) o);
        } else if (o instanceof Number) {
            if (o instanceof Short || o instanceof Integer || o instanceof Long) {
                DataFormat dataFormat = workbook.createDataFormat();
                style.setDataFormat(dataFormat.getFormat("0"));
                cell.setCellStyle(style);
                cell.setCellValue((Double.parseDouble(o.toString())));
                return;
            } else if (o instanceof Float || o instanceof Double) {
                cell.setCellValue(Double.parseDouble(o.toString()));
                return;
            } else {
                cell.setCellValue(o.toString());
                return;
            }
        } else {
            if (o != null) {
                cell.setCellValue(o.toString());
            } else {
                cell.setCellValue("");
            }
        }

    }

    private void setExcelTableHeader(Row row) {
        int cellId = 0;
        for (GetterMethodAndHeaderNames getterMethodAndHeaderName : getterMethodAndHeaderNames) {
            String[] headerNames = getterMethodAndHeaderName.getHeaderNames();
            for (String headerName : headerNames) {
                Cell cell = row.createCell(cellId++);
                cell.setCellValue(headerName);
            }
        }
    }

}
