package com.castano.SSE.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.castano.SSE.service.SseService;


@RestController
@RequiredArgsConstructor  // Lombok genera el constructor con las dependencias final
public class SseController {

    private final SseService sseService;  // Inyección a través del constructor generado por Lombok

    /*
    * Cuando un cliente hace una petición a ruta /sse entonces se crea un SseEmitter y se añade a la lista en el service
    * esto hace que el servidor envíe eventos a ese cliente mientras la conexión esté abierta*/
    @GetMapping(path = "/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribe() {
        SseEmitter emitter = new SseEmitter(Long.MAX_VALUE);
        sseService.addEmitter(emitter);  // Agregar emitter al servicio
        return emitter;
    }
}
