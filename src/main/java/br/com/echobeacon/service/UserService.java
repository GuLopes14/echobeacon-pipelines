package br.com.echobeacon.service;

import br.com.echobeacon.model.User;
import br.com.echobeacon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserService extends DefaultOAuth2UserService {
    private final UserRepository userRepository;

    private Set<String> adminEmails = new HashSet<>();

    @Value("${app.admin-emails}")
    public void setAdminEmails(String adminEmailsStr) {
        if (adminEmailsStr != null && !adminEmailsStr.isEmpty()) {
            adminEmails = new HashSet<>(Arrays.asList(adminEmailsStr.split(",")));
        }
    }

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User register(OAuth2User principal) {
        String email = principal.getAttribute("email");
        var optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isEmpty()) {
            User newUser = new User(principal);
            // Define o papel como ADMIN se o email estiver na lista de admins
            if (isAdminEmail(email)) {
                newUser.setRole("ADMIN");
            }
            return userRepository.save(newUser);
        } else {
            User existingUser = optionalUser.get();
            // Atualiza o papel do usuário existente se necessário
            if (isAdminEmail(email) && !"ADMIN".equals(existingUser.getRole())) {
                existingUser.setRole("ADMIN");
                return userRepository.save(existingUser);
            } else if (!isAdminEmail(email) && "ADMIN".equals(existingUser.getRole())) {
                existingUser.setRole("USER");
                return userRepository.save(existingUser);
            }
            return existingUser;
        }
    }

    private boolean isAdminEmail(String email) {
        return email != null && adminEmails.contains(email.trim());
    }

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        var principal = super.loadUser(userRequest);
        return register(principal);
    }
}
