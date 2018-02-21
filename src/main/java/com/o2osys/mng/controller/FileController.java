package com.o2osys.mng.controller;

import java.io.File;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.o2osys.mng.packet.manager.ResManager;

import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.NoArgsConstructor;

@RestController
@RequestMapping(value = "/v1/file")
public class FileController {
    // 로그
    private final Logger log = LoggerFactory.getLogger(FileController.class);
    private final String TAG = FileController.class.getSimpleName();


    @ApiOperation(value = "파일 업로드 API", notes = "파일 업로드 API", response = ResManager.class)
    //    @RequestMapping(value = "/mngrsts", method = RequestMethod.GET)
    @PostMapping(path = "/upload", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE }, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public @ResponseBody ResManager callFileUpload(
            @RequestPart("file") MultipartFile sourceFile
            ) throws Exception {

        //log.debug("[API] REQ_JSON : "+CommonUtils.jsonStringFromObject(reqManager));

        String sourceFileName = sourceFile.getOriginalFilename();

        String sourceFileNameExtension = FilenameUtils.getExtension(sourceFileName).toLowerCase();
        File destinationFile;
        String destinationFileName;
        do {
            //destinationFileName = RandomStringUtils.randomAlphanumeric(32) + "." + sourceFileNameExtension;
            destinationFileName = sourceFile.getOriginalFilename()+"."+sourceFileNameExtension;
            log.debug("destinationFileName : "+destinationFileName);
            destinationFile = new File("D:/mnt/storege/" + destinationFileName);
        }
        while (destinationFile.exists());
        destinationFile.getParentFile().mkdirs();
        sourceFile.transferTo(destinationFile);

        ResManager res = new ResManager();
        res.setOutMsg(sourceFile.getOriginalFilename()+" Upload Complete");

        log.debug(new UploadAttachmentResponse(sourceFile).toString());

        return res;

    }


    @NoArgsConstructor
    @Data
    private static class UploadAttachmentResponse {


        public UploadAttachmentResponse(MultipartFile sourceFile) {
            this.fileName = sourceFile.getOriginalFilename();
            this.fileSize = sourceFile.getSize();
            this.fileContentType = sourceFile.getContentType();
        }
        private String fileName;
        private long fileSize;
        private String fileContentType;
    }


}
