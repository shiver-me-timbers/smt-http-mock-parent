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

package shiver.me.timbers.http;

import java.util.HashSet;
import java.util.Set;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableSet;

/**
 * @author Karl Bennett
 */
public class Methods {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String PUT = "PUT";
    public static final String PATCH = "PATCH";
    public static final String DELETE = "DELETE";
    public static final String OPTIONS = "OPTIONS";
    public static final String HEAD = "HEAD";
    public static final String TRACE = "TRACE";

    public static final Set<String> METHODS = unmodifiableSet(new HashSet<>(asList(
        GET,
        POST,
        PUT,
        PATCH,
        DELETE,
        OPTIONS,
        HEAD,
        TRACE
    )));
}
