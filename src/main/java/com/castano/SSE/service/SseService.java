package com.castano.SSE.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;


@Service
public class SseService {
    private final List<SseEmitter> emitters = new CopyOnWriteArrayList<>(); //lista de todos los clientes suscritos

    public void addEmitter(SseEmitter emitter){
        emitters.add(emitter);
        emitter.onCompletion(() -> emitters.remove(emitter));
        emitter.onTimeout(()-> emitters.remove(emitter));
    }

    @Scheduled(fixedRate = 1000) //tarea programada que se ejecuta cada segundo
    public void sendEvents(){
        for(SseEmitter emitter : emitters){
            try{
                emitter.send(System.currentTimeMillis()); //envia tiempo 
            }catch(IOException e){
                emitter.complete();
                emitters.remove(emitter);
            }
        }
    }

}
