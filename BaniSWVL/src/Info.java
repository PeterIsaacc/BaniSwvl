
public class Info {
    private String userName;
    private String mobileNumber;
    private String email;
    private String password;

    public Info(String userName, String mobileNumber, String email, String password) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
    }
    public Info(Info data) {
        this.userName = data.userName;
        this.mobileNumber = data.mobileNumber;
        this.email = data.email;
        this.password = data.password;
    }
    public String toString()
    {
        return ("user name: " + userName + "\nmobile number: " + mobileNumber + "\nEmail: " + email);
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
