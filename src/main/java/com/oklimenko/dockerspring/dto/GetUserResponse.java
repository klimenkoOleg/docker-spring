package com.oklimenko.dockerspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import javax.persistence.Column;

/**
 * Запрос на создание клиента.
 *
 * @author oklimenko@gmail.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class GetUserResponse {
    private String username;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
