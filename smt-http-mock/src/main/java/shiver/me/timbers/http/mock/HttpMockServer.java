package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
public interface HttpMockServer {
    void ignoreHeaders(String... names);

    int getPort();

    <T> T mock(T handler);

    void stop();
}
