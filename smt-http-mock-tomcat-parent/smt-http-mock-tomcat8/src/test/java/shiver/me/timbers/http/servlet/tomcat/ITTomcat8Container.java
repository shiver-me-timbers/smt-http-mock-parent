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

import org.junit.Test;

import static shiver.me.timbers.data.random.RandomStrings.someAlphaString;

public class ITTomcat8Container {

    @Test
    public void Can_create_a_container_with_just_a_port_and_context() {
        new Tomcat8Container(0, someAlphaString(5)).stop();
    }
}