import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Classroom {

    public static int addition (int a, int b) {
        return a + b;
    }

    public static int subtraction(int a, int b) {
        return a - b;
    }

    public static int multiplication (int a, int b) {
        return a * b;
    }


    public void make_hash () throws NoSuchAlgorithmException {
        String data = "0123456789";

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] digest = messageDigest.digest(data.getBytes());

    }

}
