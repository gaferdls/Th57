package util;

public class HelpMessage {

    String body;
    String type;

    public HelpMessage(String type_, String message) {
        type = type_;
        body = message;
    }

    public String getBody() {
        return body;
    }

    public String getType() {

        return type;
    }
}
