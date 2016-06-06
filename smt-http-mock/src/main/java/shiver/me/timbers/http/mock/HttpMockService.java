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

import shiver.me.timbers.http.Request;
import shiver.me.timbers.http.Response;
import shiver.me.timbers.http.Service;

/**
 * @author Karl Bennett
 */
class HttpMockService implements Service {

    private final HttpMockReflectionHandlerRouter router;
    private final HttpMockHeaderFilter headerFilter;
    private Object handler;

    HttpMockService() {
        this(new HttpMockHeaderFilter());
    }

    private HttpMockService(HttpMockHeaderFilter headerFilter) {
        this(new HttpMockReflectionHandlerRouter(headerFilter), headerFilter);
    }

    HttpMockService(HttpMockReflectionHandlerRouter router, HttpMockHeaderFilter headerFilter) {
        this.router = router;
        this.headerFilter = headerFilter;
    }

    @Override
    public String getName() {
        return getClass().getSimpleName();
    }

    @Override
    public String getPath() {
        return "/*";
    }

    @Override
    public Response call(Request request) {
        return router.route(handler, request);
    }

    void ignoreHeaders(String... names) {
        headerFilter.ignoredHeaders(names);
    }

    @SuppressWarnings("unchecked")
    <T> T registerHandler(T handler) {
        return (T) (this.handler = handler);
    }
}
