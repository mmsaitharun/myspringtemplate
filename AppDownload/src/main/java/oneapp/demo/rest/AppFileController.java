package oneapp.demo.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import oneapp.demo.dto.IPAFileDto;
import oneapp.demo.service.IPAFileFacadeLocal;

@RestController
@RequestMapping(value="/file")
public class AppFileController {

	@Autowired
	private IPAFileFacadeLocal fileLocal;
	
	/*@RequestMapping(value="/upload", method=RequestMethod.POST)
//	public @ResponseBody
	ResponseMessage saveFile(@RequestParam("appFile") MultipartFile appFile, @RequestParam("fileVersion") String fileVersion, @RequestParam("fileType") String fileType){
//		return "Working";
		IPAFileDto dto=new IPAFileDto();
		try {
			dto.setFileVersion(fileVersion);
			dto.setFileType(fileType.toUpperCase());
			dto.setFileName(FilenameUtils.removeExtension(appFile.getOriginalFilename()));
			dto.setFile(appFile.getBytes());
		} catch (Exception e) {
			System.err.println("Exception in Getting File : "+e.getMessage());
		}
		return fileLocal.saveFile(dto);
	}*/

	@RequestMapping(value="/downloadFile", method=RequestMethod.GET)
	public void downloadFile(HttpServletResponse res, @RequestParam("fileType") String fileType, @RequestParam("fileVersion") String fileVersion, @RequestParam("application") String application){
		IPAFileDto dto = fileLocal.getFile(fileType, fileVersion, application);
		res.addHeader("Content-Disposition", "attachment;filename="+dto.getFileName()+"."+dto.getFileType()+"_"+dto.getFileVersion());
		try {
			FileCopyUtils.copy(dto.getFile(), res.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
