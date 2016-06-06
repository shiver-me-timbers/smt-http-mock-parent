package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockTRACE {

    HttpMockResponse trace(String path);

    HttpMockResponse trace(String path, Headers headers);

    HttpMockResponse trace(String path, String body);

    HttpMockResponse trace(String path, Headers headers, String body);
}
