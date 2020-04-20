package com.oklimenko.dockerspring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * Ответ с ID созданного Клиента.
 *
 * @author oklimenko@gmail.com
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@ToString
public class CreateUserResponse {
    private Long id;
}
