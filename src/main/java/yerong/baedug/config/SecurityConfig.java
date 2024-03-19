package yerong.baedug.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import yerong.baedug.oauth.handler.CustomAccessDeniedHandler;
import yerong.baedug.oauth.handler.CustomAuthenticationSuccessHandler;
import yerong.baedug.oauth.jwt.CustomAuthenticationEntryPoint;
import yerong.baedug.oauth.jwt.JwtAuthenticationFilter;
import yerong.baedug.oauth.jwt.JwtProvider;

@EnableWebSecurity
@RequiredArgsConstructor
@Configuration
public class SecurityConfig {
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
    private final JwtProvider jwtProvider;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean
    public SecurityFilterChain filterChain (HttpSecurity http) throws Exception{

        return http
                .csrf(AbstractHttpConfigurer::disable)
                .headers(headersConfigurer ->
                        headersConfigurer.frameOptions(HeadersConfigurer.FrameOptionsConfig::disable))
                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(HttpMethod.OPTIONS)
                                .permitAll())
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/health-check"),
                                        AntPathRequestMatcher.antMatcher("/health-check/**"),
                                        AntPathRequestMatcher.antMatcher("/swagger"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui.html"),
                                        AntPathRequestMatcher.antMatcher("/swagger-ui/**"),
                                        AntPathRequestMatcher.antMatcher("/swagger*/**"),
                                        AntPathRequestMatcher.antMatcher("/api-docs"),
                                        AntPathRequestMatcher.antMatcher("/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/v3/**"),
                                        AntPathRequestMatcher.antMatcher("/v3/api-docs/**"),
                                        AntPathRequestMatcher.antMatcher("/login/oauth2/code/apple"),
                                        AntPathRequestMatcher.antMatcher("/login/token")


                                ).permitAll()
                                .anyRequest().authenticated()
                )
                .authorizeHttpRequests(authorizeRequests ->
                        authorizeRequests
                                .requestMatchers(
                                        AntPathRequestMatcher.antMatcher("/"),
                                        AntPathRequestMatcher.antMatcher("/api/**"),
                                        AntPathRequestMatcher.antMatcher("/api/**"),
                                        AntPathRequestMatcher.antMatcher("/api/directory")
                                        ).authenticated().anyRequest().permitAll()
                )

                .exceptionHandling(httpSecurityExceptionHandlingConfigurer -> httpSecurityExceptionHandlingConfigurer
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                        )
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

}
