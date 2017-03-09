package service.endp;
import service.impl.PosServiceImpl;
import service.impl.SegServiceImpl;
import service.impl.ParserServiceImpl;
import service.impl.NerServiceImpl;

import javax.xml.ws.Endpoint;

//Endpoint publisher
public class ServicePublisher{

	public static void main(String[] args) {
		Endpoint.publish("http://0.0.0.0:9997/parser", new ParserServiceImpl());
		Endpoint.publish("http://0.0.0.0:9998/pos", new PosServiceImpl());
		Endpoint.publish("http://0.0.0.0:9999/seg", new SegServiceImpl());
		Endpoint.publish("http://0.0.0.0:9996/ner", new NerServiceImpl());
    }

}
