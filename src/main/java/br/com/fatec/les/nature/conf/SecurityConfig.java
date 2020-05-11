package br.com.fatec.les.nature.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable().authorizeRequests()
				.antMatchers("/admin/**").hasRole("ADMINISTRADOR")
				.antMatchers("/cliente/**").hasRole("ADMINISTRADOR")
				.antMatchers("/produto/**").hasRole("ADMINISTRADOR")
				.antMatchers("/").permitAll()
				.antMatchers("/home").permitAll()
				.antMatchers("/produto").permitAll()
				.antMatchers("/produtos").permitAll()
				.anyRequest()
                	.authenticated()
            .and()
                .formLogin()
                	.permitAll()
            .and()
              .logout()
              	.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
              		.permitAll();
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		 web.ignoring().antMatchers("/static/**");
	}
	
	
	@Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .inMemoryAuthentication()
                .withUser("admin").password("{noop}admin").roles("ADMINISTRADOR")
                .and().withUser("user").password("{noop}user").roles("USER");
        
        super.configure(auth);
    }

	
	
}
