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

package shiver.me.timbers.http.servlet.tomcat;

import org.apache.catalina.Wrapper;

/**
 * @author Karl Bennett
 */
class Tomcat8Wrapper implements CommonWrapper {

    private final Wrapper wrapper;

    Tomcat8Wrapper(Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void addMapping(String path) {
        wrapper.addMapping(path);
    }
}
