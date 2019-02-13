/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package admin.postback;

import entities.Attendance;
import entities.ClassRoom;
import entities.Lecture;
import entities.Student;
import entities.Subject;
import entities.Teaching;
import java.io.OutputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import lombok.Getter;
import lombok.Setter;
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
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import utility.ReportPostBackController;
import utility.Utils;

/**
 *
 * @author development
 */
public class GenerateReportFast extends ReportPostBackController {

    @Override
    public void process(HttpServletRequest req, HttpServletResponse resp, Session session, HttpSession session0, OutputStream out) throws Exception {
        resp.setContentType(
                "APPLICATION/OCTET-STREAM");
        resp.setHeader(
                "Content-Disposition", "attachment; filename=report " + new Date() + ".xlsx");

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet spreadsheet = workbook.createSheet(" Report ");

        //request parameters 
        final int classroomId = Integer.parseInt(req.getParameter("classroom"));
        final LocalDateTime start = Utils.getStartdate(req.getParameter("startdate"));
        final LocalDateTime end = Utils.getEndDate(req.getParameter("enddate"));

        ClassRoom classRoom = (ClassRoom) session.get(ClassRoom.class, classroomId);

        //normal style for merging text for alligment
        CellStyle style = workbook.createCellStyle();
        style.setAlignment(HorizontalAlignment.LEFT);

        //bold text font style
        Font fontBold = workbook.createFont();
        fontBold.setBold(true);

        //normal font style
        Font font = workbook.createFont();
        font.setBold(false);

        //style for centre alligment
        CellStyle styleCenter = workbook.createCellStyle();
        styleCenter.setAlignment(HorizontalAlignment.CENTER);

        //creating  row for title of the exel document
        XSSFRow row = spreadsheet.createRow(0);

        //adding the header of the spreadsheet my merginf columns of the first row
        spreadsheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
        XSSFCell cell = row.createCell(0);
        XSSFRichTextString text = new XSSFRichTextString("Classroom: ");
        text.append(classRoom.getName(), (XSSFFont) fontBold);
        text.append("   Division:   ", (XSSFFont) font);
        text.append(classRoom.getDivision(), (XSSFFont) fontBold);
        text.append("   Semester:   ", (XSSFFont) font);
        text.append(String.valueOf(classRoom.getSemester()), (XSSFFont) fontBold);
        text.append("   Course:   ", (XSSFFont) font);
        text.append(classRoom.getCourse().getName(), (XSSFFont) fontBold);
        text.append("   From:   ", (XSSFFont) font);
        text.append(start.toString(), (XSSFFont) fontBold);
        text.append("   To:   ", (XSSFFont) font);
        text.append(end.toString(), (XSSFFont) fontBold);
        cell.setCellValue(text);

        //data title headers row
        row = spreadsheet.createRow(2);

        //filling data titles
        cell = row.createCell(0);
        cell.setCellValue("Roll Number");
        cell = row.createCell(1);
        cell.setCellValue("Name");

        //this the position of the subjects to start as roll number name are added 
        int cellnumberHead = 2;

        //this will tell the maximun number of subjets a student has 
        final int maxCellNumber = classRoom.getStudents()
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

        //used fow creating cells in row 3 for name,attended,total
        XSSFCell cellTemp = row.createCell(1);

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

        /*ceatingt the last colums 
                               FORMAT
        row 2 ---   Leaves  |  Overrall Total  | Percentage
        row 3 ---           | atteneded |total | 
         */
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

        //starting row number
        final int rowNumber = 4;

        List<Student> students = classRoom.getStudents(); // have to check here is not verified student to be included or not
        Collections.sort(students); // sorting according to the rollnumber of students.

        students.parallelStream()
                .map(student -> getStudentDetails(student, start, end, classRoom, session))
                .forEachOrdered(details -> addRowOfStudentDetails(details, spreadsheet, rowNumber, maxCellNumber, style));

        for (int i = 0; i <= cellnumberHead; i++) {
            spreadsheet.autoSizeColumn(i);
        }

        // have to check this code copied from stackoverflow
        for (int i = 0; i < spreadsheet.getLastRowNum(); i++) {
            if (isRowEmpty(spreadsheet.getRow(i))) {
                spreadsheet.shiftRows(i + 1, spreadsheet.getLastRowNum(), -1);
                i--;//Adjusts the sweep in accordance to a row removal
            }
        }

        workbook.write(out);
    }

    private boolean isRowEmpty(XSSFRow row) {
        return row.getCell(0).getRawValue().equals("") || row.getCell(0).getRawValue() == null;
    }

    private void addRowOfStudentDetails(StudentRowDetails details, XSSFSheet workSheet, int startRowNumber, int maxCellNumber, CellStyle style) {

        if (!details.checkEquality()) {
            throw new RuntimeException("equaity check failed");
        }

        XSSFRow row = workSheet.createRow(startRowNumber + details.getRollNumber());
        int cellNumber = 0;

        XSSFCell cell = row.createCell(cellNumber);
        cell.setCellValue(details.getRollNumber());
        cellNumber++;

        cell = row.createCell(cellNumber);
        cell.setCellValue(details.getStudentName());
        cellNumber++;

        for (int i = 0; i < details.getAttendance().size(); i++) {
            cell = row.createCell(cellNumber++);
            cell.setCellValue(details.getSubjectName().get(i));

            cell = row.createCell(cellNumber++);
            cell.setCellValue(details.getAttendance().get(i));

            cell = row.createCell(cellNumber++);
            cell.setCellValue(details.getLectureCount().get(i));
            cell.setCellStyle(style);
        }

        for (int i = 0; i < maxCellNumber - details.getSubjectName().size(); i++) {
            cell = row.createCell(cellNumber++);
            cell.setCellValue("");
            cell = row.createCell(cellNumber++);
            cell.setCellValue("");
            cell = row.createCell(cellNumber++);
            cell.setCellValue("");
        }

        cell = row.createCell(cellNumber++);
        cell.setCellValue(details.getLeaves());
        cell = row.createCell(cellNumber++);
        cell.setCellValue(details.getTotalAttendance());
        cell = row.createCell(cellNumber++);
        cell.setCellValue(details.getTotalLectures());
        cell.setCellStyle(style);
        cell = row.createCell(cellNumber);
        cell.setCellValue(details.getPercentage());

    }

    private StudentRowDetails getStudentDetails(Student student, LocalDateTime start, LocalDateTime end, ClassRoom classRoom, Session session) {

        StudentRowDetails row = new StudentRowDetails();
        row.setStudentName(student.getFName() + " " + student.getLName());
        row.setRollNumber(student.getRollNumber());

        for (Subject subject : student.getSubjects()) {

            row.getSubjectName().add(subject.getName());

            int lecturesCount = getLectureCount(classRoom, subject, session, start, end);

            row.getLectureCount().add(lecturesCount);
            row.addToTotalLectures(lecturesCount);

            int attendanceCount = getStudentAttendanceCount(classRoom, subject, session, start, end, student);

            row.getAttendance().add(attendanceCount);
            row.addToTotalAttendance(attendanceCount);

            int leaves = getStudentAttendanceLeaveCount(classRoom, subject, session, start, end, student);

            row.addToLeaves(leaves);
        }

        //calculate the percentage
        if (row.getTotalLectures() > 0) {
            row.setPercentage((((float) row.getTotalAttendance() / (float) row.getTotalLectures()) * 100f) + "%");
        }

        return row;

    }

    private class StudentRowDetails {

        @Getter
        @Setter
        private List<String> subjectName; // subject names
        @Getter
        @Setter
        private List<Integer> attendance; // lecutres attened of that subject
        @Getter
        @Setter
        private List<Integer> lectureCount; //lectures counnt of that subject
        @Getter
        @Setter
        private int leaves;
        @Getter
        @Setter
        private int totalLectures;
        @Getter
        @Setter
        private String percentage;
        @Getter
        @Setter
        private int totalAttendance;
        @Getter
        @Setter
        private String studentName;
        @Getter
        @Setter
        private int rollNumber;

        public StudentRowDetails() {
            subjectName = new ArrayList<>();
            attendance = new ArrayList<>();
            lectureCount = new ArrayList<>();
            leaves = 0;
            totalLectures = 0;
            percentage = "NA";
        }

        public void addToTotalLectures(int count) {
            totalLectures += count;
        }

        public void addToTotalAttendance(int count) {
            totalAttendance += count;
        }

        public void addToLeaves(int count) {
            leaves += count;
        }

        private boolean checkEquality() {
            return (subjectName.size() ^ attendance.size() ^ lectureCount.size()) == 0;
        }
    }

    private int getStudentAttendanceLeaveCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end, Student student) {

        //getting teaching of that classrooo and subject
        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        //getting all the attendance of the student  
        DetachedCriteria attendanceCriteria = DetachedCriteria.forClass(Attendance.class)
                .add(Restrictions.eq("student", student))
                .add(Restrictions.eq("attended", true))
                .add(Restrictions.eq("leave", true));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria)) // getting lectures from teaching
                .add(Restrictions.between("date", start, end)) // beteween start date andend date
                .add(Property.forName("attendance").in(attendanceCriteria)) // the lectures should be in the attendace criteria
                .setProjection(Projections.sum("count")) // counting the rows
                .uniqueResult();// getting the result
    }

    private int getStudentAttendanceCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end, Student student) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        DetachedCriteria attendanceCriteria = DetachedCriteria.forClass(Attendance.class)
                .add(Restrictions.eq("student", student))
                .add(Restrictions.eq("attended", true));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria))//getting lectures of that teeaching
                .add(Restrictions.between("date", start, end)) // lectures between that start and end date
                .add(Property.forName("attendance").in(attendanceCriteria)) // lectures where the student is marked present
                .setProjection(Projections.sum("count")) // count the rows
                .uniqueResult(); // the result

    }

    private int getLectureCount(ClassRoom classRoom, Subject subject, Session session, LocalDateTime start, LocalDateTime end) {

        DetachedCriteria teachingCriteria = DetachedCriteria.forClass(Teaching.class)
                .add(Restrictions.eq("classRoom", classRoom))
                .add(Restrictions.eq("subject", subject));

        return (int) session.createCriteria(Lecture.class)
                .add(Property.forName("teaching").in(teachingCriteria)) // getting the lectures of that teaching
                .add(Restrictions.between("date", start, end)) // lecturent between that start and end date
                .setProjection(Projections.sum("count")) // counting rows
                .uniqueResult(); // result

    }
}
