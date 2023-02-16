package backupSecurityFiles.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.gb.web_market.order.services.JwtService;

@Slf4j
@RestController
@RequestMapping("api/v1/auth")
//@CrossOrigin("*")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/token")
    public AuthResponse token(@RequestBody AuthRequest request){
        log.info("Request from: {}", request.getUsername());
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authenticationToken);
        UserDetails user = (UserDetails) authenticate.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthResponse(token);
    }

}
