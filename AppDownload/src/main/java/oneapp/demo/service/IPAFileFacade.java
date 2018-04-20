package oneapp.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import oneapp.demo.dao.IPAFileDao;
import oneapp.demo.dto.IPAFileDto;
import oneapp.demo.dto.ResponseMessage;
import oneapp.demo.exception.ExecutionFault;

@Service
public class IPAFileFacade implements IPAFileFacadeLocal {

	@Autowired
	private IPAFileDao ipaFileDao;

	@Override
	public ResponseMessage saveFile(IPAFileDto dto) {
		ResponseMessage responseMessage = new ResponseMessage();
		responseMessage.setMessage("Failed to Save File");
		responseMessage.setStatus("FAILURE");
		responseMessage.setStatusCode("1");
		try {
			responseMessage = ipaFileDao.insertFile(dto);
		} catch (ExecutionFault e) {
			e.printStackTrace();
		}
		return responseMessage;
	}

	@Override
	public IPAFileDto getFile(String fileType, String fileVersion, String application) {
		return ipaFileDao.getFile(fileType, fileVersion, application);
	}
}
