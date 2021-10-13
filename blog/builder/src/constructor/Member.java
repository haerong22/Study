package constructor;

public class Member {

    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final String phone;

    public Member(String email, String password, String name, String address, String phone) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Member(String email, String password, String name) {
        this(email, password, name, null, null);
    }

    public Member(String email, String password) {
        this(email, password, null, null, null);
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "builder.Member{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
