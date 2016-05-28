package shiver.me.timbers.http;

/**
 * @author Karl Bennett
 */
public interface Container {

    void register(Service service);

    void start();

    int getPort();

    void stop();
}
