package com.Security_1.security_1.config;

//import com.Security_1.security_1.dto.AuthRequest;
import com.Security_1.security_1.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//To create a multiple username and password that's why we use configure class..
//to create a authentication related services we use a UserDetailsSerivce
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig  {
@Autowired
    private JwtAuthFilter jwtAuthFilter;
@Bean
//Authentication...
public UserDetailsService userDetailsService(){
//Now we will create a our own userdetailService and work with Db from that...

//
//    It is a hard coded value
//
//    UserDetails admin = User.withUsername("Bharat")
//            .password(encoder.encode("Pwd1"))
//            .roles("ADMIN")
//            .build();
//
//    UserDetails user = User.withUsername("Suraj")
//            .password(encoder.encode("Pwd2"))
//            .roles("USER")
//            .build();
//     To print the inmemoryconsole....
//    return new InMemoryUserDetailsManager(admin,user);

    return new UserInfoUserDetailsService();
}


//For authorization...
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http.csrf()
            .disable()
            .authorizeHttpRequests()
            .requestMatchers("/products/welcome","/products/new","/products/authenticate")
            .permitAll()
            .and()
            .authorizeHttpRequests().requestMatchers("/products/**").authenticated()
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
            .authenticationProvider(authenticationProvider())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class).build();
            //.formLogin().and().build();
    }
    //To encrypt the password create a passwordencoder
@Bean
public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
@Bean
public AuthenticationProvider authenticationProvider(){
    DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
    daoAuthenticationProvider.setUserDetailsService(userDetailsService());
    daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
return daoAuthenticationProvider;
}
//For explicitly provide the authentication manager...
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
