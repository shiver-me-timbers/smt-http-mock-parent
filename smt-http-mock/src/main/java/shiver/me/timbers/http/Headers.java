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

/**
 * @author Karl Bennett
 */
public class Headers {

    private final Set<Header> headers;

    public Headers(Headers headers) {
        this(headers.headers);
    }

    public Headers(Set<Header> headers) {
        this.headers = new HashSet<>(headers);
    }

    public boolean isEmpty() {
        return headers.isEmpty();
    }

    public void remove(String name) {
        for (Header header : new HashSet<>(headers)) {
            if (name.equalsIgnoreCase(header.getName())) {
                headers.remove(header);
            }
        }
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        final Headers that = (Headers) object;

        return headers.equals(that.headers);
    }

    @Override
    public int hashCode() {
        return headers.hashCode();
    }

    @Override
    public String toString() {
        return "Headers{" +
            "headers=" + headers +
            '}';
    }
}
