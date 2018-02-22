package com.o2osys.mng.common.excel.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.o2osys.mng.common.excel.config.ExcelConfig;

public class ExcelCommonUtil {
    // 로그
    private final Logger log = LoggerFactory.getLogger(ExcelCommonUtil.class);
    private final String TAG = ExcelCommonUtil.class.getSimpleName();

    private Workbook workbook;
    private Map<String, Object> model;
    private HttpServletResponse response;

    public ExcelCommonUtil(Workbook workbook, Map<String, Object> model, HttpServletResponse response) {
        this.workbook = workbook;
        this.model = model;
        this.response = response;
    }

    public void createExcel() {
        setFileName(response, mapToFileName());

        Sheet sheet = workbook.createSheet();

        createHead(sheet, mapToHeadList());

        createBody(sheet, mapToBodyList());

        createFile();
    }

    private void createFile() {
        log.info("#response Name : "+this.response.getHeader("Content-Disposition"));

        //원본 파일명
        String fileName = "export.xlsx";
        log.debug(" FileName : "+fileName);

        //파일 확장자명(소문자변환)
        String fileExtension = FilenameUtils.getExtension(fileName).toLowerCase();
        log.debug(" fileExtension : "+fileExtension);

        File uploadFile;
        String uploadFileName;

        do {
            //업로드패스 (ROOT패스 + UPLOAD패스 + UPLOAD파일명)
            String uploadPath = "target/files/" + fileName;
            log.debug("uploadFilePath : "+uploadPath);

            //업로드 파일 생성
            uploadFile = new File(uploadPath);
        } while (uploadFile.exists());
        //업로드 폴더 생성
        uploadFile.getParentFile().mkdirs();

        FileOutputStream fileOut = null;
        try {
            fileOut = new FileOutputStream(uploadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            this.workbook.write(fileOut);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String mapToFileName() {
        return (String) model.get(ExcelConfig.FILE_NAME);
    }

    private List<String> mapToHeadList() {
        return (List<String>) model.get(ExcelConfig.HEADER);
    }

    private List<List<String>> mapToBodyList() {
        return (List<List<String>>) model.get(ExcelConfig.BODY);
    }

    private void setFileName(HttpServletResponse response, String fileName) {
        response.setHeader("Content-Disposition",
                "attachment; filename=\"" + setFileExtension(fileName) + "\"");
    }

    private String setFileExtension(String fileName) {
        if ( workbook instanceof XSSFWorkbook) {
            fileName += ".xlsx";
        }
        if ( workbook instanceof SXSSFWorkbook) {
            fileName += ".xlsx";
        }
        if ( workbook instanceof HSSFWorkbook) {
            fileName += ".xls";
        }

        log.info("#fileName = "+fileName);

        return fileName;
    }

    private void createHead(Sheet sheet, List<String> headList) {
        createRow(sheet, headList, 0);
    }

    private void createBody(Sheet sheet, List<List<String>> bodyList) {
        int rowSize = bodyList.size();
        for (int i = 0; i < rowSize; i++) {
            createRow(sheet, bodyList.get(i), i + 1);
        }
    }

    private void createRow(Sheet sheet, List<String> cellList, int rowNum) {
        int size = cellList.size();
        Row row = sheet.createRow(rowNum);

        for (int i = 0; i < size; i++) {
            row.createCell(i).setCellValue(cellList.get(i));
        }
    }
}
