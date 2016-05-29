package shiver.me.timbers.http.servlet.tomcat;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @author Karl Bennett
 */
class PortGenerator {

    private final int port;
    private final RandomServerSocketFactory socketFactory;

    PortGenerator(int port) {
        this(port, new RandomServerSocketFactory());
    }

    PortGenerator(int port, RandomServerSocketFactory socketFactory) {
        this.port = port;
        this.socketFactory = socketFactory;
    }

    int generatePort() {
        if (port != 0) {
            return port;
        }

        try {
            final ServerSocket socket = socketFactory.create();
            final int randomPort = socket.getLocalPort();
            socket.close();
            return randomPort;
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
