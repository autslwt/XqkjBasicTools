package com.xqkj.commons.helper;

import com.xqkj.commons.model.BasicContextVO;
import com.xqkj.commons.model.HandleResult;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/19
 */
public interface MethodRunWithHandleExceptionInteceptor<T,P extends BasicContextVO<T>>
        extends MethodRunInteceptor<T,P>{
    HandleResult<T> onException(Exception ex,P param);
}
