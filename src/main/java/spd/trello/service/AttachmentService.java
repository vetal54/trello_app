package spd.trello.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domian.Attachment;
import spd.trello.domian.AttachmentFile;
import spd.trello.exeption.FileCanNotBeUpload;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.repository.AttachmentFileRepository;
import spd.trello.repository.AttachmentRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Service
public class AttachmentService extends AbstractResourceService<Attachment, AttachmentRepository> {

    @Value("${spring.servlet.multipart.location}")
    private String pathStorage;
    @Value("${application.swiche}")
    private Boolean swiche;
    private final AttachmentFileRepository fileRepository;
    private final int sizeMB = 50;

    public AttachmentService(AttachmentRepository repository, AttachmentFileRepository fileRepository) {
        super(repository);
        this.fileRepository = fileRepository;
    }

    public Attachment createAttachment(String name, String email, double size, String path, UUID id) {
        Attachment attachment = new Attachment();
        attachment.setName(name);
        attachment.setCreateBy(email);
        attachment.setSize(size);
        attachment.setLink(path);
        attachment.setCardId(id);
        return repository.save(attachment);
    }

    public void store(MultipartFile file, UUID id) throws IOException {
        Path path = Paths.get(pathStorage);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        swiche = getFileSizeMegaBytes(file) <= sizeMB;
        if (Boolean.TRUE.equals(swiche)) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Attachment attachment = createAttachment(fileName, "string@gmail.com", getFileSizeMegaBytes(file), "in Database", id);
            AttachmentFile fileDB = new AttachmentFile(fileName, file.getContentType(), file.getBytes(), attachment.getId());
            fileRepository.save(fileDB);
        } else {
            long count = Files.list(Path.of(pathStorage)).count();
            path = Paths.get(pathStorage + count + file.getOriginalFilename());
            try {
                file.transferTo(path);
                createAttachment(file.getOriginalFilename(), "string@gmail.com", getFileSizeMegaBytes(file), path.toString(), id);
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
                throw new FileCanNotBeUpload();
            }
        }
    }

    public byte[] load(UUID id) {
        swiche = findById(id).getSize() <= sizeMB;
        if (Boolean.TRUE.equals(swiche)) {
            AttachmentFile attachment = fileRepository.findAttachmentFileByAttachmentId(id);
            return attachment.getFile();
        }
        try {
            Attachment attachment = findById(id);
            File file = new File(attachment.getLink());
            Path path = Paths.get(file.getAbsolutePath());
            return new ByteArrayResource(Files.readAllBytes(path)).getByteArray();
        } catch (Exception e) {
            throw new ResourceNotFoundException();
        }
    }

    private static Double getFileSizeMegaBytes(MultipartFile file) {
        return (double) file.getSize() / (1024 * 1024);
    }
}
