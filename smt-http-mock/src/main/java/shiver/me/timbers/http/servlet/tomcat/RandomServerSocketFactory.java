package shiver.me.timbers.http.servlet.tomcat;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Karl Bennett
 */
class RandomServerSocketFactory {

    ServerSocket create() throws IOException {
        return new ServerSocket(0);
    }
}
