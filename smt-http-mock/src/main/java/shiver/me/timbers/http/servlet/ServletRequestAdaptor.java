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

import shiver.me.timbers.http.Headers;
import shiver.me.timbers.http.Request;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class ServletRequestAdaptor implements Request {

    private final HttpServletRequest servletRequest;
    private final ServletHeadersExtractor headersExtractor;
    private final Streams streams;

    ServletRequestAdaptor(HttpServletRequest servletRequest) {
        this(servletRequest, new ServletHeadersExtractor(), new Streams());
    }

    ServletRequestAdaptor(HttpServletRequest servletRequest, ServletHeadersExtractor headersExtractor, Streams streams) {
        this.servletRequest = servletRequest;
        this.headersExtractor = headersExtractor;
        this.streams = streams;
    }

    @Override
    public String getMethod() {
        return servletRequest.getMethod();
    }

    @Override
    public String getPath() {
        return servletRequest.getPathInfo() + emptyIfNull(servletRequest.getQueryString());
    }

    @Override
    public boolean hasHeaders() {
        return servletRequest.getHeaderNames().hasMoreElements();
    }

    @Override
    public Headers getHeaders() {
        return headersExtractor.extract(servletRequest);
    }

    @Override
    public boolean hasBody() {
        return servletRequest.getContentLength() > 0;
    }

    @Override
    public String getBodyAsString() {
        try {
            return streams.readToString(servletRequest.getInputStream());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private static String emptyIfNull(String string) {
        if (string == null) {
            return "";
        }
        return "?" + string;
    }
}
