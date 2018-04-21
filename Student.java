public class Student {
    public String school;
    public String yearGroup;

    public Student(String school, String yearGroup) {
        this.school = school;
        this.yearGroup = yearGroup;
    }

    public String getYearGroup() {
        return yearGroup;
    }
    public void setYearGroup(String yearGroup) {
        this.yearGroup = yearGroup;
    }

    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }
}