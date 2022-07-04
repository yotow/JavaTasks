package lesson3.tasks;

public enum WorkingDirectory {

    WINDOWS("C:\\Games"),
    LINUX("/opt/Games"),
    MAC("/Users/admin/Games");

    private final String path;

    WorkingDirectory(String val) {
        this.path = val;
    }

    public String getValue() {
        return this.path;
    }
}