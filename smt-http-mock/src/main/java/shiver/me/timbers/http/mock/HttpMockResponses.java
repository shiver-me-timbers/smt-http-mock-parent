package shiver.me.timbers.http.mock;

/**
 * @author Karl Bennett
 */
class HttpMockResponses {

    static String buildSignature(String name, Class... parameterTypes) {
        final StringBuilder builder = new StringBuilder().append(name.toLowerCase()).append("(");
        for (Class type : parameterTypes) {
            builder.append(type.getSimpleName()).append(", ");
        }
        return builder.replace(builder.length() - 2, builder.length(), ")").toString();
    }
}
