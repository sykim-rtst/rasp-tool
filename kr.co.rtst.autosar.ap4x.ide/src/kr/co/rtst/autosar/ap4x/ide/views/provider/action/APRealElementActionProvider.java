package kr.co.rtst.autosar.ap4x.ide.views.provider.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.sphinx.emf.explorer.actions.filters.ICommandParameterFilter;
import org.eclipse.sphinx.emf.explorer.actions.providers.BasicModelEditActionProvider;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;
import kr.co.rtst.autosar.ap4x.ide.action.ElementModifyActionWrapper;
import kr.co.rtst.autosar.ap4x.ide.action.IAPActionContainer;
import kr.co.rtst.autosar.ap4x.ide.action.NotSupportedAPActionException;

public class APRealElementActionProvider extends BasicModelEditActionProvider implements IAPActionContainer {

	@Override
	public void fillContextMenu(IMenuManager menuManager) {
		
		updateActions(getContext().getSelection());
		System.out.println("createChildActions:"+createChildActions.size());
		System.out.println("createChildSubmenuActions:"+createChildSubmenuActions.size());
		System.out.println("createSiblingActions:"+createSiblingActions.size());
		System.out.println("createSiblingSubmenuActions:"+createSiblingSubmenuActions.size());
		
		Iterator<Map.Entry<String, Collection<IAction>>> entrise = createChildSubmenuActions.entrySet().iterator();
		while(entrise.hasNext()) {
			Map.Entry<String, Collection<IAction>> entry = entrise.next();
			Collection<IAction> actrions = entry.getValue();
//			System.out.println("::::actrions:::"+actrions.getClass());
			for (IAction iAction : actrions) {
//				System.out.println("---------------NAVIGATOR::reateChildSubmenuActions>>iActoin-TEXT:"+iAction.getText()+", ID:"+iAction.getId());
				try {
					menuManager.add(new ElementModifyActionWrapper(
							APProjectManager.getInstance().getAPProject( (GARObject) ((IStructuredSelection)(getContext().getSelection())).getFirstElement()), 
							entry.getKey(), iAction));
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}
		}
		
		
		populateAPActions(
				APProjectManager.getInstance().getAPProject( (GARObject) ((IStructuredSelection)(getContext().getSelection())).getFirstElement()), 
				menuManager, (IStructuredSelection)getContext().getSelection());
		
		menuManager.add(new Separator());
        try {
			menuManager.add(new ElementModifyActionWrapper(
					APProjectManager.getInstance().getAPProject( (GARObject) ((IStructuredSelection)(getContext().getSelection())).getFirstElement()), 
					null, deleteAction));
		} catch (NotSupportedAPActionException e) {
			System.err.println(e.getMessage());
		}
	}
	 
	@Override
	protected ICommandParameterFilter getNewChildOrSiblingItemFilter() {
		// TODO Auto-generated method stub
		return new ICommandParameterFilter() {
			
			@Override
			public boolean accept(IStructuredSelection selection, CommandParameter descriptor) {
//				System.out.println("--------------------accept");
				if(selection.getFirstElement() instanceof GARObject && descriptor.getEValue() instanceof GARObject) {
					IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)selection.getFirstElement());
					if(modelManager != null) {
//						System.out.println(":::::"+modelManager+", :::::"+selection.getFirstElement()+", ::::::::"+modelManager.isNavigatableSubElement((GARObject)selection.getFirstElement()));
//						if(modelManager.isNavigatableSubElement((GARObject)descriptor.getEValue())) {
//							if(modelManager instanceof MachineModelManager && descriptor.getEValue() instanceof ModeDeclarationGroupPrototype) {
//								// 특별히 부모의 FunctionGroup에 소속된 자식만 인정된다.
//								EObject parent = descriptor.getEValue().eContainer();
//								if(parent instanceof Machine) {
//									if(!(((Machine) parent).getFunctionGroups()).contains(descriptor.getEValue())){
//										return false;
//									}
//								}
//							}
//							return true;
//						}
//						if(modelManager.isNavigatableSubElement((GARObject)descriptor.getEValue())) {
//							System.out.println("OK ACTION:"+((GARObject)descriptor.getEValue()));
//						}
						return modelManager.isNavigatableSubElement((GARObject)descriptor.getEValue()/*(GARObject)selection.getFirstElement()*/);
					}
				}
				
////				System.out.println("ICommandParameterFilter::accept()->"+descriptor.getEValue());
//				if(selection.getFirstElement() instanceof AdaptiveApplicationSwComponentType) {
//					if(descriptor.getEValue() instanceof ARObject && APModelManagerProvider.INSTANCE.getApplicationModelManager().isNavigatableSubElement((ARObject)descriptor.getEValue())) {
//						return true;
//					}
////					if(descriptor.getEValue() instanceof PPortPrototype ||
////							descriptor.getEValue() instanceof RPortPrototype ||
////							descriptor.getEValue() instanceof PortGroup ||
////							descriptor.getEValue() instanceof AdaptiveSwcInternalBehavior) {
////						return true;
////					}
//				}else if(selection.getFirstElement() instanceof ServiceInterface) {
//					if(descriptor.getEValue() instanceof ARObject && APModelManagerProvider.INSTANCE.getServiceModelManager().isNavigatableSubElement((ARObject)descriptor.getEValue())) {
//						return true;
//					}
//				}
//				
////				if(apVirtualElement != null) {
////					if(apVirtualElement.getPackageFullName().equals(apVirtualElement.getApProject().getApplications().getPackageFullName())) {
////						if(descriptor.getEValue() instanceof AdaptiveApplicationSwComponentType) {
////							return true;
////						}
////					}else if(apVirtualElement.getPackageFullName().equals(apVirtualElement.getApProject().getServices().getPackageFullName())) {
////						if(descriptor.getEValue() instanceof ServiceInterface) {
////							return true;
////						}
////					}else if(apVirtualElement.getPackageFullName().equals(apVirtualElement.getApProject().getMachines().getPackageFullName())) {
////						if(descriptor.getEValue() instanceof Machine) {
////							return true;
////						}
////					}else if(apVirtualElement.getPackageFullName().equals(apVirtualElement.getApProject().getTypes().getPackageFullName())) {
////						if(descriptor.getEValue() instanceof ImplementationDataType ||
////								descriptor.getEValue() instanceof ApplicationDataType) {
////							return true;
////						}
////					}
////					return false;
////				}
				return false;
			}
		};
	}
}
