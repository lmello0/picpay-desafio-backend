package com.picpay.challenge.controller;

import com.picpay.challenge.DTO.user.PostUserDTO;
import com.picpay.challenge.DTO.user.UserReturnDTO;
import com.picpay.challenge.domain.user.Type;
import com.picpay.challenge.domain.user.User;
import com.picpay.challenge.services.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
@ActiveProfiles("test")
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<PostUserDTO> postUserDTOJacksonTester;

    @Autowired
    private JacksonTester<UserReturnDTO> userReturnDTOJacksonTester;

    @MockBean
    private UserService userService;

    @Test
    @DisplayName("Should return HTTP 201 when user is created successfully")
    @WithMockUser
    void registerCase1() throws Exception {
        // given
        String userId = UUID.randomUUID().toString();
        String firstName = "test";
        String document = "11122233300";
        String email = "email@email.com";
        String password = "123456";
        Type userType = Type.COMUM;
        int wallet = 10000_00;

        User user = new User(userId, firstName, null, document, email, password, userType, wallet, true);

        UserReturnDTO expectedDTO = new UserReturnDTO(userId, firstName, null, document, wallet);

        String expectedJson = userReturnDTOJacksonTester.write(expectedDTO).getJson();

        String expectedLocation = "http://localhost/users/" + userId;

        // when
        when(userService.createUser(any())).thenReturn(user);

        MockHttpServletResponse response = mvc
                .perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(postUserDTOJacksonTester.write(
                                        new PostUserDTO(firstName, null, document, email, password, userType)
                                ).getJson())
                ).andReturn()
                .getResponse();

        // then
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getHeader("Location")).isEqualTo(expectedLocation);
        assertThat(response.getContentAsString()).isEqualTo(expectedJson);
    }

    @Test
    @DisplayName("")
    @WithMockUser
    void registerCase2() {

    }

    @Test
    void findOne() {
    }

    @Test
    void findAll() {
    }

    @Test
    void update() {
    }

    @Test
    void delete() {
    }
}