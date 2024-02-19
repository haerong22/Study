package org.example;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.*;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.util.Set;

@AutoService(Processor.class)
public class MagicMojaProcessor extends AbstractProcessor {

    // 애노테이션 지정
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Set.of(Magic.class.getName());
    }

    // 소스 코드 버전 지정
    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    // 처리할 프로세스
    // -> true 를 리턴 할 경우 애노테이션 타입 처리가 끝.(다른 프로세서들은 처리 하지 않는다.)
    // element -> 소스코드의 구성 요소들
    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        Set<? extends Element> elements = roundEnv.getElementsAnnotatedWith(Magic.class);
        // 애노테이션의 위치가 interface 일 경우에만 동작
        for (Element element: elements) {
            Name elementName = element.getSimpleName();
            if (element.getKind() != ElementKind.INTERFACE) {
                // 인터페이스가 아닌 경우 에러 메시지 출력
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "Magic annotation can not be used on " + elementName);
            } else {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.NOTE, "Processing " + elementName);
            }

            // element 의 className ( FQCN )
            TypeElement typeElement = (TypeElement) element;
            ClassName className = ClassName.get(typeElement);

            // pullOut 메소드 생성
            MethodSpec pullOut = MethodSpec.methodBuilder("pullOut")
                    .addModifiers(Modifier.PUBLIC)
                    .returns(String.class)
                    .addStatement("return $S", "Rabbit!")
                    .build();

            // magicMoja 클래스 생성
            TypeSpec magicMoja = TypeSpec.classBuilder("MagicMoja")
                    .addModifiers(Modifier.PUBLIC)
                    .addSuperinterface(className)
                    .addMethod(pullOut)
                    .build();

            // 위에 정의한 소스코드로 실제 파일 생성
            Filer filer = processingEnv.getFiler();

            try {
                JavaFile.builder(className.packageName(), magicMoja)
                        .build()
                        .writeTo(filer);
            } catch (IOException e) {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, "FATAL ERROR: " + e);
            }
        }
        return true;
    }
}
