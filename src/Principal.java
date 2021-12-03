import java.io.IOException;
import java.net.URISyntaxException;

public class Principal {

	public static void main(String[] args) throws IOException, URISyntaxException  {
		
		Photon myPhoton = new Photon();
		Acciones acc = new Acciones();
		
		acc.ejecucion(myPhoton);
		
	}
}
