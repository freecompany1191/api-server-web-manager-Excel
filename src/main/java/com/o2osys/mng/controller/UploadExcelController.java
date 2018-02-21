package com.o2osys.mng.controller;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.o2osys.mng.common.excel.component.ExcelReadComponent;
import com.o2osys.mng.common.excel.domain.Product;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("upload")
@RequiredArgsConstructor
public class UploadExcelController {

    private final ExcelReadComponent excelReadComponent;

    @PostMapping("excel")
    public List<Product> readExcel(@RequestParam("file") MultipartFile multipartFile)
            throws IOException, InvalidFormatException {
        return excelReadComponent.readExcelToList(multipartFile, Product::rowOf);
    }
}
