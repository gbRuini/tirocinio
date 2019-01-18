package multichain.servlet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

@WebServlet(name = "FileUploadServlet", urlPatterns = {"/upload"})
@MultipartConfig
public class FileUploadServlet extends HttpServlet {

	private final static Logger LOGGER =  Logger.getLogger(FileUploadServlet.class.getCanonicalName());


	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		// Create path components to save the file
		
		final Part filePart = request.getPart("file");
		final String fileName = getFileName(filePart);
		String workingDirectory = System.getProperty("user.dir");
		String path1="";
		String hashFile="";
		File file = new File(fileName);

		
		path1 = file.getAbsolutePath();
		System.out.println("file: " + file.getAbsoluteFile());
		
		//ho aggiunto /Scrivania nel path per vedere se tutto funzionasse
		String path2=	workingDirectory + "/Desktop" + File.separator + fileName;
		
	
		
		System.out.println("Path1: " + path1);
		System.out.println("path2: " + path2);
		
			
		//calcolo hash del file
		try {
			hashFile = getSHA1Checksum(path2);
			System.out.println( "hash file: " + getSHA1Checksum(path2));
			
			//passo hash del file calcolato alla webApp
			HttpSession session = request.getSession();
			session.setAttribute("hashFile", hashFile);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//passo il nome del file nella text di jsp
		request.setAttribute("fileName", fileName);
	

		request.getRequestDispatcher("index.jsp").forward(request, response);
		
	} // fine doPost

	private String getFileName(final Part part) {
		final String partHeader = part.getHeader("content-disposition");
		LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);
		for (String content : part.getHeader("content-disposition").split(";")) {
			if (content.trim().startsWith("filename")) {
				return content.substring(
						content.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;

	}

	/*
	 * Calcola hashFile
	 * 
	 */
	public static byte[] createChecksum(String filename) throws
	Exception
	{
		InputStream fis =  new FileInputStream(filename);

		byte[] buffer = new byte[1024];
		MessageDigest complete = MessageDigest.getInstance("SHA1");
		int numRead;
		do {
			numRead = fis.read(buffer);
			if (numRead > 0) {
				complete.update(buffer, 0, numRead);
			}
		} while (numRead != -1);
		fis.close();
		return complete.digest();
	}
	

	// see this How-to for a faster way to convert
	// a byte array to a HEX string
	public static String getSHA1Checksum(String filename) throws Exception {
		byte[] b = createChecksum(filename);
		String result = "";
		for (int i=0; i < b.length; i++) {
			result +=
					Integer.toString( ( b[i] & 0xff ) + 0x100, 16).substring( 1 );
		}
		return result;
	}

}


