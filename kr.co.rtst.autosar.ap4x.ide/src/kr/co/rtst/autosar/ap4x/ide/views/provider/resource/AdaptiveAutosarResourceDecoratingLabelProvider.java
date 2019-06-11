package kr.co.rtst.autosar.ap4x.ide.views.provider.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.internal.decorators.DecorationBuilder;

import kr.co.rtst.autosar.ap4x.ide.consts.APIDEColor;

public class AdaptiveAutosarResourceDecoratingLabelProvider extends DecoratingLabelProvider implements ILabelProvider {

	public AdaptiveAutosarResourceDecoratingLabelProvider() {
		super(new AdaptiveAutosarResourceLebelProvider(), new AdaptiveAutosarResourceDecorator());
	}
	
//	@Override
//	public Color getForeground(Object element) {
//		if(element instanceof IProject) {
//			return APIDEColor.BLUE;
//		}
//		return super.getForeground(element);
//	}
	
}
