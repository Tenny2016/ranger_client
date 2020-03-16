package com.waniuzhang.ranger;

/**
 * Created by 1 on 2020/3/13.
 */
public class RangerClientException extends RuntimeException{

    private static final long serialVersionUID = 1L;
    private Throwable cause;
    private int status;
    private String massage;

    public RangerClientException(int status, String massage) {
        this.status = status;
        this.massage = massage;
    }

    @Override
    public String getMessage() {
        return String.format("%s http status = %s",massage,status);
    }

    @Override
    public String toString() {
        return String.format("%s http status = %s",massage,status);
    }
}
