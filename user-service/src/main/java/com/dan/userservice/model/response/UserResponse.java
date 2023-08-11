package com.dan.userservice.model.response;

import com.dan.shared.sharedlibrary.model.response.BaseResponse;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse extends BaseResponse {

    private String username;
    private Integer status;
    private Boolean superUser;
    private String firstName;
    private String middleName;
    private String lastName;
    private String phoneNo;
    private String officeEmail;
    private String personalEmail;
    private String address;
    private String grade;
    private String emergencyContactName;
    private String emergencyContactPhoneNo;

}
