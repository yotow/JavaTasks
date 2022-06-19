package L3T1;

public enum WorkingDirectory {
    WINDOWS("C:\\Games"),
    LINUX("/opt/Games"),
    MAC("/Users/admin/Games");

    private final String path;
    WorkingDirectory(String val) {
        this.path = val;
    }

    @Override
    public String toString() {
        return this.path;
    }
}
