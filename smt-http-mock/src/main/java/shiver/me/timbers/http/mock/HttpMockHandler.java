package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public interface HttpMockHandler {

    HttpMockResponse get(String path);

    HttpMockResponse post(String path);

    HttpMockResponse put(String path);

    HttpMockResponse delete(String path);

    HttpMockResponse options(String path);

    HttpMockResponse head(String path);

    HttpMockResponse trace(String path);

    HttpMockResponse patch(String path);

    HttpMockResponse request(String method, String path);
}
