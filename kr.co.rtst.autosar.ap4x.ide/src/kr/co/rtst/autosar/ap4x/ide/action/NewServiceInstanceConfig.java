package kr.co.rtst.autosar.ap4x.ide.action;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.ProvidedServiceInstanceModelManager;

public class NewServiceInstanceConfig extends AbstractAPAction{

	public NewServiceInstanceConfig(String text, GARObject ownerObject) {
		super(text, ownerObject);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void run() {
		ProvidedServiceInstanceModelManager providedServiceInstance = (ProvidedServiceInstanceModelManager)ownerObject;
		
		
	}

}
