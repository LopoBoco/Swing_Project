import java.util.HashMap;
import java.util.Map;

public class User {
    private static final Map<String, String> users = new HashMap<>();

    static {

        users.put("user1", "password1");
        users.put("user2", "password2");
        users.put("user3", "password3");
    }

    public static boolean isAuthenticate(String username, String password) {
        return users.containsKey(username) && users.get(username).equals(password);
    }
}
