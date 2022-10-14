package spd.trello.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domian.Attachment;
import spd.trello.service.AttachmentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/attachment")
@Slf4j
public class AttachmentController extends AbstractResourceController<Attachment, AttachmentService> {

    public AttachmentController(AttachmentService service) {
        super(service);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") UUID id) throws IOException {
        log.info("File started upload");
        service.store(file, id);
        log.info("File upload was successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable UUID id) {
        log.info("File started download");
        Attachment attachment = service.findById(id);
        byte[] file = service.load(id);
        log.info("File download was successful");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }
}
