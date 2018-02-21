package com.o2osys.mng.entity;

import lombok.Data;

/**
   @FileName  : AW02_03_V01.java
   @Description : 가맹점 주문배달 이벤트3 (주문금액별할인) 정보 수정 객체
   @author      : KMS
   @since       : 2018. 2. 7.
   @version     : 1.0

   @개정이력

   수정일          수정자         수정내용
   -----------     ---------      -------------------------------
   2018. 2. 7.    KMS            최초생성

 */
@Data
public class AW02_03_V01 {

    public AW02_03_V01() {
    }

    public AW02_03_V01(String[] strArr) {

        if(strArr != null){
            if(strArr.length >= 16){
                this.stCode       = strArr[0];
                this.userId       = strArr[1];
                this.userYn       = strArr[2];
                this.periodS      = strArr[3];
                this.periodE      = strArr[4];
                this.timeS        = strArr[5];
                this.timeE        = strArr[6];
                this.giveType     = strArr[7];
                this.rewardType   = strArr[8];
                this.ordLimit1    = strArr[9];
                this.disPrice1    = strArr[10];
                this.disType1     = strArr[11];
                this.freeGoods1   = strArr[12];
                this.feeGoodsCnt1 = strArr[13];
                this.giftMemo1    = strArr[14];
                this.imgFile      = strArr[15];
            }
        }

    }

    /** 가맹점_코드 */
    private String stCode;
    /** 사용자_ID (로그인정보) */
    private String userId;
    /** 사용_여부 */
    private String userYn;
    /** 할인적용기간_시작 (YYYYMMDD) */
    private String periodS;
    /** 할인적용기간_종료 (YYYYMMDD) */
    private String periodE;
    /** 할인적용시간_시작 (HH24MI) */
    private String timeS;
    /** 할인적용시간_종료 (HH24MI) */
    private String timeE;
    /** 할인방법_구분 (1: 주문시적용, 2: 쿠폰지급) */
    private String giveType;
    /** 혜택_구분 (1: 금액할인, 2: 무료상품, 3: 경품제공) */
    private String rewardType;
    /** 주문_최소금액_1 (이상) */
    private String ordLimit1;
    /** 금액_할인가격 */
    private String disPrice1;
    /** 금액_할인구분 (1: 원 할인, 2: % 할인) */
    private String disType1;
    /** 무료_상품 (상품일련번호) */
    private String freeGoods1;
    /** 무료_상품_지급개수 */
    private String feeGoodsCnt1;
    /** 경품_내용 */
    private String giftMemo1;
    /** 이미지_파일명 (경로포함) */
    private String imgFile;

    /**
     * VALUE 값 셋팅
     * @Method Name : getInsValue
     * @return
     */
    public String getInsValue(){

        return this.stCode        +"│"+
                this.userId       +"│"+
                this.userYn       +"│"+
                this.periodS      +"│"+
                this.periodE      +"│"+
                this.timeS        +"│"+
                this.timeE        +"│"+
                this.giveType     +"│"+
                this.rewardType   +"│"+
                this.ordLimit1    +"│"+
                this.disPrice1    +"│"+
                this.disType1     +"│"+
                this.freeGoods1   +"│"+
                this.feeGoodsCnt1 +"│"+
                this.giftMemo1    +"│"+
                this.imgFile      +"│";
    }
}
