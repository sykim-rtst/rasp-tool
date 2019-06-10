package kr.co.rtst.autosar.ap4x.editor.page.section.types;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import kr.co.rtst.autosar.ap4x.core.model.manager.ImplementationDataTypesModelManager;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;

public class ImplementationDataTypeSection extends ShortNameContentGUISection {

	public ImplementationDataTypeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ImplementationDataTypesModelManager.TYPE_NAME_IMPLEMENTATION_DATA_TYPE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Implementation Data Type");
		sectionPart.getSection().setDescription("Implementation Data Type desc");
		
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
	}

}
