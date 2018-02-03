/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utility;

import com.google.gson.JsonObject;
import entities.Lecture;
import entities.UserType;
import java.io.FileOutputStream;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.helpers.XSSFRowShifter;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.Sigar;

/**
 *
 * @author sukhvir
 */
public class Testing {

    public static void main(String[] args) throws Exception {
        XSSFRow row;
        XSSFCell cell;
        LocalDateTime start, end;

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Report ");
        
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        Font font = workbook.createFont();
        font.setBold(false);
        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);
        
        
        row = spreadsheet.createRow(0);// new row

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        cell = row.createCell(0);
        XSSFRichTextString text = new XSSFRichTextString("Classroom: ");
        text.append("fybsc it", (XSSFFont) fontBold);
        text.append("   Division:   ", (XSSFFont) font);
        text.append("A", (XSSFFont) fontBold);
        text.append("   Semester:   ", (XSSFFont) font);
        text.append("1", (XSSFFont) fontBold);
        text.append("   Course:   ", (XSSFFont) font);
        text.append("bsc it", (XSSFFont) fontBold);
        text.append("   From:   ", (XSSFFont) font);
        text.append("start date", (XSSFFont) fontBold);
        text.append("   To:   ", (XSSFFont) font);
        text.append("end date", (XSSFFont) fontBold);
        cell.setCellValue(text);

        //creating headers of the table in execl sheet
        row = spreadsheet.createRow(2);// new row

        cell = row.createCell(0);
        cell.setCellValue("Roll Number");
        cell = row.createCell(1);
        cell.setCellValue("Name");
        int cellnumberHead = 2;
        int maxCellNumber = 10;

        //getting the maximum number a subjects a stuent has
//        for (Student student : classRoom.getStudents()) {
//            if (student.getSubjects().size() > maxCellNumber) {
//                maxCellNumber = student.getSubjects().size();
//            }
//        }
       

        row = spreadsheet.createRow(3);// new row

        XSSFCell cellTemp = row.createCell(1);
        for (int i = 0; i < maxCellNumber; i++) {
            spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, cellnumberHead, cellnumberHead + 2));
            row = cell.getRow();
            cell = row.createCell(cellnumberHead++);
            cell.setCellValue("Subject");
            cell.setCellStyle(styleCenter);
            row = cellTemp.getRow();
            cellTemp = row.createCell(cellnumberHead - 1);
            cellTemp.setCellValue("Name");
            cellTemp = row.createCell(cellnumberHead);
            cellTemp.setCellValue("Attended");
            cellTemp = row.createCell(cellnumberHead + 1);
            cellTemp.setCellValue("Total");
            row = cell.getRow();
            cellnumberHead++;
            cellnumberHead++;
        }

        // row = cell.getRow();
        cell = row.createCell(cellnumberHead++);
        cell.setCellValue("Leaves");
        spreadsheet.addMergedRegion(new CellRangeAddress(2, 2, cellnumberHead, cellnumberHead + 1));
        cell = row.createCell(cellnumberHead++);
        cell.setCellValue("Overall total");
        cell.setCellStyle(styleCenter);
        row = cellTemp.getRow();
        cellTemp = row.createCell(cellnumberHead - 1);
        cellTemp.setCellValue("Attended");
        cellTemp = row.createCell(cellnumberHead);
        cellTemp.setCellValue("Total");
        row = cell.getRow();
        cell = row.createCell(++cellnumberHead);
        cell.setCellValue("Percentage");
        
        
        
        workbook.write(new FileOutputStream("reporttest.xlsx"));
        
    }

}
