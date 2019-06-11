package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.swcomponent.portinterface.ClientServerOperation;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * ClientServerOperation 타입
 * @author thkim
 *
 */
public class MethodSection extends ShortNameContentGUISection implements SelectionListener {

	private Combo comboFireAndForget;
	
	public MethodSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_CLIENT_SERVER_OPERATION);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Client server operation");
		sectionPart.getSection().setDescription("Client server operation desc");
		
		comboFireAndForget = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Fire and forget*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE, this);
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		ClientServerOperation input = getCastedInputDetail();
		if(input != null) {
			comboFireAndForget.select(input.isSetFireAndForget()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboFireAndForget)) {
			ClientServerOperation input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setFireAndForget(Boolean.parseBoolean(comboFireAndForget.getText()));
						return model;
					}
				});
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	private ClientServerOperation getCastedInputDetail() {
		if(getInputDetail() instanceof ClientServerOperation) {
			return (ClientServerOperation)getInputDetail();
		}
		return null;
	}

}
