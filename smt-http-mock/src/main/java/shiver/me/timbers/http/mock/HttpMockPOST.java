package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockPOST {

    HttpMockResponse post(String path);

    HttpMockResponse post(String path, Headers headers);

    HttpMockResponse post(String path, String body);

    HttpMockResponse post(String path, Headers headers, String body);
}
