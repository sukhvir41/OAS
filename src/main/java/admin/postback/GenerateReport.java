/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;

import entities.Attendance;
import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Subject;
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
 * @author sukhvir
 */
@WebServlet(urlPatterns = "/admin/generatereportpost")
public class GenerateReport extends ReportPostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession session0, OutputStream out) throws Exception {

        resp.setContentType(
                "APPLICATION/OCTET-STREAM");
        resp.setHeader(
                "Content-Disposition", "attachment; filename=report " + new Date() + ".xlsx");
        Row row;

        Cell cell;
        LocalDateTime start, end;

        Workbook workbook = WorkbookFactory.create(true);
        Sheet spreadsheet = workbook.createSheet(" Report ");
        CreationHelper createHelper = workbook.getCreationHelper();

        int classroomId = Integer.parseInt(req.getParameter("classroom"));

        start = Utils.getStartDate(req.getParameter("startdate"));
        end = Utils.getEndDate(req.getParameter("enddate"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);
        Font font = workbook.createFont();
        font.setBold(false);
        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);

        // creating heading title of the exel sheet
        row = spreadsheet.createRow(0);// new row

        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        cell = row.createCell(0);
        RichTextString text = createHelper.createRichTextString("Classroom: " +
                classRoom.getName() +
                "   Division:   " +
                classRoom.getDivision() +
                "   Semester:   " +
                classRoom.getSemester() +
                "   Course:   " +
                classRoom.getCourse().getName() +
                "   From:   " +
                start.toString() +
                "   To:   " +
                end.toString());

        cell.setCellValue(text);

        //creating headers of the table in execl sheet
        row = spreadsheet.createRow(2);// new row

        cell = row.createCell(0);
        cell.setCellValue("Roll Number");
        cell = row.createCell(1);
        cell.setCellValue("Name");
        int cellnumberHead = 2;
        int maxCellNumber = 0;

        //getting the maximum number a subjects a stuent has
//        for (Student student : classRoom.getStudents()) {
//            if (student.getSubjects().size() > maxCellNumber) {
//                maxCellNumber = student.getSubjects().size();
//            }
//        }
        maxCellNumber = classRoom.getStudents()
                .stream()
                .map(student -> student.getSubjects())
                .mapToInt(subejcts -> subejcts.size())
                .max()
                .getAsInt();

        /* moving to row three for the folling type of formatting
           row 2 -----          Subejct
           row 3 ----- name | attedened | total|
        */
        row = spreadsheet.createRow(3);// new row

        Cell cellTemp = row.createCell(1);
        /*
        this creates the folling format 
   
                Subejct
        name | attedened | total|
        
        this happend maxCellNumber times
        */
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
        // end of setting headers of the exel sheet table

        //start of adding actual data
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
            cell.setCellValue(student.toString());
            cellNumber++;

            for (Subject subject : student.getSubjects()) {

                //getting lectures of the class and subject
//                List<Lecture> lectures = getLectures(classRoom, subject, session, start, end);
//                int lecturesCount = lectures.stream()
//                        .mapToInt(e -> e.getCount())
//                        .sum();
//                totalLectures += lecturesCount;
                int lecturesCount = getLectureCount(classRoom, subject, session, start, end);

                totalLectures += lecturesCount;

                //getting student attendace according to the lectures and the subject 
//                List<Attendance> studentAttendances = getStudentAttendance(student, lectures, session);
//                int attendanceCount = studentAttendances.stream()
//                        .mapToInt(e -> e.getLecture().getCount())
//                        .sum();
//                totalAttendance += attendanceCount;
                int attendanceCount = getStudentAttendanceCount(classRoom, subject, session, start, end, student);
                totalAttendance += attendanceCount;

                //calculating leaves according to the atendance
//                leaves += studentAttendances.stream()
//                        .filter(attendance -> attendance.isLeave())
//                        .mapToInt(attendance -> attendance.getLecture().getCount())
//                        .sum();
                leaves += getStudentAttendanceLeaveCount(classRoom, subject, session, start, end, student);

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
                cell.setCellValue("NA");
            }
            rowNumber++;

        }
        //excel table to auto resize itself to fit everything
        for (int i = 0; i <= cellnumberHead; i++) {
            spreadsheet.autoSizeColumn(i);
        }

        workbook.write(out);

    }

    /**
     * gets the lectures of the classroom and the subject. requires hibernate
     * session to do the search
     */
    private List<Lecture> getLectures(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        return session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria))
                .add(Restrictions.between("date", start, end))
                .addOrder(Order.desc("date"))
                .list();

//        
//        List<Teaching> teaching = session.createCriteria(Teaching.class)
//                .add(Restrictions.eq("classRoom", classRoom))
//                .add(Restrictions.eq("subject", subject))
//                .list();
//
//        if (teaching.size() > 0) {
//            return session.createCriteria(Lecture.class)
//                    .add(Restrictions.in("teaching", teaching))
//                    .add(Restrictions.between("date", start, end))
//                    .addOrder(Order.desc("date"))
//                    .list();
//        } else {
//            return new ArrayList<>();
//
//        }
    }

    /**
     * gets the list of attendance ie present attendance of the student for the
     * given lectures
     */
    private List<Attendance> getStudentAttendance(Student student, List<Lecture> lectures, Session session) {
        if (lectures.size() > 0) {
            return session.createCriteria(Attendance.class)
                    .add(Restrictions.in("lecture", lectures))
                    .add(Restrictions.eq("student", student))
                    .add(Restrictions.eqOrIsNull("attended", true))//  check is eqOrIsnull is requird or eq is fine
                    .list();

        } else {
            return new ArrayList<>();
        }
    }

    private int getLectureCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria))
                .add(Restrictions.between("date", start, end))
                .setProjection(Projections.sum("count"))
                .uniqueResult();

    }

    private int getStudentAttendanceCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end, Student student) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        DetachedCriteria attendanceCriteria = DetachedCriteria.forClass(Attendance.class)
                .add(Restrictions.eq("student", student))
                .add(Restrictions.eqOrIsNull("attended", true));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria))
                .add(Restrictions.between("date", start, end))
                .add(Property.forName("attendance").in(attendanceCriteria))
                .setProjection(Projections.sum("count"))
                .uniqueResult();

    }

    private int getStudentAttendanceLeaveCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end, Student student) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        DetachedCriteria attendanceCriteria = DetachedCriteria.forClass(Attendance.class)
                .add(Restrictions.eq("student", student))
                .add(Restrictions.eqOrIsNull("attended", true))
                .add(Restrictions.eq("leave", true));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria))
                .add(Restrictions.between("date", start, end))
                .add(Property.forName("attendance").in(attendanceCriteria))
                .setProjection(Projections.sum("count"))
                .uniqueResult();

    }

}
