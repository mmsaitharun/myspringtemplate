package oneapp.demo.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;

//@WebServlet("/appUpload")
public class IOPAppUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public IOPAppUploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		IOPAppUtils.addVersionTable(response);
		IOPAppUtils.appendForm(response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String fileName = "";
		String fileVersion = "";
		String fileType = "";
		String downloadUrl = "";
		String inFileName = "";
		String uploadType = "";
//		String application = "";
		
		InputStream fileContent = null;
		
		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for(FileItem item : items) {
				if(item.isFormField()) {
					String fieldName = item.getFieldName();
					String fieldValue = item.getString();
					switch(fieldName) {
						case ("fileName") : 
							fileName = fieldValue; break;
						case ("fileVersion") : 
							fileVersion = fieldValue; break;
						case ("apps") : 
							fileType = fieldValue; break;
						case ("downloadUrl") :
							downloadUrl = fieldValue; break;
						case ("upload") :
							uploadType = fieldValue; break;
//						case ("application") :
//							application = fieldValue; break;
					}
				} else {
					inFileName = FilenameUtils.getBaseName(item.getName());
					fileName = inFileName;
					fileContent = item.getInputStream();
					if((item.getInputStream() == null) && (item.getSize() <= 0)) {
						fileContent = null;
					}
				}
			}
		} catch (Exception e) {
			System.err.println("Exception : "+e.getMessage());
		}
		fileName = inFileName;
		
		String serverUrl = IOPAppUtils.getServerUrl(request);
		
		System.err.println("Server URL : "+serverUrl);
		
		if(fileType.equalsIgnoreCase(IOPAppConstants.FILE_TYPE_IPA)) {
			
		} else {
			downloadUrl = ""+serverUrl+"/iopAppDownload?fileType="+fileType.toUpperCase()+"";
		}
		
		if(uploadType.equalsIgnoreCase(IOPAppConstants.FILE_UPLOAD_TYPE_UPDATE)) {
			try {
				PreparedStatement pstmt =  IOPDBConnector.getInstance().insert("UPDATE APP_FILE_IOP SET FILE = ?, FILE_NAME = ? WHERE FILE_TYPE = ? AND FILE_VERSION = ?");
				pstmt.setBlob(1, fileContent);
				pstmt.setString(2, fileName);
				pstmt.setString(3, fileType.toUpperCase());
				pstmt.setString(4, fileVersion);
//				pstmt.setString(5, application);
				int update = pstmt.executeUpdate();
				System.err.println("Update : "+update);
				
				response.getWriter().write("Updating Application Successful! FileName : " + fileName + " File Type : "
						+ fileType + " File Version : " + fileVersion);
			} catch (Exception e) {
				System.err.println("Exception : "+e.getMessage());
			}
		} else if (uploadType.equalsIgnoreCase(IOPAppConstants.FILE_UPLOAD_TYPE_CREATE)) {
			try {
				PreparedStatement pstmt =  IOPDBConnector.getInstance().insert("INSERT INTO APP_FILE(ID, FILE_NAME, FILE_VERSION, FILE_TYPE, FILE, FILE_URL, APPLICATION) VALUES(?, ?, ?, ?, ?, ?, ?)");
				pstmt.setString(1, UUID.randomUUID().toString().replaceAll("-", ""));
				pstmt.setString(2, fileName);
				pstmt.setString(3, fileVersion);
				pstmt.setString(4, fileType.toUpperCase());
				pstmt.setBlob(5, fileContent);
				pstmt.setString(6, downloadUrl);
//				pstmt.setString(7, application);
				int update = pstmt.executeUpdate();
				System.err.println("Update : "+update);
				
				response.getWriter().write("Inserting Application Successful! FileName : " + fileName + " File Type : "
						+ fileType + " File Version : " + fileVersion );
			} catch (Exception e) {
				System.err.println("Exception : "+e.getMessage());
			}
		}
	}

}
