package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.swcomponent.portinterface.ApplicationError;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * ApplicationError 타입 표현
 * @author thkim
 *
 */
public class ErrorSection extends ShortNameContentGUISection {

	private Text textErrorCode;
	
	public ErrorSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_APPLICATION_ERROR);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Applcation error");
		sectionPart.getSection().setDescription("Applcation error desc");
		
		textErrorCode = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Error code*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textErrorCode.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						ApplicationError input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setErrorCode(Integer.parseInt(textErrorCode.getText()));
							}catch(NumberFormatException e) {
								input.setErrorCode(0);
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
		ApplicationError input = getCastedInputDetail();
		if(input != null) {
			textErrorCode.setText(String.valueOf(input.getErrorCode()));
		}
	}
	
	private ApplicationError getCastedInputDetail() {
		if(getInputDetail() instanceof ApplicationError) {
			return (ApplicationError)getInputDetail();
		}
		return null;
	}

}
