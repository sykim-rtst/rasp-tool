package kr.co.rtst.autosar.ap4x.editor.page.section.system;

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
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.serviceinstancemanifest.securecommunication.SecureComProps;
import autosar40.adaptiveplatform.serviceinstancemanifest.serviceinterfacedeployment.SomeipServiceDiscovery;
import autosar40.genericstructure.generaltemplateclasses.identifiable.Referrable;
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
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

/**
 * ServiceDiscoveryConfiguration
 * @author thkim
 *
 */
public class SomeipServiceDiscoverySection extends AbstractContentGUISection/*ShortNameContentGUISection*/ implements SelectionListener{

//	private Text textMulticastSdIpAddress;
//	private Button buttonAddMulticastSdIpAddress;
//	private Button buttonEraseMulticastSdIpAddress;
	
	private Text textMulticastSecureComProps;
	private Button buttonAddMulticastSecureComProps;
	private Button buttonEraseMulticastSecureComProps;
	
	private Text textSomeipServiceDiscoveryPort;
	
	private ListViewer listUnicastSecureComProps;
	private Button buttonAddUnicastSecureComProps;
	private Button buttonRemoveUnicastSecureComProps;
	
	public SomeipServiceDiscoverySection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, MachineDesignsModelManager.TYPE_NAME_SOMEIP_SERVICE_DISCOVERY);
	}
	
	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
//		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Someip service discovery");
		sectionPart.getSection().setDescription("Someip service discovery desc");
		
//		textMulticastSdIpAddress = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Multicast sd ip address: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
//		buttonAddMulticastSdIpAddress = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
//		buttonEraseMulticastSdIpAddress = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textMulticastSecureComProps = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Multicast secure com props: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddMulticastSecureComProps = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseMulticastSecureComProps = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		textSomeipServiceDiscoveryPort = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Someip service discovery port*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, true);
		
		listUnicastSecureComProps = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Unicast secure com props: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		listUnicastSecureComProps.setContentProvider(new SecureComPropsContentProvider());
		listUnicastSecureComProps.setLabelProvider(new SecureComPropsLabelProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddUnicastSecureComProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveUnicastSecureComProps = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
		
		textSomeipServiceDiscoveryPort.addModifyListener(new ModifyListener() {
			
			@Override
			public void modifyText(ModifyEvent e) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						SomeipServiceDiscovery input = getCastedInputDetail();
						if(input != null) {
							try {
								input.setSomeipServiceDiscoveryPort(Long.parseLong(textSomeipServiceDiscoveryPort.getText()));
							}catch(NumberFormatException e) {
								input.setSomeipServiceDiscoveryPort(0l);
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
//		super.initUIContents();
		SomeipServiceDiscovery input = getCastedInputDetail();
		if(input != null) {
//			setMulticastSdIpAddressText(input);
			
			setMulticastSecureComPropsText(input);
			
			textSomeipServiceDiscoveryPort.setText(String.valueOf(input.getSomeipServiceDiscoveryPort()));
			
//			System.out.println("secureComPropsSize:"+input.getUnicastSecureComProps().size());
//			for (SecureComProps sc : input.getUnicastSecureComProps()) {
//				System.out.println("sc:"+sc.getShortName());
//			}
			listUnicastSecureComProps.setInput(input.getUnicastSecureComProps());
			listUnicastSecureComProps.refresh();
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		/*if(e.getSource().equals(buttonAddMulticastSdIpAddress)) {
			final SomeipServiceDiscovery input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final NetworkEndpoint selectedType = ReferenceChoiceDelegator.choiceSingleNetworkEndpoint(buttonAddMulticastSdIpAddress.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setMulticastSdIpAddress(selectedType);
								return model;
							}
						});
						
						setMulticastSdIpAddressText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseMulticastSdIpAddress)) {
			final SomeipServiceDiscovery input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setMulticastSdIpAddress(null);
						return model;
					}
				});
				
				setMulticastSdIpAddressText(input);
			}
		} else */if(e.getSource().equals(buttonAddMulticastSecureComProps)) {
			final SomeipServiceDiscovery input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final SecureComProps selectedType = ReferenceChoiceDelegator.choiceSingleSecureComProps(buttonAddMulticastSecureComProps.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setMulticastSecureComProps(selectedType);
								return model;
							}
						});
						
						setMulticastSecureComPropsText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseMulticastSecureComProps)) {
			final SomeipServiceDiscovery input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setMulticastSecureComProps(null);
						return model;
					}
				});
				
				setMulticastSecureComPropsText(input);
			}
		} else if(e.getSource().equals(buttonAddUnicastSecureComProps)) {
//			final SomeipServiceDiscovery input = getCastedInputDetail();
//			if(input != null) {
//				final SecureComProps secureComProps = (SecureComProps)SecurecommunicationFactory.eINSTANCE.create(SecurecommunicationPackage.eINSTANCE.getSecureComProps());
//				doTransactionalOperation(new IAPTransactionalOperation() {
//					
//					@Override
//					public GARObject doProcess(GARObject model) throws Exception {
////						SecureComProps secureComProps = (SecureComProps)SecurecommunicationFactory.eINSTANCE.create(SecurecommunicationPackage.eINSTANCE.getSecureComProps());
//						
////								SecurecommunicationFactoryImpl.eINSTANCE.createSecureComPropsSet().getSecureComProps()
////						SecureComProps secureComProps = (SecureComProps)SecurecommunicationFactory.eINSTANCE.create(SecurecommunicationPackage.eINSTANCE.getSecureComProps());
////						if(조건1) {
////							secureComProps = SecurecommunicationFactory.eINSTANCE.createSecureComPropsSet();
////						} else if(조건2) {
////							secureComProps = SecurecommunicationFactory.eINSTANCE.createSecOcSecureComProps();
////						} else if(조건3) {
////							secureComProps = SecurecommunicationFactory.eINSTANCE.createTlsSecureComProps();
////						}
////						secureComProps.setShortName("UnicastSecureComProps "+input.getUnicastSecureComProps().size());
//						input.getUnicastSecureComProps().add(secureComProps);
//						return model;
//					}
//				});
//				
//				doTransactionalOperation(new IAPTransactionalOperation() {
//					
//					@Override
//					public GARObject doProcess(GARObject model) throws Exception {
//						secureComProps.setShortName("UnicastSecureComProps "+input.getUnicastSecureComProps().size());
//						return model;
//					}
//				});
//				
//				listUnicastSecureComProps.refresh();
//			}
		} else if(e.getSource().equals(buttonRemoveUnicastSecureComProps)) {
//			final SomeipServiceDiscovery input = getCastedInputDetail();
//			if(input != null) {
//				if(!listUnicastSecureComProps.getStructuredSelection().isEmpty()) {
//					doTransactionalOperation(new IAPTransactionalOperation() {
//						
//						@Override
//						public GARObject doProcess(GARObject model) throws Exception {
//							input.getUnicastSecureComProps().removeAll(listUnicastSecureComProps.getStructuredSelection().toList());
//							return model;
//						}
//					});
//					
//					listUnicastSecureComProps.refresh();
//				}
//			}
		}
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private SomeipServiceDiscovery getCastedInputDetail() {
		if(getInputDetail() instanceof SomeipServiceDiscovery) {
			return (SomeipServiceDiscovery)getInputDetail();
		}
		return null;
	}
	
//	private void setMulticastSdIpAddressText(SomeipServiceDiscovery model) {
//		if(model.getMulticastSdIpAddress() != null) {
//			textMulticastSdIpAddress.setText(model.getMulticastSdIpAddress().getShortName());
//		}else {
//			textMulticastSdIpAddress.setText("");
//		}
//	}
	
	private void setMulticastSecureComPropsText(SomeipServiceDiscovery model) {
		if(model.getMulticastSecureComProps() != null) {
			textMulticastSecureComProps.setText(model.getMulticastSecureComProps().getShortName());
		}else {
			textMulticastSecureComProps.setText("");
		}
	}
	
	private class SecureComPropsContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class SecureComPropsLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public SecureComPropsLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			String result =  apModelLabelProvider.getText(element);
			if(result == null || result.trim().length()==0) {
				if(element instanceof Referrable) {
					return ((Referrable) element).getShortName();
				}
			}else {
				return result;
			}
			return "";
		}
		
		@Override
		public Image getImage(Object element) {
			Image img = apModelLabelProvider.getImage(element);
			if(img == null) {
				img = AP4xEditorActivator.getDefault().getEditorImageRegistry().getImage("icons/default_object.gif");
			}
			return img;
		}
	}

}
