package com.gcw.testautomation.steps;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gcw.apiautomation.core.BaseStep;
import com.gcw.apiautomation.core.BaseStepFactory;
import com.gcw.apiautomation.core.config.ConfigProvider;
import com.gcw.testautomation.support.beans.Post;
import com.gcw.testautomation.support.beans.User;
import com.gcw.testautomation.support.constants.ConfigKeys;
import com.gcw.testautomation.support.constants.Endpoint;
import com.gcw.testautomation.support.constants.Params;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.testng.Assert;

import java.util.List;

public class ApiTestSteps {

    private BaseStep baseStep = BaseStepFactory.getBaseStep();
    private ObjectMapper objectMapper = new ObjectMapper();
    private List<User> usersList;

    private User randomUserDetails;

    private List<Post> userPostsList;


    @Given("set the base uri")
    public void setTheBaseUri() {
        baseStep.setBaseUri(ConfigProvider.getConfig(ConfigKeys.API).getString(ConfigKeys.BASE_URI));
    }

    @Given("retrieve all user details")
    public void retrieveAllUserDetails() throws JsonProcessingException {
        baseStep.setEndPoint(Endpoint.USERS);
        baseStep.getResource();
        Assert.assertEquals(baseStep.getResourceCode(), HttpStatus.SC_OK);
        String resContent =  baseStep.getResponseJson();
        usersList = objectMapper.readValue(resContent, new TypeReference<>() {});
    }

    @Then("retrieve a random user details by user id {string}")
    public void retrieveARandomUserDetailsByUserId(String userId) throws JsonProcessingException {
        //according to the requirement, suppose the user id is valid
        baseStep.setEndPoint(Endpoint.USER_DETAILS_BY_ID);
        baseStep.updatePathParam(Params.USER_ID,userId);
        baseStep.getResource();
        String resContent =  baseStep.getResponseJson();
        if (StringUtils.isNotBlank(resContent)){
            randomUserDetails = objectMapper.readValue(resContent, User.class);
        }else {
            throw new RuntimeException(String.format("Retrieve the user:%s details failed",userId));
        }
    }
    @Then("the random user details are verify correct")
    public void theRandomUserDetailsAreVerifyCorrect() {
        Assert.assertTrue(usersList.contains(randomUserDetails));
        for (User user : usersList){
            if (user.getId().equals(randomUserDetails.getId())){
                Assert.assertEquals(user,randomUserDetails);
            }
        }
    }

    @Then("user want to retrieve the posts details for using the user id {string}")
    public void retrieveThePostsDetailsForSameUser(String userId) throws JsonProcessingException {
        baseStep.setEndPoint(Endpoint.POSTS);
        baseStep.updateQueryParam(Params.USER_ID,userId);
        baseStep.getResource();
        Assert.assertEquals(baseStep.getResourceCode(), HttpStatus.SC_OK);
        String resContent =  baseStep.getResponseJson();
        if (StringUtils.isNotBlank(resContent)){
            userPostsList = objectMapper.readValue(resContent, new TypeReference<>() {});
        }else {
            throw new RuntimeException(String.format("Retrieve the user:%s posts failed",userId));
        }
    }

    @Then("all of the post id verify correct")
    public void allOfThePostIdVerifyCorrect() {
        userPostsList.stream()
                .map(Post::getId)
                .forEach(integer -> Assert.assertTrue(integer>=1 && integer <= 100));

    }

    @Given("user set the post resources endpoint")
    public void userWantToCreateAPostResource() {
        baseStep.setEndPoint(Endpoint.POSTS);
    }

    @When("user submit the {string} request")
    public void userSubmitThePostRequest(String httpOperation) {
        switch (httpOperation.toLowerCase()){
            case "post":
                baseStep.postPayload();
                break;
            case "get":
                baseStep.getResource();
        }
    }

    @Then("the response data is verify correct")
    public void theResponseIsVerifyCorrect() throws JsonProcessingException {
        Post requestPayload = objectMapper.readValue(baseStep.getRequestBody(), Post.class);
        Post responsePayload = objectMapper.readValue(baseStep.getResponseJson(), Post.class);
        Assert.assertEquals(responsePayload.getId().intValue(),101);
        Assert.assertEquals(responsePayload.getTitle(),requestPayload.getTitle());
        Assert.assertEquals(responsePayload.getBody(),requestPayload.getBody());
        Assert.assertEquals(responsePayload.getUserId(),requestPayload.getUserId());

    }

    @And("user set the request body using user id {string}")
    public void userSetTheRequestBodyUsingForSameUser(String userId) throws JsonProcessingException {
        baseStep.loadPayload("create_post_resource.json");
        Post postPayload = objectMapper.readValue(baseStep.getRequestBody(),Post.class);
        postPayload.setUserId(Integer.valueOf(userId));
        baseStep.setRequestBody(postPayload.toString());
    }

    @Then("the response is 200 success")
    public void theResponseIs200Success() {
        Assert.assertEquals(baseStep.getResourceCode(),HttpStatus.SC_OK);
    }

    @Then("the response is 201 created")
    public void theResponseIs201Created() {
        Assert.assertEquals(baseStep.getResourceCode(),HttpStatus.SC_CREATED);
    }
}
