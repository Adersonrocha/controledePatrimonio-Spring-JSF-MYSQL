package curso.springboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import curso.springboot.service.ImplementacaoUserDetailsService;



@Configuration

@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	
	
	@Override      																// Configura as solicitações de acesso por Http
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()																//Desativa as configurações padrão de memória do Spring
		.authorizeRequests()													// Permitir restinção de acessos.
		.antMatchers(HttpMethod.GET, "/").permitAll()							//Qualquer Usuario tem acesso a pagina Inicial do Sistema.
		.antMatchers(HttpMethod.GET, "/cadastropessoa").hasAnyRole("ADMIN")
		.anyRequest().authenticated()
		.and().formLogin().permitAll()											// formulario de Login permite qualquer usuário
		.loginPage("/login")													// vai procurar a pagina login.
		.defaultSuccessUrl("/cadastropessoa")                                            // se logar com sucesso vou mandar para a tela index
		.failureUrl("/login?error=true")										// caso falhe na autenticação volta pra pagina login com uma mensagem de erro true.
		.and().logout().logoutSuccessUrl("/login")								// Mapeia URL de Logout e invalida usuário autenticado, retorna para a pagina de login	
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));			// passar a url de logout invalida a sessao					
	
	}
	
	@Override																	// Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
		
		/*																				// se a autenticação for em memória utilizar o codigo abaixo.		
		auth.inMemoryAuthentication().passwordEncoder(NoOpPasswordEncoder.getInstance())
		.withUser("admin")																//usuario
		.password("admin")																//senha			
		.roles("ADMIN");																//acesso	
		*/
	}	
	

	@Override																	//Ignora URL especificas	
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/materialize/**");							//Ignorar tudo que está dentro da pasta materialize
	
	}
	
//	@Override																	// Cria autenticação do usuário com banco de dados ou em memória
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())		//metodo para senha encryptografada	
//		.withUser("admin")																//usuario
//		.password("result")																//senha	encryptografa tem que ser apontada nesse campo		
//		.roles("ADMIN");																//acesso	
//	}

	/* *
	 A classe pra implementar a senha encyptada tem que adicionar o código a seguir:
	 
	 BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
	 String result = encoder.encode("123");
	 System.out.println(result); 
	 * 
	 * 
	 * */

}
