package io.swagger.controllers;

import com.google.common.io.Files;
import io.swagger.oas.inflector.models.RequestContext;
import io.swagger.oas.inflector.models.ResponseContext;
import javax.ws.rs.core.Response.Status;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.JavaInflectorServerCodegen", date = "2019-11-16T17:17:40.998Z[GMT]")public class SpecsController  {
    public static String SPEC_FILE = "src/main/swagger/openapi.yaml";
    public ResponseContext getSpecification(RequestContext request ) {
        try {
            File file = new File(SPEC_FILE);
            if(!file.exists()) {
                file = new File(
                    getClass().getClassLoader().getResource("default.yaml").getFile()
                );
            }
            String lines = Files.readLines(file, Charset.defaultCharset()).stream()
                    .collect(Collectors.joining("\n"));

            return new ResponseContext()
                    .status(Status.OK)
                    .contentType("application/yaml")
                    .entity(lines);
        }
        catch(Exception e) {
            return new ResponseContext()
                    .status(Status.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseContext saveSpecification(RequestContext request, String body ) {
        File file = new File(SPEC_FILE);

        try {
            if(!file.exists()) {
                String[] dirs = SPEC_FILE.split("/");
                dirs = Arrays.copyOf(dirs, dirs.length-1);
                File directory = new File(String.join("/", dirs));
                System.out.println(directory.getAbsolutePath());
                directory.mkdirs();
            }
            Files.write(body.getBytes(), Paths.get(SPEC_FILE).toFile());
            System.out.println("saved spec");
            return new ResponseContext().status(201);
        }
        catch(IOException e) {
            return new ResponseContext()
                    .status(Status.INTERNAL_SERVER_ERROR);
        }
    }
}

