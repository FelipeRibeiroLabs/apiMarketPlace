package br.com.frlabs.apimarketplace.services.impl;

import br.com.frlabs.apimarketplace.domain.User;
import br.com.frlabs.apimarketplace.domain.dto.UserDTO;
import br.com.frlabs.apimarketplace.repositories.UserRepository;
import br.com.frlabs.apimarketplace.services.exceptions.DataIntegratyViolationException;
import br.com.frlabs.apimarketplace.services.exceptions.ObjectNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserServiceImplTest {

    public static final Integer ID              = 1;
    public static final String NAME_1           = "Felipe1";
    public static final String NAME_1_EMAIL_COM = "felipeclownz@gmail.com";
    public static final String OBJECT_NOT_FOUND = "Object not found";
    public static final int INDEX = 0;

    private UserServiceImpl service;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ModelMapper modelMapper;

    private User user;
    private UserDTO userDto;
    private Optional<User> optionalUser;

    @BeforeEach
    void setUp() {
        service = new UserServiceImpl(modelMapper, userRepository);
        startUser();
    }

    @Test
    void when_findById_thenReturn_userInstance() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);

        User response = service.findById(ID);
        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME_1, response.getName());
        assertEquals(NAME_1_EMAIL_COM, response.getEmail());
    }

    @Test
    void when_userNotFound_thenReturn_objectNotFoundException(){
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

        try{
            service.findById(ID);
        } catch (Exception err) {
            assertEquals(ObjectNotFoundException.class, err.getClass());
            assertEquals(OBJECT_NOT_FOUND, err.getMessage());
        }
    }

    @Test
    void when_findAll_thenReturn_userList() {
        when(userRepository.findAll()).thenReturn(List.of(user));

        List<User> response = service.findAll();

        assertNotNull(response);
        assertEquals(1, response.size());
        assertEquals(User.class, response.get(INDEX).getClass());
        assertEquals(ID, response.get(INDEX).getId());
        assertEquals(NAME_1, response.get(INDEX).getName());
        assertEquals(NAME_1_EMAIL_COM, response.get(INDEX).getEmail());
    }

    @Test
    void when_createUser_thenReturn_userInstance() {
        when(userRepository.save(any())).thenReturn(user);

        User response = service.createUser(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME_1, response.getName());
        assertEquals(NAME_1_EMAIL_COM, response.getEmail());
    }

    @Test
    void when_createUserAndEmailAlreadyRegistered_thenReturn_dataIntegratyViolationException(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(4);
            service.createUser(userDto);
        } catch (Exception err) {
            assertEquals(DataIntegratyViolationException.class, err.getClass());
            assertEquals("E-mail already registered", err.getMessage());
        }
    }

    @Test
    void when_updateUser_thenReturn_userInstance() {
        when(userRepository.save(any())).thenReturn(user);

        User response = service.update(userDto);

        assertNotNull(response);
        assertEquals(User.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME_1, response.getName());
        assertEquals(NAME_1_EMAIL_COM, response.getEmail());
    }

    @Test
    void when_updateUserAndEmailAlreadyRegistered_thenReturn_dataIntegratyViolationException(){
        when(userRepository.findByEmail(anyString())).thenReturn(optionalUser);

        try{
            optionalUser.get().setId(4);
            service.update(userDto);
        } catch (Exception err) {
            assertEquals(DataIntegratyViolationException.class, err.getClass());
            assertEquals("E-mail already registered", err.getMessage());
        }
    }

    @Test
    void when_deleteById_thenReturn_void() {
        when(userRepository.findById(anyInt())).thenReturn(optionalUser);
        doNothing().when(userRepository).deleteById(anyInt());
        service.deleteById(ID);
        verify(userRepository, times(1)).deleteById(anyInt());
    }

    @Test
    void when_deleteById_thenReturn_ObjectNotFoundException() {
        when(userRepository.findById(anyInt())).thenThrow(new ObjectNotFoundException(OBJECT_NOT_FOUND));

        try{
            service.deleteById(ID);
        } catch (Exception err) {
            assertEquals(ObjectNotFoundException.class, err.getClass());
            assertEquals(OBJECT_NOT_FOUND, err.getMessage());
        }
    }

    private void startUser(){
        user = new User(ID, NAME_1, NAME_1_EMAIL_COM);
        userDto = new UserDTO(ID, NAME_1, NAME_1_EMAIL_COM);
        optionalUser = Optional.of(new User(ID, NAME_1, NAME_1_EMAIL_COM));
    }
}