package Model;

public class UserInformation {
    private String FName;
    private String LName;
    private String Email;
    private String BirthDate;
    private String password;

    public UserInformation(String FName, String LName, String email, String birthDate, String password) {
        this.FName = FName;
        this.LName = LName;
        Email = email;
        BirthDate = birthDate;
        this.password = password;
    }

    public UserInformation() {
    }

    @Override
    public String toString() {
        return "UserInformation{" +
                "FName='" + FName + '\'' +
                ", LName='" + LName + '\'' +
                ", Email='" + Email + '\'' +
                ", BirthDate='" + BirthDate + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public String getFName() {
        return FName;
    }

    public void setFName(String FName) {
        this.FName = FName;
    }

    public String getLName() {
        return LName;
    }

    public void setLName(String LName) {
        this.LName = LName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getBirthDate() {
        return BirthDate;
    }

    public void setBirthDate(String birthDate) {
        BirthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
