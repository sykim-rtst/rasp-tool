package kr.co.rtst.autosar.ap4x.editor.page.section.services;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.genericstructure.generaltemplateclasses.primitivetypes.ArgumentDirectionEnum;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
import autosar40.swcomponent.portinterface.ArgumentDataPrototype;
import autosar40.swcomponent.portinterface.ServerArgumentImplPolicyEnum;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.InterfacesModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * ArgumentDataPrototype
 * @author thkim
 *
 */
public class ArgumentSection extends ShortNameContentGUISection implements SelectionListener{

	private Text textType;
	private Button buttonAddType;
	private Button buttonEraseType;
	private Combo comboDirection;
	private Combo comboServerArgumentImplPolicy;
	
	public ArgumentSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_ARGUMENT_DATA_PROTOTYPE);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Argument data prototype");
		sectionPart.getSection().setDescription("Argument data prototype desc");
		
		textType = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Type*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboDirection = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Direction*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "in", "inout", "out");
		comboServerArgumentImplPolicy = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Server argument impl policy: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "useArgumentType", "useArrayBaseType", "useVoid");
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final ArgumentDataPrototype input = getCastedInputDetail();
		if(input != null) {
			setTypeText(input);
			
			if(input.getDirection() != null) {
				if(input.getDirection().equals(ArgumentDirectionEnum.IN)) {
					comboDirection.select(0);
				}else if(input.getDirection().equals(ArgumentDirectionEnum.INOUT)) {
					comboDirection.select(1);
				}else if(input.getDirection().equals(ArgumentDirectionEnum.OUT)) {
					comboDirection.select(2);
				}
			}
			if(input.getServerArgumentImplPolicy() != null) {
				if(input.getServerArgumentImplPolicy().equals(ServerArgumentImplPolicyEnum.USE_ARGUMENT_TYPE)) {
					comboServerArgumentImplPolicy.select(0);
				}else if(input.getServerArgumentImplPolicy().equals(ServerArgumentImplPolicyEnum.USE_ARRAY_BASE_TYPE)) {
					comboServerArgumentImplPolicy.select(1);
				}else if(input.getServerArgumentImplPolicy().equals(ServerArgumentImplPolicyEnum.USE_VOID)) {
					comboServerArgumentImplPolicy.select(2);
				}
			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddType)) {
			IAPProject apProject = getAPProject();
			if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
				final AutosarDataType selectedType = ReferenceChoiceDelegator.choiceSingleAutosarDataType(buttonAddType.getShell(), apProject);
				if(selectedType != null) {
					final ArgumentDataPrototype input = getCastedInputDetail();
					if(input != null) {
						
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setType(selectedType);
								return model;
							}
						});
						setTypeText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseType)) {
			final ArgumentDataPrototype input = getCastedInputDetail();
			if(input != null) {
				
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setType(null);
						return model;
					}
				});
				
				setTypeText(input);
			}
		} else if(e.getSource().equals(comboDirection)) {
			final ArgumentDataPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboDirection.getSelectionIndex()) {
						case 0:input.setDirection(ArgumentDirectionEnum.IN);break;
						case 1:input.setDirection(ArgumentDirectionEnum.INOUT);break;
						case 2:input.setDirection(ArgumentDirectionEnum.OUT);break;
						}
						return model;
					}
				});
			}
		}else if(e.getSource().equals(comboServerArgumentImplPolicy)) {
			final ArgumentDataPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboServerArgumentImplPolicy.getSelectionIndex()) {
						case 0:input.setServerArgumentImplPolicy(ServerArgumentImplPolicyEnum.USE_ARGUMENT_TYPE);break;
						case 1:input.setServerArgumentImplPolicy(ServerArgumentImplPolicyEnum.USE_ARRAY_BASE_TYPE);break;
						case 2:input.setServerArgumentImplPolicy(ServerArgumentImplPolicyEnum.USE_VOID);break;
						}
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
	
	private ArgumentDataPrototype getCastedInputDetail() {
		if(getInputDetail() instanceof ArgumentDataPrototype) {
			return (ArgumentDataPrototype)getInputDetail();
		}
		return null;
	}
	
	private void setTypeText(ArgumentDataPrototype model) {
		if(model.getType() != null) {
			textType.setText(model.getType().getShortName());
		}else {
			textType.setText("");
		}
	}

}
