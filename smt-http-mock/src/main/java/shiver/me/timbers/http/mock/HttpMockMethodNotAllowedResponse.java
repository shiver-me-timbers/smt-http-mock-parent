package shiver.me.timbers.http.mock;

import static java.lang.String.format;
import static shiver.me.timbers.http.StatusCodes.METHOD_NOT_ALLOWED;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildRequest;
import static shiver.me.timbers.http.mock.HttpMockResponses.buildSignature;

/**
 * @author Karl Bennett
 */
class HttpMockMethodNotAllowedResponse implements HttpMockResponse {

    private final HttpMockArguments arguments;

    HttpMockMethodNotAllowedResponse(HttpMockArguments arguments) {
        this.arguments = arguments;
    }

    @Override
    public int getStatus() {
        return METHOD_NOT_ALLOWED;
    }

    @Override
    public String getBodyAsString() {
        return format(
            "No http mock method found with the signature \"%s\" for request \"%s\".",
            buildSignature(arguments.getHttpMethod(), arguments.toParameterTypes()),
            buildRequest(arguments.getHttpMethod(), arguments.toParameters())
        );
    }
}
