package Users;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Info {
    private String userName;
    private String mobileNumber;
    private Date dob;
    private String email;
    private String password;

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Info(String userName, String mobileNumber, String email, String password, Date dob) {
        this.userName = userName;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.password = password;
        this.dob = dob;
    }
    public Info(Info data) {
        this.userName = data.userName;
        this.mobileNumber = data.mobileNumber;
        this.email = data.email;
        this.password = data.password;
        this.dob = data.dob;
    }



    public String toString()
    {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        return ("user name: " + userName + "\nmobile number: " + mobileNumber + "\nDate of Birth: " + formatter.format(dob) +"\nEmail: " + email);
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
