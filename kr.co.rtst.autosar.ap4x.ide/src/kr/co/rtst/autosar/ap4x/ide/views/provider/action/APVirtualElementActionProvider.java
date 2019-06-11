package kr.co.rtst.autosar.ap4x.ide.views.provider.action;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.actions.ActionContext;
import org.eclipse.ui.navigator.CommonActionProvider;

import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;

public class APVirtualElementActionProvider extends CommonActionProvider {
	
	private IAPVirtualElement selectedElement;
	
	public APVirtualElementActionProvider() {
	}
	
	@Override
	public void setContext(ActionContext context) {
		super.setContext(context);
//		System.out.println("APRealElementActionProvider::setContext---->>"+context.getInput());
//		System.out.println("APRealElementActionProvider::setContext---->>"+context.getSelection());
//		System.out.println("APRealElementActionProvider::setContext---->>"+context.getSelection().isEmpty());
		if(context.getSelection() instanceof IStructuredSelection) {
			IStructuredSelection s = (IStructuredSelection)context.getSelection();
			if(s.size() == 1 && s.getFirstElement() instanceof IAPVirtualElement) {
				selectedElement = (IAPVirtualElement)s.getFirstElement();
			}
		}
	}

	
	@Override
	public void fillContextMenu(IMenuManager menu) {
		super.fillContextMenu(menu);
		switch(selectedElement.getName()) {
		// top
		case IAPVirtualElement.VE_NAME_TYPE:
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[0], "Add Implementation Data Type..."));
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[1], "Add Application Data Type..."));
			break;
		case IAPVirtualElement.VE_NAME_APPLICATION:
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[0], "Add Adaptive Application SW Component Type..."));
			break;
		case IAPVirtualElement.VE_NAME_SERVICE:
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[0], "Add Service Interface..."));
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[1], "Add Service Interface Deployment..."));
			break;
		case IAPVirtualElement.VE_NAME_SYSTEM:
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[0], "Add Machine Design..."));
			menu.add(new CreateNewPackagableElementAction(selectedElement.getChildren()[1], "Add Machine..."));
			break;
		// sub
		case IAPVirtualElement.VE_NAME_TYPE_IMPL:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Implementation Data Type... Application Data Type..."));
			break;
		case IAPVirtualElement.VE_NAME_TYPE_APP:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Application Data Type..."));
			break;
		case IAPVirtualElement.VE_NAME_APPLICATION_SWC:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Adaptive Application SW Component Type..."));
			break;
		case IAPVirtualElement.VE_NAME_APPLICATION_PLATFORM:
			break;
		case IAPVirtualElement.VE_NAME_SERVICE_INTERFACE:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Service Interface..."));
			break;
		case IAPVirtualElement.VE_NAME_SERVICE_DEPLOYMENT:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Service Interface Deployment..."));
			break;
		case IAPVirtualElement.VE_NAME_MACHINE_DESIGN:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Machine Design..."));
			break;
		case IAPVirtualElement.VE_NAME_MACHINE:
			menu.add(new CreateNewPackagableElementAction(selectedElement, "Add Machine..."));
			break;
		}
	}
}
