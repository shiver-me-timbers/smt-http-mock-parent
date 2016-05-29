package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public interface HttpMockHandler {

    HttpMockResponse get();

    HttpMockResponse post();

    HttpMockResponse put();

    HttpMockResponse delete();

    HttpMockResponse options();

    HttpMockResponse head();

    HttpMockResponse trace();

    HttpMockResponse patch();

    HttpMockResponse request(String method);
}
