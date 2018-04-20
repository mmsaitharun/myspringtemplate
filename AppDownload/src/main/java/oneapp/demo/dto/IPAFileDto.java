package oneapp.demo.dto;

import java.util.Arrays;

import oneapp.demo.exception.InvalidInputFault;
import oneapp.demo.util.EnOperation;

public class IPAFileDto extends BaseDto {
	
	private String id;
	private String fileName;
	private String fileType;
	private String fileVersion;
	private String fileUrl;
	private byte[]  file;
	private String application;
	
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
	public String getFileUrl() {
		return fileUrl;
	}
	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}
	public byte[] getFile() {
		return file;
	}
	public void setFile(byte[] file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "IPAFileDto [id=" + id + ", fileName=" + fileName + ", fileType=" + fileType + ", fileVersion="
				+ fileVersion + ", fileUrl=" + fileUrl + ", file=" + Arrays.toString(file) + ", application="
				+ application + "]";
	}
	@Override
	public Boolean getValidForUsage() {
		return null;
	}
	@Override
	public void validate(EnOperation enOperation) throws InvalidInputFault {
	}
	public String getApplication() {
		return application;
	}
	public void setApplication(String application) {
		this.application = application;
	}
}
