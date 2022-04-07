package spd.trello.service;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.core.io.Resource;
import spd.trello.domian.*;
import spd.trello.exeption.FileCanNotBeUpload;
import spd.trello.repository.AttachmentRepository;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Service
public class AttachmentService extends AbstractResourceService<Attachment, AttachmentRepository> {

    private final String path = "attachment-file\\";

    public AttachmentService(AttachmentRepository repository) {
        super(repository);
    }

    public Attachment createAttachment(String name, String email, String path, UUID id) {
        Attachment attachment = new Attachment();
        attachment.setName(name);
        attachment.setCreateBy(email);
        attachment.setLink(path);
        attachment.setCardId(id);
        return repository.save(attachment);
    }

    public Attachment store(MultipartFile file, String email, UUID id) {
        try {
            Path root = Paths.get(path);
            Files.copy(file.getInputStream(), root.resolve(Objects.requireNonNull(file.getOriginalFilename())));
            return createAttachment(file.getName(), email, file.getOriginalFilename(), id);
        } catch (Exception e) {
            throw new FileCanNotBeUpload();
        }
    }

    public Resource load(UUID id) {
        String fileName = repository.getLinkById(id);
        try {
            Path file = Paths.get(path)
                    .resolve(fileName);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            } else {
                throw new RuntimeException("Could not read the file!");
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
