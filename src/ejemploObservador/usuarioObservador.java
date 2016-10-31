package ejemploObservador;

import negocio.Observer;
import vista.Observable;

public class usuarioObservador implements Observer{

	private Observable observable = null;
	
	public usuarioObservador(Observable observable){
		
		this.observable = observable;
		this.observable.addObserver(this);
	}
	
	@Override
	public void update() {
		// TODO Auto-generated method stub
		//ACA SE HACE ALGO CUANDO SE SABE QUE REFRESCAMOS LISTA
		System.out.println("Refescando lista de Reclamos!");
	}

}
