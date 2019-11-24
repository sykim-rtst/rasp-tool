package kr.co.rtst.autosar.ap4x.ide.views.provider.model;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.ITreeContentProvider;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GARPackage;
import kr.co.rtst.autosar.ap4x.core.model.APSubVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.APTopVitualElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;

public class AdaptiveAutosarModelContentProvider implements ITreeContentProvider {

	@Override
	public Object[] getElements(Object inputElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof IProject && IAPProject.isAdaptiveAutosarProject(((IProject) parentElement))) {
			IAPProject apProject = APProjectManager.getInstance().getAPProject(((IProject) parentElement));
			if(apProject != null) {
				return apProject.getTopElements();
			}
		} else if(parentElement instanceof APTopVitualElement) {
			return ((APTopVitualElement) parentElement).getChildren();
		} else if(parentElement instanceof APSubVirtualElement) {
			
			switch(((IAPVirtualElement) parentElement).getName()) {
				case IAPVirtualElement.VE_NAME_SERVICE_INSTANCE:
					{
						IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.getProvidedServiceInstanceModelManager();
						IFile file = ((IAPVirtualElement) parentElement).getApProject().getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME);
						List<GARObject> resultList = modelManager.getChildren(file, IAPVirtualElement.VE_NAME_SERVICE_INS_PROVIDED_SOMEIP);
						
						modelManager = APModelManagerProvider.apINSTANCE.getRequiredServiceInstanceModelManager();
						resultList.addAll(modelManager.getChildren(file, IAPVirtualElement.VE_NAME_SERVICE_INS_REQUIRED_SOMEIP));
						
						GARObject[] result = new GARObject[resultList.size()];
						resultList.toArray(result);
						return result;
					}
				default:
					{
						IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((APSubVirtualElement) parentElement);
						if(modelManager != null) {
							IFile file = ((IAPVirtualElement) parentElement).getApProject().getProject().getFile(IAPProject.USER_DEFINED_ARXML_NAME);
							return modelManager.getChildren(file, ((IAPVirtualElement) parentElement).getName()).toArray();
						}
					}
					break;
			}
		} else if(parentElement instanceof GARObject) {
			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)parentElement);
//			System.out.println("--------------IAPModelManager--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
			if(modelManager != null) {
//				System.out.println("--------------=============--------------getChildren::parentElement:"+parentElement+", modelManager:"+modelManager);
				return modelManager.getChildren((GARObject)parentElement).toArray();
			}
		} 
		
		return null;
	}

	@Override
	public Object getParent(Object element) {
		if(element instanceof APTopVitualElement) {
			return ((IAPVirtualElement) element).getApProject().getProject();
		} else if(element instanceof APSubVirtualElement) {
			return ((APSubVirtualElement) element).getParent();
		} else if(element instanceof GARPackage) {
			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)element);
			if(modelManager != null) {
				return modelManager.getParent((GARObject)element);
			}
		}
		
		
		
//		else if(element instanceof GARPackage) {
//			return null;
//		} else if(element instanceof GARObject) {
//			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)element);
//			if(modelManager != null) {
//				System.out.println("getParent::element:"+element+", modelManager:"+modelManager);
//				return modelManager.getParent((GARObject)element);
//			}
//		}
		return null;
	}

	@Override
	public boolean hasChildren(Object element) {
		if(element instanceof IProject && IAPProject.isAdaptiveAutosarProject(((IProject) element))) {
			IAPProject apProject = APProjectManager.getInstance().getAPProject(((IProject) element));
			if(apProject != null) {
				return true;
			}
		} else if(element instanceof APTopVitualElement) {
			return true;
		} else if(element instanceof APSubVirtualElement) {
			// TODO 자식이 있는지 확인되어야 함
			return true;
		} 
		
		else if(element instanceof GARPackage) {
			return true;
		} else if(element instanceof GARObject) {
			IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)element);
			if(modelManager != null) {
				System.out.println("hasChildren::element:"+element+", modelManager:"+modelManager);
				return modelManager.hasChildren((GARObject)element);
			}
		}
		return false;
	}
	
	
	
	
//	@Override
//	public Object[] getChildren(Object parentElement) {
//		if(parentElement instanceof IFile && ((IFile) parentElement).getFileExtension().equals("aaswc")) {
//			GAUTOSAR autosar = AutosarCoreModelRegistry.getInstance().getCoreModel(((IFile) parentElement));
//			if(autosar != null) {
//				return autosar.gGetArPackages().toArray();
//			}
//		} else if(parentElement instanceof GARPackage) {
//			return ((GARPackage)parentElement).gGetElements().toArray();
//		} 
//		return null;
//	}
//
//	@Override
//	public Object getParent(Object element) {
//		/*if(element instanceof IFile && ((IFile) element).getFileExtension().equals("aaswc")) {
//			
//		} else if(element instanceof GAUTOSAR) {
//			
//		} else*/ if(element instanceof GARPackage) {
////			return ((GARPackage)element).get;
//		} else if(element instanceof GPackageableElement) {
//			return ((GPackageableElement)element).gGetARPackage();
//		}
//		return null;
//	}
//
//	@Override
//	public boolean hasChildren(Object element) {
//		if(element instanceof IFile && ((IFile) element).getFileExtension().equals("aaswc")) {
//			GAUTOSAR autosar = AutosarCoreModelRegistry.getInstance().getCoreModel(((IFile) element));
//			if(autosar != null) {
//				return !autosar.gGetArPackages().isEmpty();
//			}
//		}/* else if(element instanceof GAUTOSAR) {
//			return !((GAUTOSAR)element).gGetArPackages().isEmpty();
//		}*/ else if(element instanceof GARPackage) {
//			return !((GARPackage)element).gGetElements().isEmpty();
//		}
//		return false;
//	}

}
