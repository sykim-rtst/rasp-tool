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

import autosar40.adaptiveplatform.applicationdesign.portinterface.Field;
import autosar40.swcomponent.datatype.datatypes.AutosarDataType;
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

public class FieldSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textType;
	private Button buttonAddType;
	private Button buttonEraseType;
	private Combo comboGetter;
	private Combo comboSetter;
	private Combo comboNotifier;
	
	public FieldSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, InterfacesModelManager.TYPE_NAME_FIELD);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Field");
		sectionPart.getSection().setDescription("Field desc");
		
		APSectionUIFactory.createLabel(parent, "Init value: -Undefined-", AbstractContentGUISection.CLIENT_CONTENT_COLUMN);
		
		textType = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Type*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseType = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboGetter = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Getter*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE, this);
		comboSetter = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Setter*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE, this);
		comboNotifier = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Notifier*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE, this);
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final Field field = getCastedInputDetail();
		if(field != null) {
			setTypeText(field);
			comboGetter.select(field.isSetHasGetter()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
			comboSetter.select(field.isSetHasSetter()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
			comboNotifier.select(field.isSetHasNotifier()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddType)) {
			IAPProject apProject = getAPProject();
			if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
				final AutosarDataType selectedType = ReferenceChoiceDelegator.choiceSingleAutosarDataType(buttonAddType.getShell(), apProject);
				if(selectedType != null) {
					final Field field = getCastedInputDetail();				
					if(field != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								field.setType(selectedType);
								return model;
							}
						});
						
						setTypeText(field);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseType)) {
			final Field input = getCastedInputDetail();
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
		}else if(e.getSource().equals(comboGetter)) {
			final Field field = getCastedInputDetail();				
			if(field != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						field.setHasGetter(Boolean.parseBoolean(comboGetter.getText()));
						return model;
					}
				});
			}
		}else if(e.getSource().equals(comboSetter)) {
			final Field field = getCastedInputDetail();				
			if(field != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						field.setHasSetter(Boolean.parseBoolean(comboSetter.getText()));
						return model;
					}
				});
			}
		}else if(e.getSource().equals(comboNotifier)) {
			final Field field = getCastedInputDetail();				
			if(field != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						field.setHasNotifier(Boolean.parseBoolean(comboNotifier.getText()));
						return model;
					}
				});
			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	private Field getCastedInputDetail() {
		if(getInputDetail() instanceof Field) {
			return (Field)getInputDetail();
		}
		return null;
	}
	
	private void setTypeText(Field model) {
		if(model.getType() != null) {
			textType.setText(model.getType().getShortName());
		}else {
			textType.setText("");
		}
	}

}
