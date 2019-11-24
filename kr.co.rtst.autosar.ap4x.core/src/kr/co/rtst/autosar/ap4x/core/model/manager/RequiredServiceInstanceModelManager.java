package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.RequiredSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipRequiredEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdClientServiceInstanceConfig;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdServerServiceInstanceConfig;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class RequiredServiceInstanceModelManager extends AbstractAPModelManager{

	//#################################################################################################
	// Provided Service Instance 의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_REQUIRED_SERVICE_INSTANCE = "RequiredServiceInstance";
	public static final String TYPE_NAME_SERVICE_INSTANCE_CLIENT_CONFIG = "SdClientServiceInstanceConfig";
	public static final String TYPE_NAME_EVENT_GROUP = "EventGroup";
	
	public RequiredServiceInstanceModelManager() {
		// TODO Auto-generated constructor stub
	}
	

	
	@Override
	public List<GARObject> getChildren(IFile arxmlFile, String virtualElementName) {
		GAUTOSAR root = load(arxmlFile);
		if(root != null) {
			switch(virtualElementName) {
				case IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP:{
					return new ArrayList<>(getAllRequiredServiceInstance(root));
				}
			}
		}
		return null;
	}

	@Override
	public List<GARObject> getChildren(GARObject element) {
		List<GARObject> children = new ArrayList<>();
		IAPTypeDescriptor typeDesc = findAPTypeDescriptor(element);
		
		if(typeDesc != null && typeDesc.isChildable()) {
			switch(typeDesc.getTypeName()) {
				case TYPE_NAME_REQUIRED_SERVICE_INSTANCE:{
					RequiredSomeipServiceInstance model = (RequiredSomeipServiceInstance)element;
					if( model.getSdClientConfig() != null)
					{
						children.add(model.getSdClientConfig());
					}
					children.addAll(model.getRequiredEventGroups());
					System.out.println(typeDesc.getTypeName()+" 자식요소: " + children.size());
				}
				break;
			}
		}
		
		return children;
	}

	@Override
	public GARObject getParent(GARObject gaObject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected List<IAPTypeDescriptor> generateAPTypeDescriptors() {
		List<IAPTypeDescriptor> apTypeDescriptors = new ArrayList<>();
		
		IAPTypeDescriptor TD_TYPE_NAME_REQUIRED_SERVICE_INSTANCE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP, TYPE_NAME_REQUIRED_SERVICE_INSTANCE, RequiredSomeipServiceInstance.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SERVICE_INSTANCE_SD_CONFIG = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP, TYPE_NAME_SERVICE_INSTANCE_CLIENT_CONFIG, SomeipSdClientServiceInstanceConfig.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_EVENT_GROUP = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP, TYPE_NAME_EVENT_GROUP, SomeipRequiredEventGroup.class, true, true);;
		
		apTypeDescriptors.add(TD_TYPE_NAME_REQUIRED_SERVICE_INSTANCE);
		apTypeDescriptors.add(TD_TYPE_NAME_SERVICE_INSTANCE_SD_CONFIG);
		apTypeDescriptors.add(TD_TYPE_NAME_EVENT_GROUP);
		
		TD_TYPE_NAME_REQUIRED_SERVICE_INSTANCE.addChildAPTypeDescriptor(TD_TYPE_NAME_SERVICE_INSTANCE_SD_CONFIG);
		TD_TYPE_NAME_REQUIRED_SERVICE_INSTANCE.addChildAPTypeDescriptor(TD_TYPE_NAME_EVENT_GROUP);
		
		return apTypeDescriptors;
	}

	public List<RequiredSomeipServiceInstance> getAllRequiredServiceInstance(GAUTOSAR rootElement) {
		final List<RequiredSomeipServiceInstance> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getReqiredServiceInstance(p));
		});
		
		return result;
	}
	
	public List<RequiredSomeipServiceInstance> getReqiredServiceInstance(GARPackage arPackage){
		List<RequiredSomeipServiceInstance> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof RequiredSomeipServiceInstance) {
				result.add((RequiredSomeipServiceInstance)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getReqiredServiceInstance(p));
			});
		}
		
		return result;
	}
}
