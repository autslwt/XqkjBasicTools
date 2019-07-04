package com.xqkj.annotation.processer;

import com.xqkj.annotation.handler.MethodInteceptAnnoHandler;
import com.xqkj.annotation.handler.MethodInteceptAnnoHandlerImpl;
import com.xqkj.annotation.model.ExceuteResult;
import org.apache.commons.collections4.CollectionUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @ Author     ：lwt-mac<br>
 * @ Date       ：2019/6/25 3:32 PM <br>
 * @ Description：<br>
 * @ Modified By：<br>
 * @Version: 1.000
 */
@SupportedAnnotationTypes({"com.xqkj.annotation.MethodInteceptAnno"})
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class XqkjAnnotationProcessor extends AbstractProcessor {

    private MethodInteceptAnnoHandler methodInteceptAnnoHandler = new MethodInteceptAnnoHandlerImpl();

    private static final String classHodlerFullName = "com.xqkj.methodargs.ArgNamesHolder";

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Messager messager = processingEnv.getMessager();
        messager.printMessage(Diagnostic.Kind.WARNING, "开始运行--XqkjAnnotationProcessor");
        //List<Map<String,String>> classStrList=new ArrayList<>();
        for (TypeElement typeElement : annotations) {
            for (Element e : roundEnv.getElementsAnnotatedWith(typeElement)) {
                ExceuteResult<String> exceuteResult = methodInteceptAnnoHandler.exceute(processingEnv, e);
                if (!exceuteResult.isSuccess()) {
                    return false;
                }
                //classStrList.add(exceuteResult.getResult());
            }
        }
//        if(!createArgNamesHolerJavaFile(classStrList)){
//            return false;
//        }
        messager.printMessage(Diagnostic.Kind.WARNING, "运行结束--XqkjAnnotationProcessor");
        return true;
    }

    private boolean createArgNamesHolerJavaFile(List<Map<String,String>> classInforList) {
        if (CollectionUtils.isNotEmpty(classInforList)) {
            Messager messager = processingEnv.getMessager();
            Writer writer = null;
            PrintWriter printWriter = null;
            try {
                JavaFileObject javaFileObject = processingEnv.getFiler().createSourceFile(classHodlerFullName);
                writer = javaFileObject.openWriter();
                printWriter = new PrintWriter(writer);
                //
                int lastDotIndex = classHodlerFullName.lastIndexOf(".");
                String classSimpleName = classHodlerFullName.substring(lastDotIndex + 1);
                String packageName = classHodlerFullName.substring(0, lastDotIndex);
                //
                String classBody = "";
                String initStr="";
                String initMapName="argNameArrMap";
                for (Map<String,String> classInforMap : classInforList) {
                    classBody += classInforMap.get(MethodInteceptAnnoHandler.classStrName);
                    initStr+="        "+initMapName+".put(\""+classInforMap.get(MethodInteceptAnnoHandler.argsArrMapKey)+"\","+
                            classInforMap.get(MethodInteceptAnnoHandler.arrArgName)+");\n";
                }
                printWriter.println("package " + packageName + ";\n");
                printWriter.println("\n");
                printWriter.println("import java.util.Map;\n");
                printWriter.println("import java.util.HashMap;\n");

                printWriter.println("\npublic class " + classSimpleName + " { \n");
                printWriter.println("    public static final Map<String,String[]> "+initMapName+"=new HashMap<>();\n");
                printWriter.println("    static{\n");
                printWriter.println(initStr);
                printWriter.println("    }");
                printWriter.println(classBody);
                printWriter.println("\n}");
                return true;
            } catch (Exception ex) {
                messager.printMessage(Diagnostic.Kind.ERROR, "createClassHolerJavaFile exception:" + ex.getMessage());
                return false;
            } finally {
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
        }
        return false;
    }
}
