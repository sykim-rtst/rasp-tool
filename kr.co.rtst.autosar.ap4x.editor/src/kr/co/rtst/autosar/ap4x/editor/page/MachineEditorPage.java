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
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.FunctionGroupSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.FunctionGroupTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.MachineSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.MachineTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.ProcessSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.ProcessTableAppModeSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.ProcessTableStartupConfigSection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class MachineEditorPage  extends AbstractAPEditorPage {
	
	public MachineEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_MACHINE);
	}
	
	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getMachinesModelManager().getAPTypeDescriptors()) {
			controlProviderMap.put(typeDesc.getTypeName(), createDetail(parent, managedForm, typeDesc.getTypeName()));
		}
		return controlProviderMap;
	}
	
	private Control createDetail(Composite parent, IManagedForm managedForm, String typeName) {
//		public static final String TYPE_NAME_COMMUNICATION_CONNECTOR = "CommunicationConnector";
//		public static final String TYPE_NAME_SERVICE_DISCOVERY_CONFIGURATION = "ServiceDiscoveryConfiguration";
//
//		public static final String TYPE_NAME_MACHINE_DESIGN = "MachineDesign";
		Composite control = managedForm.getToolkit().createComposite(parent);
		control.setLayoutData(new GridData(GridData.FILL_HORIZONTAL));
		switch(typeName) {
		case MachineModelManager.TYPE_NAME_MODE_DECLARATION_GROUP_PROTOTYPE:
			createModeDeclarationGroupPrototype(control, managedForm);
			break;
		case MachineModelManager.TYPE_NAME_PROCESS_TO_MACHINE_MAPPING:
			createProcessToMachineMapping(control, managedForm);
			break;
		case MachineModelManager.TYPE_NAME_MACHINE:
			createDetailMachine(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createModeDeclarationGroupPrototype(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		FunctionGroupSection sectionGUI = new FunctionGroupSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		FunctionGroupTableSection tableSectionGUI = new FunctionGroupTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createProcessToMachineMapping(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		ProcessSection sectionGUI = new ProcessSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		ProcessTableAppModeSection tableSectionGUI2 = new ProcessTableAppModeSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI2.createContent(managedForm, parent);
		addSection(tableSectionGUI2);
		
		ProcessTableStartupConfigSection tableSectionGUI = new ProcessTableStartupConfigSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailMachine(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		MachineSection sectionGUI = new MachineSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		MachineTableSection tableSectionGUI = new MachineTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
