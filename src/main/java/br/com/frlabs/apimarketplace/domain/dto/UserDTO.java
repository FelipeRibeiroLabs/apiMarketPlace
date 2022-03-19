package br.com.frlabs.apimarketplace.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {

    private Integer id;
    private String name;

    //@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String email;

}
