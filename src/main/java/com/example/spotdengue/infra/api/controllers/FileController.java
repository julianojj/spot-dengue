package com.example.spotdengue.infra.api.controllers;

import com.example.spotdengue.core.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin(origins = "*")
@RestController
public class FileController {
    @Autowired
    private UploadFile uploadFile;
    @Autowired
    private GetFile getFile;
    @Autowired
    private DeleteFile deleteFile;

    @PostMapping("/upload_file")
    public ResponseEntity<Object> uploadFile(@RequestParam("file") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Please select a file!", HttpStatus.BAD_REQUEST);
        }
        UploadFileInput input = new UploadFileInput(file.getOriginalFilename(), file.getContentType(), file.getInputStream());
        UploadFileOutput output = this.uploadFile.execute(input);
       return new ResponseEntity<>(output, HttpStatus.OK);
    }

    @GetMapping("/get_file/{name}")
    public ResponseEntity<byte[]> getFile(@PathVariable String name) throws Exception {
        byte[] output = getFile.execute(name);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(output);
    }

    @PostMapping("/remove_file/{id}")
    public ResponseEntity<Object> deleteFile(@PathVariable String id) throws Exception {
        this.deleteFile.Execute(id);
        return ResponseEntity.noContent().build();
    }
}
