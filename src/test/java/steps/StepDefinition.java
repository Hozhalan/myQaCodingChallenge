package steps;

import api.model.AlbumsDTO;
import api.model.CommentsDTO;
import api.model.PhotosDTO;
import api.model.PostsDTO;
import api.model.TodosDTO;
import api.model.UsersDTO;
import api.service.ApplicationService;
import api.service.impl.ApplicationServiceImpl;
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
import static org.junit.Assert.assertTrue;

public class StepDefinition {

  private final ApplicationService applicationService = new ApplicationServiceImpl();
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

  public StepDefinition() {}

  @Given("get posts details")
  public List<PostsDTO> getPostsDetails() {
    postsDetails = applicationService.getPostsDetails("posts");
    return postsDetails;
  }

  @Given("get comments details")
  public List<CommentsDTO> getCommentsDetails() {
    commentsDetails = applicationService.getCommentsDetails("comments");
    return commentsDetails;
  }

  @Given("get albums details")
  public List<AlbumsDTO> getAlbumsDetails() {
    albumsDetails = applicationService.getAlbumsDetails("albums");
    return albumsDetails;
  }

  @Given("get photos details")
  public List<PhotosDTO> getPhotosDetails() {
    photosDetails = applicationService.getPhotosDetails("photos");
    return photosDetails;
  }

  @Given("get todos details")
  public List<TodosDTO> getTodosDetails() {
    todosDetails = applicationService.getTodosDetails("todos");
    return todosDetails;
  }

  @Given("get users details")
  public List<UsersDTO> getUsersDetails() {
    usersDetails = applicationService.getUsersDetails("users");
    return usersDetails;
  }

  @And("get the user id of the user whose name is {string}")
  public int getUserIdValueOfTheGivenUsername(String userNameValue) {

    usersDetails.stream()
        .filter(userList -> userList.getUsername().equals(userNameValue))
        .findFirst()
        .ifPresent(usersDTO -> userId = usersDTO.getId());

    assertTrue(userId > 0);
    return userId;
  }

  @And("get all the user ids")
  public List<Integer> getAllTheUsers() {
    userIds = usersDetails.stream().map(UsersDTO::getId).collect(Collectors.toList());
    assertFalse(userIds.isEmpty());
    return userIds;
  }

  @And("retrieve all the related posts made by a specific user by using their user id")
  public List<Integer> retrieveAllTheRelatedPostMadeByUserByUsingTheirUserId() {
    postIdList =
        postsDetails.stream()
            .filter(postsList -> postsList.getUserId() == userId)
            .map(PostsDTO::getId)
            .collect(Collectors.toList());
    assertFalse(postIdList.isEmpty());
    return postIdList;
  }

  @And("retrieve all the emails in the user section")
  public List<String> retrieveAllTheEmailsInTheUserSection() {
    emailList = usersDetails.stream().map(UsersDTO::getEmail).collect(Collectors.toList());
    assertFalse(emailList.isEmpty());
    return emailList;
  }

  @And("get the related comments that belong to the specific post id")
  public List<CommentsDTO> getTheRelatedCommentsRegardingToTheSpecificPostId() {
    List<CommentsDTO> commentsDTOList =
        commentsDetails.stream()
            .filter(commentsDTO -> postIdList.contains(commentsDTO.getPostId()))
            .collect(Collectors.toList());
    assertFalse(commentsDTOList.isEmpty());
    return commentsDTOList;
  }

  @And("get the related email ids with in the comments related to the specific post id")
  public List<String> getTheRelatedEmailIdsWithInTheCommentsRelatedToTheSpecificPostId() {
    List<CommentsDTO> commentsList = getTheRelatedCommentsRegardingToTheSpecificPostId();
    Assert.assertNotNull(commentsList);
    emailList = commentsList.stream().map(CommentsDTO::getEmail).collect(Collectors.toList());
    assertFalse(emailList.isEmpty());
    return emailList;
  }

  @Then("verify email ids format")
  public void verifyEmailIdsFormat() {

    for (String emailId : emailList) {
      String regexPattern = "^(.+)@(\\S+)$";

      // Compile regular expression to get the pattern
      Pattern pattern = Pattern.compile(regexPattern);
      Matcher matcher = pattern.matcher(emailId);
      assertTrue(matcher.matches());
    }
  }

  @Then("verify every user has albums with respect to their user id")
  public void verifyUserHasAlbumsThroughTheirUserId() {

    for (Integer userId : userIds) {
      albumsIds =
          albumsDetails.stream()
              .filter(albumsList -> albumsList.getUserId() == userId)
              .map(AlbumsDTO::getId)
              .collect(Collectors.toList());
      assertFalse(albumsIds.isEmpty());
    }
  }

  @Then("verify that every user's album has photos")
  public void verifyThatEveryUserAlbumHasPhotos() {

    for (Integer userId : userIds) {
      albumsIds =
          albumsDetails.stream()
              .filter(albumsList -> albumsList.getUserId() == userId)
              .map(AlbumsDTO::getId)
              .collect(Collectors.toList());
      assertFalse(albumsIds.isEmpty());
      for (Integer albumsId : albumsIds) {
        photoIds =
            photosDetails.stream()
                .map(PhotosDTO::getAlbumId)
                .filter(albumId -> albumId.equals(albumsId))
                .collect(Collectors.toList());
        assertFalse(photoIds.isEmpty());
      }
    }
  }

  @Then("verify the photo's url and thumbnailUrl base path ids are same")
  public void verifyPhotoUrlAndThumbnailUrlBasePathIdsAreSame() {

    for (Integer albumsId : albumsIds) {
      photoIds =
          photosDetails.stream()
              .map(PhotosDTO::getAlbumId)
              .filter(albumId -> albumId.equals(albumsId))
              .collect(Collectors.toList());
      assertFalse(photoIds.isEmpty());

      for (Integer photoId : photoIds) {
        Optional<PhotosDTO> first =
            photosDetails.stream().filter(photosList -> photosList.getId() == photoId).findFirst();
        assertTrue(first.isPresent());
        String thumbnailUrl = first.get().getThumbnailUrl();
        thumbnailUrl = thumbnailUrl.substring(thumbnailUrl.length() - 6);
        String url = first.get().getUrl();
        url = url.substring(url.length() - 6);

        Assert.assertEquals(thumbnailUrl, url);
      }
    }
  }
}
