package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockHandler {

    HttpMockResponse get(String path);

    HttpMockResponse get(String path, Headers headers);

    HttpMockResponse post(String path);

    HttpMockResponse post(String path, Headers headers);

    HttpMockResponse put(String path);

    HttpMockResponse put(String path, Headers headers);

    HttpMockResponse delete(String path);

    HttpMockResponse delete(String path, Headers headers);

    HttpMockResponse options(String path);

    HttpMockResponse options(String path, Headers headers);

    HttpMockResponse head(String path);

    HttpMockResponse head(String path, Headers headers);

    HttpMockResponse trace(String path);

    HttpMockResponse trace(String path, Headers headers);

    HttpMockResponse patch(String path);

    HttpMockResponse patch(String path, Headers headers);

    HttpMockResponse request(String method, String path);

    HttpMockResponse request(String method, String path, Headers headers);
}
