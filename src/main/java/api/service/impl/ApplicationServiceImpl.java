package api.service.impl;

import api.base.APIServicesBase;
import api.model.AlbumsDTO;
import api.model.CommentsDTO;
import api.model.PhotosDTO;
import api.model.PostsDTO;
import api.model.TodosDTO;
import api.model.UsersDTO;
import api.service.ApplicationService;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;

import java.util.Collections;
import java.util.List;

@Slf4j
public class ApplicationServiceImpl extends APIServicesBase implements ApplicationService {

  public ApplicationServiceImpl() {}

  @Override
  public List<PostsDTO> getPostsDetails(String relativeURL) {
    try {
      log.info("Posts Details");
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<PostsDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }

  @Override
  public List<CommentsDTO> getCommentsDetails(String relativeURL) {

    try {
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<CommentsDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }

  @Override
  public List<AlbumsDTO> getAlbumsDetails(String relativeURL) {

    try {
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<AlbumsDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }

  @Override
  public List<PhotosDTO> getPhotosDetails(String relativeURL) {

    try {
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<PhotosDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }

  @Override
  public List<TodosDTO> getTodosDetails(String relativeURL) {

    try {
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<TodosDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }

  @Override
  public List<UsersDTO> getUsersDetails(String relativeURL) {

    try {
      Response response = getRequest(relativeURL);

      if (response.statusCode() == 200) {
        return response.as(new TypeRef<List<UsersDTO>>() {});
      }

      return Collections.emptyList();

    } catch (Exception e) {
      log.error("Failed due to ", e);
      throw e;
    }
  }
}
