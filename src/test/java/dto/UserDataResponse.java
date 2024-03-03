package dto;

import lombok.Getter;

@Getter
public class UserDataResponse {
    private int id;
    private String email;
    private String first_name;
    private String last_name;
    private String avatar;
}
