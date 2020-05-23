package br.com.fatec.les.nature.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import br.com.fatec.les.nature.util.Criptografia;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	private ImplementsDetailsService userDetailsService;
	
	private static final String[] AUTH_LIST = {
			"/",
			"/login",
			"/home",
			"/produto",
			"/produtos",
			"/sobre_nos",
			"/contato",
			"/cadastro",
			"/produto/categoria/**",
			"/cupom/**"
	};
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		
		http.csrf().disable().authorizeRequests()
					.antMatchers(AUTH_LIST)
						.permitAll()
					.antMatchers("/admin","/produto/**","/cliente/**")
						.hasAnyRole("ADMINISTRATIVO","DESENVOLVEDOR")	
					.anyRequest()
						.authenticated()
					.and()
						.formLogin()
						.loginPage("/login")
						.permitAll()
					.and()
						.logout()
						.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));
	}
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
				
		auth.userDetailsService(userDetailsService)
		.passwordEncoder(new Criptografia());
		
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception{
		
		web.ignoring().antMatchers("/css/**",
									"/assets/**", 
									"/fonts/**",
									"/images/**", 
									"/js/**", 
									"/scss/**",
									"/vendor/**",
									"/layout/**",
									"/fotos/**");
	}
	
	
}
