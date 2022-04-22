package com.example.springmongo.commands;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author <a href="pulkit.aggarwal@upgrad.com">Pulkit Aggarwal</a>
 * @version 1.0
 * @since 27/09/21
 */
@Setter
@Getter
@NoArgsConstructor
public class CategoryCommand {
    private String id;
    private String description;
}
