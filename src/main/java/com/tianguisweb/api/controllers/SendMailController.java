package com.tianguisweb.api.controllers;

import com.tianguisweb.api.model.entities.Email;
import com.tianguisweb.api.model.services.ISendMailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http//:localhost:4200"})
@RequestMapping("/api")
public class SendMailController {

    @Autowired
    private ISendMailService sendMailService;

    //Send to mail
    @PostMapping("/send-mail")
    public ResponseEntity<?> sendImail(@RequestBody Email email){

        Map<String,Object> response = new HashMap<>();
        String message  = email.getBody() + "\n\nPara cualquier duda o aclaración, datos de contacto: " + "\nNombre: "
                + email.getName() + "\nE-mail: "+email.getMail() + "\n\nEnlace de recuperación: "
                +"\nURL: "+email.getUrl();
        try{
            this.sendMailService.sendMail("erickgozan@gmail.com",email.getMail(),email.getSubject(),message);
        }catch (Exception e){
            response.put("error","Hubo un error al mandar el correo: " + e.getMessage());
            return  new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","El correo se mando de forma exitosa");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
