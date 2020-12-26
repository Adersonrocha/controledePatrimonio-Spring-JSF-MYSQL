package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import curso.springboot.model.Ambiente;
import curso.springboot.model.Equipamento;
import curso.springboot.repository.AmbienteRepository;
import curso.springboot.repository.CategoriaRepository;
import curso.springboot.repository.EquipamentoRepository;

@Controller
public class EquipamentoController {

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Autowired
	private AmbienteRepository ambienteRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroequipamento")
	public ModelAndView inicioEquipamento() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		Iterable<Equipamento> equipamentoIt = equipamentoRepository.findAll();
		
		
		
		modelAndView.addObject("equipamentos", equipamentoIt);
		modelAndView.addObject("equipamentoobj", new Equipamento());
		
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarequipamento")
	public ModelAndView salvarEquipamento(Equipamento equipamento) {
		
		if (equipamento != null && (equipamento.getNome() != null && equipamento.getNome().isEmpty())                                  // Não consegui resolver atraves de anotações utilizei o metodo tradicional
				|| equipamento.getNome() ==null) {
				
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
			Iterable<Equipamento> equipamentoIt =equipamentoRepository.findAll();
			modelAndView.addObject("equipamentos", equipamentoIt);
			modelAndView.addObject("equipamentoobj", new Equipamento());
			
			List<String> msg =new ArrayList<String>();
			msg.add("Os campos de CodPatrimonio e nome devem ser preenchidos!");
			modelAndView.addObject("msg", msg);
			
			return modelAndView;
		}
		
		
		equipamentoRepository.save(equipamento);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		Iterable<Equipamento> equipamentoIt =equipamentoRepository.findAll();
		modelAndView.addObject("equipamentos", equipamentoIt);
		modelAndView.addObject("equipamentoobj", new Equipamento());
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
	}
	
	@RequestMapping(method = RequestMethod.GET, value="/listaequipamentos")
	public ModelAndView equipamentos() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		Iterable<Equipamento> equipamentoIt = equipamentoRepository.findAll();
		modelAndView.addObject("equipamentos", equipamentoIt);
		modelAndView.addObject("equipamentoobj", new Equipamento());  
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
		
	}

	@GetMapping("/editarequipamento/{idequipamento}")
	public ModelAndView editar(@PathVariable("idequipamento") Long idequipamento) {
		Optional<Equipamento> equipamento =equipamentoRepository.findById(idequipamento);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		modelAndView.addObject("equipamentoobj", equipamento.get());
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
	}

	@GetMapping("/excluirequipamento/{idequipamento}")
    public ModelAndView excluir(@PathVariable("idequipamento") Long idequipamento) {
	    equipamentoRepository.deleteById(idequipamento);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		modelAndView.addObject("equipamentoobj", equipamentoRepository.findAll());                             //retorna o objeto para poder editar no form
		modelAndView.addObject("equipamentoobj", new Equipamento());
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
    }
	
	@PostMapping("**/pesquisarequipamento")
	public ModelAndView pesquisar(@RequestParam("codPesquisa") String codPesquisa) {
		
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroequipamento");
		modelAndView.addObject("equipamentos", equipamentoRepository.findEquipamentoByName(codPesquisa));
		modelAndView.addObject("equipamentoobj", new Equipamento());
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		return modelAndView;
		
	}
	
	
}
