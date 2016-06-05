package shiver.me.timbers.http.mock.integration;

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.mock.HttpMockResponse;

interface CustomHttpMethodHandler {

    HttpMockResponse custom(String path);

    HttpMockResponse custom(String path, Headers headers);
}
