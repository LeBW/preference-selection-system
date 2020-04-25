package fudan.se.preferenceselectionsystem.service;

import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.repository.StudentRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

/**
 * @author LBW
 */
@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;

    public StudentService(StudentRepository studentRepository, CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager) {
        this.studentRepository = studentRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;

    }


    public Student login(String ticketNumber, String idNumber) {
        UserDetails userdetails = customUserDetailsService.loadUserByUsername(ticketNumber);
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(ticketNumber, idNumber);

        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return studentRepository.findByTicketNumber(ticketNumber);
    }

    public Student getPersonalInfo(String ticketNumber) {
        return studentRepository.findByTicketNumber(ticketNumber);
    }

    public void modifyChoice(String ticketNumber, Student request) {
        Student student = studentRepository.findByTicketNumber(ticketNumber);
        //modify the choice
        student.setFirstChoiceMajor(request.getFirstChoiceMajor());
        student.setFirstChoiceDirection(request.getFirstChoiceDirection());
        student.setSecondChoiceMajor(request.getSecondChoiceMajor());
        student.setSecondChoiceDirection(request.getSecondChoiceDirection());
        student.setAdjustDegreeType(request.getAdjustDegreeType());
        student.setAdjustMajor(request.getAdjustMajor());
        //save into database
        studentRepository.save(student);
    }
}
