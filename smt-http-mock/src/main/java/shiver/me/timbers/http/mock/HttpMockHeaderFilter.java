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

/**
 * @author Karl Bennett
 */
class HttpMockHeaderFilter {

    private String[] names;

    void ignoredHeaders(String... names) {
        this.names = names;
    }

    Headers filter(Headers headers) {
        final Headers filteredHeaders = new Headers(headers);

        if (names == null) {
            return filteredHeaders;
        }

        for (String name : names) {
            filteredHeaders.remove(name);
        }
        return filteredHeaders;
    }
}
