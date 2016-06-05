package shiver.me.timbers.http.mock;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author Karl Bennett
 */
class HttpMockMethodCall {

    private final Method method;
    private final HttpMockArguments arguments;

    HttpMockMethodCall(Method method, HttpMockArguments arguments) {
        this.method = method;
        this.arguments = arguments;
    }

    HttpMockResponse invoke(Object object) {
        try {
            return (HttpMockResponse) method.invoke(object, arguments.toParameters());
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new UnsupportedOperationException();
        }
    }
}
