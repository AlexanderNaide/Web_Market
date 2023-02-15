package ru.gb.web_market.core.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.gb.web_market.core.api.MainFilter;
import ru.gb.web_market.core.services.UserService;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@Slf4j
public class SecurityConfig {

    private final UserService userService;

    @Bean
    public SecurityFilterChain filterChain(MainFilter filter, HttpSecurity http) throws Exception{
        return http
                .cors()
                .and()
                .csrf().disable()//csrf нужна для MVC но бесполезна для REST
//                .cors().disable()//cors нужна, чтобы фильтровать запросы по источникам запросов
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/cart/**").authenticated()
                .requestMatchers("/orders/**").authenticated()
//                .requestMatchers("/api/v1/cart/**").hasAnyRole("USER")
                .anyRequest().permitAll()
//                .and().formLogin()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//эта настройка, чтобы не создавались сессии, вообще никакие, ведь в RESTE токены
                .and().exceptionHandling().authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))//это, чтобы кидаться 401(unauthorized) во всех, кто пытается стучаться в защищенную область не имея токен
                .and().addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return webSecurity -> webSecurity.ignoring().requestMatchers("/auth/**", "/api/v1/products/**");
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationProvider... providers){
        return new ProviderManager(providers);
    }


//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user = User.builder()
//                .username("user")
//                .password("pass")
//                .authorities("ADMIN", "MANAGER")
//                .build();
//        return new InMemoryUserDetailsManager(user);

//    }

//    @Bean
//    public AuthenticationProvider authenticationProvider(){
//        return new StandardAuthenticationProvider();  // для версии без базы данных
//    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
//        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        DaoAuthenticationProvider authenticationProvider = new MainDaoProvider();
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        authenticationProvider.setUserDetailsService(userService);
        return authenticationProvider;
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
