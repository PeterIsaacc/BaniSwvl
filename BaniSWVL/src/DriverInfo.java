public class DriverInfo extends Info {
    private String driverLicense;
    private String nationalId;
    public DriverInfo(Info data, String driverLicense, String nationalId) {
        super(data);
        this.driverLicense = driverLicense;
        this.nationalId = nationalId;
    }
}
