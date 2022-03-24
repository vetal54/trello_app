package spd.trello.service;

import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@Component
public class FileService {

    private final String attachmentPath = "attachment-file\\";

    public String copyFile(String sourcePath) throws IOException {
        if (pathValidity(sourcePath)) {
            Path from = Paths.get(sourcePath);
            Path to = Paths.get(attachmentPath + from.getFileName());
            CopyOption[] options = new CopyOption[]{
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES
            };
            Files.copy(from, to, options);
            return to.toString();
        }
        return sourcePath;
    }

    private boolean pathValidity(String path) {
        return (new File(path).exists());
    }
}
