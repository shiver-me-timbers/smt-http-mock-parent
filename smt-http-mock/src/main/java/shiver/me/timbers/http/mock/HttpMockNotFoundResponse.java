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

import static java.lang.String.format;
import static shiver.me.timbers.http.StatusCodes.NOT_FOUND;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildRequest;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildSignature;

/**
 * @author Karl Bennett
 */
class HttpMockNotFoundResponse implements HttpMockResponse {

    private final HttpMockArguments arguments;

    HttpMockNotFoundResponse(HttpMockArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public int getStatus() {
        return NOT_FOUND;
    }

    @Override
    public String getBodyAsString() {
        return format(
            "No result returned from mock method with the signature \"%s\" for request \"%s\".",
            buildSignature(arguments.getHttpMethod(), arguments.toParameterTypes()),
            buildRequest(arguments.getHttpMethod(), arguments.toParameters())
        );
    }
}
