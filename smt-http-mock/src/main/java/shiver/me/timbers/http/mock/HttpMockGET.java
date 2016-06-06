package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockGET {

    HttpMockResponse get(String path);

    HttpMockResponse get(String path, Headers headers);

    HttpMockResponse get(String path, String body);

    HttpMockResponse get(String path, Headers headers, String body);
}
