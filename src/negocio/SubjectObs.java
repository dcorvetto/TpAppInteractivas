package negocio;

public interface SubjectObs {

	public void attach(NuevoReclamoObs observador);
	public void dettach(NuevoReclamoObs observador);
	public void notifyObservers();

}
