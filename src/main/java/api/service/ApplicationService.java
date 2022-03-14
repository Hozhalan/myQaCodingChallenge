package api.service;

import api.model.AlbumsDTO;
import api.model.CommentsDTO;
import api.model.PhotosDTO;
import api.model.PostsDTO;
import api.model.TodosDTO;
import api.model.UsersDTO;

import java.util.List;

public interface ApplicationService {
  List<PostsDTO> getPostsDetails(String relativeURL);

  List<CommentsDTO> getCommentsDetails(String relativeURL);

  List<AlbumsDTO> getAlbumsDetails(String relativeURL);

  List<PhotosDTO> getPhotosDetails(String relativeURL);

  List<TodosDTO> getTodosDetails(String relativeURL);

  List<UsersDTO> getUsersDetails(String relativeURL);
}
