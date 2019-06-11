package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;

public class AdaptiveSwcInternalBehaviorSection  extends ShortNameContentGUISection {
	
	public AdaptiveSwcInternalBehaviorSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Adaptive swc internal behavior");
		sectionPart.getSection().setDescription("Adaptive swc internal behavior desc");
	}
	
	@Override
	public void initUIContents() {
		// TODO Auto-generated method stub
		super.initUIContents();
	}
	
}
