package curso.springboot.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Categoria;
import curso.springboot.repository.CategoriaRepository;

@Controller
public class CategoriaController {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@GetMapping("/cadastrocategoria")
	public ModelAndView inicio() {
		ModelAndView modelAndView =new ModelAndView("cadastro/cadastrocategoria");
		modelAndView.addObject("categoriaobj", new Categoria());
		return modelAndView;
	}
	
	@PostMapping("/salvarcategoria")
	public ModelAndView salvar(Categoria categoria) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastrocategoria");
		categoriaRepository.save(categoria);
		modelAndView.addObject("categoriaobj", new Categoria());
		Iterable<Categoria> categoriaIt = categoriaRepository.findAll();
		modelAndView.addObject("categorias", categoriaIt);
		return modelAndView;
	}
	
	@GetMapping("**/listcategorias")
	public ModelAndView categorias() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastrocategoria");
		Iterable<Categoria> categoriaIt = categoriaRepository.findAll();
		modelAndView.addObject("categorias", categoriaIt);
		modelAndView.addObject("categoriaobj", new Categoria());
		return modelAndView;
	}
	
}
