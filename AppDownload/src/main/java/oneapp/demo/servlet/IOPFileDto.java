package oneapp.demo.servlet;

import java.io.InputStream;

/**
 * @author INC00718
 *
 */
public class IOPFileDto {

	private String id;
	private String fileName;
	private String fileType;
	private String fileVersion;
	private InputStream file;
	private String table;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileVersion() {
		return fileVersion;
	}

	public void setFileVersion(String fileVersion) {
		this.fileVersion = fileVersion;
	}

	public InputStream getFile() {
		return file;
	}

	public void setFile(InputStream file) {
		this.file = file;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	@Override
	public String toString() {
		return "FileDto [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", fileVersion="
				+ fileVersion + ", file=" + file + ", table=" + table + "]";
	}

}
