package br.com.frlabs.apimarketplace.services.impl;

import br.com.frlabs.apimarketplace.domain.User;
import br.com.frlabs.apimarketplace.domain.dto.UserDTO;
import br.com.frlabs.apimarketplace.repositories.UserRepository;
import br.com.frlabs.apimarketplace.services.UserService;
import br.com.frlabs.apimarketplace.services.exceptions.DataIntegratyViolationException;
import br.com.frlabs.apimarketplace.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {


    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    @Override
    public User findById(Integer id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow( () -> new ObjectNotFoundException("Object not found"));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User createUser(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(modelMapper.map(obj, User.class));
    }

    @Override
    public User update(UserDTO obj) {
        findByEmail(obj);
        return userRepository.save(modelMapper.map(obj, User.class));
    }

    @Override
    public void deleteById(Integer id) {
        findById(id);
        userRepository.deleteById(id);
    }

    private void findByEmail(UserDTO obj){
        Optional<User> user = userRepository.findByEmail(obj.getEmail());
        if(user.isPresent() && !user.get().getId().equals(obj.getId())){
            throw new DataIntegratyViolationException("E-mail already registered");
        }

    }


}
