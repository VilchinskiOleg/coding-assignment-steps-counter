package com.example.codingassignmentstepscounter.rest.service;

import com.example.codingassignmentstepscounter.rest.request.LoginReq;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

  private final AuthenticationManager authManager;
  private final SecurityContextRepository securityContextRepository;

  @PostMapping("/login")
  public ResponseEntity<String> createTeam(@RequestBody LoginReq login,
      HttpServletRequest request,
      HttpServletResponse response) {
    Authentication token = new UsernamePasswordAuthenticationToken(
        login.getUsername(), login.getPassword());
    Authentication auth = authManager.authenticate(token);

    SecurityContext context = SecurityContextHolder.createEmptyContext();
    context.setAuthentication(auth);
    SecurityContextHolder.setContext(context);

    securityContextRepository.saveContext(context, request, response);

    return ResponseEntity.ok().build();
  }

  @PostMapping("/logout")
  public ResponseEntity<String> logout(HttpServletRequest request, HttpServletResponse response) {
    SecurityContextHolder.clearContext();
    Optional.ofNullable(request.getSession(false))
        .ifPresent(HttpSession::invalidate);
    clearCookies(response);
    return ResponseEntity.noContent().build();
  }

  private void clearCookies(HttpServletResponse response) {
    Cookie cookie = new Cookie("JSESSIONID", null);
    cookie.setPath("/");
    cookie.setHttpOnly(true);
    cookie.setMaxAge(0);
    response.addCookie(cookie);
  }
}
