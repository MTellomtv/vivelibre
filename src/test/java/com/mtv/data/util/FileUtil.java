package com.mtv.data.util;

import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.nio.file.Path;

import static java.nio.file.Files.readAllBytes;

public class FileUtil {

    public static String getResourceAsString(String filePath) throws IOException {
        Path path = new ClassPathResource(filePath).getFile().toPath();
        return new String(readAllBytes(path));
    }

}
