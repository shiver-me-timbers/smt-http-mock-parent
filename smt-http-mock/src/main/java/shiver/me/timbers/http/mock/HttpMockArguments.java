package shiver.me.timbers.http.mock;

import java.util.List;

/**
 * @author Karl Bennett
 */
public class HttpMockArguments {

    private final String httpMethod;
    private final List<Class> types;
    private final List<Object> values;

    public HttpMockArguments(String httpMethod, List<Class> types, List<Object> values) {
        this.httpMethod = httpMethod;
        this.types = types;
        this.values = values;
    }

    String getHttpMethod() {
        return httpMethod;
    }

    Class[] toParameterTypes() {
        return types.toArray(new Class[types.size()]);
    }

    Object[] toParameters() {
        return values.toArray(new Object[values.size()]);
    }
}
