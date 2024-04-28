package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс для создания сущностей пользователей из БД.
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class User {

    int id;
    String name;
    String password;

}
