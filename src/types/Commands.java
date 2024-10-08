package types;

public enum Commands {
    LIST("list"),
    DOWNLOAD("download"),
    UPLOAD("upload"),
    QUIT("quit"),
    DEFAULT("");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public String getCommand() {
        return this.command;
    }

    public static Commands returnCommand(String command) {
        try {
            return Commands.valueOf(command.toUpperCase());
        } catch(IllegalArgumentException e) {
            return Commands.DEFAULT;
        }
    }


}
