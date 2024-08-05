package org.example.elsgraphql.resolver;

import lombok.RequiredArgsConstructor;
import org.example.elsgraphql.model.CourseSession;
import org.example.elsgraphql.model.CourseSessionFile;
import org.example.elsgraphql.service.FileService;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class CourseSessionDataResolver {

    private final FileService fileService;

    @SchemaMapping(typeName = "CourseSession", field = "files")
    public List<CourseSessionFile> getFiles(CourseSession courseSession) {
        return fileService.findFilesBySessionId(courseSession.getId());
    }
}