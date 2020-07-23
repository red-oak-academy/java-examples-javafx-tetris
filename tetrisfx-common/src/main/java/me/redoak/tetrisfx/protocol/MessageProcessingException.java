package me.redoak.tetrisfx.protocol;

public class MessageProcessingException extends Exception {
    public MessageProcessingException(Throwable cause) {
        super(cause);
    }

    public MessageProcessingException(String s) {
        super(s);
    }
}
