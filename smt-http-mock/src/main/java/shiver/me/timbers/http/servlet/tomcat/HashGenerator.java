package shiver.me.timbers.http.servlet.tomcat;

/**
 * @author Karl Bennett
 */
class HashGenerator {

    int generate(Object object) {
        return System.identityHashCode(object);
    }
}
