package br.com.echobeacon.auth;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class AuthUtils {

    // Set com todos os emails que têm permissão de admin
    private static Set<String> ADMIN_EMAILS = new HashSet<>();

    //Método que lê os emails de admin do application.properties
    @Value("${app.admin-emails}")
    public void setAdminEmails(String adminEmails) {
        if (adminEmails != null && !adminEmails.isEmpty()) {
            ADMIN_EMAILS = new HashSet<>(Arrays.asList(adminEmails.split(",")));
        }
    }


     // Método principal que verifica se um usuário é admin
    public static boolean isAdmin(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }

        String email = extractEmail(authentication);
        return email != null && ADMIN_EMAILS.contains(email.trim());
    }

    //Este método extrai o email do objeto de autenticaçãoIsso é necessário porque o Google OAuth retorna tipos diferentes (DefaultOidcUser)
    public static String extractEmail(Authentication authentication) {
        if (authentication == null) {
            return null;
        }

        Object principal = authentication.getPrincipal();

        // Se for autenticação OpenID Connect (Google)
        if (principal instanceof DefaultOidcUser) {
            return ((DefaultOidcUser) principal).getEmail();
        }
        // Se for OAuth2 genérico
        else if (principal instanceof DefaultOAuth2User) {
            return (String) ((DefaultOAuth2User) principal).getAttributes().get("email");
        }

        return null;
    }
}
