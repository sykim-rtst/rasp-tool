package kr.co.rtst.autosar.ap4x.editor.page.section.applications;

import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.ListViewer;
import org.eclipse.sphinx.emf.explorer.BasicExplorerLabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;

import autosar40.adaptiveplatform.applicationdesign.applicationstructure.AdaptiveApplicationSwComponentType;
import autosar40.swcomponent.components.PortGroup;
import autosar40.swcomponent.datatype.units.UnitGroup;
import autosar40.swcomponent.datatype.units.UnitsFactory;
import gautosar.ggenericstructure.ginfrastructure.GARObject;
import gautosar.ggenericstructure.ginfrastructure.GAUTOSAR;
import kr.co.rtst.autosar.ap4x.core.model.IAPProject;
import kr.co.rtst.autosar.ap4x.core.model.manager.SwComponentsModelManager;
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
 * AdaptiveApplicationSwComponentType
 * @author thkim
 *
 */
public class AdaptiveApplicationSwComponentTypeSection extends ShortNameContentGUISection implements SelectionListener{
	
	private ListViewer listUnitGroup;
	private Button buttonAddUnitGroup;
	private Button buttonRemoveUnitGroup;
	
	public AdaptiveApplicationSwComponentTypeSection(AbstractAPEditorPage formPage, int style) {
		super(formPage, style, SwComponentsModelManager.TYPE_NAME_ADAPTIVE_APPLICATION_SWCOMPONENT_TYPE);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void createSectionClientContentDetail(IManagedForm managedForm, SectionPart sectionPart, Composite parent) {
		super.createSectionClientContentDetail(managedForm, sectionPart, parent);
		
		sectionPart.getSection().setText("Adaptive application sw component type");
		sectionPart.getSection().setDescription("Adaptive application sw component type desc");
		
		listUnitGroup = APSectionUIFactory.createLabelListViewer(parent, ToolTipFactory.get(""), "Unit group: ", AbstractContentGUISection.CLIENT_CONTENT_COLUMN-2);
		listUnitGroup.setContentProvider(new UnitGroupsContentProvider());
		listUnitGroup.setLabelProvider(new UnitGroupsLabelProvider());
		Composite compButton = new Composite(parent, SWT.NONE);
		compButton.setLayout(APUILayoutFactory.getGridLayoutDefault(1, false));
		GridData gData = new GridData(GridData.FILL_VERTICAL);
		gData.verticalAlignment = SWT.TOP;
		compButton.setLayoutData(gData);
		buttonAddUnitGroup = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_PLUS, 1, this);
		buttonRemoveUnitGroup = APSectionUIFactory.createButtonImage(compButton, ToolTipFactory.get(""), SWT.PUSH, APSectionUIFactory.BUTTON_IMAGE_MINUS, 1, this);
	}
	
	@Override
	public void initUIContents() {
		super.initUIContents();
		final AdaptiveApplicationSwComponentType input = getCastedInputDetail();
		if(input != null) {
			System.out.println("SIZE:"+input.getUnitGroups().size());
			listUnitGroup.setInput(input.getUnitGroups());
			listUnitGroup.refresh();
		}
	}
	
	@Override
	public void widgetDefaultSelected(SelectionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void widgetSelected(SelectionEvent e) {
		if(e.getSource().equals(buttonAddUnitGroup)) {
			final AdaptiveApplicationSwComponentType input = getCastedInputDetail();
			if(input != null) {
				IAPProject apProject = getAPProject();
				if(apProject != null && apProject.getRootObject() instanceof GAUTOSAR) {
					final PortGroup selectedType = ReferenceChoiceDelegator.choiceSinglePortGroup(buttonAddUnitGroup.getShell(), apProject);
					if(selectedType != null) {
						doTransactionalOperation(new IAPTransactionalOperation() {
							@Override
							public GARObject doProcess(GARObject model) throws Exception {
								UnitGroup unitGroup = UnitsFactory.eINSTANCE.createUnitGroup();
								unitGroup.setShortName(selectedType.getShortName());
								input.getUnitGroups().add(unitGroup);
								return model;
							}
						});
						
						listUnitGroup.refresh();
					}
				}
			}
		} else if(e.getSource().equals(buttonRemoveUnitGroup)) {
			final AdaptiveApplicationSwComponentType input = getCastedInputDetail();
			if(input != null) {
				if(!listUnitGroup.getStructuredSelection().isEmpty()) {
					doTransactionalOperation(new IAPTransactionalOperation() {
						
						@Override
						public GARObject doProcess(GARObject model) throws Exception {
							input.getUnitGroups().removeAll(listUnitGroup.getStructuredSelection().toList());
							return model;
						}
					});
					
					listUnitGroup.refresh();
				}
			}
		}		
	}
	
	private AdaptiveApplicationSwComponentType getCastedInputDetail() {
		if(getInputDetail() instanceof AdaptiveApplicationSwComponentType) {
			return (AdaptiveApplicationSwComponentType)getInputDetail();
		}
		return null;
	}
	
	private class UnitGroupsContentProvider implements IStructuredContentProvider {

		@Override
		public Object[] getElements(Object inputElement) {
			if(inputElement instanceof EList) {
				return ((EList) inputElement).toArray();
			}
			return new Object[0];
		}
		
	}
	
	private class UnitGroupsLabelProvider extends LabelProvider{
		private BasicExplorerLabelProvider apModelLabelProvider;
		
		public UnitGroupsLabelProvider() {
			apModelLabelProvider = new BasicExplorerLabelProvider();
		}
		
		@Override
		public String getText(Object element) {
			String result =  apModelLabelProvider.getText(element);
			if(result == null || result.trim().length()==0) {
				if(element instanceof UnitGroup) {
//					System.out.println("LABEL1:"+((UnitGroup) element).getShortName());
//					System.out.println("LABEL2:"+((UnitGroup) element).gGetShortName());
//					System.out.println("LABEL3:"+((UnitGroup) element));
//					return ((UnitGroup) element).getShortName();
					return ((UnitGroup) element).getShortName();
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
