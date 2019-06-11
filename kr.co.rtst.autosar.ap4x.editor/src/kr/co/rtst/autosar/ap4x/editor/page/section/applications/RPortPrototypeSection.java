package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.SearchBehaviorEnum;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.ApplicationstructureFactory;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.PortInstantiationBehaviorEnum;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.RPortPrototypeProps;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.swcomponent.components.RPortPrototype;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
import kr.co.rtst.autosar.ap4x.core.model.transaction.IAPTransactionalOperation;
import kr.co.rtst.autosar.ap4x.editor.consts.ToolTipFactory;
import kr.co.rtst.autosar.ap4x.editor.page.AbstractAPEditorPage;
import kr.co.rtst.autosar.ap4x.editor.page.section.AbstractContentGUISection;
import kr.co.rtst.autosar.ap4x.editor.page.section.ReferenceChoiceDelegator;
import kr.co.rtst.autosar.ap4x.editor.page.section.ShortNameContentGUISection;
import kr.co.rtst.autosar.common.ui.util.APSectionUIFactory;

/**
 * RPortPrototype
 * @author thkim
 *
 */
public class RPortPrototypeSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textRequiredInterface;
	private Button buttonAddRequiredInterface;
	private Button buttonEraseRequiredInterface;
	
	private Combo comboPortInstantiationBehavior;
	private Combo comboSearchBehavior;
	
	public RPortPrototypeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_RPORT_PROTOTYPE);
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("RPort prototype");
		sectionPart.getSection().setDescription("RPort prototype desc");
		
		textRequiredInterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Required interface*: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddRequiredInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseRequiredInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
		
		comboPortInstantiationBehavior = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Port instantiation behavior: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "multiple", "single");
		comboSearchBehavior = APSectionUIFactory.createLabelCombo(parent, ToolTipFactory.get(""), "Port search behavior: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-1, 0, this, "searchForAny", "searchForId");
		
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final RPortPrototype input = getCastedInputDetail();
		if(input != null) {
//			System.out.println("INPUT:"+input.getClass());
//			System.out.println("INPUT PROPS:"+input.getPortPrototypeProps());
			if(input.getPortPrototypeProps() == null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						RPortPrototypeProps portProps = ApplicationstructureFactory.eINSTANCE.createRPortPrototypeProps();
						input.setPortPrototypeProps(portProps);
						return model;
					}
				});
			}
//			System.out.println("INPUT PROPS:"+input.getPortPrototypeProps());
//			input.getPortPrototypeProps()
			setRequiredInterfaceText(input);
			
			if(input.getPortPrototypeProps().getPortInstantiationBehavior() != null) {
				if(input.getPortPrototypeProps().getPortInstantiationBehavior().equals(PortInstantiationBehaviorEnum.MULTIPLE)) {
					comboPortInstantiationBehavior.select(0);
				} else if(input.getPortPrototypeProps().getPortInstantiationBehavior().equals(PortInstantiationBehaviorEnum.SINGLE)) {
					comboPortInstantiationBehavior.select(1);
				}
			} 
			if(input.getPortPrototypeProps().getSearchBehavior() != null) {
				if(input.getPortPrototypeProps().getSearchBehavior().equals(SearchBehaviorEnum.SEARCH_FOR_ANY)) {
					comboSearchBehavior.select(0);
				} else if(input.getPortPrototypeProps().getSearchBehavior().equals(SearchBehaviorEnum.SEARCH_FOR_ID)) {
					comboSearchBehavior.select(1);
				}
			}
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(comboPortInstantiationBehavior)) {
			final RPortPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboPortInstantiationBehavior.getSelectionIndex()) {
						case 0:input.getPortPrototypeProps().setPortInstantiationBehavior(PortInstantiationBehaviorEnum.MULTIPLE);break;
						case 1:input.getPortPrototypeProps().setPortInstantiationBehavior(PortInstantiationBehaviorEnum.SINGLE);break;
						}
						return model;
					}
				});
			}
		}else if(e.getSource().equals(comboSearchBehavior)) {
			final RPortPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						switch(comboSearchBehavior.getSelectionIndex()) {
						case 0:input.getPortPrototypeProps().setSearchBehavior(SearchBehaviorEnum.SEARCH_FOR_ANY);break;
						case 1:input.getPortPrototypeProps().setSearchBehavior(SearchBehaviorEnum.SEARCH_FOR_ID);break;
						}
						return model;
					}
				});
			}
		} else if(e.getSource().equals(buttonAddRequiredInterface)) {
			final RPortPrototype input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final ServiceInterface selectedType = ReferenceChoiceDelegator.choiceSinglePortInterface(buttonAddRequiredInterface.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setRequiredInterface(selectedType);
								return model;
							}
						});
						
						setRequiredInterfaceText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseRequiredInterface)) {
			final RPortPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setRequiredInterface(null);
						return model;
					}
				});
				
				setRequiredInterfaceText(input);
			}
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private RPortPrototype getCastedInputDetail() {
		if(getInputDetail() instanceof RPortPrototype) {
			return (RPortPrototype)getInputDetail();
		}
		return null;
	}
	
	private void setRequiredInterfaceText(RPortPrototype model) {
		if(model.getRequiredInterface() != null) {
			textRequiredInterface.setText(model.getRequiredInterface().getShortName());
		}else {
			textRequiredInterface.setText("");
		}
	}
	
}
