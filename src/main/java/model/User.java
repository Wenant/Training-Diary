package model;


import lombok.*;
import util.UserRoles;

/**
 * Represents a user in the system.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    /**
     * The id of the user.
     */
    private Long id;

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * The role of the user. Defaults to UserRoles.USER.
     */
    private UserRoles role = UserRoles.USER;

}