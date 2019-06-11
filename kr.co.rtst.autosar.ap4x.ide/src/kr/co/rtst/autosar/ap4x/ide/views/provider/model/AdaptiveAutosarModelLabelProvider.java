package kr.co.rtst.autosar.ap4x.ide.views.provider.model;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.graphics.Image;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.IAPVirtualElement;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;

public class AdaptiveAutosarModelLabelProvider extends LabelProvider implements ILabelProvider {
	
	private BasicExplorerLabelProvider apModelLabelProvider;
	
	public AdaptiveAutosarModelLabelProvider() {
		apModelLabelProvider = new BasicExplorerLabelProvider();
	}
	
	@Override
	public String getText(Object element) {
		if(element instanceof IAPVirtualElement) {
			return ((IAPVirtualElement) element).getName();
		}
		
		else if(element instanceof Referrable) {
			return ((Referrable) element).getShortName();
		}
		
		else if(element instanceof EObject) {
			return apModelLabelProvider.getText(element);
		}
		
//		else if(element instanceof BaseType) {
//			return ((BaseType)element).getShortName();
//		}else if(element instanceof AdaptiveApplicationSwComponentType) {
//			return AdaptiveAutosarProjectUtil.getLabel((AdaptiveApplicationSwComponentType)element);
//		}else if(element instanceof ServiceInterface) {
//			return ((ServiceInterface)element).getShortName();
//		}else if(element instanceof Machine) {
//			return ((Machine)element).getShortName();
//		}
//		
//		if(element instanceof GARPackage) {
//			System.out.println("---------------------------:"+((GARPackage)element).gGetShortName());
////			return ((GARPackage)element).gGetShortName();
//		}else if(element instanceof AdaptiveApplicationSwComponentType) {
//			((AdaptiveApplicationSwComponentType)element).gGetShortName();
//		}
		return null;
	}
	
	@Override
	public Image getImage(Object element) {
		if(element instanceof IAPVirtualElement) {
			return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage(((IAPVirtualElement) element).getIconPath());
		}
		
		if(element instanceof EObject) {
			return apModelLabelProvider.getImage(element);
		}
		
		/*else if(element instanceof BaseType) {
			return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/type/type.png");
		}else if(element instanceof AdaptiveApplicationSwComponentType) {
			return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/swc/swc.png");
		}else if(element instanceof ServiceInterface) {
			return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/service/service.png");
		}else if(element instanceof Machine) {
			return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/machine/machine.png");
		}*/
		return null;
	}

}
