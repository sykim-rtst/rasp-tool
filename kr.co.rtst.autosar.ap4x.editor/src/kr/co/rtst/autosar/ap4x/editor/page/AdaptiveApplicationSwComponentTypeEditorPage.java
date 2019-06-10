package kr.co.rtst.autosar.ap4x.editor.page;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.Section;

import kr.co.rtst.autosar.ap4x.core.model.desc.IAPTypeDescriptor;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.AdaptiveApplicationSwComponentTypeSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.AdaptiveSwcInternalBehaviorSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ClientComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ClientComSpecTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.PPortPrototypeSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.PersistencyDataProvidedComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.PersistencyDataRequiredComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.PortGroupSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.RPortPrototypeSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ReceiverComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ReceiverComSpecTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.SenderComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.SenderComSpecTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ServerComSpecSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.applications.ServerComSpecTableSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class AdaptiveApplicationSwComponentTypeEditorPage extends AbstractAPEditorPage {
	
	public AdaptiveApplicationSwComponentTypeEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_ADAPTIVE_APPLICATION_SW_COMPONENT_TYPE);
	}
	
//	@Override
//	protected Object getPageInputFromEditor() {
//		// TODO Auto-generated method stub
//		return getAPFormEditor().getAPFormEditorInput().getDetailObject();
//	}
	
	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getSwComponentsModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case SwComponentsModelManager.TYPE_NAME_CLIENT_COM_SPEC:
			createDetailClientComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_RECEIVER_COM_SPEC:
			createDetailReceiverComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_PERSISTENCY_DATA_REQUIRED_COM_SPEC:
			createDetailPersistencyDataRequiredComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_SENDER_COM_SPEC:
			createDetailSenderComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_SERVER_COM_SPEC:
			createDetailServerComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_PERSISTENCY_DATA_PROVIDED_COM_SPEC:
			createDetailPersistencyDataProvidedComSpec(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE:
			createDetailAdaptiveApplicationSwComponentType(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_PPORT_PROTOTYPE:
			createDetailPPortPrototype(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_RPORT_PROTOTYPE:
			createDetailRPortPrototype(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_PORT_GROUP:
			createDetailPortGroup(control, managedForm);
			break;
		case SwComponentsModelManager.TYPE_NAME_ADAPTIVE_SWC_INTERNAL_BEHAVIOR:
			createDetailInternalBehavior(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createDetailClientComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ClientComSpecSection sectionGUI = new ClientComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		ClientComSpecTableSection tableSectionGUI = new ClientComSpecTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailReceiverComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ReceiverComSpecSection sectionGUI = new ReceiverComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		ReceiverComSpecTableSection tableSectionGUI = new ReceiverComSpecTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailPersistencyDataRequiredComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		PersistencyDataRequiredComSpecSection sectionGUI = new PersistencyDataRequiredComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSenderComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SenderComSpecSection sectionGUI = new SenderComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		SenderComSpecTableSection tableSectionGUI = new SenderComSpecTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailServerComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ServerComSpecSection sectionGUI = new ServerComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
//		return new SectionPart(parent, toolkit, Section.TWISTIE|Section.TITLE_BAR|Section.NO_TITLE_FOCUS_BOX|Section.DESCRIPTION|Section.COMPACT|Section.SHORT_TITLE_BAR|Section.FOCUS_TITLE|Section.CLIENT_INDENT/*ExpandableComposite.TITLE_BAR | Section.DESCRIPTION | ExpandableComposite.TWISTIE*/);
		ServerComSpecTableSection tableSectionGUI = new ServerComSpecTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailPersistencyDataProvidedComSpec(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		PersistencyDataProvidedComSpecSection sectionGUI = new PersistencyDataProvidedComSpecSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		parent.setData(sectionGUI.getSectionTypeName());
		addSection(sectionGUI);
	}
	
	private void createDetailPortGroup(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		PortGroupSection sectionGUI = new PortGroupSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailAdaptiveApplicationSwComponentType(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		AdaptiveApplicationSwComponentTypeSection sectionGUI = new AdaptiveApplicationSwComponentTypeSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailInternalBehavior(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		AdaptiveSwcInternalBehaviorSection sectionGUI = new AdaptiveSwcInternalBehaviorSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailRPortPrototype(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		RPortPrototypeSection sectionGUI = new RPortPrototypeSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailPPortPrototype(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		PPortPrototypeSection sectionGUI = new PPortPrototypeSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}

}
