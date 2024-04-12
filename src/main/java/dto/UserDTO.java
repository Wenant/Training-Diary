package dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * Data Transfer Object (DTO) representing a user.
 */
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Returns the string representation of the user, which is the username.
     *
     * @return The username of the user.
     */
    @Override
    public String toString() {
        return username;
    }
}
