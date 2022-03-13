package api.services;

import api.base.APIServicesBase;
import api.model.*;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

public class ApplicationService extends APIServicesBase {


    private Response response;

    public ApplicationService() {

    }

    public List<PostsDTO> getPostsDetails(String relativeURL) throws Exception {
        List<PostsDTO> postsDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                postsDTOList = response.as(new TypeRef<List<PostsDTO>>() {
                });
            }

            return postsDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<CommentsDTO> getCommentsDetails(String relativeURL) throws Exception {
        List<CommentsDTO> commentsDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                commentsDTOList = response.as(new TypeRef<List<CommentsDTO>>() {
                });
            }

            return commentsDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<AlbumsDTO> getAlbumsDetails(String relativeURL) throws Exception {
        List<AlbumsDTO> albumsDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                albumsDTOList = response.as(new TypeRef<List<AlbumsDTO>>() {
                });
            }

            return albumsDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<PhotosDTO> getPhotosDetails(String relativeURL) throws Exception {
        List<PhotosDTO> photosDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                photosDTOList = response.as(new TypeRef<List<PhotosDTO>>() {
                });
            }

            return photosDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<TodosDTO> getTodosDetails(String relativeURL) throws Exception {
        List<TodosDTO> todosDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                todosDTOList = response.as(new TypeRef<List<TodosDTO>>() {
                });
            }

            return todosDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public List<UsersDTO> getUsersDetails(String relativeURL) throws Exception {
        List<UsersDTO> usersDTOList = new ArrayList<>();

        try {
            response = getRequest(relativeURL);

            if (response.statusCode() == 200) {
                usersDTOList = response.as(new TypeRef<List<UsersDTO>>() {
                });
            }

            return usersDTOList;

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }
}
