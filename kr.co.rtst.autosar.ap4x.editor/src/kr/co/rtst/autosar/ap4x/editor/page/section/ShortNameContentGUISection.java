package kr.co.rtst.autosar.ap4x.editor.page.section;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public abstract class ShortNameContentGUISection extends AbstractContentGUISection {

	protected Text textShortName;
//	protected Text textShortNameFragment;
	
	public ShortNameContentGUISection(AbstractAPEditorPage formPage, int style,
			String sectionName) {
		super(formPage, style, sectionName);
	}

	@Override
	public void initUIContents() {
		System.out.println("Section Detail Type:"+getInputDetail());
		if(getInputDetail() instanceof Referrable) {
			Referrable detailObject = (Referrable)getInputDetail();
			textShortName.setText(detailObject.getShortName());
//			textShortNameFragment.setText(APChildModelAccessAgent.getShortNameFregment(this, detailObject).getFragment());
		}
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		
		textShortName = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(EditorText.COMMON_ATTR_SHORT_NAME), EditorText.COMMON_ATTR_SHORT_NAME, AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
//		textShortNameFragment = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(EditorText.COMMON_ATTR_SHORT_NAME_FRAGMENT), EditorText.COMMON_ATTR_SHORT_NAME_FRAGMENT, AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textShortName.addModifyListener(e->{
			doTransactionalOperation(new IAPTransactionalOperation() {
				@Override
				public GARObject doProcess(GARObject model) throws Exception {
					if(model instanceof Referrable) {
						Referrable detailModel = (Referrable)model;
						detailModel.setShortName(textShortName.getText().trim());
						notifyShortNameChanged();
					}
					return model;
				}
			});
		});
		
//		textShortNameFragment.addModifyListener(e->{
//			doTransactionalOperation(new IAPTransactionalOperation() {
//				
//				@Override
//				public GARObject doProcess(GARObject model) throws Exception {
//					if(model instanceof Referrable) {
//						APChildModelAccessAgent.getShortNameFregment(ShortNameContentGUISection.this, (Referrable)model).setFragment(textShortNameFragment.getText());
//					}
//					return model;
//				}
//			});
//		});
		
	}
	
	protected void notifyShortNameChanged() {
		
	}
	
}
