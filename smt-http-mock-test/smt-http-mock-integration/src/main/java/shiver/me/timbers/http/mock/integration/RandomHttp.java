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

package shiver.me.timbers.http.mock.integration;

import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.MultivaluedMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.lang.String.format;
import static shiver.me.timbers.data.random.RandomStrings.buildSomeString;
import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;

class RandomHttp {

    static String somePath() {
        return "/" + buildSomeString().thatContainsAlphanumericCharacters().withLengthBetween(1, 10).build();
    }

    static String someQuery(String name, String value) {
        return format("?%s=%s", name, value);
    }

    static MultivaluedMap<String, Object> someHeaders() {
        return toMap(
            someAlphaString(4), someAlphaString(6),
            someAlphaString(4), someAlphaString(6),
            someAlphaString(4), someAlphaString(6)
        );
    }

    static MultivaluedMap<String, Object> toMap(String... values) {
        final MultivaluedMap<String, Object> headerMap = new MultivaluedHashMap<>();
        for (int i = 0; i < values.length; ) {
            headerMap.putSingle(values[i++], values[i++]);
        }
        return headerMap;
    }

    static Headers toHeaders(MultivaluedMap<String, Object> headerMap) {
        final Set<Header> headers = new HashSet<>();
        final Set<Map.Entry<String, List<Object>>> entries = headerMap.entrySet();
        for (Map.Entry<String, List<Object>> entry : entries) {
            headers.add(new Header(entry.getKey(), entry.getValue().get(0).toString()));
        }
        return new Headers(headers);
    }
}
