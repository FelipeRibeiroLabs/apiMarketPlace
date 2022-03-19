package br.com.frlabs.apimarketplace.resource;

import br.com.frlabs.apimarketplace.domain.dto.UserDTO;
import br.com.frlabs.apimarketplace.services.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/user")
public class UserResource {

    public static final String ID = "{id}";

    private final ModelMapper mapper;
    private final UserService userService;

    @GetMapping(value = ID)
    public ResponseEntity<UserDTO> getUserById(@PathVariable(value = "id") Integer id){
        return ResponseEntity.ok(mapper.map(userService.findById(id), UserDTO.class));
    }

    @GetMapping
    public ResponseEntity< List<UserDTO>> findAll(){
         return ResponseEntity.ok(userService
                 .findAll().stream().map( user -> mapper.map(user, UserDTO.class)).collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO request){
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path(ID).buildAndExpand(userService.createUser(request).getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = ID)
    public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Integer id, @RequestBody UserDTO request){
        request.setId(id);
        return ResponseEntity.ok().body(mapper.map(userService.update(request), UserDTO.class));
    }

    @DeleteMapping(value = ID)
    public ResponseEntity<UserDTO> deleteUser(@PathVariable Integer id){
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
