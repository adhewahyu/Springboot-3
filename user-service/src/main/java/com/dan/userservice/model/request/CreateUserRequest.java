package com.dan.userservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateUserRequest extends BaseRequest {

    private String username;
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
