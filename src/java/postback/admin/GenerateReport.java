/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package postback.admin;

import entities.Attendance;
import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Subject;
import entities.Teaching;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import utility.Utils;

/**
 *
 * @author sukhvir import org.hibernate.Session; import utility.Utils;
 */
@WebServlet(urlPatterns = "/admin/generatereportpost")
public class GenerateReport extends HttpServlet {

    private XSSFRow row;
    private Session session;
    private XSSFCell c;
    private Date start, end;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        resp.setContentType("APPLICATION/OCTET-STREAM");
        resp.setHeader("Content-Disposition", "attachment; filename=report " + new Date() + ".xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Report ");

        session = Utils.openSession();
        session.beginTransaction();
        OutputStream out = resp.getOutputStream();

        try {
            int classroomId = Integer.parseInt(req.getParameter("classroom"));
            System.out.println(req.getParameter("startdate"));
            start = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("startdate"));
            end = new SimpleDateFormat("yyyy-mm-dd").parse(req.getParameter("enddate"));
            SimpleDateFormat dateForamt = new SimpleDateFormat("dd-mm-yyyy");
            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

            CellStyle style = workbook.createCellStyle();
            style.setAlignment(HorizontalAlignment.LEFT);
            Font font = workbook.createFont();
            font.setBold(true);
            CellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(font);
            row = spreadsheet.createRow(0);
            c = row.createCell(0);
            c.setCellValue("ClassRoom:");
            c = row.createCell(1);
            c.setCellValue(classRoom.getName());
            c.setCellStyle(styleBold);
            c = row.createCell(2);
            c.setCellValue("Division:");
            c = row.createCell(3);
            c.setCellValue(classRoom.getDivision());
            c.setCellStyle(styleBold);
            c = row.createCell(4);
            c.setCellValue("Semester:");
            c = row.createCell(5);
            c.setCellValue(classRoom.getSemister() + "");
            c.setCellStyle(styleBold);
            c = row.createCell(6);
            c.setCellValue("Course:");
            c = row.createCell(7);
            c.setCellValue(classRoom.getCourse().getName());
            c.setCellStyle(styleBold);
            c = row.createCell(8);
            c.setCellValue("From:" + dateForamt.format(start));
            c.setCellStyle(styleBold);
            c = row.createCell(9);
            c.setCellValue("To:" + dateForamt.format(end));
            c.setCellStyle(styleBold);

            row = spreadsheet.createRow(2);
            c = row.createCell(0);
            c.setCellValue("Roll Number");
            c = row.createCell(1);
            c.setCellValue("Name");
            int cellnumberHead = 2;
            int maxCellNumber = 0;
            for (Student student : classRoom.getStudents()) {
                maxCellNumber = student.getSubjects().size() > maxCellNumber ? student.getSubjects().size() : maxCellNumber;
            }
            for (int i = cellnumberHead; i < maxCellNumber + 2; i++) {
                c = row.createCell(cellnumberHead++);
                c.setCellValue("Subject");
                c = row.createCell(cellnumberHead++);
                c.setCellValue("attended");
                c = row.createCell(cellnumberHead++);
                c.setCellValue("total");
            }
            c = row.createCell(cellnumberHead++);
            c.setCellValue("leaves");
            c = row.createCell(cellnumberHead++);
            c.setCellValue("Overall total");
            c = row.createCell(cellnumberHead++);
            c.setCellValue("percentage");
            int rowNumber = 4;
            List<Student> students = classRoom.getStudents();
            Collections.sort(students);
            for (Student student : students) {

                int cellNumber = 0;
                int totalLectures = 0;
                int totalAttendance = 0;
                int leaves = 0;

                row = spreadsheet.createRow(rowNumber);
                c = row.createCell(cellNumber);
                c.setCellValue(student.getRollNumber());
                cellNumber++;
                c = row.createCell(cellNumber);
                c.setCellValue(student.getfName() + " " + student.getlName());
                cellNumber++;

                for (Subject subject : student.getSubjects()) {

                    //getting lectures of the class and subject
                    List<Lecture> lectures = getLectures(classRoom, subject);
                    int lecturesCount = lectures.stream()
                            .map(e -> e.getCount())
                            .reduce(0, (c, e) -> c + e);
                    totalLectures += lecturesCount;

                    //getting student attendace according to the lectures and the subject 
                    List<Attendance> studentAttendances = getStudentAttendance(student, lectures);
                    int attendanceCount = studentAttendances.stream()
                            .map(e -> e.getLecture().getCount())
                            .reduce(0, (c, e) -> c + e);
                    totalAttendance += attendanceCount;

                    //calculating leaves according to the atendance
                    leaves += studentAttendances.stream()
                            .filter(attendance -> attendance.isLeave())
                            .map(attendance -> attendance.getLecture().getCount())
                            .reduce(0, (c, e) -> c + e);

                    c = row.createCell(cellNumber++);
                    c.setCellValue(subject.getName());

                    c = row.createCell(cellNumber++);
                    c.setCellValue(attendanceCount);

                    c = row.createCell(cellNumber++);
                    c.setCellValue(lecturesCount);
                    c.setCellStyle(style);

                }
                for (int i = 0; i < maxCellNumber - student.getSubjects().size(); i++) {
                    c = row.createCell(cellNumber++);
                    c.setCellValue("");
                    c = row.createCell(cellNumber++);
                    c.setCellValue("");
                    c = row.createCell(cellNumber++);
                    c.setCellValue("");
                }
                c = row.createCell(cellNumber);
                c.setCellValue(leaves);
                cellNumber++;
                c = row.createCell(cellNumber);
                c.setCellValue(totalAttendance + "/" + totalLectures);
                cellNumber++;
                c = row.createCell(cellNumber);
                if (totalLectures > 0) {
                    c.setCellValue((((double) totalAttendance / (double) totalLectures) * 100d) + "%");
                } else {
                    c.setCellValue("100%");
                }
                rowNumber++;

            }
            for (int i = 0; i < 30; i++) {
                spreadsheet.autoSizeColumn(i);
            }
            session.getTransaction().commit();
            session.close();
            workbook.write(out);
        } catch (Exception e) {
            e.printStackTrace();;
            session.getTransaction().rollback();
            session.close();
        } finally {
            out.close();
        }
    }

    private List<Lecture> getLectures(ClassRoom classRoom, Subject subject) {
        List<Teaching> teaching = session.createCriteria(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject))
                .list();

        return session.createCriteria(Lecture.class)
                .add(Restrictions.in("teaching", teaching))
                //.add(Restrictions.between("date", start, end))
                .list();
    }

    private List<Attendance> getStudentAttendance(Student student, List<Lecture> lectures) {
        if (lectures.size() > 0) {
            return session.createCriteria(Attendance.class)
                    .add(Restrictions.in("lecture", lectures))
                    .add(Restrictions.eq("student", student))
                    .add(Restrictions.eqOrIsNull("attended", true))
                    .list();

        } else {
            return new ArrayList<>();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("error");
    }

}
