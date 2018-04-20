package oneapp.demo.service;

import oneapp.demo.dto.IPAFileDto;
import oneapp.demo.dto.ResponseMessage;

public interface IPAFileFacadeLocal {

	ResponseMessage saveFile(IPAFileDto dto);

	IPAFileDto getFile(String fileType, String fileVersion, String application);

}
