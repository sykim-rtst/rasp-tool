package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;

public class PortGroupSection extends ShortNameContentGUISection {

	public PortGroupSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_PORT_GROUP);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Port group");
		sectionPart.getSection().setDescription("Port group desc");
		
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
	}

}
