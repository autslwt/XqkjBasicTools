package com.xqkj.commons.utils;

import java.io.Closeable;
import java.io.IOException;
import java.util.Scanner;

public class IOUtil {

    public static void closeIOStream(Object... objs) {
        if (objs == null)
            return;
        for (int i = 0; i < objs.length; i++) {
            Object obj = objs[i];
            if (obj == null)
                continue;
            try {
                //log.debug("closeIOStream: " + obj);
                // 实现Closeable的类有InputStream,OutputStream,Reader(InputStreamReader 继承Reader)
                if (obj instanceof Closeable) {
                    Closeable c = (Closeable) obj;
                    c.close();
                    c = null;
                } else if (obj instanceof Scanner) {
                    Scanner s = (Scanner) obj;
                    s.close();
                    s = null;
                } else {
                    //log.error("closeIOStream error 发现不支持的类型，请增加对此类型的处理: " + obj.getClass());
                }
            } catch (IOException e) {
                //log.error("closeIOStream error");
            } finally {
                obj = null;
            }
        }
    }

}
