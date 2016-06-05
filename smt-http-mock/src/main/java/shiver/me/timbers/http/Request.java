package shiver.me.timbers.http;

/**
 * @author Karl Bennett
 */
public interface Request {

    String getMethod();

    String getPath();

    boolean hasHeaders();

    Headers getHeaders();

    boolean hasBody();

    String getBodyAsString();
}
