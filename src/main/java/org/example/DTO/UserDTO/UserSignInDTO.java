package org.example.DTO.UserDTO;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для авторизации пользователей при входе.
 *
 * @see org.example.entities.User
 * */

@NoArgsConstructor
@Getter
@Setter
public class UserSignInDTO {

    String name;
    String password;

    /**
     * Конструктор с валидацией полей.
     *
     * @param name имя пользователя
     *
     * @param password пароль пользователя.
     * */

    public UserSignInDTO(String name, String password) throws Exception {
        if(name != null && password != null && name.length() <= 20 && password.length() <= 20){
            this.name = name;
            this.password = password;
        } else {
            throw new Exception("Validation failed");
        }
    }

}
