package com.arthurh.portfolio.socialmedia.service;

import static com.arthurh.portfolio.socialmedia.config.RequestHeaderContext.getHeader;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import com.arthurh.portfolio.socialmedia.config.RequestHeaderWrapper;
import com.arthurh.portfolio.socialmedia.db.model.User;
import com.arthurh.portfolio.socialmedia.db.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class ControllerHelperService {
    final UserService userService;

    @Transactional
    public User findAndAuthorizeUser() {
        return getHeader()
                .filter(h -> h.userId() != null)
                .map(this::findOrRegisterByUniqueId)
                .orElseThrow(() -> new ResponseStatusException(UNAUTHORIZED));
    }

    User findOrRegisterByUniqueId(RequestHeaderWrapper header) {
        assert header.userId() != null;
        User user = userService.findByUniqueId(header.userId())
                .orElse(null);
        String username = header.username() == null
                ? header.userId() : header.username();
        if (user == null) {
            user = User.builder()
                    .uniqueId(header.userId())
                    .username(username)
                    .build();
            return userService.save(user);
        }
        if (username.equals(user.getUsername())) {
            return user;
        }
        user.setUsername(username);
        return userService.save(user);
    }
}
