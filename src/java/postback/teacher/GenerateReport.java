/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.teacher;

import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Teacher;
import entities.Teaching;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
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
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/generatereportpost")
public class GenerateReport extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Session session = Utils.openSession();
        session.beginTransaction();
        try {
            process(req, resp, session, req.getSession());
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            session.getTransaction().rollback();
            session.close();
            e.printStackTrace();
            resp.sendRedirect("/OAS/error");
        } finally {

        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("/OAS/error");
    }

    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        int teachingId = Integer.parseInt(req.getParameter("teaching"));
        Teaching teaching = (Teaching) session.get(Teaching.class, teachingId);

        LocalDateTime startDate = Utils.getStartdate(req.getParameter("startdate"));
        LocalDateTime endDate = Utils.getEndDate(req.getParameter("enddate"));

        ClassRoom classRoom = teaching.getClassRoom();

        List<Student> students = teaching.getClassRoom()
                .getStudents()
                .stream()
                .filter(student -> student.getSubjects().contains(teaching.getSubject()))
                .sorted()
                .collect(Collectors.toList());

        List<Lecture> lectures = session.createCriteria(Lecture.class)
                .add(Restrictions.eq("teaching", teaching))
                .add(Restrictions.between("date", startDate, endDate))
                .list();

        int totalLecture = lectures.stream()
                .mapToInt(lecture -> lecture.getCount())
                .sum();

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

        // creating heading title of the exel sheet
        XSSFRow row = spreadsheet.createRow(0);// new row

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        XSSFCell cell = row.createCell(0);
        XSSFRichTextString text = new XSSFRichTextString("Classroom: ");
        text.append(classRoom.getName(), (XSSFFont) fontBold);
        text.append("   Division:   ", (XSSFFont) font);
        text.append(classRoom.getDivision(), (XSSFFont) fontBold);
        text.append("   Semester:   ", (XSSFFont) font);
        text.append(String.valueOf(classRoom.getSemister()), (XSSFFont) fontBold);
        text.append("   Course:   ", (XSSFFont) font);
        text.append(classRoom.getCourse().getName(), (XSSFFont) fontBold);
        text.append("   From:   ", (XSSFFont) font);
        text.append(startDate.toString(), (XSSFFont) fontBold);
        text.append("   To:   ", (XSSFFont) font);
        text.append(endDate.toString(), (XSSFFont) fontBold);
        cell.setCellValue(text);

        row = spreadsheet.createRow(2);

        cell = row.createCell(0);
        cell.setCellValue("Subejct : " + teaching.getSubject().toString() + " teacher : " + teacher.toString());

        row = spreadsheet.createRow(3); // new Row

        cell = row.createCell(0);
        cell.setCellValue("Roll Number");
        cell = row.createCell(1);
        cell.setCellValue("Name");
        cell = row.createCell(2);
        cell.setCellValue("attended");
        cell = row.createCell(3);
        cell.setCellValue("total");
        cell = row.createCell(4);
        cell.setCellValue("percentage");

        int rowNumber = 4;

        for (Student student : students) {
            row = spreadsheet.createRow(rowNumber);

            cell = row.createCell(0);
            cell.setCellValue(student.getRollNumber());

            cell = row.createCell(1);
            cell.setCellValue(student.toString());

            int attended = lectures.stream()
                    .mapToInt(lecture -> getAttended(lecture, student))
                    .sum();

            cell = row.createCell(2);
            cell.setCellValue(attended);

            cell = row.createCell(3);
            cell.setCellStyle(style);
            cell.setCellValue(totalLecture);

            cell = row.createCell(4);
            if (totalLecture > 0) {
                double percentage = ((double) attended / (double) totalLecture) * 100d;
                System.out.println(percentage);
                cell.setCellValue(percentage + "%");
            } else {
                cell.setCellValue("NA");
            }
            rowNumber++;
        }

        resp.setContentType(
                "APPLICATION/OCTET-STREAM");
        resp.setHeader(
                "Content-Disposition", "attachment; filename=report " + new Date() + ".xlsx");

        workbook.write(resp.getOutputStream());
        resp.getOutputStream().close();

    }

    private int getAttended(Lecture lecture, Student student) {
        return lecture.getAttendance()
                .stream()
                .filter(attendace -> attendace.isAttended())
                .filter(attendace -> attendace.getStudent().getId() == student.getId())
                .mapToInt(attendance -> lecture.getCount())
                .sum();
    }

}
