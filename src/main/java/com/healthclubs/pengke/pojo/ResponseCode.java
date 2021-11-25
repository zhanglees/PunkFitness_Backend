package com.healthclubs.pengke.pojo;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 响应参数常量字典
 *
 */
public class ResponseCode {
    private static String unknownErrorMessageKey = "unknownErrorMessageKey";

    public static String getMessageKeyByCode(Integer value) {

        List<Field> fields = Arrays.asList(ResponseCode.class.getFields());
        List<Field> matchFields = fields.stream().filter(field -> {
            try{
                return Integer.parseInt(field.get(null).toString()) == value;
            }catch(Exception e){
                return false;}
        }).collect(Collectors.toList());
        Field matchField = matchFields.size() > 0 ? matchFields.get(0) : null;
        if(matchField != null){
            return  matchField.getName();
        }else {
            return unknownErrorMessageKey;
        }
    }

    public static final Integer SUCCESS_PROCESSED = 0;
    public static final Integer GENERIC_FAILURE = 1;
    public static final Integer PARAMETER_INVALID = 2;
    public static final Integer JWT_DECODE_FAILURE = 3;

    public static final Integer SPECIAL_TOPIC_ACTIVITY_NULL = 100;
    public static final Integer CLASS_CODE_NULL = 1000;
    public static final Integer USER_INTO_CLASS_REDO = 1001;

    public static final Integer USER_OVER = 1003;
    public static final Integer USER_COACHId_EMPTY = 1007;
    public static final Integer APPOINTTIME_BE_OVERDUE = 1008;
    public static final Integer NOT_BELONG_ACTIVE = 1004;
    public static final Integer DBERROR_INSERT_ERROR = 20000;
    public static final Integer DBERROR_INSERT_APPOINTMENTID_ForeignkeyError = 20007;


}
