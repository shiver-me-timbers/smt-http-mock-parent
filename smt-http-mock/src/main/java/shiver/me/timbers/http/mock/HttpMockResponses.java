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

/**
 * @author Karl Bennett
 */
class HttpMockResponses {

    static String buildSignature(String name, Class... parameterTypes) {
        final StringBuilder builder = new StringBuilder().append(name.toLowerCase()).append("(");
        for (Class type : parameterTypes) {
            builder.append(type.getSimpleName()).append(", ");
        }
        return builder.replace(builder.length() - 2, builder.length(), ")").toString();
    }

    static String buildRequest(String method, Object... parameters) {
        final StringBuilder builder = new StringBuilder().append(method).append(" ");
        for (Object type : parameters) {
            builder.append(type).append(" ");
        }
        return builder.toString().trim();
    }
}
