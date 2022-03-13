package api.model;

import lombok.Data;

@Data
public class CommentsDTO {

    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;

}
