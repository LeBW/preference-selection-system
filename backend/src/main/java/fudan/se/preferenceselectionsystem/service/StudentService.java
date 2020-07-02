package fudan.se.preferenceselectionsystem.service;

import fudan.se.preferenceselectionsystem.domain.Major;
import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.repository.MajorRepository;
import fudan.se.preferenceselectionsystem.repository.StudentRepository;
import fudan.se.preferenceselectionsystem.utils.ChoicesOverview;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author LBW
 */
@Service
public class StudentService {
    private StudentRepository studentRepository;
    private CustomUserDetailsService customUserDetailsService;
    private AuthenticationManager authenticationManager;
    private MajorRepository majorRepository;

    public StudentService(StudentRepository studentRepository, CustomUserDetailsService customUserDetailsService, AuthenticationManager authenticationManager, MajorRepository majorRepository) {
        this.studentRepository = studentRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.authenticationManager = authenticationManager;
        this.majorRepository = majorRepository;

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
        student.setLastModifyTime(new Date());
        //save into database
        studentRepository.save(student);
    }

    public Iterable<Major> getMajorInfo(String ticketNumber) {
        Student student = studentRepository.findByTicketNumber(ticketNumber);
        String degreeType = student.getDegreeType();  // 这里其实是一级学科
        if (student.getDepartment().contains("软件")) {
            if (degreeType.contains("学术"))
                return majorRepository.findAll();
            else
                return majorRepository.findByDegreeType(degreeType);
        }
        // get majors by 一级学科
        return majorRepository.findByMajor(degreeType);
    }

    public List<ChoicesOverview> getChoicesOverview(String ticketNumber) {
        Student student = studentRepository.findByTicketNumber(ticketNumber);
        // get choices overview by degree type
        ArrayList<ChoicesOverview> choicesOverviews = new ArrayList<>();

        List<Major> majors = majorRepository.findByMajor(student.getDegreeType());
        for (Major major: majors) {
            ChoicesOverview choicesOverview = new ChoicesOverview();
            choicesOverview.setMajor(major.getMajor());
            choicesOverview.setDegreeType(major.getDegreeType());
            choicesOverview.setSpots(major.getSpots());
            // TODO
            choicesOverview.setFirstChoiceMajorNumbers(studentRepository.countByDegreeTypeEqualsAndFirstChoiceMajorEquals(student.getDegreeType(), major.getMajor()));
            choicesOverviews.add(choicesOverview);
        }
        return choicesOverviews;
    }
}