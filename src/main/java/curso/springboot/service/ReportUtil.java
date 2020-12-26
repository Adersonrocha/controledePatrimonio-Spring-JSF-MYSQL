package curso.springboot.service;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletResponse;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable {

	/* Retorna nosso PDF em Byte para download no navegador */
	public byte[] gerarRelatorio(List listDados, String relatorio, ServletContext contexto) throws Exception {
		
		/* Cria nossa Lista de dados para o relatorio com nossa lista de objetos para imprimir*/
		JRBeanCollectionDataSource jrbcds = new JRBeanCollectionDataSource(listDados);
		
		/* Carrega o caminho do arquivo Jasper compilado*/
		//se der erro nesse caminho possa ser que por causa do nome do Documento esta iniciando com a letra maiscula
		String caminhoJasper = contexto.getRealPath("relatorios") + File.separator + relatorio + ".jasper"; 
			
		/* Carrega o arquivo Jasper passando os dados */
		JasperPrint imprimir = JasperFillManager.fillReport(caminhoJasper, new HashMap(), jrbcds);
		
		/* Exporta para byte[] para fazer o download do PDF*/
		return JasperExportManager.exportReportToPdf(imprimir);
	}
	
}
