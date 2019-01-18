package multichain.command;

import java.util.ResourceBundle;

public class Configurazione {
	private static Configurazione istanza = null;
	private MultiChainCommand multiChainCommand;
	
	public static Configurazione getConfigurazione() {
		if(istanza == null)
			istanza = new Configurazione();
		
		return istanza;
	}
	
	public MultiChainCommand getMultiChainCommand() {
		try {
			if(multiChainCommand == null) {
				multiChainCommand = new MultiChainCommand("localhost", "6730", "multichainrpc","A5nVqdkFL3L5UjD6MFnGVCrbuo2mhp7NZ93yTNUq2TYy");
				System.out.println("Connessione OK");
				
			}
			return multiChainCommand;
		}catch(Throwable throwable) {
			throw new IllegalStateException(throwable);
		}
	}
/*	
	public String getStringa(String nome) {
		if(nome == null || "".equals(nome))
			throw new IllegalArgumentException();
		
		ResourceBundle bundle = ResourceBundle.getBundle("configurazione");
		return bundle.getString(nome);
	}
*/
}
