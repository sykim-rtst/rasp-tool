package kr.co.rtst.autosar.ap4x.core.model.manager;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.emf.common.util.EList;

import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.ProvidedSomeipServiceInstance;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipProvidedEventGroup;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinstancedeployment.SomeipSdServerServiceInstanceConfig;
import autosar40.genericstructure.generaltemplateclasses.arpackage.ARPackage;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import gautosar.ggenericstructure.ginfrastructure.GPackageableElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.desc.APTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;

public class ProvidedServiceInstanceModelManager extends AbstractAPModelManager{

	//#################################################################################################
	// Provided Service Instance 의 하위에 오는 타입들
	//#################################################################################################
	public static final String TYPE_NAME_PROVIDED_SERVICE_INSTANCE = "ProvidedServiceInstance";
	public static final String TYPE_NAME_SD_SERVER_SERVICE_INSTANCE_CONFIG = "SdServerServiceInstanceConfig";
	public static final String TYPE_NAME_EVENT_GROUP = "ProvidedEventGroup";
	
	public ProvidedServiceInstanceModelManager() {
		// TODO Auto-generated constructor stub
	}
	

	
	@Override
	public List<GARObject> getChildren(IFile arxmlFile, String virtualElementName) {
		GAUTOSAR root = load(arxmlFile);
		if(root != null) {
			switch(virtualElementName) {
				case IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP:{
					return new ArrayList<>(getAllProvidedServiceInstance(root));
				}
				case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:{
					// TODO 
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
				case TYPE_NAME_PROVIDED_SERVICE_INSTANCE:{
					ProvidedSomeipServiceInstance model = (ProvidedSomeipServiceInstance)element;
					if(model.getSdServerConfig() != null)
					{
						children.add(model.getSdServerConfig());
					}
					children.addAll(model.getProvidedEventGroups());
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
		
		IAPTypeDescriptor TD_TYPE_NAME_PROVIDED_SERVICE_INSTANCE = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP, TYPE_NAME_PROVIDED_SERVICE_INSTANCE, ProvidedSomeipServiceInstance.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_SERVICE_INSTANCE_CONFIG = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP, TYPE_NAME_SD_SERVER_SERVICE_INSTANCE_CONFIG, SomeipSdServerServiceInstanceConfig.class, true, true);
		IAPTypeDescriptor TD_TYPE_NAME_EVENT_GROUP = new APTypeDescriptor(IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP, TYPE_NAME_EVENT_GROUP, SomeipProvidedEventGroup.class, true, true);;
		
		apTypeDescriptors.add(TD_TYPE_NAME_PROVIDED_SERVICE_INSTANCE);
		apTypeDescriptors.add(TD_TYPE_NAME_SERVICE_INSTANCE_CONFIG);
		apTypeDescriptors.add(TD_TYPE_NAME_EVENT_GROUP);
		
		TD_TYPE_NAME_PROVIDED_SERVICE_INSTANCE.addChildAPTypeDescriptor(TD_TYPE_NAME_SERVICE_INSTANCE_CONFIG);
		TD_TYPE_NAME_PROVIDED_SERVICE_INSTANCE.addChildAPTypeDescriptor(TD_TYPE_NAME_EVENT_GROUP);
		
		
		
		return apTypeDescriptors;
	}

	public List<ProvidedSomeipServiceInstance> getAllProvidedServiceInstance(GAUTOSAR rootElement) {
		final List<ProvidedSomeipServiceInstance> result = new ArrayList<>();
		
		EList<GARPackage> arPackages = rootElement.gGetArPackages();
		arPackages.stream().forEach(p->{
			result.addAll(getProvidedServiceInstance(p));
		});
		
		return result;
	}
	
	public List<ProvidedSomeipServiceInstance> getProvidedServiceInstance(GARPackage arPackage){
		List<ProvidedSomeipServiceInstance> result = new ArrayList<>();
		
		EList<GPackageableElement> elements = arPackage.gGetElements();
		for (GPackageableElement e: elements) {
			if(e instanceof ProvidedSomeipServiceInstance) {
				result.add((ProvidedSomeipServiceInstance)e);
			}
		}
		if(arPackage instanceof ARPackage) {
			EList<ARPackage> arPackages = ((ARPackage)arPackage).getArPackages();
			arPackages.stream().forEach(p->{
				result.addAll(getProvidedServiceInstance(p));
			});
		}
		
		return result;
	}
}
