package kr.co.rtst.autosar.ap4x.ide.views.provider.resource;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.viewers.IColorProvider;
import org.eclipse.jface.viewers.IFontProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Image;

import kr.co.rtst.autosar.ap4x.ide.consts.APIDEColor;
import kr.co.rtst.autosar.ap4x.ide.consts.APIDEFont;

public class AdaptiveAutosarResourceLebelProvider extends LabelProvider implements ILabelProvider, IFontProvider, IColorProvider {
	
	@Override
	public String getText(Object element) {
		if(element instanceof IProject) {
			return ((IProject) element).getName();
		}
		return null;
	}

	@Override
	public Image getImage(Object element) {
//		if(IAdaptiveAutosarProject.isAdaptiveAutosarTopFolder(element)) {
//			return IDEActivator.getDefault().getIdeImageRegistry().getImage("icons/"+IAdaptiveAutosarProject.getTopDirImageName(((IFolder)element).getName())+".png");
//		}
		return null;
	}

	@Override
	public Color getForeground(Object element) {
//		if(element instanceof IProject) {
//			return APIDEColor.ORANGE;
//		}
		return null;
	}

	@Override
	public Color getBackground(Object element) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Font getFont(Object element) {
//		if(element instanceof IProject) {
//			return APIDEFont.CHECK_TEXT;
//		}
		return null;
	}
	
	
	
}
