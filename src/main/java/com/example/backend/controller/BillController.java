package com.example.backend.controller;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.backend.dto.BillDto;
import com.example.backend.model.Bill;
import com.example.backend.service.BillService;


@RestController
@RequestMapping("/api/bills")
@CrossOrigin (value = "http://localhost:5173")
public class BillController {
    @Autowired
    private BillService billService;

    @PostMapping
    public ResponseEntity<Bill> createBill(@RequestBody BillDto dto){
        Bill bill = billService.addBill(dto);
        return new ResponseEntity<>(bill, HttpStatus.CREATED);
    }

    @GetMapping("/{username}/{month}")
    public ResponseEntity<Bill> getBillByUsernameMonth(@PathVariable String username, @PathVariable String month){
        Bill bill = billService.getBillByUsernameAndMonth(username, month);
        return ResponseEntity.ok(bill);
    }

    @GetMapping("/{username}")
    public ResponseEntity<List<Bill>> getBillByUsername(@PathVariable String username){
        return ResponseEntity.ok(billService.getBillUsername(username));
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadPdfFile(@RequestParam ("username") String username, @RequestParam ("billMonth") String billMonth, @RequestParam ("file") MultipartFile file){
        if(file.isEmpty() || !file.getOriginalFilename().endsWith(".pdf")){
            return ResponseEntity.badRequest().body("Invalid File. Please upload a pdf file.");
        }

        try{
            Bill bill = billService.getBillByUsernameAndMonth(username, billMonth);
            String fileName = username + "_" + billMonth + ".pdf";
            Path uploadPath = Paths.get("uploads");
            if(!Files.exists(uploadPath)){
                Files.createDirectories(uploadPath);
            }

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            bill.setFilePath(filePath.toString());
            billService.save(bill);

            return ResponseEntity.ok("PDF uploaded and saved in DB.");
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Upload failed: " + e.getMessage());
        }
    }

    @GetMapping("/download/{username}/{month}")
    public ResponseEntity<UrlResource> downloadPdf(@PathVariable String username, @PathVariable String month) throws Exception{
        Bill bill = billService.getBillByUsernameAndMonth(username, month);
        Path path = Paths.get(bill.getFilePath());
        UrlResource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + path.getFileName())
                .body(resource);
    }

   @GetMapping("/view/{username}/{month}")
    public ResponseEntity<UrlResource> viewPdf(@PathVariable String username, @PathVariable String month) throws Exception {
        Bill bill = billService.getBillByUsernameAndMonth(username, month);
        Path path = Paths.get(bill.getFilePath());

        UrlResource resource = new UrlResource(path.toUri());

        if (!resource.exists()) {
            return ResponseEntity.notFound().build();
        }                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_PDF)
                .body(resource);
    }



}
