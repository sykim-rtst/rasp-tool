package kr.co.rtst.autosar.ap4x.ide.action;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdServerServiceInstanceConfig;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import kr.co.rtst.autosar.ap4x.core.model.handler.ARPackageBuilder;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;

public class DeleteSdServerConfig extends AbstractAPAction{

	public DeleteSdServerConfig(String text, GARObject ownerObject) {
		super(text, ownerObject);
	}
	
	public void run() {
		GARPackage serviceInstancePkg = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_SERVICE, ARPackageBuilder.LV2_SERVICE_INSTANCE_SD_SERVER_CONFIG);
		
		doTransactionalOperation(new IAPTransactionalOperation() {
			
			@Override
			public GARObject doProcess(GARObject model) throws Exception {
				ProvidedSomeipServiceInstance serviceInstance = (ProvidedSomeipServiceInstance)ownerObject;
				SomeipSdServerServiceInstanceConfig serviceInstanceConfig = serviceInstance.getSdServerConfig();
				
				if(serviceInstanceConfig != null && serviceInstancePkg.gGetElements().remove(serviceInstanceConfig))
				{
					serviceInstance.setSdServerConfig(null);
				}
				return model;
			}
				
			});
	}

}
