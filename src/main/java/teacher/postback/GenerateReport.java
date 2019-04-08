/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package teacher.postback;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Teacher;
import entities.Teaching;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import utility.ReportPostBackController;
import utility.Utils;

/**
 *
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/teacher/generatereportpost")
public class GenerateReport extends ReportPostBackController {

//    private List<Student> getStudents(Session session, ClassRoom classRoom, Subject subject) {
//
//        return session.createCriteria(Student.class)
//                .add(Restrictions.eq("classRoom", classRoom))
//                .add
//    
//    }

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession httpSession, OutputStream out) throws Exception {
        Teacher teacher = (Teacher) httpSession.getAttribute("teacher");
        teacher = (Teacher) session.get(Teacher.class, teacher.getId());

        int teachingId = Integer.parseInt(req.getParameter("teaching"));
        Teaching teaching = (Teaching) session.get(Teaching.class, teachingId);

        LocalDateTime startDate = Utils.getStartDate(req.getParameter("startdate"));
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

        Workbook workbook = WorkbookFactory.create(true);
        Sheet spreadsheet = workbook.createSheet(" Report ");
        CreationHelper createHelper = workbook.getCreationHelper();

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        Font font = workbook.createFont();
        font.setBold(false);
        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);

        // creating heading title of the exel sheet
        Row row = spreadsheet.createRow(0);// new row

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        Cell cell = row.createCell(0);

        RichTextString text = createHelper.createRichTextString("Classroom: " +
                classRoom.getName() +
                "   Division:   " +
                classRoom.getDivision() +
                "   Semester:   " +
                classRoom.getSemester() +
                "   Course:   " +
                classRoom.getCourse().getName() +
                "   From:   " +
                startDate.toString() +
                "   To:   " +
                endDate.toString());

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

        workbook.write(out);

    }

    private int getAttended(Lecture lecture, Student student) {

        //todo : rewrite sql for this
       /* return lecture.getAttendance()
                .stream()
                .filter(attendace -> attendace.isAttended())
                .filter(attendace -> attendace.getStudent().getId() == student.getId())
                .mapToInt(attendance -> lecture.getCount())
                .sum();*/
       return 0;
    }

//    private int getAttendedCount(Lecture lecture, Student student, Session session) {
//
//        session.createCriteria(Attendance.class)
//                .add(Restrictions.eq("lecture", lecture))
//                .add(Restrictions.eq("student", student))
//                .setProjection(Projections.sum(""))
//        
//    }
}
