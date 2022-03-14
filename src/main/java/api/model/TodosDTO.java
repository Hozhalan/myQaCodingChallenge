package api.model;

import lombok.Data;

@Data
public class TodosDTO {

  private int userId;
  private int id;
  private String title;
  private Boolean completed;
}
