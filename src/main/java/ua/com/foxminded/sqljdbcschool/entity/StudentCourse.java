package ua.com.foxminded.sqljdbcschool.entity;

public class StudentCourse {
    private int studentId;
    private int courseId;
    
    public StudentCourse(int studentId, int courseId) {
        this.studentId = studentId;
        this.courseId = courseId;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {
        this.courseId = courseId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + courseId;
        result = prime * result + studentId;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        StudentCourse other = (StudentCourse) obj;
        if (courseId != other.courseId)
            return false;
        if (studentId != other.studentId)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "StudentCourse [studentId=" + studentId + ", courseId="
                + courseId + "]";
    }
    
    

}
