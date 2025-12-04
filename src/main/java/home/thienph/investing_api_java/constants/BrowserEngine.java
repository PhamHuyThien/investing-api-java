package home.thienph.investing_api_java.constants;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum BrowserEngine {
    CHROMIUM(0, "chromium"),
    FIREFOX(1, "firefox"),
    WEBKIT(2, "webkit");
    private final int id;
    private final String name;

    public static BrowserEngine fromId(int id) {
        for (BrowserEngine type : BrowserEngine.values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        return null;
    }

    public static BrowserEngine fromName(String name) {
        for (BrowserEngine type : BrowserEngine.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }
        return null;
    }
}
