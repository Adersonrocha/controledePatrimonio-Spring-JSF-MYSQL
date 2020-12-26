package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Setor;
import curso.springboot.repository.SetorRepository;

@Controller
public class SetorController {

	@Autowired
	private SetorRepository setorRepository;
	
	
	@GetMapping("/cadastrosetor")
	public ModelAndView inicio() {
		
		ModelAndView modelAndView =new ModelAndView("cadastro/cadastrosetor");
		modelAndView.addObject("setorobj", new Setor());
		
		return modelAndView;
	}
	
	@PostMapping("/salvarsetor")
	public ModelAndView salvar(Setor setor) {
		
		ModelAndView modelAndView =new ModelAndView("cadastro/cadastrosetor");
		setorRepository.save(setor);
		modelAndView.addObject("setorobj", new Setor());
		Iterable<Setor> setorIt = setorRepository.findAll();
		modelAndView.addObject("setores", setorIt);
		return modelAndView;
	}
	
	@GetMapping("**/listsetores")
	public ModelAndView categorias() {
		ModelAndView modelAndView =new ModelAndView("cadastro/cadastrosetor");
		Iterable<Setor> setorIt = setorRepository.findAll();
		modelAndView.addObject("setores", setorIt);
		modelAndView.addObject("setorobj", new Setor());
		return modelAndView;
	}
	
}
