package sample.dao;

import sample.controllers.Controller;
import sample.model.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.File;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class XMLDAO {

    public static boolean saveToFile(String filename, Event ev){
//        Event ev = new Event("Hello", new Date());
//            System.out.println("elo");

       try{
           File file = new File(filename);
           JAXBContext jaxbContext = JAXBContext.newInstance(Event.class);
           Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

           // output pretty printed
           jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

           jaxbMarshaller.marshal(ev, file);
           jaxbMarshaller.marshal(ev, System.out);

        } catch(JAXBException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean saveToFile(String filename, Controller.Database database){

        try{
            File file = new File(filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(Controller.Database.class);
            Marshaller jaxbMarshaller = jaxbContext.createMarshaller();

            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            jaxbMarshaller.marshal(database, file);
            jaxbMarshaller.marshal(database, System.out);

        } catch(JAXBException e) {
            e.printStackTrace();
        }

        return false;
    }

    public static Controller.Database loadFromFile(String filename){

        try{
            File file = new File(filename);
            JAXBContext jaxbContext = JAXBContext.newInstance(Controller.Database.class);
            Unmarshaller jaxbMarshaller = jaxbContext.createUnmarshaller();

//            jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            Controller.Database database = (Controller.Database)jaxbMarshaller.unmarshal(file);
            return database;

        } catch(JAXBException e) {
            e.printStackTrace();
        }

        return null;
    }
}
