package com.xqkj.commons.helper;

import com.xqkj.commons.basic.ComponentHolder;
import com.xqkj.commons.export.ExcelHeaderRowWriter;
import com.xqkj.commons.export.ExcelRowCellWriter;
import com.xqkj.commons.proxy.ExcelProxFactory;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/29 4:53 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class DefExcelWriterComponentHolder implements ComponentHolder {

    private static Map<String,String> defNameMap=new HashMap<>();

    static {
        defNameMap.put(ExcelRowCellWriter.class.getName(),ExcelProxFactory.defExcelRowCellWriterName);
        defNameMap.put(ExcelHeaderRowWriter.class.getName(),ExcelProxFactory.defExcelHeaderRowWriterName);
    }

    @Override
    public <T> T getComponentByName(String componentName,Class<T> clazz) {
        if(StringUtils.isBlank(componentName)){
            componentName=defNameMap.get(clazz.getName());
        }
        return ExcelProxFactory.getRegistedObj(componentName);
    }
}
