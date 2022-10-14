package spd.trello.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domian.Attachment;
import spd.trello.domian.AttachmentFile;
import spd.trello.exeption.FileCanNotBeUpload;
import spd.trello.exeption.ResourceNotFoundException;
import spd.trello.exeption.ValidationIsWrong;
import spd.trello.repository.AttachmentFileRepository;
import spd.trello.repository.AttachmentRepository;
import spd.trello.repository.CardRepository;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;


@Service
@Slf4j
public class AttachmentService extends AbstractResourceService<Attachment, AttachmentRepository> implements Validator<Attachment> {

    @Value("${spring.servlet.multipart.location}")
    private String pathStorage;
    @Value("${application.swiche}")
    private Boolean swiche;
    private final CardRepository cardRepository;
    private final AttachmentFileRepository fileRepository;
    private final int sizeMB = 50;

    public AttachmentService(AttachmentRepository repository, CardRepository cardRepository, AttachmentFileRepository fileRepository) {
        super(repository);
        this.cardRepository = cardRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public Attachment save(Attachment attachment) throws JsonProcessingException {
        log.info("Try saving attachment");
        validateName(attachment.getName());
        validateReference(attachment);
        log.info("Attachment created successfully {}", mapper.writeValueAsString(attachment));
        return repository.save(attachment);
    }

    @Override
    public Attachment update(Attachment attachment) throws JsonProcessingException {
        log.info("Try updating attachment");
        validateName(attachment.getName());
        validateReference(attachment);
        log.info("Attachment updated successfully {}", mapper.writeValueAsString(attachment));
        return repository.save(attachment);
    }

    public Attachment createAttachment(String name, double size, String path, UUID id) throws JsonProcessingException {
        log.info("Create attachment");
        Attachment attachment = new Attachment();
        attachment.setName(name);
        attachment.setSize(size);
        attachment.setLink(path);
        attachment.setCardId(id);
        validateReference(attachment);
        log.info("Attachment created successfully {}", mapper.writeValueAsString(attachment));
        return save(attachment);
    }

    public void store(MultipartFile file, UUID id) throws IOException {
        log.info("Trying to save the file");
        Path path = Paths.get(pathStorage);
        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }
        swiche = getFileSizeMegaBytes(file) <= sizeMB;
        if (Boolean.TRUE.equals(swiche)) {
            String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
            Attachment attachment = createAttachment(fileName, getFileSizeMegaBytes(file), "in Database", id);
            AttachmentFile fileDB = new AttachmentFile(fileName, file.getContentType(), file.getBytes(), attachment.getId());
            log.info("File was saved to DataBase");
            fileRepository.save(fileDB);
        } else {
            long count = Files.list(Path.of(pathStorage)).count();
            path = Paths.get(pathStorage + count + file.getOriginalFilename());
            try {
                file.transferTo(path);
                log.info("File was saved to file system");
                createAttachment(file.getOriginalFilename(), getFileSizeMegaBytes(file), path.toString(), id);
            } catch (IllegalStateException | IOException e) {
                log.error("File can`t be upload to file system");
                throw new FileCanNotBeUpload();
            }
        }
    }

    public byte[] load(UUID id) {
        log.info("Trying to download the file");
        swiche = findById(id).getSize() <= sizeMB;
        if (Boolean.TRUE.equals(swiche)) {
            AttachmentFile attachment = fileRepository.findAttachmentFileByAttachmentId(id);
            log.info("File will be download from DataBase");
            return attachment.getFile();
        }
        try {
            Attachment attachment = findById(id);
            File file = new File(attachment.getLink());
            Path path = Paths.get(file.getAbsolutePath());
            log.info("File will be download from file system");
            return new ByteArrayResource(Files.readAllBytes(path)).getByteArray();
        } catch (Exception e) {
            log.error("The file can`t be loaded with the file system");
            throw new ResourceNotFoundException("Resource not found");
        }
    }

    private static Double getFileSizeMegaBytes(MultipartFile file) {
        log.info("Get file size");
        return (double) file.getSize() / (1024 * 1024);
    }

    @Override
    public void validateReference(Attachment attachment) {
        log.info("Try checked foreign key");
        cardRepository.findById(attachment.getCardId()).orElseThrow(() ->
                new ResourceNotFoundException("Card reference not valid. Id not corrected: " + attachment.getCardId()));
    }

    private void validateName(String name) {
        if (name.length() > 100) {
            log.error("Name size must be between 10 and 100");
            throw new ValidationIsWrong("Name size must be between 10 and 100");
        }
    }
}
