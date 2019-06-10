package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.ApplicationstructureFactory;
import autosar40.adaptiveplatform.applicationdesign.applicationstructure.RPortPrototypeProps;
import autosar40.adaptiveplatform.applicationdesign.portinterface.ServiceInterface;
import autosar40.swcomponent.components.PPortPrototype;
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

/***
 * 
 * @author thkim
 *
 */
public class PPortPrototypeSection extends ShortNameContentGUISection implements SelectionListener{
	
	private Text textProvidedInterface;
	private Button buttonAddProvidedInterface;
	private Button buttonEraseProvidedInterface;
	
	public PPortPrototypeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_PPORT_PROTOTYPE);
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("PPort prototype");
		sectionPart.getSection().setDescription("PPort prototype desc");
		
		textProvidedInterface = APSectionUIFactory.createLabelText(parent, ToolTipFactory.get(""), "Provided interface: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-3, false);
		buttonAddProvidedInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_SEARCH, 1, this);
		buttonEraseProvidedInterface = APSectionUIFactory.createButtonImage(parent, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_ERASE, 1, this);
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final PPortPrototype input = getCastedInputDetail();
		if(input != null) {
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
			setProvidedInterfaceText(input);
		}
	}

	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddProvidedInterface)) {
			final PPortPrototype input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final ServiceInterface selectedType = ReferenceChoiceDelegator.choiceSinglePortInterface(buttonAddProvidedInterface.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								input.setProvidedInterface(selectedType);
								return model;
							}
						});
						
						setProvidedInterfaceText(input);
					}
				}
			}
		} else if(e.getSource().equals(buttonEraseProvidedInterface)) {
			final PPortPrototype input = getCastedInputDetail();
			if(input != null) {
				doTransactionalOperation(new IAPTransactionalOperation() {
					@Override
					public GARObject doProcess(GARObject model) throws Exception {
						input.setProvidedInterface(null);
						return model;
					}
				});
				
				setProvidedInterfaceText(input);
			}
		}		
	}

	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
	}
	
	private PPortPrototype getCastedInputDetail() {
		if(getInputDetail() instanceof PPortPrototype) {
			return (PPortPrototype)getInputDetail();
		}
		return null;
	}
	
	private void setProvidedInterfaceText(PPortPrototype model) {
		if(model.getProvidedInterface() != null) {
			textProvidedInterface.setText(model.getProvidedInterface().getShortName());
		}else {
			textProvidedInterface.setText("");
		}
	}
	
}
