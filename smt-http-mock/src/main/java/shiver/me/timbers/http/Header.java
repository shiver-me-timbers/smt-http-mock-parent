package shiver.me.timbers.http;

/**
 * @author Karl Bennett
 */
public class Header {

    private final String name;
    private final String value;

    public Header(String name, String value) {
        this.name = name.toLowerCase();
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Header that = (Header) object;

        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }

        return value != null ? value.equals(that.value) : that.value == null;

    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Header{" +
            "name='" + name + '\'' +
            ", value='" + value + '\'' +
            '}';
    }
}
