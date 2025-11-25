package com.example.nexdew.JWT;

import com.example.nexdew.service.impl.PermissionMappingService;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class DynamicPermissionFilter extends OncePerRequestFilter {

    private final PermissionMappingService permissionMappingService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException, java.io.IOException {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated()) {
            String path = request.getRequestURI();
            String method = request.getMethod();

            String requiredPermission = permissionMappingService.getRequiredPermission(path, method);

            if (requiredPermission != null) {
                boolean hasPermission = auth.getAuthorities().stream()
                        .anyMatch(a -> a.getAuthority().equals(requiredPermission));

                if (!hasPermission) {
                    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                    response.getWriter().write("Access Denied: Missing permission " + requiredPermission);
                    return;
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
