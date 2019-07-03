package com.lxb.utils;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.hssf.util.Region;
import org.apache.poi.hssf.util.HSSFCellUtil;


import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * ${DESCRIPTION}
 *
 * @author Xiaobing.Lu
 * @create 2019-07-02 23:16
 **/
public class NewExcelExportUtil {
    public void exportExcel(HSSFWorkbook workbook, int sheetNum,
                            String sheetTitle, String[] headers, List<List<String>> result,
                            OutputStream out) throws Exception {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell((short) i);

            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 1;
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str.toString());
                    cellIndex++;
                }
                index++;
            }
        }
    }

    /**
     * @param outputStream 导出流
     * @param data         数据集 一个map表示一行  一个key表示一个单元格,如果单元格没有数据传“”,不能跳空传值
     * @throws IOException
     */
    public void exportFromList(OutputStream outputStream, List<Map<String, String>> data) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        generateHeader(workbook, sheet, data);
        HSSFCellStyle style = getCellStyle(workbook, false);
        for (int i = 1; i < data.size(); i++) {
            HSSFRow row = sheet.createRow(i);
            Map<String, String> map = data.get(i);
            Set<String> keySet = map.keySet();
            int columnIndex = 0;
            for (String key : keySet) {
                String value = map.get(key);
                HSSFCell cell = row.createCell(columnIndex);
                columnIndex++;
                cell.setCellStyle(style);
                cell.setCellValue(value);
            }
        }
        workbook.write(outputStream);
        outputStream.close();
    }

    /**
     * 设置表头
     *
     * @param workbook
     * @param sheet
     * @param headers
     * @Author wengf
     */
    public void generateHeader(HSSFWorkbook workbook, HSSFSheet sheet, List<Map<String, String>> headers) {
        HSSFCellStyle style = getCellStyle(workbook, true);
        Row row = sheet.createRow(0);
        row.setHeightInPoints(30);
        Map<String, String> map = headers.get(0);
        Set<String> keySet = map.keySet();
        int cellIndex = 0;
        for (Iterator<String> keyIterator = keySet.iterator(); keyIterator.hasNext(); ) {
            String key = (String) keyIterator.next();
            String header = map.get(key);
            Cell cell = row.createCell(cellIndex);
            sheet.setColumnWidth((cellIndex++), header.getBytes().length * 2 * 255);
            cell.setCellValue(header);
            cell.setCellStyle(style);
        }
    }

    /**
     * 设置单元格样式
     *
     * @param workbook
     * @param isHeader
     * @return
     * @Author wengf
     */
    public HSSFCellStyle getCellStyle(HSSFWorkbook workbook, boolean isHeader) {
        HSSFCellStyle style = workbook.createCellStyle();
        /*style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setLocked(true);
        if (isHeader) {//表头样式加粗
            style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
            style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
            style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//居中对齐
            style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//垂直居中
            HSSFFont font = workbook.createFont();
            font.setColor(HSSFColor.BLACK.index);
            font.setFontHeightInPoints((short) 12);
            font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            style.setFont(font);
        }*/
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);

        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);//下边框
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        return style;
    }

    public void exportExcel(HSSFWorkbook workbook, int sheetNum,
                            String sheetTitle, String[] fistHeaders, String[] secondHeaders, List<List<String>> result,
                            OutputStream out) throws Exception {
        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, sheetTitle);
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        // 产生表格标题行
        HSSFRow row = sheet.createRow(0);
        for (int i = 0; i < fistHeaders.length; i++) {
            HSSFCell cell = row.createCell((short) i);

            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(fistHeaders[i]);
            cell.setCellValue(text.toString());
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, secondHeaders.length - 1));

        row = sheet.createRow(1);
        for (int i = 0; i < secondHeaders.length; i++) {
            HSSFCell cell = row.createCell((short) i);

            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(secondHeaders[i]);
            cell.setCellValue(text.toString());
        }

        /*for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell((short) i);
            row = sheet.createRow(i);
            row.setHeight((short) 700);
            for (int j = 0; j < headers[i].length; j++) {
                cell = row.createCell(j);
                cell.setCellStyle(style);
                cell.setCellValue(headers[i][j]);
            }
        }*/

       /* for (int i = 0; i < headers.length; i++) {
            HSSFCell cell = row.createCell((short) i);

            cell.setCellStyle(style);
            HSSFRichTextString text = new HSSFRichTextString(headers[i]);
            cell.setCellValue(text.toString());
        }*/
        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 2;
            for (List<String> m : result) {
                row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str == null ? "" : str.toString());
                    cellIndex++;
                }
                index++;
            }
        }
    }


    public void exportExcel4Gather(HSSFWorkbook workbook, int sheetNum,
                                   String sheetTitle, List<List<String>> result,
                                   OutputStream out) throws Exception {


        // 生成一个表格
        HSSFSheet sheet = workbook.createSheet();
        workbook.setSheetName(sheetNum, "统计汇总表");
        // 设置表格默认列宽度为20个字节
        sheet.setDefaultColumnWidth((short) 20);
        // 生成一个样式
        HSSFCellStyle style = workbook.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
        style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        // 生成一个字体
        HSSFFont font = workbook.createFont();
        font.setColor(HSSFColor.BLACK.index);
        font.setFontHeightInPoints((short) 12);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        // 把字体应用到当前的样式
        style.setFont(font);

        // 指定当单元格内容显示不下时自动换行
        style.setWrapText(true);

        Region region1 = new Region(0, (short) 0, 0, (short) 16);
        Region region2 = new Region(1, (short) 2, 1, (short) 5);
        Region region3 = new Region(1, (short) 7, 1, (short) 10);
        Region region4 = new Region(1, (short) 12, 1, (short) 15);
        Region region5 = new Region(2, (short) 11, 2, (short) 12);
        Region region6 = new Region(3, (short) 7, 3, (short) 10);
        Region region7 = new Region(1, (short) 16, 2, (short) 16);
        Region region8 = new Region(1, (short) 0, 2, (short) 0);
        Region region9 = new Region(1, (short) 1, 2, (short) 1);

        //合并
        sheet.addMergedRegion(region1);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region2);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region3);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region4);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region5);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region6);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region7);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region8);// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(region9);// 起始行, 终止行, 起始列, 终止列

        /*
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 16));
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 2, 5));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 7, 10));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 12, 15));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 11, 12));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(3, 3, 7, 10));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 16, 16));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 0, 0));// 起始行, 终止行, 起始列, 终止列
        sheet.addMergedRegion(new CellRangeAddress(1, 2, 1, 1));// 起始行, 终止行, 起始列, 终止列
       */

        //go
        {
            HSSFRow row = sheet.createRow(0);
            for (int i = 0; i <= 16; i++) {
                HSSFCell cell = row.createCell(i);
            }
            HSSFCell cell = row.getCell(0);
            cell.setCellValue("X年X日-X日 XXX账单汇总表");
            style = getCellStyle(workbook, true);
            cell.setCellStyle(style);

            setRegionStyle(sheet, region1, style);

        }
        {
            HSSFRow row = sheet.createRow(1);
            for (int i = 0; i <= 16; i++) {
                HSSFCell cell = row.createCell(i);
            }
            HSSFCell cell0 = row.getCell(0);
            cell0.setCellValue("序号");

            HSSFCell cell1 = row.getCell(1);
            cell1.setCellValue("部门");

            HSSFCell cell2 = row.getCell(2);
            cell2.setCellValue("机票");

            HSSFCell cell7 = row.getCell(7);
            cell7.setCellValue("火车票");

            HSSFCell cell12 = row.getCell(12);
            cell12.setCellValue("酒店");

            HSSFCell cell16 = row.getCell(16);
            cell16.setCellValue("合计");
            cell0.setCellStyle(style);
            cell1.setCellStyle(style);
            cell2.setCellStyle(style);
            cell7.setCellStyle(style);
            cell12.setCellStyle(style);
            cell16.setCellStyle(style);
            HSSFCell cell6 = row.getCell(6);
            cell6.setCellStyle(style);
            HSSFCell cell11 = row.getCell(11);
            cell11.setCellStyle(style);


        }
        {
            HSSFRow row = sheet.createRow(2);
            for (int i = 0; i <= 16; i++) {
                HSSFCell cell = row.createCell(i);
            }

            HSSFCell cell2 = row.getCell(2);
            cell2.setCellValue("张数");
            HSSFCell cell3 = row.getCell(3);
            cell3.setCellValue("票款");
            HSSFCell cell4 = row.getCell(4);
            cell4.setCellValue("保险");
            HSSFCell cell5 = row.getCell(5);
            cell5.setCellValue("服务费");
            HSSFCell cell6 = row.getCell(6);
            cell6.setCellValue("小计");
            HSSFCell cell7 = row.getCell(7);
            cell7.setCellValue("张数");
            HSSFCell cell8 = row.getCell(8);
            cell8.setCellValue("票款");
            HSSFCell cell9 = row.getCell(9);
            cell9.setCellValue("服务费");
            HSSFCell cell10 = row.getCell(10);
            cell10.setCellValue("小计");
            HSSFCell cell11 = row.getCell(11);
            cell11.setCellValue("间夜");
            HSSFCell cell13 = row.getCell(13);
            cell13.setCellValue("房费");
            HSSFCell cell14 = row.getCell(14);
            cell14.setCellValue("服务费");
            HSSFCell cell15 = row.getCell(15);
            cell15.setCellValue("小计");

            cell2.setCellStyle(style);
            cell3.setCellStyle(style);
            cell4.setCellStyle(style);
            cell5.setCellStyle(style);
            cell6.setCellStyle(style);
            cell7.setCellStyle(style);
            cell8.setCellStyle(style);
            cell9.setCellStyle(style);

            cell10.setCellStyle(style);
            cell11.setCellStyle(style);
            cell13.setCellStyle(style);
            cell14.setCellStyle(style);
            cell15.setCellStyle(style);
        }

        setRegionStyle(sheet, region2, style);
        setRegionStyle(sheet, region3, style);
        setRegionStyle(sheet, region4, style);
        setRegionStyle(sheet, region5, style);
        setRegionStyle(sheet, region6, style);
        setRegionStyle(sheet, region7, style);
        setRegionStyle(sheet, region8, style);
        setRegionStyle(sheet, region9, style);


        // 遍历集合数据，产生数据行
        if (result != null) {
            int index = 3;
            for (List<String> m : result) {
                HSSFRow row = sheet.createRow(index);
                int cellIndex = 0;
                for (String str : m) {
                    HSSFCell cell = row.createCell((short) cellIndex);
                    cell.setCellValue(str.toString());
                    cellIndex++;
                }
                index++;
            }
        }
    }

    public void download(final String path, final HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            final File file = new File(path);
            // 取得文件名。
            final String filename = file.getName();
            // 以流的形式下载文件。
            final InputStream fis = new BufferedInputStream(new FileInputStream(path));
            final byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            // response.addHeader("Content-Disposition", "attachment;filename="
            // + new String(filename.getBytes()));
            // response.addHeader("Content-Length", "" + file.length());
            // response.setContentType("application/vnd.ms-excel;charset=utf-8");
            final OutputStream toClient = new BufferedOutputStream(response.getOutputStream());

            response.setContentType("application/x-download");// 下面三行是关键代码，处理乱码问题
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-Disposition",
                    "attachment;filename=" + new String(filename.getBytes("gbk"), "iso8859-1"));

            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (final IOException ex) {
            ex.printStackTrace();
        }
    }

    @SuppressWarnings("deprecation")
    public static void setRegionStyle(HSSFSheet sheet, Region region, HSSFCellStyle cs) {
        for (int i = region.getRowFrom(); i <= region.getRowTo(); i++) {
            HSSFRow row = HSSFCellUtil.getRow(i, sheet);
            for (int j = region.getColumnFrom(); j <= region.getColumnTo(); j++) {
                HSSFCell cell = HSSFCellUtil.getCell(row, (short) j);
                cell.setCellStyle(cs);
            }
        }
    }

}
