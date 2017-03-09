package service.impl;

import java.util.List;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

//Service Endpoint Interface
@WebService
@SOAPBinding(style = Style.RPC)
public interface SegService {
	@WebMethod String getSegmentResult(String text) throws Exception;
}
