package kr.co.rtst.autosar.ap4x.editor.page.section.types;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import kr.co.rtst.autosar.ap4x.core.model.manager.ApplicationDataTypesModelManager;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;

public class ApplicationDataTypeSection extends ShortNameContentGUISection {

	public ApplicationDataTypeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, ApplicationDataTypesModelManager.TYPE_NAME_APPLICATION_DATA_TYPE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Application Data Type");
		sectionPart.getSection().setDescription("Application Data Type desc");
		
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
	}

}
