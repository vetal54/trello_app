package spd.trello.controller;

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
public class AttachmentFileController {

    private final AttachmentService service;

    public AttachmentFileController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> save(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") UUID id) throws IOException {
        service.store(file, id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(String.format("File uploaded successfully: %s", file.getOriginalFilename()));
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<byte[]> download(@PathVariable UUID id) {
        Attachment attachment = service.findById(id);
        byte[] file = service.load(id);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + attachment.getName() + "\"")
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(file);
    }
}
