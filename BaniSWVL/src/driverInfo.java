public class driverInfo extends Info {
    private String driverLicense;
    private String nationalId;

    public driverInfo(Info data, String driverLicense, String nationalId) {
        super(data);
        this.driverLicense = driverLicense;
        this.nationalId = nationalId;
    }
}
