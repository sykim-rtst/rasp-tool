package kr.co.rtst.autosar.ap4x.core.model.manager;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.IAPSubVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;

/**
 * 모델 관리 객체를 제공하는 객체
 * 도구에서 필요로 하는 모든 모델 관리 객체의 인스턴스를 생성해두고 요청에 따라 알맞은 인스턴스를 제공한다.
 * @author thkim
 *
 */
public class APModelManagerProvider {

	public static final APModelManagerProvider apINSTANCE = new APModelManagerProvider();
	
	private final ImplementationDataTypesModelManager implementationdataTypesModelManager;
	private final ApplicationDataTypesModelManager applicationDataTypesModelManager;
	private final SwComponentsModelManager swComponentsModelManager;
	private final InterfacesModelManager interfacesModelManager;
	private final InterfaceDeploymentModelManager interfaceDeploymentModelManager;
	private final ProvidedServiceInstanceModelManager providedServiceInstanceModelManager;
	private final RequiredServiceInstanceModelManager requiredServiceInstanceModelManager;
	private final MachineDesignsModelManager machineDesignsModelManager;
	private final MachineModelManager machinesModelManager;
	
	
	
	private APModelManagerProvider() {
		implementationdataTypesModelManager = new ImplementationDataTypesModelManager();
		applicationDataTypesModelManager = new ApplicationDataTypesModelManager();
		swComponentsModelManager = new SwComponentsModelManager();
		machineDesignsModelManager = new MachineDesignsModelManager();
		interfacesModelManager = new InterfacesModelManager();
		providedServiceInstanceModelManager = new ProvidedServiceInstanceModelManager();
		requiredServiceInstanceModelManager = new RequiredServiceInstanceModelManager();
		interfaceDeploymentModelManager = new InterfaceDeploymentModelManager();
		machinesModelManager = new MachineModelManager();
	}
	
	/**
	 * 주어진 모델객체를 관리하기 위한 매니저 객체를 찾아 반환한다. 매니저 객체가 관리하는 최상위 모델의 자식인 경우 해당 모델의 매니저가 관리하는 모델 객체이다.
	 * @param element
	 * @return
	 */
	public IAPModelManager lookupModelManager(GARObject element) {
		if(implementationdataTypesModelManager.isNavigatableSubElement(element)) {
			return implementationdataTypesModelManager;
		}else if(applicationDataTypesModelManager.isNavigatableSubElement(element)) {
			return applicationDataTypesModelManager;
		}else if(swComponentsModelManager.isNavigatableSubElement(element)) {
			return swComponentsModelManager;
		}else if(interfacesModelManager.isNavigatableSubElement(element)) {
			return interfacesModelManager;
		}else if(interfaceDeploymentModelManager.isNavigatableSubElement(element)) {
			return interfaceDeploymentModelManager;
		}else if(providedServiceInstanceModelManager.isNavigatableSubElement(element)) {
			return providedServiceInstanceModelManager;
		}else if(requiredServiceInstanceModelManager.isNavigatableSubElement(element)) {
			return requiredServiceInstanceModelManager;
		}else if(machineDesignsModelManager.isNavigatableSubElement(element)) {
			return machineDesignsModelManager;
		}else if(machinesModelManager.isNavigatableSubElement(element)) {
			return machinesModelManager;
		}
		return null;
	}
	
//	public IAPModelManager lookupModelManager(IAPVirtualElement virtualElement) {
//		switch(virtualElement.getCategoryName()) {
//		case IAPVirtualElement.VE_NAME_TYPE:
//			return implementationdataTypesModelManager;
//		case IAPVirtualElement.VE_NAME_APPLICATION:
//			return swComponentsModelManager;
//		case IAPVirtualElement.VE_NAME_SERVICE:
//			return interfacesModelManager;
//		case IAPVirtualElement.VE_NAME_MACHINE:
//			return machineDesignsModelManager;
//		}
//		return null;
//	}
	
	public IAPModelManager lookupModelManager(IAPSubVirtualElement virtualElement) {
		switch(virtualElement.getName()) {
		case IAPVirtualElement.VE_NAME_TYPE_IMPL:
			return implementationdataTypesModelManager;
		case IAPVirtualElement.VE_NAME_TYPE_APP:
			return applicationDataTypesModelManager;
		case IAPVirtualElement.VE_NAME_APPLICATION_SWC:
			return swComponentsModelManager;
		case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:
			return null;
		case IAPVirtualElement.VE_NAME_SERVICE_INTERFACE:
			return interfacesModelManager;
		case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:
			return interfaceDeploymentModelManager;
		case IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP:
			return providedServiceInstanceModelManager;
		case IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP:
			return requiredServiceInstanceModelManager;
		case IAPVirtualElement.VE_NAME_MACHINE_DESIGN:
			return machineDesignsModelManager;
		case IAPVirtualElement.VE_NAME_MACHINE:
			return machinesModelManager;
		}
		return null;
	}
	
	public ImplementationDataTypesModelManager getImplementationDataTypesModelManager() {
		return implementationdataTypesModelManager;
	}
	
	public ApplicationDataTypesModelManager getApplicationDataTypesModelManager() {
		return applicationDataTypesModelManager;
	}

	public SwComponentsModelManager getSwComponentsModelManager() {
		return swComponentsModelManager;
	}

	public MachineDesignsModelManager getMachineDesignsModelManager() {
		return machineDesignsModelManager;
	}

	public InterfacesModelManager getInterfacesModelManager() {
		return interfacesModelManager;
	}
	
	public InterfaceDeploymentModelManager getInterfaceDeploymentModelManager() {
		return interfaceDeploymentModelManager;
	}
	
	public ProvidedServiceInstanceModelManager getProvidedServiceInstanceModelManager() {
		return providedServiceInstanceModelManager;
	}
	
	public RequiredServiceInstanceModelManager getRequiredServiceInstanceModelManager() {
		return requiredServiceInstanceModelManager;
	}
	
	public MachineModelManager getMachinesModelManager() {
		return machinesModelManager;
	}
	
}
