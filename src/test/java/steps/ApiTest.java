package steps;

import api.model.*;
import api.services.ApplicationService;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ApiTest extends ApplicationService {

    private List<AlbumsDTO> albumsDetails;
    private List<CommentsDTO> commentsDetails;
    private List<PhotosDTO> photosDetails;
    private List<TodosDTO> todosDetails;
    private List<UsersDTO> usersDetails;
    private List<PostsDTO> postsDetails;

    private int userId = 0;

    public ApiTest() {
    }

    @BeforeTest
    public List<PostsDTO> getPostsDetails() throws Exception {
        postsDetails = getPostsDetails("posts");
        return postsDetails;
    }

    @BeforeTest
    public List<CommentsDTO> getCommentsDetails() throws Exception {
        commentsDetails = getCommentsDetails("comments");
        return commentsDetails;
    }

    @BeforeTest
    public List<AlbumsDTO> getAlbumsDetails() throws Exception {
        albumsDetails = getAlbumsDetails("albums");
        return albumsDetails;
    }

    @BeforeTest
    public List<PhotosDTO> getPhotosDetails() throws Exception {
        photosDetails = getPhotosDetails("photos");
        return photosDetails;
    }

    @BeforeTest
    public List<TodosDTO> getTodosDetails() throws Exception {
        todosDetails = getTodosDetails("todos");
        return todosDetails;
    }

    @BeforeTest
    public List<UsersDTO> getUsersDetails() throws Exception {
        usersDetails = getUsersDetails("users");
        return usersDetails;
    }

    public int getSpecificUserMadePosts() throws Exception {
        userId = 0;
        Optional<UsersDTO> userName = usersDetails.stream().filter(userList -> userList.getUsername()
                .equals("Delphine")).findFirst();
        if (userName.isPresent()) {
            userId = userName.get().getId();
        }
        return userId;

    }

    public List<Integer> getTheRelatedPostByUsingSpecificUserId() throws Exception {

        userId = getSpecificUserMadePosts();
        List<Integer> postIdList = postsDetails.stream().filter(postsList -> postsList.getUserId() == userId)
                .map(postsDTOList -> postsDTOList.getId()).collect(Collectors.toList());
        return postIdList;
    }

    public List<CommentsDTO> getTheRelatedCommentsRegardingToThSpecificPostId() throws Exception {

        List<Integer> postIdList = getTheRelatedPostByUsingSpecificUserId();
        List<CommentsDTO> commentsDTOList = getCommentsDetails().stream()
                .filter(commentsDTO -> postIdList.contains(commentsDTO.getPostId())).collect(Collectors.toList());
        return commentsDTOList;

    }

    public List<String> getTheRelatedEmailIdsWithInTheCommentsRelatedToThSpecificPostId() throws Exception {
        List<CommentsDTO> commentsList = getTheRelatedCommentsRegardingToThSpecificPostId();
        List<String> emailList = commentsList.stream().map(commentsDTO -> commentsDTO.getEmail()).collect(Collectors.toList());
        return emailList;

    }

    @Test
    public void verifyEmailIdsFormatInsideTheCommentsRelatedToThSpecificPostId() throws Exception {
        List<String> emailList = getTheRelatedEmailIdsWithInTheCommentsRelatedToThSpecificPostId();

        for (String emailId : emailList) {
            String regexPattern = "^(.+)@(\\S+)$";

            //Compile regular expression to get the pattern
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(emailId);
            Assert.assertTrue(matcher.matches());
        }

    }
}
