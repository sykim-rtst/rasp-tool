package kr.co.rtst.autosar.ap4x.ide.action;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.RequiredSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdClientServiceInstanceConfig;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import kr.co.rtst.autosar.ap4x.core.model.handler.ARPackageBuilder;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;

public class DeleteSdClientConfig extends AbstractAPAction{

	public DeleteSdClientConfig(String text, GARObject ownerObject) {
		super(text, ownerObject);
	}

	public void run() {
		GARPackage serviceInstancePkg = ARPackageBuilder.getGARPackege(getAPProject(), ARPackageBuilder.LV1_SERVICE, ARPackageBuilder.LV2_SERVICE_INSTANCE_SD_CLIENT_CONFIG);
		
		doTransactionalOperation(new IAPTransactionalOperation() {
			
			@Override
			public GARObject doProcess(GARObject model) throws Exception {
				RequiredSomeipServiceInstance serviceInstance = (RequiredSomeipServiceInstance)ownerObject;
				SomeipSdClientServiceInstanceConfig serviceInstanceConfig = serviceInstance.getSdClientConfig();
				
				
				if(serviceInstanceConfig != null && serviceInstancePkg.gGetElements().remove(serviceInstanceConfig))
				{
					serviceInstance.setSdClientConfig(null);
				}
				return model;
			}
		});
	}
}