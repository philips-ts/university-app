package com.tarasenko.universityapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {
    private int id;

    private String firstname;

    private String lastname;

    private GroupDTO group;
}
