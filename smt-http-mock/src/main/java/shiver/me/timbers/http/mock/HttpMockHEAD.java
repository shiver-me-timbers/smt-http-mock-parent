package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockHEAD {

    HttpMockResponse head(String path);

    HttpMockResponse head(String path, Headers headers);

    HttpMockResponse head(String path, String body);

    HttpMockResponse head(String path, Headers headers, String body);
}
