package kr.co.rtst.autosar.ap4x.ide.views.provider.model;

import org.eclipse.jface.viewers.DecoratingLabelProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.jface.viewers.ILabelProvider;

public class AdaptiveAutosarModelDecoratingLabelProvider extends DecoratingLabelProvider implements ILabelProvider {

	public AdaptiveAutosarModelDecoratingLabelProvider() {
		super(new AdaptiveAutosarModelLabelProvider(), new AdaptiveAutosarModelDecorator());
	}

}
