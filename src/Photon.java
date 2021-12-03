import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;
import javax.swing.JOptionPane;

public class Photon {

	// Un objeto photon tiene los siguientes attributos
	HashMap<Integer, Integer> idRegistry;
	HashMap<Integer, Integer> e_click;
	String eventStore[][];
	int joiner[][];
	Impresion_v impresora;
	
	public Photon() {
		
		// Como parte del constructor inicializamos el id registry 
		// con un monton de elementos.
		this.idRegistry = new HashMap<Integer, Integer>();
		
		this.idRegistry.put(232, 6532);
		this.idRegistry.put(233, 6533);
		this.idRegistry.put(234, 6534);
		this.idRegistry.put(235, 6535);
		this.idRegistry.put(236, 6536);
		this.idRegistry.put(237, 6537);
		this.idRegistry.put(238, 6538);
		this.idRegistry.put(239, 6539);
		this.idRegistry.put(240, 6540);
		this.idRegistry.put(241, 6541);
		this.idRegistry.put(242, 6542);
		this.idRegistry.put(243, 6543);
		
		this.e_click = new HashMap<Integer, Integer>();
		this.eventStore = new String[1][4];
		this.joiner = new int[2][2];
		this.impresora = new Impresion_v();
	}
	
	public void guardar_e_consulta(Integer advertiser_id, String texto_a, HashMap<Integer, String> e_consulta) {
		if (e_consulta.containsKey(advertiser_id)) {
			System.out.println("No se puede introducir el producto. El c�digo esta repetido.");
		} else {
			e_consulta.put(advertiser_id, texto_a);
		}
	}

	// EVENTO CLICK
	public void guardar_e_click(int click_id, int query_id) {
		if (this.e_click.containsKey(click_id)) {
			JOptionPane.showMessageDialog(null, "No se puede introducir. El c�digo esta repetido.");
		} else {
			this.e_click.put(click_id, query_id);
		}
	}

	public void dispatcher(int click_id, int idr) {
		Integer clave;
		if (this.idRegistry.containsKey(idr)) {
			System.out.print("\nEvento click ya existente\n");
			
		} else {
			this.idRegistry.put(click_id, idr);
			
			Iterator<Integer> claves = this.idRegistry.keySet().iterator();
			System.out.print("Muestra de id_Registry:\n");
			while (claves.hasNext()) {
				clave = claves.next();
				System.out.println(clave + " - " + this.idRegistry.get(clave));
			}
			
			System.out.print("Registro al id_Registry correctamente");
		}
	}

	public void joiner(int click_id, int idr) {
		if (!this.idRegistry.containsKey(idr)) {
			this.joiner[0][0] = Integer.valueOf(click_id);
			System.out.print(
					"\nEl valor del click_id: " + "'" + this.joiner[0][0] + "'" + " ha sido agregado correctamente :=)");
		} else {
			// Este tal vez es un bug, el segundo argumento deberia ser el click id, no?
			reintentar(idr, idr);
		}
	}


	public void reintentar(int click_id, int idr) {
		System.out.print("Fallo al unir click_id a joiner, reintentando");
		if (!this.idRegistry.containsKey(idr)) {
			this.joiner[1][1] = Integer.valueOf(click_id);
			System.out.print("El segundo valor del click_id: " + this.joiner[1][1] + " ha sido agregado correctamente :=)");

		} else {
			reintentar(idr, idr);
		}
	}

	public void saveInEventStore(int query_id, int advertiser_id, Consulta myConsulta) {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		String Hora = (dtf.format(LocalDateTime.now()));
		this.eventStore[0][0] = Hora;
		this.eventStore[0][1] = String.valueOf(query_id);
		this.eventStore[0][2] = String.valueOf(advertiser_id);
		this.eventStore[0][3] = myConsulta.query;
		
		// El event_store no se muestra
		// System.out.print(Arrays.deepToString(e_store));
	}

	public String[][] joinerExtraccion(int query_id, Consulta myConsulta,
										int click_id, int idr) throws IOException {
		
		ThreadLocalRandom tlr = ThreadLocalRandom.current();
        DecimalFormat df = new DecimalFormat();
        String clickUnion[][] = new String[2][6];
        
        df.setMaximumFractionDigits(2);
		int extraccion = this.joiner[0][0];
		
		//System.out.println("\nJOINER EXTRACCION");
		//System.out.println(extraccion);
		
		if (myConsulta.query != this.eventStore[0][3]) {
			System.out.print("Error al encontrar la busqueda, re insertando en idRegistry");
			dispatcher(click_id, idr);
			return null;
			
		} else {
			//Texto del anuncio
			clickUnion[0][4] = myConsulta.query;
			//Hora de ejecucion
			clickUnion[0][0] = this.eventStore[0][0];
			//Query_id
			clickUnion[0][1] = this.eventStore[0][1];
			//Advertiser_id
			clickUnion[0][2] = this.eventStore[0][2];
			//click_id
			clickUnion[0][3] = String.valueOf(extraccion);
			//Precio
			clickUnion[0][5] = String.valueOf(df.format(tlr.nextInt(30, 70 + 1)));
			
			System.out.println("\n------------------------------CLICK UNIDO COMPLETADO"+"------------------------------------------------------------------------");
			System.out.print("Texto del anuncio: "+(clickUnion[0][4] ) + "\n" +
								"Hora de ejecuci�n: " + (clickUnion[0][0] ) + "\n" + 
								"Query_id: "+ (clickUnion[0][1] ) + "\n" +
								"Advertiser_id: "+ (clickUnion[0][2] ) + "\n" +
								"click_id: " + (clickUnion[0][3]) + "\n" +
								"Precio: "+ (clickUnion[0][5]));
			
			impresora.crearTicket(this.joiner, query_id, this.eventStore,
									myConsulta,click_id, this.idRegistry,idr,clickUnion);
			
			return clickUnion;
		}
	}

}
