package shiver.me.timbers.http.servlet.tomcat;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class FileCleaner {

    void cleanUp(String tempDir) throws IOException {
        final File file = new File(tempDir);

        if (file.isFile()) {
            file.delete();
        }

        FileUtils.deleteDirectory(file);
    }
}
