package com.o2osys.mng.service;

import java.io.File;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FilenameUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.o2osys.mng.common.util.CommonUtils;
import com.o2osys.mng.entity.MnGrSt;
import com.o2osys.mng.mapper.MngMapper;

import oracle.sql.TIMESTAMP;

/**
   @FileName  : ConMapperServiceImpl.java
   @Description : 배달대행 연동 서비스 구현체
   @author      : KMS
   @since       : 2017. 7. 18.
   @version     : 1.0

   @개정이력

   수정일           수정자         수정내용
   -----------      ---------      -------------------------------
   2017. 7. 18.     KMS            최초생성

 */
@Service("MngMapperService")
public class MngMapperService {
    // 로그
    private final Logger log = LoggerFactory.getLogger(MngMapperService.class);
    private final String TAG = MngMapperService.class.getSimpleName();

    @Autowired
    MngMapper mngMapper;


    /**
     * 연동대상 목록 출력 (배달대행요청)
     * [PKG_SY_CON_DVRY.BD_GET_CON_LIST]
     * @Method Name : getConList
     * @param version
     * @param language
     * @param device
     * @param serviceYn
     * @return
     * @throws Exception
     */
    public ArrayList<MnGrSt> mnGrStList(int pageno, int pagesize) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();

        int countPage = pagesize;
        int page = pageno;
        int startCount = (page - 1) * countPage + 1;  // 21 이 되겠죠
        int endCount = page * countPage;  // 30 이 될 겁니다.

        map.put("startCount", startCount);
        map.put("endCount", endCount);

        //연동대상 목록 출력 (배달대행요청)
        ArrayList<MnGrSt> result = mngMapper.mnGrStList(map);
        log.info("#DB MnGrSt : "+CommonUtils.jsonStringFromObject(result));

        return result;
    }


    public int mnGrStListSize() throws Exception {
        return mngMapper.mnGrStListSize();
    }

    public Map<String, Object> mnGrStExcelList() throws Exception {
        Map<String, Object> resultMap = new HashMap<String, Object>();

        //데이터 조회
        ArrayList<LinkedHashMap<String,Object>> dataList = mngMapper.mnGrStExcelList(null);

        //헤더 맵 생성
        //Map<String, Object> header = getHeaderMap(dataList);

        //바디 맵 생성
        //List<LinkedHashMap<String, Object>> body = convertMap(dataList);
        //log.info("#DB MnGrSt : "+CommonUtils.jsonStringFromObject(result));

        List<String> header = getDefaultHeader(dataList);
        List<List<String>> body = getBody(dataList);

        resultMap.put("header", header);
        resultMap.put("body", body);
        resultMap.put("fileName", "export");

        return resultMap;
    }


    /**
     * 파일업로드
     * @Method Name : fileUpload
     * @param reqFile - 업로드 파일
     * @param UPLOAD_PATH - 업로드 경로
     * @param addStr - 추가 문자열
     * @throws Exception
     */
    public void fileUpload(MultipartFile reqFile) throws Exception {

        //원본 파일명
        String fileName = reqFile.getOriginalFilename();
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
        //파일 업로드
        reqFile.transferTo(uploadFile);

        //response.setAttachmentUrl("http://localhost:8080/attachments/" + destinationFileName);

    }

    /**
     * 프로시저 결과값 컨버팅 ArrayList
     * @Method Name : convertMap
     * @param object
     * @return
     * @throws Exception
     */
    private List<String> getDefaultHeader(ArrayList<LinkedHashMap<String, Object>> rows) throws Exception {

        List<String> resultList = new ArrayList<String>();
        int[] i = {0};

        if (rows == null || rows.size() <= 0) {
            return null;
        }

        log.info("ROW SIZE = "+rows.size());

        if (null == rows.get(0) || "NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return null;
        }

        log.info("ROW.get(0) SIZE = "+rows.get(0).size());
        rows.get(0).forEach((k,v)->{

            if (k != null) {
                //log.debug("## KEYNAME : "+keyName+" | VALUE : "+row.get(keyName)+" | class : "+row.get(keyName).getClass().getName());
                //log.info("NAME : "+row.get(k).getClass().getName());

                log.info("LIST ["+i[0]+"] KEY : "+ k+", VALUE : "+k);
                resultList.add(k);

            }
            i[0]++;

        });

        return resultList;
    }


    /**
     * 프로시저 결과값 컨버팅 ArrayList
     * @Method Name : convertMap
     * @param object
     * @return
     * @throws Exception
     */
    private List<List<String>> getBody(ArrayList<LinkedHashMap<String, Object>> rows) throws Exception {
        List<List<String>> resultList = new ArrayList<List<String>>();

        if (rows == null || rows.size() <= 0) {
            return null;
        }

        log.info("ROW SIZE = "+rows.size());

        if (null == rows.get(0) || "NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return null;
        }

        int[] i = {0};
        for (LinkedHashMap<String, Object> row : rows) {
            List<String> dataList = new ArrayList<String>();

            row.forEach((k,v)->{

                try {
                    if (k != null) {
                        //log.debug("## KEYNAME : "+keyName+" | VALUE : "+row.get(keyName)+" | class : "+row.get(keyName).getClass().getName());
                        //log.info("NAME : "+row.get(k).getClass().getName());

                        if(v != null){
                            if ("java.sql.Timestamp".equals(row.get(k).getClass().getName())) {
                                /*
                            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
                            String returntime = dateFormat3.format(row.get(keyName));
                                 */
                                Timestamp time = (Timestamp) row.get(k);
                                String datetime = CommonUtils.localToDate(time.toLocalDateTime(), null);
                                log.info("LIST java.sql.Timestamp ["+i[0]+"] KEY : "+ k+", VALUE : "+datetime);
                                dataList.add(datetime);

                            } else if("oracle.sql.TIMESTAMP".equals(row.get(k).getClass().getName())) {

                                TIMESTAMP ts = (TIMESTAMP) row.get(k);
                                Timestamp time = ts.timestampValue();

                                String datetime = CommonUtils.localToDate(time.toLocalDateTime(), null);
                                log.info("LIST oracle.sql.TIMESTAMP ["+i[0]+"] KEY : "+ k+", VALUE : "+datetime);
                                dataList.add(datetime);

                            } else {
                                //log.debug("["+REQ_TYPE+"]["+i[0]+"] KEYNAME : "+ k);

                                log.info("LIST ["+i[0]+"] KEY : "+ k+", VALUE : "+v);
                                dataList.add(String.valueOf(v));

                            }

                        } else {
                            log.info("LIST NULL ["+i[0]+"] KEY : "+ k+", VALUE : "+v);
                            dataList.add("");
                        }


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            resultList.add(dataList);
            i[0]++;
        }

        return resultList;
    }



    /**
     * 프로시저 결과값 컨버팅 ArrayList
     * @Method Name : convertMap
     * @param object
     * @return
     * @throws Exception
     */
    private Map<String, Object> getHeaderMap(ArrayList<LinkedHashMap<String, Object>> rows) throws Exception {

        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        int[] i = {0};

        if (rows == null) {
            return new LinkedHashMap<String, Object>();
        }

        if (rows == null || rows.size() <= 0) {
            return new LinkedHashMap<String, Object>();
        }

        log.info("ROW SIZE = "+rows.size());

        if (null == rows.get(0) || "NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return new LinkedHashMap<String, Object>();
        }

        rows.get(0).forEach((k,v)->{

            if (k != null) {
                //log.debug("## KEYNAME : "+keyName+" | VALUE : "+row.get(keyName)+" | class : "+row.get(keyName).getClass().getName());
                //log.info("NAME : "+row.get(k).getClass().getName());

                log.info("LIST ["+i[0]+"] KEY : "+ k+", VALUE : "+k);
                resultMap.put(k, k);

            }
            i[0]++;

        });

        return resultMap;
    }

    /**
     * 프로시저 결과값 컨버팅 ArrayList
     * @Method Name : convertMap
     * @param object
     * @return
     * @throws Exception
     */
    private ArrayList<LinkedHashMap<String, Object>> convertMap(ArrayList<LinkedHashMap<String, Object>> rows) throws Exception {
        if (rows == null) {
            return new ArrayList<LinkedHashMap<String, Object>>();
        }

        if (rows == null || rows.size() <= 0) {
            return new ArrayList<LinkedHashMap<String, Object>>();
        }

        log.info("ROW SIZE = "+rows.size());

        if (null == rows.get(0) || "NULL".equals((rows.get(0).keySet().iterator().next()))) {
            return new ArrayList<LinkedHashMap<String, Object>>();
        }

        ArrayList<LinkedHashMap<String, Object>> arrayList = new ArrayList<LinkedHashMap<String, Object>>();

        int[] i = {0};
        for (LinkedHashMap<String, Object> row : rows) {
            LinkedHashMap<String, Object> resultMap = new LinkedHashMap<String, Object>();

            row.forEach((k,v)->{

                try {
                    if (k != null) {
                        //log.debug("## KEYNAME : "+keyName+" | VALUE : "+row.get(keyName)+" | class : "+row.get(keyName).getClass().getName());
                        //log.info("NAME : "+row.get(k).getClass().getName());



                        if(v != null){
                            if ("java.sql.Timestamp".equals(row.get(k).getClass().getName())) {
                                /*
                            SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd a hh:mm:ss");
                            String returntime = dateFormat3.format(row.get(keyName));
                                 */
                                Timestamp time = (Timestamp) row.get(k);
                                String datetime = CommonUtils.localToDate(time.toLocalDateTime(), null);
                                log.info("LIST java.sql.Timestamp ["+i[0]+"] KEY : "+ k+", VALUE : "+datetime);
                                resultMap.put(k, datetime);

                            } else if("oracle.sql.TIMESTAMP".equals(row.get(k).getClass().getName())) {

                                TIMESTAMP ts = (TIMESTAMP) row.get(k);
                                Timestamp time = ts.timestampValue();

                                String datetime = CommonUtils.localToDate(time.toLocalDateTime(), null);
                                log.info("LIST oracle.sql.TIMESTAMP ["+i[0]+"] KEY : "+ k+", VALUE : "+datetime);
                                resultMap.put(k, datetime);

                            } else {
                                //log.debug("["+REQ_TYPE+"]["+i[0]+"] KEYNAME : "+ k);

                                log.info("LIST ["+i[0]+"] KEY : "+ k+", VALUE : "+v);
                                resultMap.put(k, String.valueOf(v));

                            }

                        } else {
                            log.info("LIST ["+i[0]+"] KEY : "+ k+", VALUE : "+v);
                            resultMap.put(k, "");
                        }


                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });

            /*
            Iterator<String> iterator = row.keySet().iterator();

            String keyName;

            while (iterator.hasNext()) {
                keyName = iterator.next();

                log.debug("["+REQ_TYPE+"]["+i+"] KEYNAME : "+ keyName);

                if (row.get(keyName) != null){

                    log.debug("["+REQ_TYPE+"]["+i+"] KEY : "+ keyName+", VALUE : "+row.get(keyName));
                    resultMap.put(keyName, String.valueOf(row.get(keyName)));

                }else {
                    log.debug("["+REQ_TYPE+"]["+i+"] KEY : "+ keyName+", VALUE : ");
                    resultMap.put(keyName, "");
                }

            }
             */

            arrayList.add(resultMap);
            i[0]++;
        }

        return arrayList;
    }

}
