package com.xqkj.annotation.handler;

import com.xqkj.annotation.MethodInteceptAnno;
import com.xqkj.annotation.model.ExceuteResult;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.VariableElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * @ Author     ：lwt-mac <br>
 * @ Date       ：2019/6/25 5:24 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
public class MethodInteceptAnnoHandlerImpl implements MethodInteceptAnnoHandler{

    private static final String argNamesHodlerClassName="com.xqkj.commons.helper.ArgNamesHodler";
    private static final String holerMapName="argNameArrMap";

    @Override
    public ExceuteResult<String> exceute(ProcessingEnvironment processingEnv, Element ele) {
        Messager messager = processingEnv.getMessager();
        MethodInteceptAnno annotation = ele.getAnnotation(MethodInteceptAnno.class);
        String resultStr="";
        Writer writer = null;
        PrintWriter printWriter = null;
        try{
            if(annotation!=null && StringUtils.isNotBlank(annotation.argsNamesHolderClass()) && ele instanceof ExecutableElement){
                String holderClassName=annotation.argsNamesHolderClass();
                //
                int lastDotIndex = holderClassName.lastIndexOf(".");
                String classSimpleName = holderClassName.substring(lastDotIndex + 1);
                String packageName = holderClassName.substring(0, lastDotIndex);
                String blankWord="    ";
                ExecutableElement realEle=(ExecutableElement) ele;
                //String methodName=realEle.getSimpleName().toString();
                //Element classElement = realEle.getEnclosingElement();
                //String methodClassName=classElement.getSimpleName().toString();
                //String createdClassName=methodClassName+"_"+methodName;
                String argNameArrStrName="argNameArrStr";
                List paramList = realEle.getParameters();
                if(CollectionUtils.isNotEmpty(paramList)){
                    //blankWord+blankWord+"public static final String[] "+argNameArrStrName+"="
                    String argNameArrStr="{";
                    String argNameMembersStr="";
                    for(Object param:paramList){
                        VariableElement variableElement=(VariableElement)param;
                        //String classTypeName=variableElement.asType().toString();
                        String paramName=variableElement.toString();
                        argNameArrStr+="\""+paramName+"\",";
                        argNameMembersStr+=blankWord+blankWord+"public static final String "+paramName+"=\""+paramName+"\";\n";
                    }
                    argNameArrStr=argNameArrStr.substring(0,argNameArrStr.length()-1);
                    argNameArrStr+="}";
                    resultStr+="public class "+classSimpleName+"{\n";
                    resultStr+=blankWord+"static{\n"
                             +argNamesHodlerClassName+"."+holerMapName+".put(\""+holderClassName+"\","
                             +"new String[]"+argNameArrStr +");\n"
                             +blankWord+"}\n";
                    //resultStr+=argNameArrStr;
                    resultStr+=argNameMembersStr;
                    resultStr+="}\n";
                }
                //
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(holderClassName);
                writer = javaFileObject.openWriter();
                printWriter = new PrintWriter(writer);
                printWriter.println("package " + packageName + ";\n");
                printWriter.println(resultStr);
                messager.printMessage(Diagnostic.Kind.NOTE,"MethodInteceptAnnoHandler createdClassName:"+holderClassName);
            }
        }catch (Exception ex){
            messager.printMessage(Diagnostic.Kind.ERROR,"MethodInteceptAnnoHandler exception:"+ex.getMessage());
            return ExceuteResult.failureResult("MethodInteceptAnnoHandler exception:"+ex.getMessage());
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (printWriter == null) {
                    printWriter.close();
                }
            } catch (Exception ex) {

            }

        }
        //

        return ExceuteResult.successResult("");
    }


}
