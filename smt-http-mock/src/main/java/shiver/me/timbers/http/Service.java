package shiver.me.timbers.http;

/**
 * @author Karl Bennett
 */
public interface Service {

    String getName();

    String getPath();

    Response call(Request request);
}
