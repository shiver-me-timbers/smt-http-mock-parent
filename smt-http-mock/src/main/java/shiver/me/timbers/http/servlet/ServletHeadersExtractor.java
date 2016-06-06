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

package shiver.me.timbers.http.servlet;

import shiver.me.timbers.http.Header;
import shiver.me.timbers.http.Headers;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Karl Bennett
 */
class ServletHeadersExtractor {

    Headers extract(HttpServletRequest request) {
        final Set<Header> headers = new HashSet<>();

        final Enumeration<String> names = request.getHeaderNames();
        while (names.hasMoreElements()) {
            final String name = names.nextElement();
            addHeaders(headers, name, request.getHeaders(name));
        }

        return new Headers(headers);
    }

    private static void addHeaders(Set<Header> headers, String name, Enumeration<String> values) {
        while (values.hasMoreElements()) {
            headers.add(new Header(name, values.nextElement()));
        }
    }
}
