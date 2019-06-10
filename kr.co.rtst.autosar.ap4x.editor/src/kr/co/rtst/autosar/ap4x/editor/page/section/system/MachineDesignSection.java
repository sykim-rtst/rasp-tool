package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.systemdesign.MachineDesign;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineDesignsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class MachineDesignSection extends ShortNameContentGUISection {

	private Text textPnResetTimer;
	
	public MachineDesignSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineDesignsModelManager.TYPE_NAME_MACHINE_DESIGN);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Machine design");
		sectionPart.getSection().setDescription("Machine design desc");
		
		textPnResetTimer = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "PN reset timer: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textPnResetTimer.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						MachineDesign input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setPnResetTimer(Double.parseDouble(textPnResetTimer.getText()));
							}catch(NumberFormatException e) {
								input.setPnResetTimer(0d);
							}
						}
						return model;
					}
				});
			}
		});
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		MachineDesign input = getCastedInputDetail();
		if(input != null) {
			textPnResetTimer.setText(String.valueOf(input.getPnResetTimer().longValue()));
		}
	}
	
	private MachineDesign getCastedInputDetail() {
		if(getInputDetail() instanceof MachineDesign) {
			return (MachineDesign)getInputDetail();
		}
		return null;
	}

}
