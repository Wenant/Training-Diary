package mapper;

import dto.UserDTO;
import model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * Mapper interface for converting between User and UserDTO objects.
 */
@Mapper
public interface UserMapper {

    /**
     * Instance of the UserMapper interface.
     */
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a UserDTO object to a User object.
     *
     * @param userDTO The UserDTO object to convert.
     * @return The converted User object.
     */
    User userDTOToUser(UserDTO userDTO);

    /**
     * Converts a User object to a UserDTO object.
     *
     * @param user The User object to convert.
     * @return The converted UserDTO object.
     */
    UserDTO userToUserDTO(User user);

    /**
     * Converts a list of User objects to a list of UserDTO objects.
     *
     * @param userList The list of User objects to convert.
     * @return The list of converted UserDTO objects.
     */
    List<UserDTO> userListToUserDtoList(List<User> userList);
}