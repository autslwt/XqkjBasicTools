package com.xqkj.commons.utils;

import com.xqkj.commons.anno.ExcelCellInforAnno;
import com.xqkj.commons.export.model.ExcelCellConfig;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/26 11:18 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class ExcelInfoAnnoUtils {

//    public static ExcelCellConfig decodeExcelTitleInforAnno(ExcelTitleInforAnno excelTitleInforAnno) {
//        return null;
//    }

    public static List<ExcelCellConfig> readClassExcelCellInforAnno(Class annoedClass) {
        List<ExcelCellConfig> list = new ArrayList<>();
        Field[] fields = annoedClass.getDeclaredFields();
        for (Field field : fields) {
            ExcelCellInforAnno excelCellInforAnno = field.getAnnotation(ExcelCellInforAnno.class);
            if (excelCellInforAnno != null) {
                list.add(fillExcelCellInforAnno(excelCellInforAnno,null));
            }
        }
        sortExcelCellConfig(list);
        return list;
    }

    public static List<ExcelCellConfig> readDataExcelCellInforAnno(Object data)
            throws InvocationTargetException, IllegalAccessException {
        List<ExcelCellConfig> list = new ArrayList<>();
        Class clazz = data.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            ExcelCellInforAnno excelCellInforAnno = field.getAnnotation(ExcelCellInforAnno.class);
            if (excelCellInforAnno != null) {
                String fieldName = field.getName();
                String getMethodName = createGetNameByFiledName(fieldName);
                Method getMethod = getMethodByName(getMethodName, clazz);
                Object dataVal;
                if (getMethod != null) {
                    dataVal=getMethod.invoke(data);
                } else {
                    field.setAccessible(true);
                    dataVal=field.get(data);
                }
                ExcelCellConfig excelCellConfig =fillExcelCellInforAnno(excelCellInforAnno,dataVal);
                list.add(excelCellConfig);
            }
        }
        sortExcelCellConfig(list);
        return list;
    }

    private static ExcelCellConfig fillExcelCellInforAnno(ExcelCellInforAnno excelCellInforAnno, Object value) {
        ExcelCellConfig excelCellConfig = new ExcelCellConfig();
        excelCellConfig.setCellCode(excelCellInforAnno.cellCode());
        excelCellConfig.setHeader(excelCellInforAnno.header());
        excelCellConfig.setColumWidth(excelCellInforAnno.columWidth()*256);
        excelCellConfig.setFormat(excelCellInforAnno.format());
        excelCellConfig.setIndex(excelCellInforAnno.index());
        excelCellConfig.setValue(value);
        return excelCellConfig;
    }


    /**
     *
     * 给数据或者表头信息排序
     *
     * @author liwentao
     * @param list
     */
    private static void sortExcelCellConfig(List<ExcelCellConfig> list) {
        Collections.sort(list, new Comparator<ExcelCellConfig>() {
            @Override
            public int compare(ExcelCellConfig arg0, ExcelCellConfig arg1) {
                int index0 = arg0.getIndex();
                int index1 = arg1.getIndex();
                return index0 - index1;
            }
        });
    }

    /**
     *
     *
     * @author liwentao
     * @param name
     * @param beanClass
     * @return
     */
    private static Method getMethodByName(String name, Class<?> beanClass) {
        try {
            return beanClass.getMethod(name);
        } catch (NoSuchMethodException | SecurityException e) {
            return null;
        }
    }

    /**
     *
     *
     * @author liwentao
     * @param fieldName
     * @return
     */
    private static String createGetNameByFiledName(String fieldName) {
        return "get" + upperFirstCase(fieldName);
    }

    private static String upperFirstCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
