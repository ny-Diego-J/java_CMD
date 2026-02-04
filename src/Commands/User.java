package Commands;

public class User {
    public String name;
    public String password;
    public boolean doesSudo;

    public User(){};

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDoesSudo() {
        return doesSudo;
    }

    public void setDoesSudo(boolean doesSudo) {
        this.doesSudo = doesSudo;
    }
}
