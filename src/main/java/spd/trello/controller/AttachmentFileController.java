package spd.trello.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import spd.trello.domian.Attachment;
import spd.trello.service.AttachmentService;

import java.util.UUID;


@RestController
@RequestMapping("/attachment-file")
public class AttachmentFileController {

    private final AttachmentService service;

    public AttachmentFileController(AttachmentService service) {
        this.service = service;
    }

    @PostMapping("/upload")
    public ResponseEntity<Attachment> create(
            @RequestParam("file") MultipartFile file,
            @RequestParam("id") UUID id,
            @RequestParam("email") String email) {
        Attachment result = service.store(file, email, id);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @GetMapping("/download/{id}")
    @ResponseBody
    public ResponseEntity<Resource> getFile(@PathVariable UUID id) {
        Resource file = service.load(id);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(file);
    }

}
