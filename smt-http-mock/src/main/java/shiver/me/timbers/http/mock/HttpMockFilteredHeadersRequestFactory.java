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

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

/**
 * @author Karl Bennett
 */
class HttpMockFilteredHeadersRequestFactory {

    private final HttpMockHeaderFilter headerFilter;

    HttpMockFilteredHeadersRequestFactory(HttpMockHeaderFilter headerFilter) {
        this.headerFilter = headerFilter;
    }

    public Request create(final Request request) {
        final Headers headers = headerFilter.filter(request.getHeaders());
        return new Request() {
            @Override
            public String getMethod() {
                return request.getMethod();
            }

            @Override
            public String getPath() {
                return request.getPath();
            }

            @Override
            public boolean hasHeaders() {
                return !headers.isEmpty();
            }

            @Override
            public Headers getHeaders() {
                return headers;
            }

            @Override
            public boolean hasBody() {
                return request.hasBody();
            }

            @Override
            public String getBodyAsString() {
                return request.getBodyAsString();
            }
        };
    }
}
