package api.model;

import lombok.Data;

@Data
public class UsersDTO {

    private int id;
    private String name;
    private String username;
    private String email;
    private AddressDTO address;
    private String phone;
    private String website;
    private CompanyDTO company;

}
