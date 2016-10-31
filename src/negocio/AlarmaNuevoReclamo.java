package negocio;

import java.util.ArrayList;

public class AlarmaNuevoReclamo implements SubjectObs{

	private static ArrayList<NuevoReclamoObs> observadores = new ArrayList<NuevoReclamoObs>();
	
	@Override
	public void attach(NuevoReclamoObs observador) {
		// TODO Auto-generated method stub
		observadores.add(observador);
		
	}

	@Override
	public void dettach(NuevoReclamoObs observador) {
		// TODO Auto-generated method stub
		observadores.remove(observador);
	}

	@Override
	public void notifyObservers() {
		// TODO Auto-generated method stub
		for (int i = 0 ; i < observadores.size(); i++){
			
			observadores.get(i).update();
		}
	}

}
