package fudan.se.preferenceselectionsystem.controller;

import fudan.se.preferenceselectionsystem.controller.request.StudentLoginRequest;
import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.security.jwt.JwtTokenUtil;
import fudan.se.preferenceselectionsystem.service.CustomUserDetailsService;
import fudan.se.preferenceselectionsystem.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    public StudentController(CustomUserDetailsService customUserDetailsService, StudentService studentService, JwtTokenUtil jwtTokenUtil) {
        this.customUserDetailsService = customUserDetailsService;
        this.studentService = studentService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody StudentLoginRequest request) {
        Student student = studentService.login(request.getTicketNumber(), request.getIdNumber());
        HashMap<String, Object> responseMap = new HashMap<>();
        responseMap.put("token", jwtTokenUtil.generateToken(request.getTicketNumber()));
        responseMap.put("student", student);
        return ResponseEntity.ok(responseMap);
    }
}
