package com.example.demo.domain.version.controller;

import com.example.demo.domain.version.entity.Version;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class VersionController {

    @QueryMapping
    public Version getVersion() {
        return Version.getVersion();
    }
}
