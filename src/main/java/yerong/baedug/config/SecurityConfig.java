package yerong.baedug.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import yerong.baedug.domain.member.Role;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http
                .csrf((csrfConfig) ->
                        csrfConfig.disable())
                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(frameOptionsConfig ->
                                frameOptionsConfig.disable()))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/api/**"),
                                        AntPathRequestMatcher.antMatcher("/swagger"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                                        AntPathRequestMatcher.antMatcher("/api-docs"),
                                        AntPathRequestMatcher.antMatcher("/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/v3/api-docs/**")



                                        ).permitAll()
                                .anyRequest().authenticated()
                )
                .build();
    }
}
