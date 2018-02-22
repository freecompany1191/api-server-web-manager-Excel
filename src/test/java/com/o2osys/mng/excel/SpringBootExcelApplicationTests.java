package com.o2osys.mng.excel;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.o2osys.mng.common.excel.component.ExcelReadComponent;
import com.o2osys.mng.service.MngMapperService;

@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
@SpringBootTest
public class SpringBootExcelApplicationTests {

    @Autowired
    private MockMvc mvc;

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

        this.mvc.perform(get("/v1/exceltest")).andExpect(status().isOk()).andReturn();

    }

}
