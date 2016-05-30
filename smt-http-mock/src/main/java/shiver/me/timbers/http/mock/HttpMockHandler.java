package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockHandler {

    HttpMockResponse get(String path);

    HttpMockResponse get(String path, Headers headers);

    HttpMockResponse post(String path);

    HttpMockResponse put(String path);

    HttpMockResponse delete(String path);

    HttpMockResponse options(String path);

    HttpMockResponse head(String path);

    HttpMockResponse trace(String path);

    HttpMockResponse patch(String path);

    HttpMockResponse request(String method, String path);
}
