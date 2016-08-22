package com.xsynergy.dm.excelexporter;

import com.xsynergy.dm.excelexporter.Util;

import oracle.dbtools.crest.fcp.DMDiagramNode;

import oracle.ide.Context;
import oracle.ide.Ide;
import oracle.ide.controller.IdeAction;
import oracle.ide.controller.TriggerController;
import oracle.ide.wizard.WizardManager;

public class Controller
  implements TriggerController
{
  public Controller()
  {
    super();
  }

  public static final int EBO_CMD_ID = Ide.findCmdID("com.xsynergy.dm.excelexporter");

  @Override
  public boolean handleEvent(IdeAction ideAction, Context context)
  {
    WizardManager.getInstance().getWizard(Wizard.class).invoke(context);
    return true;
  }

  @Override
  @SuppressWarnings("oracle.jdeveloper.java.insufficient-catch-block")
  public boolean update(IdeAction ideAction, Context context)
  {
                                                  // The Extension API doesn't allow for complex filters
                                                  // .. to check if the Menu should be enabled before the first
                                                  // .. call.  So check here, and disable if neccessary and deal
                                                  // .. with the error as per docs 
                                                  // (https://docs.oracle.com/cd/E24382_01/doc.1112/e20067/dev_extensions_jdev.htm#OJDEG116)
    
    try
    {
                                                  // This will throw an exception if:
                                                  // .. no project/design loaded
                                                  // .. nothing selected
                                                  // .. the selected item is not a SubView.
      DMDiagramNode node = (DMDiagramNode) context.getNode();

                                                  // If we get here, we have a subview selected.
      ideAction.setEnabled(true);
      return true;
	  
    }
    catch (Exception e)
    {
												  // This will happen when an EBO subview is NOT selected.
    }
                                                  // If we get here, an EBO Subview is not selected.  This will
                                                  // .. trigger a error handle by getInvalidState.
    ideAction.setEnabled(false);
    return true;
  }

  @Override
  public Object getInvalidStateMessage(IdeAction ideAction, Context context)
  {
    return Util.InvalidState;					  // Called when the menu item is incorrectly enabled (usually happens 
												  // .. when then menu is clicked before the extension is loaded).
  }
}
