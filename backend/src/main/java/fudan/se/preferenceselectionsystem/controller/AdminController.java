package fudan.se.preferenceselectionsystem.controller;

import fudan.se.preferenceselectionsystem.domain.Attachment;
import fudan.se.preferenceselectionsystem.repository.AttachmentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author LBW
 */
@RestController
@RequestMapping("/admin")
public class AdminController {
    private final Logger logger = LoggerFactory.getLogger(AdminController.class);

    private AttachmentRepository attachmentRepository;

    public AdminController(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }
    @PostMapping("/upload-attachment")
    public ResponseEntity<?> uploadAttachment(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
        Attachment attachment = new Attachment();
        try {
            attachment.setContent(file.getBytes());
            attachment.setName(name);
        } catch (IOException e) {
            logger.info("[uploadAttachment] IOException");
            return ResponseEntity.badRequest().body("Upload failed: IOException");
        }
        attachment = attachmentRepository.save(attachment);
        return ResponseEntity.ok(attachment);
    }
}
