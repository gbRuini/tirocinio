package multichain.command;



public class CollegamentoChain {
	
	static MultiChainCommand multiChainCommand;
	
	public static void main(String[] args) {
		System.out.println("Sono nel main");
		multiChainCommand = new MultiChainCommand("localhost", "6730", "multichainrpc","A5nVqdkFL3L5UjD6MFnGVCrbuo2mhp7NZ93yTNUq2TYy");
	}
	
	
	public static void testpublish(String nameStream, String nome, String hashCode) {
		String result = "";
		System.out.println("testpublish :");
	/*	String nome="peppe";
		String hashCode="";
		
		MessageDigest md;
	      try {
	        md = MessageDigest.getInstance("MD5");
	        byte[] messageDigest = md.digest(nome.getBytes());
	        BigInteger number = new BigInteger(1, messageDigest);
	        hashCode = number.toString(16);
	        
	        System.out.println(hashCode); 
	      } catch (NoSuchAlgorithmException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	      }
	  */
		
		try {
			result = multiChainCommand.getStreamCommand().publish(nameStream, nome, hashCode);

		} catch (MultichainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (result == null || "".equals(result)) {
			System.err.println("testpublish - result is empty");
		} else {
			System.out.println("Result :");
			System.out.println(result);
		}

	}
	
}
