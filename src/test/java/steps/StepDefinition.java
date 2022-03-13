package steps;

import api.model.*;
import api.services.ApplicationService;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.junit.Assert;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StepDefinition extends ApplicationService {

    private List<AlbumsDTO> albumsDetails;
    private List<CommentsDTO> commentsDetails;
    private List<PhotosDTO> photosDetails;
    private List<TodosDTO> todosDetails;
    private List<UsersDTO> usersDetails;
    private List<PostsDTO> postsDetails;

    private List<String> emailList;
    private List<Integer> postIdList;

    private int userId = 0;

    public StepDefinition() {
    }


    @Given("get posts details")
    public List<PostsDTO> getPostsDetails() throws Exception {
        postsDetails = getPostsDetails("posts");
        return postsDetails;
    }

    @Given("get comments details")
    public List<CommentsDTO> getCommentsDetails() throws Exception {
        commentsDetails = getCommentsDetails("comments");
        return commentsDetails;
    }

    @Given("get albums details")
    public List<AlbumsDTO> getAlbumsDetails() throws Exception {
        albumsDetails = getAlbumsDetails("albums");
        return albumsDetails;
    }

    @Given("get photos details")
    public List<PhotosDTO> getPhotosDetails() throws Exception {
        photosDetails = getPhotosDetails("photos");
        return photosDetails;
    }

    @Given("get todos details")
    public List<TodosDTO> getTodosDetails() throws Exception {
        todosDetails = getTodosDetails("todos");
        return todosDetails;
    }

    @Given("get users details")
    public List<UsersDTO> getUsersDetails() throws Exception {
        usersDetails = getUsersDetails("users");
        return usersDetails;
    }

    @And("get the user id of the user whose name is {string}")
    public int getUserIdValueOfTheGivenUsername(String userNameValue) throws Exception {
        Optional<UsersDTO> userName = usersDetails.stream().filter(userList -> userList.getUsername()
                .equals(userNameValue)).findFirst();
        if (userName.isPresent()) {
            userId = userName.get().getId();
        }
        return userId;

    }

    @And("retrieve all the related posts made by a specific user by using their user id")
    public List<Integer> retrieveAllTheRelatedPostMadeByUserByUsingTheirUserId() throws Exception {
        postIdList = postsDetails.stream().filter(postsList -> postsList.getUserId() == userId)
                .map(postsDTOList -> postsDTOList.getId()).collect(Collectors.toList());
        Assert.assertNotNull(postIdList);
        return postIdList;
    }

    @And("get the related comments that belong to the specific post id")
    public List<CommentsDTO> getTheRelatedCommentsRegardingToTheSpecificPostId() throws Exception {
        List<CommentsDTO> commentsDTOList = commentsDetails.stream().filter(commentsDTO -> postIdList
                .contains(commentsDTO.getPostId())).collect(Collectors.toList());
        Assert.assertNotNull(commentsDTOList);
        return commentsDTOList;

    }

    @And("get the related email ids with in the comments related to the specific post id")
    public List<String> getTheRelatedEmailIdsWithInTheCommentsRelatedToTheSpecificPostId() throws Exception {
        List<CommentsDTO> commentsList = getTheRelatedCommentsRegardingToTheSpecificPostId();
        Assert.assertNotNull(commentsList);
        emailList = commentsList.stream().map(commentsDTO -> commentsDTO.getEmail())
                .collect(Collectors.toList());
        Assert.assertNotNull(emailList);
        return emailList;

    }

    @Then("verify email ids format inside the comments related to the specific post id")
    public void verifyEmailIdsFormatInsideTheCommentsRelatedToTheSpecificPostId() throws Exception {

        for (String emailId : emailList) {
            String regexPattern = "^(.+)@(\\S+)$";

            //Compile regular expression to get the pattern
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(emailId);
            Assert.assertTrue(matcher.matches());
        }

    }
}