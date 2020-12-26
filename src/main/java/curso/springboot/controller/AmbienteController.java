package curso.springboot.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

import curso.springboot.model.Ambiente;
import curso.springboot.model.Equipamento;
import curso.springboot.model.Setor;
import curso.springboot.repository.AmbienteRepository;
import curso.springboot.repository.CategoriaRepository;
import curso.springboot.repository.EquipamentoRepository;
import curso.springboot.repository.SetorRepository;
import curso.springboot.service.ReportUtil;

@Controller
public class AmbienteController {

	@Autowired
	private AmbienteRepository ambienteRepository;

	@Autowired
	private EquipamentoRepository equipamentoRepository;

	@Autowired
	private SetorRepository setorRepository;
	
	@Autowired
	private CategoriaRepository categoriaRepository;
	
	@Autowired
	private ReportUtil reportUtil;
	
	@RequestMapping(method = RequestMethod.GET, value = "/cadastroambiente")
	public ModelAndView inicioAmbiente() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		Iterable<Ambiente> ambienteIt = ambienteRepository.findAll();
		modelAndView.addObject("ambientes", ambienteIt);
		modelAndView.addObject("ambienteobj", new Ambiente());
		modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.POST, value = "**/salvarambiente")
	public ModelAndView salvarAmbiente(@Valid Ambiente ambiente, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
			Iterable<Ambiente> ambienteIt = ambienteRepository.findAll();
			
			modelAndView.addObject("ambientes", ambienteIt); // esssa variavel ambientes tá se relacionando com a table
			modelAndView.addObject("ambienteobj", ambiente);
			modelAndView.addObject("setor", setorRepository.findAll());
			List<String> msg = new ArrayList<String>();
			for (ObjectError objectError : bindingResult.getAllErrors()) {
				msg.add(objectError.getDefaultMessage());
			}
			modelAndView.addObject("msg", msg);
			return modelAndView;
		}
		ambiente.setLocal(ambiente.getSetor());
		ambienteRepository.save(ambiente);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		Iterable<Ambiente> ambienteIt = ambienteRepository.findAll();
		modelAndView.addObject("ambientes", ambienteIt); // esssa variavel ambientes tá se relacionando com a table
		modelAndView.addObject("ambienteobj", new Ambiente());
		modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;
	}

	@RequestMapping(method = RequestMethod.GET, value = "/listaambientes")
	public ModelAndView ambientes() {
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		Iterable<Ambiente> ambienteIt = ambienteRepository.findAll();
		modelAndView.addObject("ambientes", ambienteIt);
		modelAndView.addObject("ambienteobj", new Ambiente());
		modelAndView.addObject("setor", setorRepository.findAll());
		//modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;

	}

	@GetMapping("/editarambiente/{idambiente}")
	public ModelAndView editar(@PathVariable("idambiente") Long idambiente) {

		Optional<Ambiente> ambiente = ambienteRepository.findById(idambiente);

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		modelAndView.addObject("ambienteobj", ambiente.get());
		modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;
	}

	@GetMapping("/excluirambiente/{idambiente}")
	public ModelAndView excluirAmbiente(@PathVariable("idambiente") Long idambiente) {

		ambienteRepository.deleteById(idambiente);
		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		modelAndView.addObject("ambientes", ambienteRepository.findAll());
		modelAndView.addObject("ambienteobj", new Ambiente());
		modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;

	}

	@PostMapping("**/pesquisarAmbiente")
	public ModelAndView pesquisar(@RequestParam("nomepesquisa") String nomepesquisa) {

		ModelAndView modelAndView = new ModelAndView("cadastro/cadastroambiente");
		modelAndView.addObject("ambientes", ambienteRepository.findAmbienteByName(nomepesquisa));
		modelAndView.addObject("ambienteobj", new Ambiente());
		modelAndView.addObject("setor", setorRepository.findAll());
		return modelAndView;

	}
	
	@GetMapping("**/pesquisarAmbiente")
	public void imprimePdf(@RequestParam("nomepesquisa") String nomepesquisa, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		List<Ambiente> listAmbientes = new ArrayList<Ambiente>();
		
		if( nomepesquisa != null && !nomepesquisa.isEmpty()) {
			listAmbientes = ambienteRepository.findAmbienteByName(nomepesquisa);
		}else {
			Iterable<Ambiente> iterator = ambienteRepository.findAll();
			for (Ambiente  ambiente : iterator) {
				listAmbientes.add(ambiente);
			}
		}
		
		byte[] pdf = reportUtil.gerarRelatorio(listAmbientes, "ambiente", request.getServletContext());
		
		response.setContentLength(pdf.length);
		
		response.setContentType("application/octet-stream");
		
		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"", "relatorio.pdf");
		response.setHeader(headerKey, headerValue);
		
		response.getOutputStream().write(pdf);
	
		
	}
	
	
	
	@GetMapping("/cadastroequipamentos/{idambiente}")
	public ModelAndView cadastroEquipamentos(@PathVariable("idambiente") Long idambiente) {

		Optional<Ambiente> ambiente = ambienteRepository.findById(idambiente);
		ModelAndView modelAndView = new ModelAndView("cadastro/equipamento");
		modelAndView.addObject("ambienteobj", ambiente.get());
		modelAndView.addObject("equipamentos", equipamentoRepository.getEquipamento(idambiente));
		modelAndView.addObject("categoria", categoriaRepository.findAll());
		modelAndView.addObject("equipamentoobj",new Equipamento());
		return modelAndView;
	}
	
		@PostMapping("**/addEquipamentoAmbiente/{ambienteid}")
		public ModelAndView addEquipamentoAmbiente(Equipamento equipamento, @PathVariable("ambienteid") Long ambienteid) {
			Ambiente ambiente = ambienteRepository.findById(ambienteid).get();
			equipamento.setAmbiente(ambiente);
			
			equipamentoRepository.save(equipamento);
			ModelAndView modelAndView = new ModelAndView("cadastro/equipamento");
			modelAndView.addObject("ambienteobj", ambiente);
			modelAndView.addObject("equipamentoobj",new Equipamento());
			modelAndView.addObject("equipamentos", equipamentoRepository.getEquipamento(ambienteid));
			modelAndView.addObject("categorias", categoriaRepository.findAll());
			return modelAndView;
		}

		@GetMapping("**/editarequipamentos/{equipamentoid}")
		public ModelAndView editarEquipamento(@PathVariable("equipamentoid") Long equipamentoid) {

			Optional<Equipamento> equipamento = equipamentoRepository.findById(equipamentoid);
			Ambiente ambiente = equipamentoRepository.findById(equipamentoid).get().getAmbiente();
			ModelAndView modelAndView = new ModelAndView("cadastro/equipamento");
			modelAndView.addObject("ambienteobj", ambiente);
			modelAndView.addObject("equipamento", equipamento.get());
			modelAndView.addObject("equipamentos", equipamento);
			modelAndView.addObject("categoria", categoriaRepository.findAll());
			modelAndView.addObject("equipamentoobj",new Equipamento());
			return modelAndView;
		}

		@GetMapping("/excluirequipamentos/{equipamentoid}")
		public ModelAndView excluirEquipamento(@PathVariable("equipamentoid") Long equipamentoid) {
			Ambiente ambiente = equipamentoRepository.findById(equipamentoid).get().getAmbiente();
			equipamentoRepository.deleteById(equipamentoid);

			ModelAndView modelAndView = new ModelAndView("cadastro/equipamento");
			modelAndView.addObject("ambienteobj", ambiente); // retorna o objeto para poder editar no form
			modelAndView.addObject("equipamentoobj",new Equipamento());
			modelAndView.addObject("equipamentos", equipamentoRepository.getEquipamento(ambiente.getId()));
			modelAndView.addObject("categoria", categoriaRepository.findAll());
			return modelAndView;

		}

		@GetMapping("/addEquipamentoAmbiente/{ambienteid}")
		public void imprimeEquipamentos(@PathVariable("idambiente") Long idambiente, HttpServletRequest request, HttpServletResponse response) {
		
			List<Equipamento> listEquipamentos =new ArrayList<Equipamento>();

			Iterable<Equipamento> iterator = equipamentoRepository.getEquipamento(idambiente);
			for (Equipamento  equipamento : iterator) {
				listEquipamentos.add(equipamento);
			}
			
		}
		
		//public void transicaoEquipamento
		
	}

	


