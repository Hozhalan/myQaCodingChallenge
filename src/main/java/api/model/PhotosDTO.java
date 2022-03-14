package api.model;

import lombok.Data;

@Data
public class PhotosDTO {

  private int albumId;
  private int id;
  private String title;
  private String url;
  private String thumbnailUrl;
}
