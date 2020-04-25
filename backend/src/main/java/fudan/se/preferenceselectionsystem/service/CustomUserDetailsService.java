package fudan.se.preferenceselectionsystem.service;

import fudan.se.preferenceselectionsystem.domain.Student;
import fudan.se.preferenceselectionsystem.repository.StudentRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * @author LBW
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {
    private StudentRepository studentRepository;

    public CustomUserDetailsService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // here the username is the student ticket number
        Student student = studentRepository.findByTicketNumber(username);

        if (student != null) {
            String password = "{noop}" + student.getIdNumber();
            return new User(student.getTicketNumber(), password, Collections.singleton(new SimpleGrantedAuthority("ROLE_STUDENT")));
        }
        throw new UsernameNotFoundException("User: " + username + " not found");
    }
}
