package light.house.rest.starter;

public class HelloService {
    private String name;

    private String language;

    public String sayHello() {
        return "hello," + name + ",welcome to the " + language + " world !";
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
