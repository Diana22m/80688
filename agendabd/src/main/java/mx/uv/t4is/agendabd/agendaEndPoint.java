package mx.uv.t4is.agendabd;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.agenda.AgendarRequest;
import https.t4is_uv_mx.agenda.AgendarResponse;
import https.t4is_uv_mx.agenda.BorrarAgendaRequest;
import https.t4is_uv_mx.agenda.BorrarAgendaResponse;
import https.t4is_uv_mx.agenda.BuscarAgendaResponse;
import https.t4is_uv_mx.agenda.ModificarAgendaRequest;
import https.t4is_uv_mx.agenda.ModificarAgendaResponse;






@Endpoint
public class agendaEndPoint{
   
    
    @Autowired
    Iagendar iagendar;

    @PayloadRoot(localPart = "AgendarRequest", namespace = "https://t4is.uv.mx/agenda")
    @ResponsePayload
    public AgendarResponse Agendar(@RequestPayload AgendarRequest peticion) {
        AgendarResponse respuesta = new AgendarResponse();
        //se agrega a la lista
        
        mx.uv.t4is.agendabd.agendar a = new  mx.uv.t4is.agendabd.agendar();
        respuesta.setEventoNuevo("fecha: "+peticion.getFecha()+" Nombre de la actividad: "+peticion.getNombreActividad()+" Descripción: "+peticion.getDescripcion()+" Lugar: "+peticion.getLugar());//resultado al crear un envento nuevo 
        a.setFecha(peticion.getFecha());
        a.setNombreActividad(peticion.getNombreActividad());
        a.setDescripcion(peticion.getDescripcion());
        a.setLugar(peticion.getLugar());
        iagendar.save(a);
     
        return respuesta;
        
    }

   
    @PayloadRoot(localPart = "BuscarAgendaRequest", namespace = "https://t4is.uv.mx/agenda")
    @ResponsePayload
    public BuscarAgendaResponse buscarSaludos(){
        BuscarAgendaResponse respuesta = new BuscarAgendaResponse();
       Iterable<agendar> lista = iagendar.findAll();
       
        for (agendar o : lista) {
           
            BuscarAgendaResponse.Agendar e = new  BuscarAgendaResponse.Agendar();
           
            e.setId(o.getId());
            e.setDescripcion(o.getDescripcion());
            e.setFecha(o.getFecha());
            e.setNombreActividad(o.getNombreActividad());
            e.setLugar(o.getLugar());
            
            respuesta.getAgendar().add(e);
           
              
        }
        return respuesta;
    }

    @PayloadRoot(localPart = "ModificarAgendaRequest", namespace = "https://t4is.uv.mx/agenda")
    @ResponsePayload
    public ModificarAgendaResponse modificarAgenda(@RequestPayload ModificarAgendaRequest peticion){
        ModificarAgendaResponse respuesta = new ModificarAgendaResponse();
    
       
        agendar element = new agendar();
        element.setFecha(peticion.getFecha());
        element.setDescripcion(peticion.getDescripcion());
        element.setNombreActividad(peticion.getNombreActividad());
        element.setLugar(peticion.getLugar());
        iagendar.save(element);
        respuesta.setAgendaModificada(true);
        
        return respuesta;
    }

    @PayloadRoot(localPart = "BorrarAgendaRequest", namespace = "https://t4is.uv.mx/agenda")
    @ResponsePayload
    public BorrarAgendaResponse borrarTarea(@RequestPayload BorrarAgendaRequest peticion){
        BorrarAgendaResponse respuesta = new BorrarAgendaResponse();
        //eliminar de la lista
        iagendar.deleteById(peticion.getId());
        respuesta.setRespuesta(true);
        return respuesta;
    }


    
}