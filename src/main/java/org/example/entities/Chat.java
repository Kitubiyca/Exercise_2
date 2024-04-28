package org.example.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Класс для создания сущностей чатов из БД.
 * */

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {

    private int id;
    private String name;
    private int owner;

}
