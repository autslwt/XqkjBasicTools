package com.xqkj.commons.helper;

import com.xqkj.commons.model.BasicContextVO;
import com.xqkj.commons.model.HandleResult;

/**
 * @author lwt<br>
 * @description <br>
 * @date 2019/5/19
 */
public interface MethodRunInteceptor<T,P extends BasicContextVO<T>> {
    HandleResult<T> before(P param);
    HandleResult<T> after(P param);
}
