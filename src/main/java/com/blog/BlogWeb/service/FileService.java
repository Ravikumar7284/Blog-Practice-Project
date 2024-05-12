package com.blog.BlogWeb.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class FileService {

  public String uploadImage(String path, MultipartFile file) throws IOException {
    String fileName = file.getOriginalFilename();
    String randomID = UUID.randomUUID().toString();
    String tempFileName = randomID.concat(fileName.substring(fileName.lastIndexOf(".")));
    String filePath = path + File.separator + tempFileName;
    File tempFile = new File(path);
    if (!tempFile.exists()) {
      tempFile.mkdir();
    }

    Files.copy(file.getInputStream(), Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
    return tempFileName;
  }

  public InputStream getResource(String path, String fileName) throws FileNotFoundException {
    String filePath = path + File.separator + fileName;
    return new FileInputStream(filePath);
  }

}
