import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PermissionManagerTest {
    PermissionManager permissionManager;

    @BeforeEach
    void initialize() {
        permissionManager = new PermissionManager();
    }

    @Test
    void getmCurrentLevel() {
        assertEquals("Developer", permissionManager.getmCurrentLevel());
    }

    @Test
    void setPermissionLevel() {
        permissionManager.setPermissionLevel(PermissionLevel.USER);
        assertEquals("User", permissionManager.getmCurrentLevel());
    }

    @Test
    void setPermissionLevel() {
        permissionManager.setPermissionLevel(PermissionLevel.ADMIN);
        assertEquals("Admin", permissionManager.getmCurrentLevel());
    }
}
