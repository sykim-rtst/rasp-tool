package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.deployment.machine.Machine;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineDesignsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.listener.PositiveDecimalVerifier;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

public class NetworkEndpointSection extends ShortNameContentGUISection implements SelectionListener {

	private Text textFullyQualifiedDomainName;
	private Text textPriority;
	
	public NetworkEndpointSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineDesignsModelManager.TYPE_NAME_NETWORK_ENDPOINT);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart,
			Composite parent) {
		// TODO Auto-generated method stub
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Network endpoint");
		sectionPart.getSection().setDescription("Network endpoint desc");
		
		textFullyQualifiedDomainName = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Fully qualified domain name: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textPriority = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Priority: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		hookTextListener();
	}
	
	@Override
	public void initUIContents() {
		// TODO Auto-generated method stub
		super.initUIContents();
		NetworkEndpoint input = getCastedInputDetail();
		if(input != null) {
			textFullyQualifiedDomainName.setText(input.getFullyQualifiedDomainName());
			textPriority.setText(String.valueOf(input.getPriority()));
		}
	}
	
	private void hookTextListener() {
		textFullyQualifiedDomainName.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						NetworkEndpoint input = getCastedInputDetail();
						if(input != null) {
							input.setFullyQualifiedDomainName(textFullyQualifiedDomainName.getText());
						}
						return model;
					}
				});
			}
		});
		
		textPriority.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						NetworkEndpoint input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setPriority(Long.parseLong(textFullyQualifiedDomainName.getText()));
							}catch(Exception e) {
								input.setPriority(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textPriority.addVerifyListener(new PositiveDecimalVerifier());
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub

	}

	private NetworkEndpoint getCastedInputDetail() {
		if(getInputDetail() instanceof NetworkEndpoint) {
			return (NetworkEndpoint)getInputDetail();
		}
		return null;
	}
}
