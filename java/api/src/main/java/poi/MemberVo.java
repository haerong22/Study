package poi;

public class MemberVo {

    private String name;
    private int age;
    private String birthDate;
    private String phone;
    private String address;
    private boolean isMarried;

    public MemberVo() {
    }

    public MemberVo(String name, int age, String birthDate, String phone, String address, boolean isMarried) {
        this.name = name;
        this.age = age;
        this.birthDate = birthDate;
        this.phone = phone;
        this.address = address;
        this.isMarried = isMarried;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public boolean isMarried() {
        return isMarried;
    }

    public void setMarried(boolean married) {
        isMarried = married;
    }

    @Override
    public String toString() {
        return "MemberVo{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthDate='" + birthDate + '\'' +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", isMarried=" + isMarried +
                '}';
    }
}
