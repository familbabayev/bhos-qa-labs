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

    public static double division (int a, int b) {
        return a / b;
    }

    public static int power (int a, int b) {
        return (int) Math.pow(a, b);
    }

    public static int modulo (int a, int b) {
        return a % b;
    }

    public static int square (int n) {
        return n * n;
    }

    public static int cube (int n) {
        return n * n * n;
    }

    public static int factorial (int n) {
        int i, fact = 1;
        for(i=1 ;i<=n ; i++){
            fact=fact*i;
        }
        return fact;
    }

    public void make_hash (String data) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("SHA-1");
        byte[] digest = messageDigest.digest(data.getBytes());
    }

}
