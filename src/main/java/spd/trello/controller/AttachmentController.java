package spd.trello.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spd.trello.domian.Attachment;
import spd.trello.service.AttachmentService;
import spd.trello.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/attachment")
public class AttachmentController extends AbstractResourceController<Attachment, AttachmentService> {

    private final FileService fileService;

    public AttachmentController(AttachmentService service, FileService fileService) {
        this.service = service;
        this.fileService = fileService;
    }

    @PostMapping
    @Override
    public ResponseEntity<Attachment> create(@RequestBody Attachment resource) throws IOException {
        resource.setLink(fileService.copyFile(resource.getLink()));
        Attachment result = service.save(resource);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}
