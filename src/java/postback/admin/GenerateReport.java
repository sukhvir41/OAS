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
import javax.servlet.ServletException;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

            ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

            row = spreadsheet.createRow(0);
            c = row.createCell(0);
            c.setCellValue(classRoom.getName() + " " + classRoom.getDivision() + "  " + classRoom.getCourse().getName()
                    + " from " + start + " to " + end);

            int rowNumber = 1;

            for (Student student : classRoom.getStudents()) {

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
                    
                    List<Lecture> lectures = getLectures(classRoom, subject);
                    int lecturesCount = lectures.stream()
                            .map(e -> e.getCount())
                            .reduce(0, (c, e) -> c + e);
                    totalLectures += lecturesCount;
                    
                    List<Attendance> studentAttendances = getStudentAttendance(student, lectures);
                    int attendanceCount = studentAttendances.stream()
                            .map(e -> e.getLecture().getCount())
                            .reduce(0, (c, e) -> c + e);
                    totalAttendance += attendanceCount;

                    c = row.createCell(cellNumber);
                    c.setCellValue(subject.getName() + " " + attendanceCount + "/" + lecturesCount);
                    cellNumber++;
                }

                c = row.createCell(cellNumber);
                c.setCellValue("leaves " + leaves);
                cellNumber++;
                c = row.createCell(cellNumber);
                c.setCellValue(totalAttendance + "/" + totalLectures);
                cellNumber++;
                c = row.createCell(cellNumber);
                if (totalLectures > 0) {
                    c.setCellValue("percentage : " + ((totalAttendance / totalLectures) * 100));
                } else {
                    c.setCellValue("percentage : 100%");
                }
                rowNumber++;
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
                .add(Restrictions.between("date", start, end))
                .list();
    }

    private List<Attendance> getStudentAttendance(Student student, List<Lecture> lectures) {
        if (lectures.size() > 0) {
            return session.createCriteria(Attendance.class)
                    .add(Restrictions.in("lecture", lectures))
                    .add(Restrictions.eq("student", student))
                    .add(Restrictions.eq("attended", true))
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
