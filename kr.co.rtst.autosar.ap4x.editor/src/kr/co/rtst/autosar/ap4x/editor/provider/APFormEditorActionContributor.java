package kr.co.rtst.autosar.ap4x.editor.provider;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.sphinx.emf.editors.forms.BasicTransactionalEditorActionBarContributor;
import org.eclipse.sphinx.emf.editors.forms.BasicTransactionalFormEditor;
import org.eclipse.sphinx.platform.ui.util.SelectionUtil;

import gautosar.ggenericstructure.ginfrastructure.GARObject;
import kr.co.rtst.autosar.ap4x.core.model.APProjectManager;
import kr.co.rtst.autosar.ap4x.core.model.manager.APModelManagerProvider;
import kr.co.rtst.autosar.ap4x.core.model.manager.IAPModelManager;
import kr.co.rtst.autosar.ap4x.editor.APFormEditor;
import kr.co.rtst.autosar.ap4x.ide.action.ElementModifyActionWrapper;
import kr.co.rtst.autosar.ap4x.ide.action.IAPActionContainer;
import kr.co.rtst.autosar.ap4x.ide.action.NotSupportedAPActionException;

public class APFormEditorActionContributor extends BasicTransactionalEditorActionBarContributor  /*EditingDomainActionBarContributor*/ implements IAPActionContainer {
	
	private IStructuredSelection structuredSelection;
	
//	@Override
//	public void contributeToMenu(IMenuManager menuManager) {
//		// TODO Auto-generated method stub
////		super.contributeToMenu(menuManager);
//		System.out.println("===============contributeToMenu 개수:"+menuManager.getItems().length);
//		for (IContributionItem i : menuManager.getItems()) {
//			System.out.println(":::"+i);
//		}
////		menuAboutToShow(menuManager);
//		System.out.println("===============contributeToMenu 종료");
//	}
//	
//	@Override
//	protected void addGlobalActions(IMenuManager menuManager) {
//		// TODO Auto-generated method stub
//		System.out.println("===============addGlobalActions 개수1:"+menuManager.getItems().length);
//		super.addGlobalActions(menuManager);
//		System.out.println("===============addGlobalActions 개수2:"+menuManager.getItems().length);
//	}
//	
//	@Override
//	public void activate() {
//		// TODO Auto-generated method stub
//		super.activate();
//	}
//	
//	@Override
//	public void init(IActionBars actionBars) {
//		// TODO Auto-generated method stub
//		super.init(actionBars);
//	}
	
	@Override
	public void menuAboutToShow(IMenuManager menuManager) {
//		super.menuAboutToShow(menuManager);
//		menuAboutToShow(menuManager);
//        MenuManager submenuManager = null;
//        submenuManager = new MenuManager(getStringFromKey("_UI_CreateChild_menu_item"));
		
//		final IAPActionObserver actionObserver = new IAPActionObserver() {
//			
//			@Override
//			public void preAction() {
//				
//			}
//			
//			@Override
//			public void postAction() {
//				AdaptiveAUTOSARNavigator.refresh();
//				APFormEditor.refresh(getActiveEditor().getEditorInput());
//			}
//		};
		
//		System.out.println("::::createChildSubmenuActions:::"+createChildSubmenuActions.getClass());
		Map<String, Collection<IAction>> newCreateChildSubmenuActions = new LinkedHashMap<>();
		Collection<IAction> wrappingList = null;
		Iterator<Map.Entry<String, Collection<IAction>>> entrise = createChildSubmenuActions.entrySet().iterator();
		while(entrise.hasNext()) {
			Map.Entry<String, Collection<IAction>> entry = entrise.next();
			Collection<IAction> actrions = entry.getValue();
//			System.out.println("::::actrions:::"+actrions.getClass());
			wrappingList = new ArrayList<>();
			for (IAction iAction : actrions) {
//				System.out.println("---------------EDITOR::reateChildSubmenuActions>>iActoin-TEXT:"+iAction.getText()+", ID:"+iAction.getId());
				try {
					wrappingList.add(new ElementModifyActionWrapper(
							APProjectManager.getInstance().getAPProject((GARObject)((APFormEditor)getActiveEditor()).getEditorInputObject()), 
							entry.getKey(), iAction));
				} catch (NotSupportedAPActionException e) {
					System.err.println(e.getMessage());
				}
			}
			newCreateChildSubmenuActions.put(entry.getKey(), wrappingList);
		}
		
		
        populateManager(menuManager, newCreateChildSubmenuActions/*createChildSubmenuActions*/, null);
//        populateManager(menuManager, createChildActions, null);
//        menuManager.insertBefore("edit", submenuManager);
//        submenuManager = new MenuManager(getStringFromKey("_UI_CreateSibling_menu_item"));
//        populateManager(submenuManager, createSiblingSubmenuActions, null);
//        populateManager(submenuManager, createSiblingActions, null);
//        menuManager.insertBefore("edit", submenuManager);
        
        populateAPActions(
        		APProjectManager.getInstance().getAPProject((GARObject)((APFormEditor)getActiveEditor()).getEditorInputObject()), 
        		menuManager, structuredSelection);
        
        menuManager.add(new Separator());
        try {
			menuManager.add(new ElementModifyActionWrapper(
					APProjectManager.getInstance().getAPProject((GARObject)((APFormEditor)getActiveEditor()).getEditorInputObject()), 
					null, deleteAction));
		} catch (NotSupportedAPActionException e) {
			System.err.println(e.getMessage());
		}
	}
	
	@Override
	protected void populateManager(IContributionManager manager, Map<String, Collection<IAction>> submenuActions,
			String contributionId) {
//		super.populateManager(manager, submenuActions, contributionId);
		
		if(submenuActions != null)
        {
            java.util.Map.Entry entry;
            for(Iterator iterator = submenuActions.entrySet().iterator(); iterator.hasNext(); populateManager(manager, (Collection)entry.getValue(), null))
            {
                entry = (java.util.Map.Entry)iterator.next();
            }

        }
	}
	
	@Override
	public void selectionChanged(SelectionChangedEvent event) {
//		System.out.println("SOURCE------------------->"+event.getSource());
		// TODO Auto-generated method stub
//		super.selectionChanged(event);
		
		ISelection selection = event.getSelection();
        structuredSelection = SelectionUtil.getStructuredSelection(selection);
        List nonEditorInputObjects = new ArrayList();
        for(Iterator iterator = structuredSelection.toList().iterator(); iterator.hasNext();)
        {
            Object selected = iterator.next();
            if(activeEditor instanceof BasicTransactionalFormEditor)
            {
                BasicTransactionalFormEditor activeFormEditor = (BasicTransactionalFormEditor)activeEditor;
                if(selected != activeFormEditor.getEditorInputObject())
                    nonEditorInputObjects.add(selected);
            }
        }

        IStructuredSelection nonModelRootSelection = new StructuredSelection(nonEditorInputObjects);
        deleteAction.selectionChanged(nonModelRootSelection);
        cutAction.selectionChanged(nonModelRootSelection);
        if(createChildMenuManager != null)
        {
            depopulateManager(createChildMenuManager, createChildSubmenuActions);
            depopulateManager(createChildMenuManager, createChildActions);
        }
        if(createSiblingMenuManager != null)
        {
            depopulateManager(createSiblingMenuManager, createSiblingSubmenuActions);
            depopulateManager(createSiblingMenuManager, createSiblingActions);
        }
        Collection newChildDescriptors = null;
        Collection newSiblingDescriptors = null;
        if(structuredSelection.size() == 1)
        {
            Object object = ((IStructuredSelection)selection).getFirstElement();
            org.eclipse.emf.edit.domain.EditingDomain domain = ((IEditingDomainProvider)activeEditor).getEditingDomain();
            if(domain instanceof TransactionalEditingDomain)
            {
                newChildDescriptors = getNewChildDescriptors((TransactionalEditingDomain)domain, object, null);
                newSiblingDescriptors = getNewChildDescriptors((TransactionalEditingDomain)domain, null, object);
            }
        }
        
//        newChildDescriptors.stream().forEach(d->{
//        	System.out.println("Child DESC OWN:"+((CommandParameter)d).getEOwner()+", VAL:"+((CommandParameter)d).getValue()+", REF:"+((CommandParameter)d).getEReference()+", COL:"+((CommandParameter)d).getCollection());
//        });
//        
//        newSiblingDescriptors.stream().forEach(d->{
//        	System.out.println("Sibling DESC OWN:"+((CommandParameter)d).getEOwner()+", VAL:"+((CommandParameter)d).getValue()+", REF:"+((CommandParameter)d).getEReference()+", COL:"+((CommandParameter)d).getCollection());
////    		System.out.println("DESC OWN:"+((CommandParameter)d).getEOwner()+", VAL:"+((CommandParameter)d).getValue()+", REF:"+((CommandParameter)d).getEReference()+", COL:"+((CommandParameter)d).getCollection());
//        });
        
        IAPModelManager modelManager = APModelManagerProvider.apINSTANCE.lookupModelManager((GARObject)((IStructuredSelection)selection).getFirstElement());
        if(modelManager != null) {
	        List<Object> notSupportedDescriptors = new ArrayList<>();
	        for (Object object : newChildDescriptors) {
	        	if(object instanceof CommandParameter && ((CommandParameter) object).getEValue() instanceof GARObject) {
	        		
	        		if(!modelManager.isNavigatableSubElement((GARObject)((CommandParameter) object).getEValue())) {
	        			notSupportedDescriptors.add(object);
	        		}
	        	}
			}
	        if(!notSupportedDescriptors.isEmpty()) {
//	        	System.out.println("ChildDescriptors REMOVE SIZE:"+notSupportedDescriptors.size());
	        	for (Object garObject : notSupportedDescriptors) {
	        		newChildDescriptors.remove(garObject);
				}
	        }
	        
	        if(modelManager != null) {
		        notSupportedDescriptors = new ArrayList<>();
		        for (Object object : newSiblingDescriptors) {
		        	if(object instanceof CommandParameter && ((CommandParameter) object).getEValue() instanceof GARObject) {
		        		
		        		if(!modelManager.isNavigatableSubElement((GARObject)((CommandParameter) object).getEValue())) {
		        			notSupportedDescriptors.add(object);
		        		}
		        	}
				}
		        if(!notSupportedDescriptors.isEmpty()) {
//		        	System.out.println("SiblingDescriptors REMOVE SIZE:"+notSupportedDescriptors.size());
		        	for (Object garObject : notSupportedDescriptors) {
		        		newSiblingDescriptors.remove(garObject);
					}
		        }
	        }
        }
        
        createChildActions = generateCreateChildActions(newChildDescriptors, selection);
        createChildSubmenuActions = extractSubmenuActions(createChildActions, selection);
        createSiblingActions = generateCreateSiblingActions(newSiblingDescriptors, selection);
        createSiblingSubmenuActions = extractSubmenuActions(createSiblingActions, selection);
        
        if(createChildMenuManager != null)
        {
            populateManager(createChildMenuManager, createChildSubmenuActions, null);
            populateManager(createChildMenuManager, createChildActions, null);
            createChildMenuManager.update(true);
        }
        if(createSiblingMenuManager != null)
        {
            populateManager(createSiblingMenuManager, createSiblingSubmenuActions, null);
            populateManager(createSiblingMenuManager, createSiblingActions, null);
            createSiblingMenuManager.update(true);
        }
	}
	
}
