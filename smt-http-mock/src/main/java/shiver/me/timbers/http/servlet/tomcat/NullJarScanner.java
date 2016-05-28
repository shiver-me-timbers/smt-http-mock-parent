package shiver.me.timbers.http.servlet.tomcat;

import org.apache.tomcat.JarScanFilter;
import org.apache.tomcat.JarScanType;
import org.apache.tomcat.JarScanner;
import org.apache.tomcat.JarScannerCallback;

import javax.servlet.ServletContext;

/**
 * @author Karl Bennett
 */
class NullJarScanner implements JarScanner {

    @Override
    public void scan(JarScanType scanType, ServletContext context, JarScannerCallback callback) {
    }

    @Override
    public JarScanFilter getJarScanFilter() {
        return new JarScanFilter() {
            @Override
            public boolean check(JarScanType jarScanType, String jarName) {
                return false;
            }
        };
    }

    @Override
    public void setJarScanFilter(JarScanFilter jarScanFilter) {
    }
}
