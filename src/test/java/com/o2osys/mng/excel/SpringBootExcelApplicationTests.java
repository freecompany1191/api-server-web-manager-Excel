package com.o2osys.mng.excel;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.o2osys.mng.common.excel.component.ExcelReadComponent;
import com.o2osys.mng.service.MngMapperService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootExcelApplicationTests {

    @Autowired
    ExcelReadComponent excelReadComponent;

    @Autowired
    MngMapperService mngMapperService;
    /*
    @Test
    public void test_readExcel() throws IOException, InvalidFormatException {

        ClassLoader classLoader = this.getClass().getClassLoader();
        //        System.out.println("::RESOURCE PATH : "+resource.getPath());
        // System.out.println("::RESOURCE FILE : "+resource.getFile());
        // System.out.println("::RESOURCE URL : "+resource.getURL());

        File xlsxFile = new File(classLoader.getResource("files/test.xlsx").getFile());
        System.out.println("XLSX : "+xlsxFile.getPath());
        String filename = xlsxFile.getName();
        System.out.println("filename : "+filename);
        System.out.println(filename.endsWith("xlsx"));

        excelReadComponent
        .readExcelToList(new MockMultipartFile("test","test.xlsx","xlsx", new FileInputStream(xlsxFile)),
                Product::rowOf)
        .forEach(System.out::println);

    }
     */

    @Test
    public void excelList() throws Exception {

        mngMapperService.mnGrStExcelList();

    }



}
