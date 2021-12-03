
public class Consulta {
	// Definimos los attributos de la clase (normalmente se definirian como 
	// privados y se accederia a ellos con metodos de acceso, pero hagamoslo asi
	// por ahora).
	public String queryId;
	public String advertiserId;
	public String query;
	
	// Definimos los metodos de la clase
	public Consulta(String queryId, String advertiserId, String advertise) {
		this.queryId = queryId;
		this.advertiserId = advertiserId;
		this.query = advertise;
	}
}
