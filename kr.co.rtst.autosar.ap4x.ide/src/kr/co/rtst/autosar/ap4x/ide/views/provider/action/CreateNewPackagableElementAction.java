package kr.co.rtst.autosar.ap4x.ide.views.provider.action;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.ApplicationstructureFactory;
import autosar40.adaptiveplatform.applicationdesign.portinterface.PortinterfaceFactory;
import autosar40.adaptiveplatform.machinemanifest.MachinemanifestFactory;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.ServiceinterfacedeploymentFactory;
import autosar40.adaptiveplatform.systemdesign.SystemdesignFactory;
import autosar40.commonstructure.implementationdatatypes.ImplementationdatatypesFactory;
import autosar40.swcomponent.datatype.datatypes.impl.DatatypesFactoryImpl;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;

/**
 * 패키지 아래 바로 등록되는 모델 객체의 생성을 담당하는 액션이다.
 * @author thkim
 *
 */
public class CreateNewPackagableElementAction extends AbstractCreatePackagableElementAction {

	public CreateNewPackagableElementAction(IAPVirtualElement apVirtualElement, String title) {
		super(apVirtualElement, title);
	}
	
	@Override
	protected GPackageableElement createGPackageableElement() {
		switch(apVirtualElement.getName()) {
		case IAPVirtualElement.VE_NAME_TYPE_IMPL:
			return ImplementationdatatypesFactory.eINSTANCE.createImplementationDataType();
		case IAPVirtualElement.VE_NAME_TYPE_APP:
			return new DatatypesFactoryImpl().createApplicationDataType();
		case IAPVirtualElement.VE_NAME_APPLICATION_SWC:
			return ApplicationstructureFactory.eINSTANCE.createAdaptiveApplicationSwComponentType();
//		case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:
		case IAPVirtualElement.VE_NAME_SERVICE_INTERFACE:
			return PortinterfaceFactory.eINSTANCE.createServiceInterface();
		case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:
			return ServiceinterfacedeploymentFactory.eINSTANCE.createSomeipServiceInterfaceDeployment();
		case IAPVirtualElement.VE_NAME_MACHINE_DESIGN:
			return SystemdesignFactory.eINSTANCE.createMachineDesign();
		case IAPVirtualElement.VE_NAME_MACHINE:
			return MachinemanifestFactory.eINSTANCE.createMachine();
		}
		return null;
	}
	
	@Override
	protected String getCreateGPackageableElementName() {
		switch(apVirtualElement.getName()) {
		case IAPVirtualElement.VE_NAME_TYPE_IMPL:
			return "Implementation Data Type";
		case IAPVirtualElement.VE_NAME_TYPE_APP:
			return "Application Data Type";
		case IAPVirtualElement.VE_NAME_APPLICATION_SWC:
			return "Adaptive Applciation SW Component Type";
//		case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:
		case IAPVirtualElement.VE_NAME_SERVICE_INTERFACE:
			return "Service Interface";
		case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:
			return "Service Interface Deployment";
		case IAPVirtualElement.VE_NAME_MACHINE_DESIGN:
			return "Machine Design";
		case IAPVirtualElement.VE_NAME_MACHINE:
			return "Machine";
		}
		return null;
	}
	
}
