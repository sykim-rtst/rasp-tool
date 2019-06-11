package kr.co.rtst.autosar.ap4x.editor.page.section.system;

import java.math.BigInteger;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.EthernetCommunicationConnector;
import autosar40.system.fibex.fibex4ethernet.ethernettopology.NetworkEndpoint;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.MachineDesignsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.AP4xEditorActivator;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

/**
 * EthernetCommunicationConnector
 * @author thkim
 *
 */
public class CommunicationConnectorSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textMaximumTransmissionUnit;
//	private ListViewer listNetworkEndpoint;
//	private Button buttonAddNetworkEndpoint;
//	private Button buttonRemoveNetworkEndpoint;
//	private Text textUnicastNetworkEndpoint;
//	private Button buttonAddUnicastNetworkEndpoint;
//	private Button buttonEraseUnicastNetworkEndpoint;
	
	private Combo comboPathMtuEnabled;
	private Text textPathMtuTimout;
	private Text textPncFilterDataMask;


	public CommunicationConnectorSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineDesignsModelManager.TYPE_NAME_ETHERNET_COMMUNICATION_CONNECTOR);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Ethernet communication connector");
		sectionPart.getSection().setDescription("Ethernet communication connector desc");
		
		textMaximumTransmissionUnit = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Maximum transmission unit: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
//		listNetworkEndpoint = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Network endpoint: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
//		listNetworkEndpoint.setContentProvider(new NetworkEndpointContentProvider());
//		listNetworkEndpoint.setLabelProvider(new NetworkEndpointLabelProvider());
//		Composite compButton = new Composite(parent, SWT.NONE);
//		compButton.setLayout(APUIFactory.getGridLayoutDefault(1, false));
//		GridData gData = new GridData(GridData.FILL_VERTICAL);
//		gData.verticalAlignment = SWT.TOP;
//		compButton.setLayoutData(gData);
//		buttonAddNetworkEndpoint = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
//		buttonRemoveNetworkEndpoint = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
//		
//		textUnicastNetworkEndpoint = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Unicast network endpoint: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
//		buttonAddUnicastNetworkEndpoint = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
//		buttonEraseUnicastNetworkEndpoint = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboPathMtuEnabled = APSectionUIFactory.createLabelComboForBoolean(parent, ToolTipFactory.get(""), "Path MTU enabled", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this);
		
		textPathMtuTimout = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Path MTU timout: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		textPncFilterDataMask = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "pnc Filter Data Mask: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		textMaximumTransmissionUnit.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						EthernetCommunicationConnector input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setMaximumTransmissionUnit(Long.parseLong(textMaximumTransmissionUnit.getText()));
							}catch(NumberFormatException e) {
								input.setMaximumTransmissionUnit(0l);
							}
						}
						return model;
					}
				});
			}
		});
		
		textPathMtuTimout.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						EthernetCommunicationConnector input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setPathMtuTimeout(Double.parseDouble(textPathMtuTimout.getText()));
							}catch(NumberFormatException e) {
								input.setPathMtuTimeout(0d);
							}
						}
						return model;
					}
				});
			}
		});
		
		textPncFilterDataMask.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						EthernetCommunicationConnector input = getCastedInputDetail();
						if(input != null) {
							try {
//								input.setPncFilterDataMask(BigInteger.valueOf(Long.parseLong(textMaximumTransmissionUnit.getText())));
								input.setPncFilterDataMask(new BigInteger(textPncFilterDataMask.getText()));
							}catch(NumberFormatException e) {
								input.setPncFilterDataMask(new BigInteger("0"));
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
		EthernetCommunicationConnector input = getCastedInputDetail();
		if(input != null) {
			textMaximumTransmissionUnit.setText(String.valueOf(input.getMaximumTransmissionUnit()));
			
//			listNetworkEndpoint.setInput(input.getNetworkEndpoints());
//			listNetworkEndpoint.refresh();
//			
//			setUnicastNetworkEndpointText(input);
			
			comboPathMtuEnabled.select(input.isSetPathMtuEnabled()?APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_TRUE:APSectionUIFactory.COMBO_BOOLEAN_VALUE_INDEX_FALSE);
			
			textPathMtuTimout.setText(String.valueOf(input.getPathMtuTimeout().longValue()));
			
			textPncFilterDataMask.setText(String.valueOf(input.getPncFilterDataMask()));
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboPathMtuEnabled)) {
			final EthernetCommunicationConnector input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setPathMtuEnabled(Boolean.parseBoolean(comboPathMtuEnabled.getText()));
						return model;
					}
				});
			}
		}/* else if(e.getSource().equals(buttonAddNetworkEndpoint)) {
			final EthernetCommunicationConnector input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					// TODO 타입을 선택할 수 있어야 한다.
					final List<GARObject> selectedTypes = ReferenceChoiceDelegator.choiceMultiNetworkEndpoint(buttonAddUnicastNetworkEndpoint.getShell(), apProject);
					if(selectedTypes != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								for (GARObject garObject : selectedTypes) {
									if(garObject instanceof NetworkEndpoint) {
										input.getNetworkEndpoints().add((NetworkEndpoint)garObject);
									}
								}
								return model;
							}
						});
						
						listNetworkEndpoint.refresh();
					}
				}
			}
		} else if(e.getSource().equals(buttonRemoveNetworkEndpoint)) {
			final EthernetCommunicationConnector input = getCastedInputDetail();
			if(input != null) {
				if(!listNetworkEndpoint.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getNetworkEndpoints().removeAll(listNetworkEndpoint.getStructuredSelection().toList());
							return model;
						}
					});
					
					listNetworkEndpoint.refresh();
				}
			}
		} else if(e.getSource().equals(buttonAddUnicastNetworkEndpoint)) {
			final EthernetCommunicationConnector input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					// TODO 타입을 선택할 수 있어야 한다.
					final NetworkEndpoint selectedType = ReferenceChoiceDelegator.choiceSingleNetworkEndpoint(buttonAddUnicastNetworkEndpoint.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setUnicastNetworkEndpoint(selectedType);
								return model;
							}
						});
						
						setUnicastNetworkEndpointText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseUnicastNetworkEndpoint)) {
			final EthernetCommunicationConnector input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setUnicastNetworkEndpoint(null);
						return model;
					}
				});
				
				setUnicastNetworkEndpointText(input);
			}
		}*/
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		
	}
	
	private EthernetCommunicationConnector getCastedInputDetail() {
		if(getInputDetail() instanceof EthernetCommunicationConnector) {
			return (EthernetCommunicationConnector)getInputDetail();
		}
		return null;
	}
	
//	private void setUnicastNetworkEndpointText(EthernetCommunicationConnector model) {
//		if(model.getUnicastNetworkEndpoint() != null) {
//			textUnicastNetworkEndpoint.setText(model.getUnicastNetworkEndpoint().getShortName());
//		}else {
//			textUnicastNetworkEndpoint.setText("");
//		}
//	}
//	
//	private class NetworkEndpointContentProvider implements IStructuredContentProvider {
//
//		@Override
//		public Object[] getElements(Object inputElement) {
//			if(inputElement instanceof EList) {
//				return ((EList) inputElement).toArray();
//			}
//			return new Object[0];
//		}
//		
//	}
//	
//	private class NetworkEndpointLabelProvider extends LabelProvider{
//		private BasicExplorerLabelProvider apModelLabelProvider;
//		
//		public NetworkEndpointLabelProvider() {
//			apModelLabelProvider = new BasicExplorerLabelProvider();
//		}
//		
//		@Override
//		public String getText(Object element) {
//			String result =  apModelLabelProvider.getText(element);
//			if(result == null || result.trim().length()==0) {
//				if(element instanceof Referrable) {
//					return ((Referrable) element).getShortName();
//				}
//			}else {
//				return result;
//			}
//			return "";
//		}
//		
//		@Override
//		public Image getImage(Object element) {
//			Image img = apModelLabelProvider.getImage(element);
//			if(img == null) {
//				img = AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
//			}
//			return img;
//		}
//	}
	
}
