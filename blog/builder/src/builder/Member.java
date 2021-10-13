package builder;

public class Member {

    private final String email;
    private final String password;
    private final String name;
    private final String address;
    private final String phone;

    public Member(String id, String password, String name, String address, String phone) {
        this.email = id;
        this.password = password;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public static MemberBuilder builder() {
        return new MemberBuilder();
    }

    public static class MemberBuilder {
        private String email;
        private String password;
        private String name;
        private String address;
        private String phone;

        private MemberBuilder() {
        }

        public MemberBuilder email(String email) {
            this.email = email;
            return this;
        }
        public MemberBuilder password(String password) {
            this.password = password;
            return this;
        }
        public MemberBuilder name(String name) {
            this.name = name;
            return this;
        }
        public MemberBuilder address(String address) {
            this.address = address;
            return this;
        }
        public MemberBuilder phone(String phone) {
            this.phone = phone;
            return this;
        }
        public Member build() {
            return new Member(email, password, name, address, phone);
        }
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
