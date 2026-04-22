import java.util.List;

public interface UserDAO {
    boolean insertUser(User user);
    User getUser(String username);
    boolean updateUser(User user);
    boolean deleteUser(String username);
    List<User> getAllUsers();
    boolean checkLogin(String username, String password);
}