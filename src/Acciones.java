import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;

public class Acciones {
	
	public void ejecucion(Photon myPhoton) throws IOException, URISyntaxException {
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
		Consulta myConsulta;
		String clickUnion[][];
		String tmpBuf;
		String urls;
		URL url = null;
		int click_id = 0;
		int idr = tlr.nextInt(6520, 6600 + 1);
		int query_id = tlr.nextInt(3100000, 4100000 + 1);
		int advertiser_id = tlr.nextInt(1100000, 1200000 + 1);

		/*
		 * Emisi�n de consulta Se genera la query_id Se hace la busqueda del anuncio
		 */
		tmpBuf = JOptionPane.showInputDialog(null, "�Que deseas buscar?", "Busquedas",
											 JOptionPane.QUESTION_MESSAGE);

		// Creamos un objeto consulta
		myConsulta = new Consulta(String.valueOf(query_id),
									String.valueOf(advertiser_id),
									tmpBuf);

		String anuncio = (("Elige un numero del anuncio que deseas comprar\n" 
							+ "1.- " + myConsulta.query + " rojas\n"
							+ "2.- " + myConsulta.query + " blancas\n" 
							+ "3.- " + myConsulta.query + " nuevas"));
		
		int opciones = Integer.parseInt(JOptionPane.showInputDialog(anuncio));
		
		// Click_id y query_id del evento opciones
		int query_id_c = Integer.valueOf(myConsulta.queryId);
		
		switch (opciones) {
		case 1:
			//TODO: Un click podria tambien ser un objeto.
			click_id = tlr.nextInt(211, 310 + 1);
			myPhoton.guardar_e_click(click_id, query_id_c);
			urls = myConsulta.query.replace(" ", "-");
			url = new URL("https://listado.mercadolibre.com.mx/" + urls + "-rojas");
			break;

		case 2:
			click_id = tlr.nextInt(311, 410 + 1);
			myPhoton.guardar_e_click(click_id, query_id_c);
			urls = myConsulta.query.replace(" ", "+");
			url = new URL("https://www.amazon.com.mx/s?k=" + urls + "+blancas" + "&__mk_es_MX=�MŎ��&ref=nb_sb_noss_2");
			break;
			
		case 3:
			click_id = tlr.nextInt(411, 510 + 1);
			myPhoton.guardar_e_click(click_id, query_id_c);
			urls = myConsulta.query.replace(" ", "-");
			String urls2 = myConsulta.query.replace(" ", "+");
			url = new URL("https://es.aliexpress.com/af/" + urls + "-nuevas" + ".html?d=y&origin=n&SearchText=" + urls2
					+ "+nuevas" + "&catId=0&initiative_id=SB_20211128182236");
			break;
			
		default:
			JOptionPane.showMessageDialog(null, "Bienvenido a: * * * :)");
			urls = myConsulta.query;
			url = new URL("https://listado.mercadolibre.com.mx/" + urls);
			break;

		}
		
		// TODO: Crear un metodo en Consulta para imprimir esto.
		// Mostrar resultados del evento de consulta
		System.out.println("[[query_id, advertiser_id,texto del anuncio]]");
		System.out.println(myConsulta.queryId + ", " + myConsulta.advertiserId + ", " + myConsulta.query);
		System.out.println("---------------------------------------------------------------");
		
		// TODO: Crear un metodo en Photon para imprimir esto
		// Mostrar resultados del evento click
		System.out.println("{{click_id,query_id}}");
		System.out.println(myPhoton.e_click);

		// TODO: Crear un metodo en Photon para imprimir esto
		Impresion_v myImpresora = new Impresion_v();
		myImpresora.mostrarResultados(myPhoton.e_click);
		
		// Abrir Link
		Desktop.getDesktop().browse(url.toURI());
		
		// Uso del dispatcher
		myPhoton.dispatcher(click_id, idr);
		myPhoton.joiner(click_id, idr);
		myPhoton.saveInEventStore(query_id, advertiser_id, myConsulta);
		clickUnion = myPhoton.joinerExtraccion(query_id, myConsulta, click_id, idr);
		
		// TODO: Check if clickUnion is null...
	}
}
