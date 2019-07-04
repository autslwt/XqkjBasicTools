package com.xqkj.commons.constant;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/5/27 10:37 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public enum RunInfoStatusEnum {

    DATE_INIT(0,"开始倒出数据"),
    FILE_MAKE_START(1,"开始生成文件"),
    FILE_MAKE_RUNNING(2,"文件生成中"),
    FILE_MAKE_OVER(3,"文件生成完毕"),
    FILE_MAKE_ERROR(-3,"文件生成错误"),
    FILE_UPLOAD_START(4,"文件上传开始"),
    FILE_UPLOAD_RUNNING(5,"文件上传中"),
    FILE_UPLOAD_OVER(6,"文件上传完毕"),
    FILE_UPLOAD_ERROR(-6,"文件上传错误"),
    ;

    private int code;
    private String desc;

    private RunInfoStatusEnum(int code,String desc){
        this.code=code;
        this.desc=desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
