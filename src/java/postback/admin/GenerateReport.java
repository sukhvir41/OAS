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
 * @author sukhvir import org.hibernate.Session; import utility.Utils;
 */
@WebServlet(urlPatterns = "/admin/generatereportpost")
public class GenerateReport extends HttpServlet {

    private XSSFRow row;
    private Session session;
    private XSSFCell cell;
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
            Font fontBold = workbook.createFont();
            fontBold.setBold(true);
            Font font = workbook.createFont();
            font.setBold(false);
            CellStyle styleCenter = workbook.createCellStyle();
            styleCenter.setAlignment(HorizontalAlignment.CENTER);

            row = spreadsheet.createRow(0);
            spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
            cell = row.createCell(0);
            XSSFRichTextString text = new XSSFRichTextString("Classroom: ");
            text.append(classRoom.getName(), (XSSFFont) fontBold);
            text.append("   Division:   ", (XSSFFont) font);
            text.append(classRoom.getDivision(), (XSSFFont) fontBold);
            text.append("   Semester:   ", (XSSFFont) font);
            text.append(String.valueOf(classRoom.getSemister()), (XSSFFont) fontBold);
            text.append("   Course:   ", (XSSFFont) font);
            text.append(classRoom.getCourse().getName(), (XSSFFont) fontBold);
            text.append("   From:   ", (XSSFFont) font);
            text.append(dateForamt.format(start), (XSSFFont) fontBold);
            text.append("   To:   ", (XSSFFont) font);
            text.append(dateForamt.format(end), (XSSFFont) fontBold);
            cell.setCellValue(text);

            row = spreadsheet.createRow(2);
            cell = row.createCell(0);
            cell.setCellValue("Roll Number");
            cell = row.createCell(1);
            cell.setCellValue("Name");
            int cellnumberHead = 2;
            int maxCellNumber = 0;
            for (Student student : classRoom.getStudents()) {
                maxCellNumber = student.getSubjects().size() > maxCellNumber ? student.getSubjects().size() : maxCellNumber;
            }

            row = spreadsheet.createRow(3);
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

            row = cell.getRow();
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
            int rowNumber = 4;
            List<Student> students = classRoom.getStudents();
            Collections.sort(students);
            for (Student student : students) {

                int cellNumber = 0;
                int totalLectures = 0;
                int totalAttendance = 0;
                int leaves = 0;

                row = spreadsheet.createRow(rowNumber);
                cell = row.createCell(cellNumber);
                cell.setCellValue(student.getRollNumber());
                cellNumber++;
                cell = row.createCell(cellNumber);
                cell.setCellValue(student.getfName() + " " + student.getlName());
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

                    cell = row.createCell(cellNumber++);
                    cell.setCellValue(subject.getName());

                    cell = row.createCell(cellNumber++);
                    cell.setCellValue(attendanceCount);

                    cell = row.createCell(cellNumber++);
                    cell.setCellValue(lecturesCount);
                    cell.setCellStyle(style);

                }
                for (int i = 0; i < maxCellNumber - student.getSubjects().size(); i++) {
                    cell = row.createCell(cellNumber++);
                    cell.setCellValue("");
                    cell = row.createCell(cellNumber++);
                    cell.setCellValue("");
                    cell = row.createCell(cellNumber++);
                    cell.setCellValue("");
                }
                cell = row.createCell(cellNumber++);
                cell.setCellValue(leaves);
                cell = row.createCell(cellNumber++);
                cell.setCellValue(totalAttendance);
                cell = row.createCell(cellNumber++);
                cell.setCellValue(totalLectures);
                cell.setCellStyle(style);
                cell = row.createCell(cellNumber);
                if (totalLectures > 0) {
                    cell.setCellValue((((double) totalAttendance / (double) totalLectures) * 100d) + "%");
                } else {
                    cell.setCellValue("100%");
                }
                rowNumber++;

            }
            for (int i = 0; i <= cellnumberHead; i++) {
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
        if (teaching.size() > 0) {
            return session.createCriteria(Lecture.class)
                    .add(Restrictions.in("teaching", teaching))
                    //.add(Restrictions.between("date", start, end))
                    .list();
        } else {
            return new ArrayList<>();
        }
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
