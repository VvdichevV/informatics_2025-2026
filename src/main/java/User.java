// Victor Dichev 12/4
public class User {
    String userId;
    String password;
    String loginStatus;

    public User(String userId, String password, String loginStatus) {
        this.userId = userId;
        this.password = password;
        this.loginStatus = loginStatus;
    }

    public boolean verifyLogin() {
        return true;
    }

    public static void main(String[] args) {
    }
}

class Administrator extends User {
    String adminName;
    String email;

    public Administrator(String userId, String password, String loginStatus, String adminName, String email) {
        super(userId, password, loginStatus);
        this.adminName = adminName;
        this.email = email;
    }

    public boolean updateCatalog() {
        return true;
    }

}
