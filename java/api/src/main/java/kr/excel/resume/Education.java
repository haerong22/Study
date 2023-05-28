package kr.excel.resume;

public class Education {

    private String graduationYear;
    private String schoolName;
    private String major;
    private String graduationStatus;

    public Education() {
    }

    public Education(String graduationYear, String schoolName, String major, String graduationStatus) {
        this.graduationYear = graduationYear;
        this.schoolName = schoolName;
        this.major = major;
        this.graduationStatus = graduationStatus;
    }

    public String getGraduationYear() {
        return graduationYear;
    }

    public void setGraduationYear(String graduationYear) {
        this.graduationYear = graduationYear;
    }

    public String getSchoolName() {
        return schoolName;
    }

    public void setSchoolName(String schoolName) {
        this.schoolName = schoolName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getGraduationStatus() {
        return graduationStatus;
    }

    public void setGraduationStatus(String graduationStatus) {
        this.graduationStatus = graduationStatus;
    }

    @Override
    public String toString() {
        return "Education{" +
                "graduationYear='" + graduationYear + '\'' +
                ", schoolName='" + schoolName + '\'' +
                ", major='" + major + '\'' +
                ", graduationStatus='" + graduationStatus + '\'' +
                '}';
    }
}
