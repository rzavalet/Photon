import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class Impresion_v {
	private Process process;
	
	public void mostrarResultados(HashMap<Integer, Integer> e_click) {
		int clave;
		Iterator<Integer> res = e_click.keySet().iterator();
		System.out.println("\nResultados del evento click:\n");
		while (res.hasNext()) {
			clave = res.next();
			System.out.print(clave);
			System.out.println("\n--------------------------------------------------------");

		}
	}
  
	public void crearTicket(int joiner[][], int query_id, String e_store[][], Consulta myConsulta,
			int click_id, HashMap<Integer, Integer> id_r1, int idr, String Click_u[][]) throws IOException {

		File directorio = new File ("C:\\Users\\santi\\Desktop\\Photon\\");
		try {
            //Si no existe el directorio
			if(!directorio.exists()) {
				if(directorio.mkdirs()) {
				} else {
	                System.out.println("Error al crear directorio");
	            }
			}
			String archivo = e_store[0][0].replace("/", "-").replace(":", "-");
			String ruta = "C:\\Users\\santi\\Desktop\\Photon\\ticket-"+archivo+".txt";
            String contenido1 = "-------------------------------------------------------------------------------------------------------";
            String contenido2 = "\n|  Texto del anuncio  | Hora de ejecuci�n   | Query_id |      Advertiser_id      | click_id  | Precio | ";
            String contenido3 = "\n-------------------------------------------------------------------------------------------------------\n";
            String contenido4 = "|   "+(Click_u[0][4]) +"    | " +(Click_u[0][0]) +" |" + (Click_u[0][1])+"   |" +  (Click_u[0][2])
            		+"                 | " + (Click_u[0][3]) +"        | " + (Click_u[0][5])+"  |";
            String contenido5 = "\n-------------------------------------------------------------------------------------------------------\n";
            String contenido = contenido1+contenido2+contenido3+contenido4+contenido5;

            File file = new File(ruta);
            // Si el archivo no existe es creado
            if (!file.exists()) {
                file.createNewFile();
                Runtime runtime = Runtime.getRuntime(); 
        		setProcess(runtime.exec("C:\\Windows\\notepad.exe "+ruta)); 
            }
                    
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(contenido);
            bw.close();
            
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		
    }
	
	// Para que necesitamos estos dos metodos???
	public Process getProcess() {
		return process;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
        
}
