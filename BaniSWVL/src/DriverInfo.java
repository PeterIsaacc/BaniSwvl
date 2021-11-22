public class DriverInfo extends Info {
    private String driverLicense;
    private String nationalId;
    public DriverInfo(Info data, String driverLicense, String nationalId) {
        super(data);
        this.driverLicense = driverLicense;
        this.nationalId = nationalId;
    }

    public String toString()
    {
        return String.format("user name: " + getUserName() + "\nmobile number: " + getMobileNumber() + "\nEmail: "
                + getEmail() + "\nnational Id: " + nationalId + "\ndriver license: " + driverLicense);
    }
}
