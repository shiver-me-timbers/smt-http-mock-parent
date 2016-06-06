package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockPUT {

    HttpMockResponse put(String path);

    HttpMockResponse put(String path, Headers headers);

    HttpMockResponse put(String path, String body);

    HttpMockResponse put(String path, Headers headers, String body);
}
