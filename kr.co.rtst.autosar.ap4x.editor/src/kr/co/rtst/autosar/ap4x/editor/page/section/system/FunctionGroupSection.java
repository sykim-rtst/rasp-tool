package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.commonstructure.modedeclaration.ModeDeclarationGroupPrototype;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;

/**
 * ModeDeclarationGroupPrototype
 * @author thkim
 *
 */
public class FunctionGroupSection extends ShortNameContentGUISection {

	public FunctionGroupSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineModelManager.TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Function Group");
		sectionPart.getSection().setDescription("Function Group desc");
	}

	
	@Override
	public void initUIContents() {
		super.initUIContents();
		ModeDeclarationGroupPrototype input = getCastedInputDetail();
		if(input != null) {
//			textPnResetTimer.setText(String.valueOf(input.getPnResetTimer().longValue()));
		}
	}
	
	private ModeDeclarationGroupPrototype getCastedInputDetail() {
		if(getInputDetail() instanceof ModeDeclarationGroupPrototype) {
			return (ModeDeclarationGroupPrototype)getInputDetail();
		}
		return null;
	}
}
