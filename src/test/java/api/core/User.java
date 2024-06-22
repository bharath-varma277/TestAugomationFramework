package api.core;

import lombok.Builder;

@Builder
//@JsonFilter("api.core.User")
public class User {

    public int id;
    public String userName;
    public String firstName;
    public String lastName;
    public String email;
    public String password;
    public String phone;
    public int userStatus;
}
