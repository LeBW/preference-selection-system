package fudan.se.preferenceselectionsystem.controller;

import fudan.se.preferenceselectionsystem.controller.request.StudentLoginRequest;
import fudan.se.preferenceselectionsystem.domain.Attachment;
import fudan.se.preferenceselectionsystem.domain.Major;
import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.repository.AttachmentRepository;
import fudan.se.preferenceselectionsystem.security.jwt.JwtTokenUtil;
import fudan.se.preferenceselectionsystem.service.StudentService;
import fudan.se.preferenceselectionsystem.utils.ChoicesOverview;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

/**
 * @author LBW
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private StudentService studentService;
    private JwtTokenUtil jwtTokenUtil;
    private AttachmentRepository attachmentRepository;

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(StudentService studentService, JwtTokenUtil jwtTokenUtil, AttachmentRepository attachmentRepository) {
        this.studentService = studentService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.attachmentRepository = attachmentRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentLoginRequest request) {
        logger.info("Student Login: " + request.getTicketNumber() + " " + request.getIdNumber());
        Student student = studentService.login(request.getTicketNumber(), request.getIdNumber());
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", jwtTokenUtil.generateToken(request.getTicketNumber()));
        responseMap.put("student", student);
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/info")
    public ResponseEntity<?> getPersonalInfo(Authentication authentication) {
        logger.info("Student get personal info: " + authentication.getName());
        Student result = studentService.getPersonalInfo(authentication.getName());
        return ResponseEntity.ok(result);
    }

    @PostMapping("/choice")
    public ResponseEntity<?> modifyChoice(@RequestBody Student request, Authentication authentication) {
        logger.info("Student modify choice: " + authentication.getName());
        studentService.modifyChoice(authentication.getName(), request);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "success");
        return ResponseEntity.ok(responseMap);
    }

    @GetMapping("/major")
    public ResponseEntity<?> getMajorInfo(Authentication authentication) {
        logger.info("Student get majors: " + authentication.getName());
        Iterable<Major> majorInfo = studentService.getMajorInfo(authentication.getName());
        return ResponseEntity.ok(majorInfo);
    }

    @GetMapping("/choices-overview")
    public ResponseEntity<?> getChoicesOverview(Authentication authentication) {
        logger.info("Student get Choices Overview: " + authentication.getName());
        List<ChoicesOverview> choicesOverviews = studentService.getChoicesOverview(authentication.getName());
        return ResponseEntity.ok(choicesOverviews);
    }

    @GetMapping("/files")
    public Iterable<Attachment> getAllAttachments(Authentication authentication) {
        return attachmentRepository.getIdAndNameOnly();
    }

    @GetMapping("files/{id}")
    public ResponseEntity<?> getAttachmentById(@PathVariable("id") Long id) throws UnsupportedEncodingException {
        Attachment attachment = attachmentRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + URLEncoder.encode(attachment.getName(), "UTF-8") + "\"")
                .body(attachment.getContent());
    }
}
