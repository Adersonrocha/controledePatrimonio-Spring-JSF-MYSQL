package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Pessoa;
import curso.springboot.model.Telefone;
import curso.springboot.repository.PessoaRepository;
import curso.springboot.repository.TelefoneRepository;

@Controller
public class PessoaController {

	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private TelefoneRepository telefoneRepository;

	@RequestMapping(method = RequestMethod.GET, value = "/cadastropessoa")
	public ModelAndView inicioPessoa() {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		 modelAndView.addObject("pessoas", pessoasIt);
		modelAndView.addObject("pessoaobj", new Pessoa());							// adiciona um objeto vazio pq o form está esperando um objeto
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarpessoa")     //o ** é para ingnorar qualquer url que venha antes de /salvarpessoa
	public ModelAndView salvarPessoa(@Valid Pessoa pessoa, BindingResult bindingResult) {
		pessoa.setTelefones(telefoneRepository.getTelefones(pessoa.getId()));
		
		if(bindingResult.hasErrors()) {														// se tiver erros retornando do @valid entra nesse if
			ModelAndView modelandView = new ModelAndView("cadastro/cadastropessoa");		// retornarei para a mesma tela
			 Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();						// so pra retornar a lista
			 modelandView.addObject("pessoas", pessoasIt);										
			 modelandView.addObject("pessoaobj", pessoa);									// retorno o mesmo objeto já preenchido na tela
			
			 List<String> msg= new ArrayList<String>();
			 for(ObjectError objectError : bindingResult.getAllErrors()) {
				 msg.add(objectError.getDefaultMessage());                                 // esse erros vem das anatoções @Notnull,@Notempity do model	 
			 }
			 
			 modelandView.addObject("msg", msg);
			 return modelandView;
		}
		
		pessoaRepository.save(pessoa);
		ModelAndView modelandView = new ModelAndView("cadastro/cadastropessoa");
		 Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		 modelandView.addObject("pessoas", pessoasIt);
		 modelandView.addObject("pessoaobj", new Pessoa());							// adiciona um objeto vazio pq o form está esperando um objeto
		 
		return modelandView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listapessoas")
	public ModelAndView pessoas() {
		ModelAndView modelandView = new ModelAndView("cadastro/cadastropessoa");
		 Iterable<Pessoa> pessoasIt = pessoaRepository.findAll();
		 modelandView.addObject("pessoas", pessoasIt);
		 modelandView.addObject("pessoaobj", new Pessoa());                          // adiciona um objeto vazio pq o form está esperando um objeto
		 
		return modelandView;
	}
	
	@GetMapping("/editarpessoa/{idpessoa}")         // mesmaa coisa que fazer com @requestMapping dentro do parenteses vai o nome do metodo e o parametro
	public ModelAndView editar(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoaobj", pessoa.get());                             //retorna o objeto para poder editar no form
		return modelAndView;
	}
	
	@GetMapping("/excluirpessoa/{idpessoa}")
    public ModelAndView excluir(@PathVariable("idpessoa") Long idpessoa) {
    	
	    pessoaRepository.deleteById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoaRepository.findAll());                             //retorna o objeto para poder editar no form
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
    	
    } 
	
	@PostMapping("**/pesquisarpessoa")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa,
									@RequestParam("pesqsexo") String pesqsexo) {
		
		List<Pessoa> pessoas = new ArrayList<Pessoa>();
		
		if(pesqsexo !=null && !pesqsexo.isEmpty()) {
			
			pessoas= pessoaRepository.findPessoaByNameSexo(nomepesquisa, pesqsexo);	
		}else {
			pessoas= pessoaRepository.findPessoaByName(nomepesquisa);
		}
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastropessoa");
		modelAndView.addObject("pessoas", pessoas);
		
		modelAndView.addObject("pessoaobj", new Pessoa());
		return modelAndView;
		
	}
	@GetMapping("/telefones/{idpessoa}")         // mesmaa coisa que fazer com @requestMapping dentro do parenteses vai o nome do metodo e o parametro
	public ModelAndView telefone(@PathVariable("idpessoa") Long idpessoa) {
		
		Optional<Pessoa> pessoa = pessoaRepository.findById(idpessoa);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefone");
		modelAndView.addObject("pessoaobj", pessoa.get());  														//retorna o objeto para poder editar no form
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(idpessoa));
		return modelAndView;
	}
	
	@PostMapping("**/addFonePessoa/{pessoaid}")
	public ModelAndView addFondePessoa(Telefone telefone, @PathVariable("pessoaid")Long pessoaid) {
		Pessoa pessoa = pessoaRepository.findById(pessoaid).get();
		telefone.setPessoa(pessoa);
		telefoneRepository.save(telefone);
		ModelAndView modelAndView = new ModelAndView("cadastro/telefone");
		modelAndView.addObject("pessoaobj", pessoa);
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoaid));
		return modelAndView;
	}
	
	@GetMapping("/excluirtelefone/{idtelefone}")
    public ModelAndView excluirfone(@PathVariable("idtelefone") Long idtelefone) {
    	
		Pessoa pessoa= telefoneRepository.findById(idtelefone).get().getPessoa();
		telefoneRepository.deleteById(idtelefone);
		
		ModelAndView modelAndView = new ModelAndView("cadastro/telefone");
		modelAndView.addObject("pessoaobj", pessoa);  														//retorna o objeto para poder editar no form
		modelAndView.addObject("telefones", telefoneRepository.getTelefones(pessoa.getId()));
		return modelAndView;
    	
    }

	@GetMapping("/editartelefone/{idtelefone}")         // mesmaa coisa que fazer com @requestMapping dentro do parenteses vai o nome do metodo e o parametro
	public ModelAndView editartelefone(@PathVariable("idtelefone") Long idtelefone) {
		
		Optional<Telefone> telefone = telefoneRepository.findById(idtelefone);
		Pessoa pessoa=telefoneRepository.findById(idtelefone).get().getPessoa();
		ModelAndView modelAndView = new ModelAndView("cadastro/telefone");
		modelAndView.addObject("telefone", telefone);
		modelAndView.addObject("pessoaobj", pessoa);                             //retorna o objeto para poder editar no form
		return modelAndView;
	}
	
}
