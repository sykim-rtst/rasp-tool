package kr.co.rtst.autosar.ap4x.ide.wizards;

import org.artop.aal.workspace.ui.wizards.pages.NewAutosarProjectCreationPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import kr.co.rtst.autosar.ap4x.ide.consts.IDEText;
import kr.co.rtst.autosar.common.ui.util.APUILayoutFactory;

public class AdaptiveAutosarNewProjectWizardMainPage extends NewAutosarProjectCreationPage{

	public static final String PAGE_NAME = "kr.co.rtst.autosar.ap4x.ide.wizards.mainPage";
	
	private final AdaptiveAutosarProjectCreationInfo projectInfo;
//	private Text txtPackageName;
	private Button btnPredefinedArxml;
	
	public AdaptiveAutosarNewProjectWizardMainPage(AdaptiveAutosarProjectCreationInfo projectInfo) {
		super(PAGE_NAME);
		this.projectInfo = projectInfo;
		setTitle(IDEText.WIZARD_MAIN_PAGE_TITLE);
		setDescription(IDEText.WIZARD_MAIN_PAGE_MESSAGE);
	}
	
	private Composite findNamegroupComposite(Composite parent) {
		for(Control c: parent.getChildren()) {
			if(c instanceof Label) {
				if(((Label)c).getText().toLowerCase().contains("project name")) {
					return c.getParent();
				}
			}
		}
		for(Control c: parent.getChildren()) {
			if(c instanceof Composite) {
				Composite comp = findNamegroupComposite((Composite)c);
				if(comp != null) {
					return comp;
				}
			}
		}
		return null;
	}
	
	@Override
	protected void createAdditionalControls(Composite parent) {
		createMetaModelVersionGroup(parent);
		createDefaultPackageGroup(parent);
		
		init();
	}
	
	protected void createDefaultPackageGroup(Composite topParent) {
		
		Composite nameGroupComp = findNamegroupComposite(topParent);
		
		Composite parent = new Composite(nameGroupComp, SWT.NONE);
		parent.setLayout(APUILayoutFactory.getGridLayoutNoMargin(2, false));
		GridData gData = new GridData(GridData.FILL_BOTH);
		gData.horizontalSpan = ((GridLayout)nameGroupComp.getLayout()).numColumns;
		gData.verticalIndent = 8;
		parent.setLayoutData(gData);
		
		btnPredefinedArxml = new Button(parent, SWT.CHECK);
		btnPredefinedArxml.setText(IDEText.WIZARD_PREDEFINED_ARXML_BUTTON);
		gData = new GridData();
		gData.horizontalSpan = 2;
		btnPredefinedArxml.setLayoutData(gData);
		btnPredefinedArxml.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				projectInfo.setPredefinedArxml(btnPredefinedArxml.getSelection());
			}
		});
	}
	
	@Override
	protected boolean validatePage() {
		boolean result = super.validatePage();
		return result;
	}
	
	private void init() {
		btnPredefinedArxml.setSelection(true);
		projectInfo.setPredefinedArxml(btnPredefinedArxml.getSelection());
	}
	
}
