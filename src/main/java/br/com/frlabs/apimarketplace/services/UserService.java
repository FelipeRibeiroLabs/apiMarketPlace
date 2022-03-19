package br.com.frlabs.apimarketplace.services;

import br.com.frlabs.apimarketplace.domain.User;
import br.com.frlabs.apimarketplace.domain.dto.UserDTO;

import java.util.List;

public interface UserService {

    User findById(Integer id);

    List<User> findAll();

    User createUser(UserDTO obj);

    User update(UserDTO obj);

    void deleteById(Integer id);

}
