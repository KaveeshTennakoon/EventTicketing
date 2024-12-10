package lk.iit.eventticketing.dto;


public class VendorDto {

    private long vendorID;
    private String vendorName;
    private String vendorEmail;
    private String vendorPassword;

    public VendorDto() {}

    public VendorDto(long vendorID, String vendorName, String vendorEmail, String vendorPassword) {
        this.vendorID = vendorID;
        this.vendorName = vendorName;
        this.vendorEmail = vendorEmail;
        this.vendorPassword = vendorPassword;
    }

    public long getVendorID() {
        return vendorID;
    }

    public void setVendorID(long vendorID) {
        this.vendorID = vendorID;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public String getVendorEmail() {
        return vendorEmail;
    }

    public void setVendorEmail(String vendorEmail) {
        this.vendorEmail = vendorEmail;
    }

    public String getVendorPassword() {
        return vendorPassword;
    }

    public void setVendorPassword(String vendorPassword) {
        this.vendorPassword = vendorPassword;
    }

    @Override
    public String toString() {
        return "VendorDto{" +
                "vendorID=" + vendorID +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", vendorPassword='" + vendorPassword + '\'' +
                '}';
    }
}
