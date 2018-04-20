package oneapp.demo.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import oneapp.demo.dto.FileVersionDto;
import oneapp.demo.dto.IPAFileDto;
import oneapp.demo.dto.ResponseMessage;
import oneapp.demo.entity.IPAFileDo;
import oneapp.demo.exception.ExecutionFault;
import oneapp.demo.util.ServicesUtil;

@Repository("FileUploadDao")
@Transactional
public class IPAFileDao extends BaseDao<IPAFileDo, IPAFileDto> {

	protected IPAFileDo importDto(IPAFileDto fromDto) {
		IPAFileDo entity = new IPAFileDo();
		entity.setFileName(fromDto.getFileName());
		entity.setFileType(fromDto.getFileType());
		entity.setFileUrl(fromDto.getFileUrl());
		entity.setFileVersion(fromDto.getFileVersion());
		entity.setFile(fromDto.getFile());
		return entity;
	}

	protected IPAFileDto exportDto(IPAFileDo entity) {
		IPAFileDto dto = new IPAFileDto();
		dto.setFileName(entity.getFileName());
		dto.setFileType(entity.getFileType());
		dto.setFileUrl(entity.getFileUrl());
		dto.setFileVersion(entity.getFileVersion());
		dto.setFile(entity.getFile());
		return dto;
	}

	public ResponseMessage insertFile(IPAFileDto dto) throws ExecutionFault {

		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Failed to Save File");
		responseMessage.setStatus("FAILURE");
		responseMessage.setStatusCode("1");
		
		if(!ServicesUtil.isEmpty(dto)) {
			this.persist(importDto(dto));
			responseMessage.setMessage("Sucessfully Inserted File");
			responseMessage.setStatus("SUCCESS");
			responseMessage.setStatusCode("0");
		}
		
		return responseMessage;
	}

	@SuppressWarnings("rawtypes")
	public IPAFileDto getFile(String fileType, String fileVersion, String application) {
		FileVersionDto versionDto = this.getVersionList(fileType, fileVersion, application);
		Query query = getSession().createQuery(
				"from IPAFileDo where fileType = :fileType and fileVersion = :fileVersion and application = :application");
		query.setParameter("fileType", fileType);
		query.setParameter("application", application);
		query.setParameter("fileVersion", versionDto.getFileVersion());
		return exportDto((IPAFileDo) query.uniqueResult());
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	private FileVersionDto getVersionList(String fileType, String fileVersion, String application) {
		FileVersionDto versionDto = new FileVersionDto();
		List<Object[]> result = getSession().createSQLQuery(
				"SELECT FILE_TYPE AS FILE_TYPE, MAX(FILE_VERSION) AS VERSION FROM APP_FILE_IPA WHERE APPLICATION = '"
						+ application + "' AND FILE_TYPE = '" + fileType + "' GROUP BY(FILE_TYPE)")
				.list();
		if (!ServicesUtil.isEmpty(result)) {
			for (Object[] obj : result) {
				versionDto.setFileType((String) obj[0]);
				versionDto.setFileVersion((String) obj[1]);
				versionDto.setApplication(application);
			}
		}
		return versionDto;
	}

}
