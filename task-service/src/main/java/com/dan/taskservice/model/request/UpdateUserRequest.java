package com.dan.taskservice.model.request;

import com.dan.shared.sharedlibrary.model.request.BaseRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateUserRequest extends BaseRequest {

    private String id;
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
