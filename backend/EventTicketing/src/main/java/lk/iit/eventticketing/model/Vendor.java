package lk.iit.eventticketing.model;

import jakarta.persistence.*;

@Entity
public class Vendor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long vendorID;
    @Column(nullable = false)
    private String vendorName;
    @Column(nullable = false, unique = true)
    private String vendorEmail;
    @Column(nullable = false)
    private String vendorPassword;

    public Vendor(){}

    public Vendor(long vendorID, String vendorName, String vendorEmail, String vendorPassword) {
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
        return "Vendor{" +
                "vendorID=" + vendorID +
                ", vendorName='" + vendorName + '\'' +
                ", vendorEmail='" + vendorEmail + '\'' +
                ", vendorPassword='" + vendorPassword + '\'' +
                '}';
    }
}
