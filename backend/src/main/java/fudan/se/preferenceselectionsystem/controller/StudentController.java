package fudan.se.preferenceselectionsystem.controller;

import fudan.se.preferenceselectionsystem.controller.request.StudentLoginRequest;
import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.security.jwt.JwtTokenUtil;
import fudan.se.preferenceselectionsystem.service.CustomUserDetailsService;
import fudan.se.preferenceselectionsystem.service.StudentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * @author LBW
 */
@RestController
@RequestMapping("/student")
public class StudentController {
    private CustomUserDetailsService customUserDetailsService;
    private StudentService studentService;
    private JwtTokenUtil jwtTokenUtil;

    private final Logger logger = LoggerFactory.getLogger(StudentController.class);

    public StudentController(CustomUserDetailsService customUserDetailsService, StudentService studentService, JwtTokenUtil jwtTokenUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.studentService = studentService;
        this.jwtTokenUtil = jwtTokenUtil;
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

    @PostMapping("choice")
    public ResponseEntity<?> modifyChoice(@RequestBody Student request, Authentication authentication) {
        logger.info("Student modify choice: " + authentication.getName());
        studentService.modifyChoice(authentication.getName(), request);
        HashMap<String, String> responseMap = new HashMap<>();
        responseMap.put("message", "success");
        return ResponseEntity.ok(responseMap);
    }
}
