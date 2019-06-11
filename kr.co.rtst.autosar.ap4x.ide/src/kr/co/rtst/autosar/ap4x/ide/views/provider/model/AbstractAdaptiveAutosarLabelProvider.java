package kr.co.rtst.autosar.ap4x.ide.views.provider.model;

import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Image;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import kr.co.rtst.autosar.ap4x.ide.AP4xIDEActivator;

abstract public class AbstractAdaptiveAutosarLabelProvider extends LabelProvider implements ILabelProvider {
	
	@Override
	public String getText(Object element) {
		if(element instanceof Referrable) {
			return ((Referrable ) element).getShortName();
		}
		return null;
	}
	
	@Override
	public Image getImage(Object element) {
		return AP4xIDEActivator.getDefault().getIdeImageRegistry().getImage("icons/default_object.gif");
	}
	
}
