package org.example;

import com.google.auto.service.AutoService;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
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
        }
        return true;
    }
}
