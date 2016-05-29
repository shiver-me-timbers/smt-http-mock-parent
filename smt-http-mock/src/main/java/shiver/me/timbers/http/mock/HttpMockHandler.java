package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public interface HttpMockHandler {

    HttpMockResponse get();

    HttpMockResponse post();

    HttpMockResponse put();
}
