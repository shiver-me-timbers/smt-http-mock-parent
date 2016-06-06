package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockPATCH {

    HttpMockResponse patch(String path);

    HttpMockResponse patch(String path, Headers headers);

    HttpMockResponse patch(String path, String body);

    HttpMockResponse patch(String path, Headers headers, String body);
}
