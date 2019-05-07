package sample.dao;

import sample.model.Event;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.Date;

public class XMLDAO {

    boolean saveToFile(String filename){
        Event ev = new Event("Hello", new Date());
            System.out.println("elo");

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

}
