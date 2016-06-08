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

import org.apache.catalina.Context;
import org.apache.tomcat.JarScanner;

/**
 * @author Karl Bennett
 */
class Tomcat7Context implements CommonContext<JarScanner> {

    private final Context context;

    Tomcat7Context(Context context) {
        this.context = context;
    }

    @Override
    public void setJarScanner(JarScanner jarScanner) {
        context.setJarScanner(jarScanner);
    }

    @Override
    public String getPath() {
        return context.getPath();
    }
}
