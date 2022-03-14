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

import static org.junit.Assert.assertFalse;

public class StepDefinition extends ApplicationService {

    private List<AlbumsDTO> albumsDetails;
    private List<CommentsDTO> commentsDetails;
    private List<PhotosDTO> photosDetails;
    private List<TodosDTO> todosDetails;
    private List<UsersDTO> usersDetails;
    private List<PostsDTO> postsDetails;

    private List<String> emailList;
    private List<Integer> postIdList;
    private List<Integer> userIds;
    private List<Integer> photoIds;
    private List<Integer> albumsIds;

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
        assertFalse(Optional.empty().isPresent());
        userId = userName.get().getId();
        return userId;

    }

    @And("get all the user ids")
    public List<Integer> getAllTheUsers() throws Exception {
        userIds = usersDetails.stream().map(UsersDTO::getId).collect(Collectors.toList());
        assertFalse(userIds.isEmpty());
        return userIds;

    }

    @And("retrieve all the related posts made by a specific user by using their user id")
    public List<Integer> retrieveAllTheRelatedPostMadeByUserByUsingTheirUserId() throws Exception {
        postIdList = postsDetails.stream().filter(postsList -> postsList.getUserId() == userId)
                .map(postsDTOList -> postsDTOList.getId()).collect(Collectors.toList());
        assertFalse(postIdList.isEmpty());
        return postIdList;
    }

    @And("retrieve all the emails in the user section")
    public List<String> retrieveAllTheEmailsInTheUserSection() throws Exception {
        emailList = usersDetails.stream().map(UsersDTO::getEmail).collect(Collectors.toList());
        assertFalse(emailList.isEmpty());
        return emailList;
    }

    @And("get the related comments that belong to the specific post id")
    public List<CommentsDTO> getTheRelatedCommentsRegardingToTheSpecificPostId() throws Exception {
        List<CommentsDTO> commentsDTOList = commentsDetails.stream().filter(commentsDTO -> postIdList
                .contains(commentsDTO.getPostId())).collect(Collectors.toList());
        assertFalse(commentsDTOList.isEmpty());
        return commentsDTOList;

    }

    @And("get the related email ids with in the comments related to the specific post id")
    public List<String> getTheRelatedEmailIdsWithInTheCommentsRelatedToTheSpecificPostId() throws Exception {
        List<CommentsDTO> commentsList = getTheRelatedCommentsRegardingToTheSpecificPostId();
        Assert.assertNotNull(commentsList);
        emailList = commentsList.stream().map(commentsDTO -> commentsDTO.getEmail())
                .collect(Collectors.toList());
        assertFalse(emailList.isEmpty());
        return emailList;

    }

    @Then("verify email ids format")
    public void verifyEmailIdsFormat() throws Exception {

        for (String emailId : emailList) {
            String regexPattern = "^(.+)@(\\S+)$";

            //Compile regular expression to get the pattern
            Pattern pattern = Pattern.compile(regexPattern);
            Matcher matcher = pattern.matcher(emailId);
            Assert.assertTrue(matcher.matches());
        }


    }

    @Then("verify every user has albums with respect to their user id")
    public void verifyUserHasAlbumsThroughTheirUserId() throws Exception {

        for (Integer userId : userIds) {
            albumsIds = albumsDetails.stream().filter(albumsList -> albumsList.getUserId() == userId)
                    .map(AlbumsDTO::getId).collect(Collectors.toList());
            assertFalse(albumsIds.isEmpty());
        }

    }

    @Then("verify that every user's album has photos")
    public void verifyThatEveryUserAlbumHasPhotos() throws Exception {

        for (Integer userId : userIds) {
            albumsIds = albumsDetails.stream().filter(albumsList -> albumsList.getUserId() == userId)
                    .map(AlbumsDTO::getId).collect(Collectors.toList());
            assertFalse(albumsIds.isEmpty());
            for (Integer albumsId : albumsIds) {
                photoIds = photosDetails.stream().filter(photosList -> photosList.getAlbumId() == albumsId)
                        .map(PhotosDTO::getAlbumId).collect(Collectors.toList());
                assertFalse(photoIds.isEmpty());
            }
        }

    }

    @Then("verify the photo's url and thumbnailUrl base path ids are same")
    public void verifyPhotoUrlAndThumbnailUrlBasePathIdsAreSame() throws Exception {

        for (Integer albumsId : albumsIds) {
            photoIds = photosDetails.stream().filter(photosList -> photosList.getAlbumId() == albumsId)
                    .map(PhotosDTO::getAlbumId).collect(Collectors.toList());
            assertFalse(photoIds.isEmpty());

            for (Integer photoId : photoIds) {
                Optional<PhotosDTO> first = photosDetails.stream().filter(photosList -> photosList
                        .getId() == photoId).findFirst();
                assertFalse(Optional.empty().isPresent());
                String thumbnailUrl = first.get().getThumbnailUrl();
                thumbnailUrl = thumbnailUrl.substring(thumbnailUrl.length() - 6);
                String url = first.get().getUrl();
                url = url.substring(url.length() - 6);

                Assert.assertEquals(thumbnailUrl, url);

            }
        }

    }


}