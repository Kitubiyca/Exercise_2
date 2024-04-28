package org.example.DTO.UserDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * DTO для авторизации пользователей при регистрации.
 *
 * @see org.example.entities.User
 * */

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class UserSignUpDTO {

    String name;
    String password;

    /**
     * Конструктор с валидацией полей.
     *
     * @param name имя пользователя
     *
     * @param password1 пароль пользователя.
     *
     * @param password2 повторение пароля пользователя.
     * */

    public UserSignUpDTO(String name, String password1, String password2) throws Exception {
        if(name != null && password1 != null && password2 != null && name.length() <= 20 && password1.length() <= 20 && password2.length() <= 20 && password1.equals(password2)){
            this.name = name;
            this.password = password1;
        } else {
            throw new Exception("Validation failed");
        }
    }

}
