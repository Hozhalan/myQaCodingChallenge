package api.model;

import lombok.Data;

@Data
public class AddressDTO {

    private String street;
    private String suite;
    private String city;
    private String zipcode;
    private Object geo;

}
