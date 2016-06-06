package shiver.me.timbers.http.servlet.tomcat;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @author Karl Bennett
 */
class FileCleaner {

    private final Logger log = LoggerFactory.getLogger(getClass());

    void cleanUp(String tempDir) throws IOException {
        final File file = new File(tempDir);

        if (file.isFile()) {
            file.delete();
        }

        FileUtils.deleteDirectory(file);
        log.info("Cleaned up the temporary tomcat directory ({}).", tempDir);
    }
}
