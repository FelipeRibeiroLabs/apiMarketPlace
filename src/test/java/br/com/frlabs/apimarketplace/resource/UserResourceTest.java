package br.com.frlabs.apimarketplace.resource;

import br.com.frlabs.apimarketplace.domain.User;
import br.com.frlabs.apimarketplace.domain.dto.UserDTO;
import br.com.frlabs.apimarketplace.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserResourceTest {

    private static final Integer ID = 1;
    private static final String NAME_1 = "Name1";
    private static final String NAME_1_EMAIL_COM = "email1@email.com";

    private UserResource userResource;

    @Mock
    private ModelMapper modelMapper;

    @Mock
    private UserService userService;

    UserDTO userDto = new UserDTO(ID, NAME_1, NAME_1_EMAIL_COM);
    User user = new User(ID, NAME_1, NAME_1_EMAIL_COM);

    @BeforeEach
    void setUp(){
        userResource = new UserResource(modelMapper, userService);
    }

    @Test
    void when_getUserById_thenReturn_ResponseEntityUserDTO(){
        when(userService.findById(Mockito.anyInt())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDto);

        ResponseEntity<UserDTO> response = userResource.getUserById(ID);

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME_1, response.getBody().getName());
        assertEquals(NAME_1_EMAIL_COM, response.getBody().getEmail());
    }

    @Test
    void when_findAll_thenReturn_listUserDto() {
        when(userService.findAll()).thenReturn(List.of(user));
        when(modelMapper.map(any(), any())).thenReturn(userDto);

        ResponseEntity< List<UserDTO>> response = userResource.findAll();

        assertNotNull(response);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(ArrayList.class, response.getBody().getClass());
        assertEquals(UserDTO.class, response.getBody().get(0).getClass());

        assertEquals(ID, response.getBody().get(0).getId());
        assertEquals(NAME_1, response.getBody().get(0).getName());
        assertEquals(NAME_1_EMAIL_COM, response.getBody().get(0).getEmail());
    }

    @Test
    void when_createUser_thenReturn_UserDto() {
        when(userService.createUser(any())).thenReturn(user);

        ResponseEntity<UserDTO> response = userResource.createUser(userDto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ResponseEntity.class, response.getClass());
    }

    @Test
    void when_updateUser_thenReturn_UserDto() {
        when(userService.update(any())).thenReturn(user);
        when(modelMapper.map(any(), any())).thenReturn(userDto);
        ResponseEntity<UserDTO> response = userResource.updateUser(1 ,userDto);

        assertEquals(ResponseEntity.class, response.getClass());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(UserDTO.class, response.getBody().getClass());

        assertEquals(ID, response.getBody().getId());
        assertEquals(NAME_1, response.getBody().getName());
        assertEquals(NAME_1_EMAIL_COM, response.getBody().getEmail());
    }



}