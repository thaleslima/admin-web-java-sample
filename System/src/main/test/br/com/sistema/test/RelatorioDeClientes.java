package br.com.sistema.test;


import java.util.ArrayList;
import java.util.List;
import br.com.sistema.data.UsuarioDAO;
import br.com.sistema.entity.Usuario;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


public class RelatorioDeClientes {
	public static void main(String[] args) throws  Exception {

		System.out.println("Gerando relatório...");
		// lista com os nossos clientes
		List<Cliente> lista = new ArrayList<Cliente>();

		Cliente c1 = new Cliente();
		c1.setNome("Alexandre Macedo");
		c1.setEmail("alexbmac@gmail.com");
		c1.setTelefone("9999-9999");

		Cliente c2 = new Cliente();
		c2.setNome("Rafael Cosentino");
		c2.setEmail("cosen@gmail.com");
		c2.setTelefone("8888-8888");

		Cliente c3 = new Cliente();
		c3.setNome("Daniel Machado");
		c3.setEmail("daniel@gmail.com");
		c3.setTelefone("7777-7777");

		lista.add(c1);
		lista.add(c2);
		lista.add(c3);

		// compilacao do JRXML
		//JasperReport report = JasperCompileManager
		//		.compileReport("relatorio/users.jrxml");


		
		UsuarioDAO userDAO = new UsuarioDAO();
		List<Usuario> lista2 = userDAO.select(); 
		
		
		JasperPrint print = JasperFillManager.fillReport("relatorio/usuariosGrupos.jasper", null,
				new JRBeanCollectionDataSource(lista2));

		// exportacao do relatorio para outro formato, no caso PDF
		JasperExportManager.exportReportToPdfFile(print,
				"relatorio/RelatorioClientes.pdf");

		System.out.println("Relatório gerado.");
	}
}
