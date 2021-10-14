import java.security.MessageDigest;

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


    public byte make_hash () {
        byte[] data1 = "0123456789".getBytes("UTF-8");

        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] digest = messageDigest.digest(data1);
    }

}
