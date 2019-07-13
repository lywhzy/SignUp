package hbue.it.exception;

public class UserNotFoundException extends RuntimeException{

    @Override
    public String getMessage() {
        return "请登录";
    }
}
