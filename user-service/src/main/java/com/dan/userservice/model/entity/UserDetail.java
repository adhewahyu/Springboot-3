package com.dan.userservice.model.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "user_details", indexes = {
        @Index(name = "user_dtl_idx_0", columnList = "phone_no"),
        @Index(name = "user_dtl_idx_1", columnList = "office_email"),
        @Index(name = "user_dtl_idx_2", columnList = "personal_email"),
})
public class UserDetail {

    @Id
    @Column(name = "id", length = 100, unique = true, nullable = false)
    private String id;

    @Column(name = "first_name", length = 50, nullable = false)
    private String firstName;

    @Column(name = "middle_name", length = 50)
    private String middleName;

    @Column(name = "last_name", length = 50, nullable = false)
    private String lastName;

    @Column(name = "phone_no", length = 20, nullable = false)
    private String phoneNo;

    @Column(name = "office_email", length = 50, nullable = false)
    private String officeEmail;

    @Column(name = "personal_email", length = 50, nullable = false)
    private String personalEmail;

    @Column(name = "address", length = 250)
    private String address;

    @Column(name = "grade", length = 5)
    private String grade;

    @Column(name = "emergency_contact_name", length = 150, nullable = false)
    private String emergencyContactName;

    @Column(name = "emergency_contact_phone_no", length = 20, nullable = false)
    private String emergencyContactPhoneNo;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
