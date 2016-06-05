package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockRequestMethodFinder {

    HttpMockMethodCall find(Object object, HttpMockArguments arguments) throws NoSuchMethodException {
        return new HttpMockMethodCall(
            object.getClass().getMethod(arguments.getHttpMethod().toLowerCase(), arguments.toParameterTypes()),
            arguments
        );
    }
}
