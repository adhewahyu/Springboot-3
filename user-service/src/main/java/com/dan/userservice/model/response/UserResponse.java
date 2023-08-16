package com.dan.userservice.model.response;

import com.dan.shared.sharedlibrary.model.response.BaseResponse;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends BaseResponse {

    private String id;
    private String username;
    private Integer status;
    private String statusString;
    private Boolean superUser;
    private String fullName;
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
