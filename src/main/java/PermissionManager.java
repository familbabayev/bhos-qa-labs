public class PermissionManager {
    private PermissionLevel mCurrentLevel = PermissionLevel.DEVELOPER;

    public String getmCurrentLevel(){
        String currentLevel = "";
        switch (mCurrentLevel){
            case USER:
                currentLevel = "User";
                break;
            case ADMIN:
                currentLevel = "Admin";
                break;
            case DEVELOPER:
                currentLevel = "Developer";
                break;
        }

        return currentLevel;
    }

    public void setPermissionLevel(PermissionLevel permissionLevel){
        mCurrentLevel = permissionLevel;
    }
}
