package spd.trello.controller;

import lombok.extern.log4j.Log4j2;
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
@RequestMapping("/attachment-file")
@Log4j2
public class AttachmentFileController {

    private final AttachmentService service;

    public AttachmentFileController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") UUID id) throws IOException {
        log.trace("Entering upload() method");
        service.store(file, id);
        log.info("File upload was successful");
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable UUID id) {
        log.trace("Entering download() method");
        Attachment attachment = service.findById(id);
        byte[] file = service.load(id);
        log.info("File download was successful");
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }
}
