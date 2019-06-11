package kr.co.rtst.autosar.ap4x.ide.views.provider.resource;

import org.artop.aal.common.metamodel.AutosarReleaseDescriptor;
import org.artop.aal.workspace.preferences.IAutosarWorkspacePreferences;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IColorDecorator;
import org.eclipse.jface.viewers.IDecoration;
import org.eclipse.jface.viewers.IFontDecorator;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IDecoratorManager;
import org.eclipse.ui.internal.decorators.DecoratorManager;

import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.ide.consts.APIDEColor;
import kr.co.rtst.autosar.ap4x.ide.consts.APIDEFont;

public class AdaptiveAutosarResourceDecorator extends /*DecoratingStyledCellLabelProvider*/LabelProvider implements /*ILightweightLabelDecorator,*/ ILabelDecorator, IFontDecorator, IColorDecorator {
	
	@Override
	public Image decorateImage(Image image, Object element) {
		return null;
	}

	@Override
	public String decorateText(String text, Object element) {
		if(element instanceof IProject) {
			IAPProject apProject = APProjectManager.getInstance().getAPProject((IProject)element);
			AutosarReleaseDescriptor releaseDescription = apProject.getAutosarReleaseDescriptor();
			if(releaseDescription != null) {
				return text + " ["+releaseDescription.getAutosarVersionData().getName()+"]";
			}
		}
		return null;
	}

	@Override
	public Font decorateFont(Object element) {
//		if(element instanceof IProject) {
//			return APIDEFont.SMALL_TEXT;
//		}
		return null;
	}

	@Override
	public Color decorateForeground(Object element) {
//		if(element instanceof IProject) {
//			System.out.println("------------------------decorateForeground");
//			return APIDEColor.GREEN;
//		}
		return null;
	}

	@Override
	public Color decorateBackground(Object element) {
		return null;
	}

//	@Override
//	public void decorate(Object element, IDecoration decoration) {
//		System.out.println("aaaaaaaaaaaaaaaaaaa");
//		if(element instanceof IProject) {
//			decoration.addSuffix("[AP Project]");
//			decoration.setForegroundColor(APIDEColor.GREEN);
//			decoration.setFont(APIDEFont.SMALL_TEXT);
//		}
//	}
	
}
