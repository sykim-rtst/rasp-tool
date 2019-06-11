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
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineDesignsModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.editor.consts.EditorText;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.CommunicationConnectorSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.MachineDesignSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.NetworkEndpointSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.NetworkEndpointTableSection;
import kr.co.rtst.autosar.ap4x.editor.page.section.system.SomeipServiceDiscoverySection;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class MachineDesignEditorPage extends AbstractAPEditorPage {
	
	public MachineDesignEditorPage(APFormEditor formEditor) {
		super(formEditor, EditorText.EDIT_PAGE_TITLE_MACHINE_DESIGN);
	}
	
	@Override
	protected Map<String, Control> createDetailControls(Composite parent, IManagedForm managedForm) {
		Map<String, Control> controlProviderMap = new HashMap<>();
		for (IAPTypeDescriptor typeDesc : APModelManagerProvider.apINSTANCE.getMachineDesignsModelManager().getAPTypeDescriptors()) {
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
		case MachineDesignsModelManager.TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR:
			createDetailEthernetCommunicationConnector(control, managedForm);
			break;
		case MachineDesignsModelManager.TYPE_NAME_SOMEIP_SERVICE_DISCOVERY:
			createDetailSomeipServiceDiscovery(control, managedForm);
			break;
		case MachineDesignsModelManager.TYPE_NAME_MACHINE_DESIGN:
			createDetailMachineDesign(control, managedForm);
			break;
		case MachineDesignsModelManager.TYPE_NAME_NETWORK_ENDPOINT:
			createDetailNetworkEndpoint(control, managedForm);
			break;
		}
		return control;
	}
	
	private void createDetailEthernetCommunicationConnector(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		CommunicationConnectorSection sectionGUI = new CommunicationConnectorSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailSomeipServiceDiscovery(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		SomeipServiceDiscoverySection sectionGUI = new SomeipServiceDiscoverySection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailMachineDesign(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		MachineDesignSection sectionGUI = new MachineDesignSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
	private void createDetailNetworkEndpoint(Composite parent, IManagedForm managedForm) {
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(1, false));
		
		NetworkEndpointSection sectionGUI = new NetworkEndpointSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		sectionGUI.createContent(managedForm, parent);
		addSection(sectionGUI);
		
		NetworkEndpointTableSection tableSectionGUI = new NetworkEndpointTableSection(this, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);
		tableSectionGUI.createContent(managedForm, parent);
		addSection(tableSectionGUI);
		
		parent.setData(sectionGUI.getSectionTypeName());
	}
	
}
