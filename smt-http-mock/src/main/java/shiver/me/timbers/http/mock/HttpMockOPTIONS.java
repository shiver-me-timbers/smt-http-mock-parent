package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Headers;

/**
 * @author Karl Bennett
 */
public interface HttpMockOPTIONS {

    HttpMockResponse options(String path);

    HttpMockResponse options(String path, Headers headers);

    HttpMockResponse options(String path, String body);

    HttpMockResponse options(String path, Headers headers, String body);
}
