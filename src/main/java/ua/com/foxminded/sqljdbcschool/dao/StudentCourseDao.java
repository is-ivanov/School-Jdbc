package ua.com.foxminded.sqljdbcschool.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import ua.com.foxminded.sqljdbcschool.entity.StudentCourse;

public class StudentCourseDao {
    private static final String FIELD_STUDENT_ID = "student_id";
    private static final String FIELD_COURSE_ID = "course_id";
    private static final String LF = System.lineSeparator();
    private static final String SQL_CREATE_TABLE = "CREATE TABLE public.students_courses" + LF
            + "(" + LF
            + "student_id integer NOT NULL," + LF
            + "course_id integer NOT NULL," + LF
            + "CONSTRAINT students_courses_pkey PRIMARY KEY (student_id, course_id)," + LF
            + "CONSTRAINT course_id FOREIGN KEY (course_id)" + LF
            + "REFERENCES public.courses (course_id) MATCH SIMPLE" + LF
            + "ON UPDATE NO ACTION" + LF
            + "ON DELETE NO ACTION," + LF
            + "CONSTRAINT student_id FOREIGN KEY (student_id)" + LF
            + "REFERENCES public.students (student_id) MATCH SIMPLE" + LF
            + "ON UPDATE NO ACTION" + LF
            + "ON DELETE NO ACTION" + LF
            + ")";
    

    private DaoUtils daoUtil = new DaoUtils();
    
    public void createTable() throws DAOException {
        try (Connection connection = daoUtil.getConnection()) {
            try (PreparedStatement statement = connection
                    .prepareStatement(SQL_CREATE_TABLE)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't create table", e);
        }
    }

    public void add(StudentCourse studentCourse) throws DAOException {
        String sql = "INSERT INTO students_courses(student_id, course_id)  VALUES (?, ?)";
        try (Connection connection = daoUtil.getConnection()) {

            try (PreparedStatement statement = connection
                    .prepareStatement(sql)) {
                statement.setInt(1, studentCourse.getStudentId());
                statement.setInt(2, studentCourse.getCourseId());

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new DAOException("Can't add pair student-course", e);
        }
    }

//    public Optional<StudentCourse> getById(int id) throws DAOException {
//        // TODO Auto-generated method stub
//        String sql = "SELECT course_id, course_name, course_description FROM courses WHERE course_id = ?";
//        Course course = null;
//        try (Connection connection = daoUtil.getConnection();
//                PreparedStatement statement = connection
//                        .prepareStatement(sql)) {
//            statement.setInt(1, courseId);
//
//            try (ResultSet resultSet = statement.executeQuery()) {
//                if (resultSet.next()) {
//                    course = new Course();
//                    course.setCourseId(courseId);
//                    course.setCourseName(resultSet.getString(FIELD_STUDENT_ID));
//                    course.setCourseDescription(
//                            resultSet.getString(COURSE_DESCRIPTION));
//                }
//            }
//        } catch (SQLException e) {
//            throw new DAOException("Can't get course by ID = " + courseId, e);
//        }
//        return Optional.ofNullable(course);
//    }

    public List<StudentCourse> getAll() throws DAOException {
        String sql = "SELECT student_id, course_id FROM courses";
        List<StudentCourse> studentsCourses = new ArrayList<>();
        try (Connection connection = daoUtil.getConnection();
                Statement statement = connection.createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(sql)) {
                while (resultSet.next()) {
                    StudentCourse studentCourse = new StudentCourse(
                            resultSet.getInt(FIELD_STUDENT_ID),
                            resultSet.getInt(FIELD_COURSE_ID));

                    studentsCourses.add(studentCourse);
                }
            }
        } catch (SQLException e) {
            throw new DAOException("Can't get all pair students-courses", e);
        }
        return studentsCourses;
    }

    public void update(StudentCourse studentCourseOld,
            StudentCourse studentCourseNew) throws DAOException {
        String sql = "UPDATE students_courses SET student_id = ?, course_id = ? WHERE student_id = ? AND course_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, studentCourseNew.getStudentId());
            statement.setInt(2, studentCourseNew.getCourseId());
            statement.setInt(3, studentCourseOld.getStudentId());
            statement.setInt(4, studentCourseOld.getCourseId());

            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can't update pair student-course"
                    + studentCourseOld.getStudentId() + "-"
                    + studentCourseOld.getCourseId(), e);
        }
    }

    public void delete(StudentCourse studentCourse) throws DAOException {
        String sql = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
        try (Connection connection = daoUtil.getConnection();
                PreparedStatement statement = connection
                        .prepareStatement(sql)) {
            statement.setInt(1, studentCourse.getStudentId());
            statement.setInt(2, studentCourse.getCourseId());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException("Can't delete pair student-course "
                    + studentCourse.getStudentId() + "-"
                    + studentCourse.getCourseId(), e);
        }
    }

}
