package api.model;

import lombok.Data;

@Data
public class PostsDTO {

    private int userId;
    private int id;
    private String title;
    private String body;

}
