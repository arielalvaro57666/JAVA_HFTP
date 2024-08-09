package types;

public enum ReturnCodes {

    SUCCESFUL(200, "The requested action has been successfully completed"),
    BAD_REQUEST(400, "Bad request"),
    ABORT(426, "Connection closed; transfer aborted"),
    INTERNAL_ERROR(500, "Internal error"),
    INVALID_COMMAND(502, "Invalid command"),
    FILE_NOT_FOUND(550, "File not found");

    private final int code;
    private final String description;

    ReturnCodes(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return this.code;
    }

    public String getDescription() {
        return this.description;
    }



}
