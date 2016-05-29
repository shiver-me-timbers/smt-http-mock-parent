package shiver.me.timbers.http.mock;

import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
interface HttpMockRequestHandler {

    HttpMockResponse handle(HttpMockHandler handler, Request request);
}
