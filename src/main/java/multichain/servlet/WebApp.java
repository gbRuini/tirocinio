package multichain.servlet;

import static java.lang.annotation.ElementType.FIELD;

import java.io.IOException;
import java.io.PrintWriter;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.swing.UIDefaults.ProxyLazyValue;

import multichain.command.CollegamentoChain;
import multichain.command.Configurazione;
import multichain.command.MultiChainCommand;
import multichain.command.MultichainException;
import multichain.object.Stream;
import multichain.object.StreamKey;
import multichain.object.StreamKeyItem;

public class WebApp extends HttpServlet{

	static MultiChainCommand multiChainCommand;
	String hashCode;
	String key ;
	String keyInHash;
	String hashFile="";
	List<StreamKey> nameExisting;
	String resultPublish = "";
	List<StreamKeyItem> resultCerca;
	String controllOfString= "";
	String checkKey = "";



	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//connessione alla chain
		multiChainCommand = new MultiChainCommand("localhost", "4778", "multichainrpc","5Lq3qwpopwZVBdXk2R2vR8ZnBRNVeAQcerGjN7JEct22");

		key = request.getParameter("key");
		System.out.println("key iniziale: " + key);

	/*	
	 *        Calcola hash della stringa 
		keyInHash = getSha256(key);
		System.out.println(key + " in hex è: " + keyInHash);
	*/

		//Acquisizione dell hashFile dalla servletFileUploadServlet
		HttpSession session = request.getSession();
		hashFile = (String)session.getAttribute("hashFile");
		
		//per controllare se l'hash arriva correttamente alla web app
		//System.out.println("hashFile in WebApp: " +hashFile);
		
		
		//restituisce le key presenti all'interno dello stream
		try {
			nameExisting = multiChainCommand.getStreamCommand().listStreamKeys("stream1");
		} catch (MultichainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		checkKey = nameExisting.toString();
		
		
		// Publish
		if(request.getParameter("publishbtn") != null) {


			//controllo che la key che si vuole inserire non sia già presente nella chain
			if(checkKey.toLowerCase().contains(key.toLowerCase())) {
				String log1 = "Un file con lo stesso nome è già presente!";
				request.setAttribute("log", log1);
				System.out.println("File già presente!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;
			}
			else {
				//se la key non è gia presente faccio publish
				try {
					resultPublish = multiChainCommand.getStreamCommand().publish("stream1", key, hashFile);
				} catch (MultichainException e) {
					e.printStackTrace();
				}

				String log2 = "Inserimento completato!";
				request.setAttribute("log", log2);
				System.out.println("Result Txid: " + resultPublish);		
			}  	
		}
		else if(request.getParameter("cercabtn") != null) {
			//CERCA
			try {
				resultCerca= multiChainCommand.getStreamCommand().listStreamKeyItems("stream1", key);
			}catch(MultichainException e) {
				e.printStackTrace();
			}

			controllOfString = resultCerca.toString();
			System.out.println("Lhash del file scelto e': " + hashFile);
			
			if(checkKey.toLowerCase().contains(key.toLowerCase()) && controllOfString.contains(hashFile)) {
				String log1 = "E' presente un file " + key + " con lo stesso hash!";
				request.setAttribute("log", log1);
				System.out.println("File presente!");
				request.getRequestDispatcher("index.jsp").forward(request, response);
				return;

			}else {
				String log4 = "Il file " + key + " è già presente, ma ha un hash diverso!" ;
				request.setAttribute("log", log4);
				System.out.println("File già presente, l'hash è diverso!");
			}

		}

		request.getRequestDispatcher("index.jsp").forward(request, response);

	
	/*
		// controllo sugli stream
		List<Stream> resultStreams = null;
		try {
			resultStreams= multiChainCommand.getStreamCommand().listStreams(null);
		} catch (MultichainException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String checkStreams = resultStreams.toString();

		Stream result;
		
	*/	
	
	}


	    // Hash Function SHA-256
	public static String getSha256(String value) {
		try{
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(value.getBytes());
			return bytesToHex(md.digest());
		} catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	private static String bytesToHex(byte[] bytes) {
		StringBuffer result = new StringBuffer();
		for (byte b : bytes) result.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
		return result.toString();
	}
	
	
	
	
}


