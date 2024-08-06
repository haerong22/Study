package org.example.elsgraphql.service.dummy;

import org.example.elsgraphql.model.CourseSessionFile;
import org.example.elsgraphql.service.FileService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class DummyFileService implements FileService {

    private final List<CourseSessionFile> files = new ArrayList<>(100);

    private final AtomicLong counter = new AtomicLong(100);

    public DummyFileService() {
        initData(); // Initialize with dummy data
    }

    private void initData() {
        // Adding some dummy files to the service
        files.add(new CourseSessionFile(counter.getAndIncrement(), 1L, null, "Introduction to GraphQL.mp4", "mp4", "/files/intro_to_graphql.mp4"));
        files.add(new CourseSessionFile(counter.getAndIncrement(), 1L, null,"GraphQL Queries.mp4", "mp4", "/files/graphql_queries.mp4"));
        files.add(new CourseSessionFile(counter.getAndIncrement(), 2L, null,"Advanced GraphQL.mp4", "mp4", "/files/advanced_graphql.mp4"));
        files.add(new CourseSessionFile(counter.getAndIncrement(), 2L, null,"GraphQL Mutation Strategies.mp4", "mp4", "/files/graphql_mutation_strategies.mp4"));
    }

    @Override
    public List<CourseSessionFile> findAll() {
        return new ArrayList<>(files);
    }

    @Override
    public Optional<CourseSessionFile> findById(Long sessionId, Long fileId) {
        return files.stream()
                .filter(file -> file.getId().equals(fileId))
                .findFirst();
    }

    @Override
    public List<CourseSessionFile> findFilesBySessionId(Long sessionId) {
        return files.stream()
                .filter(file -> file.getCourseSessionId().equals(sessionId))
                .collect(Collectors.toList());
    }

    public CourseSessionFile save(CourseSessionFile file) {
        if (file.getId() == null) {
            file.setId(counter.getAndIncrement());
        }
        files.add(file);
        return file;
    }

    public void delete(Long fileId) {
        files.removeIf(file -> file.getId().equals(fileId));
    }
}