/*
 * Copyright 2016 Karl Bennett
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package shiver.me.timbers.http.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockReflectionHandlerRouter {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final HttpMockFilteredHeadersRequestFactory requestFactory;
    private final HttpMockRequestArgumentFactory argumentFactory;
    private final HttpMockRequestMethodFinder methodFinder;

    HttpMockReflectionHandlerRouter(HttpMockHeaderFilter headerFilter) {
        this(
            new HttpMockFilteredHeadersRequestFactory(headerFilter),
            new HttpMockRequestArgumentFactory(),
            new HttpMockRequestMethodFinder()
        );
    }

    HttpMockReflectionHandlerRouter(
        HttpMockFilteredHeadersRequestFactory requestFactory,
        HttpMockRequestArgumentFactory argumentFactory,
        HttpMockRequestMethodFinder methodFinder
    ) {
        this.requestFactory = requestFactory;
        this.argumentFactory = argumentFactory;
        this.methodFinder = methodFinder;
    }

    HttpMockResponse route(Object handler, Request request) {
        log.info(
            "Received handler {} and request ({} {}).",
            handler == null ? null : handler.getClass().getSimpleName(), request.getMethod(), request.getPath()
        );
        final HttpMockArguments arguments = argumentFactory.create(requestFactory.create(request));

        try {
            final HttpMockResponse response = methodFinder.find(handler, arguments).invoke(handler);
            if (response != null) {
                return response;
            }

            final HttpMockNotFoundResponse notFoundResponse = new HttpMockNotFoundResponse(arguments);
            log.error(notFoundResponse.getBodyAsString());
            return notFoundResponse;
        } catch (NoSuchMethodException e) {
            final HttpMockMethodNotAllowedResponse response = new HttpMockMethodNotAllowedResponse(arguments);
            log.error(response.getBodyAsString(), e);
            return response;
        }
    }
}
