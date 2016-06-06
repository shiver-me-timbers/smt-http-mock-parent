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

import java.util.ArrayList;
import java.util.List;

/**
 * @author Karl Bennett
 */
class HttpMockRequestArgumentFactory {

    HttpMockArguments create(Request request) {
        final List<Class> types = new ArrayList<>();
        final List<Object> values = new ArrayList<>();

        types.add(String.class);
        values.add(request.getPath());

        if (request.hasHeaders()) {
            types.add(Headers.class);
            values.add(request.getHeaders());
        }
        if (request.hasBody()) {
            types.add(String.class);
            values.add(request.getBodyAsString());
        }

        return new HttpMockArguments(request.getMethod(), types, values);
    }
}
