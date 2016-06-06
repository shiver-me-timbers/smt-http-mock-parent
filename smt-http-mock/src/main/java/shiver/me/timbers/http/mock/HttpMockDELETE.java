package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockDELETE {

    HttpMockResponse delete(String path);

    HttpMockResponse delete(String path, Headers headers);

    HttpMockResponse delete(String path, String body);

    HttpMockResponse delete(String path, Headers headers, String body);
}
