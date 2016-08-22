package com.xsynergy.dm.excelexporter;

import com.xsynergy.dm.excelexporter.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import oracle.dbtools.crest.fcp.DMDiagramNode;
import oracle.dbtools.crest.model.design.Design;
import oracle.dbtools.crest.model.design.logical.Attribute;
import oracle.dbtools.crest.model.design.logical.Entity;
import oracle.dbtools.crest.model.design.logical.LogicalDesign;
import oracle.dbtools.crest.swingui.ApplicationView;
import oracle.dbtools.crest.swingui.logical.DPVLogicalSubView;

import oracle.ide.Context;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

public class Wizard
  extends oracle.ide.wizard.Wizard
{

  public Wizard()
  {	
    super();
  }

  @Override
  public boolean isAvailable(Context context)
  {
    return true;
  }

  @Override
  public String getShortLabel()
  {
    return Util.ShortName;
  }

  @Override
  public boolean invoke(Context context)
  {
    try
    { 
	
                                                          // Wrap everything in an Exception handler to
                                                          // .. prevent errors in DataModeler.
      String subviewName = "";
      List entities = null;
                                                          // Confirm that a subview is selected.
                                                          // (The menu will be disabled until this is true - no need to
                                                          // (.. double check).
      
      DMDiagramNode node = (DMDiagramNode) context.getNode();
	  
      subviewName = node.getComponent().getName();		  // TODO: Throws and exeception for unsaved/empty subviews.

	  
                                                          // Get the current model
      ApplicationView view = ApplicationView.getInstance();

                                                          // Get the Entities for the selected subview.
      Design design = view.getCurrentDesign();
      LogicalDesign logical = design.getLogicalDesign();
      DPVLogicalSubView subView = (DPVLogicalSubView) logical.getSubViewByName(subviewName);
	  
      entities = subView.getEntities();                   // The list of entities for this subview.
	  
                                                          // Get the preferences
      oracle.ide.config.Preferences p = oracle.ide.config.Preferences.getPreferences();
      Preferences prefs = Preferences.getInstance(p);
	  
                                                          // Confirm the Action
      if(prefs.getConfirmation() == true)
      {
        Object[] options =
        {
          Util.ConfirmReport, Util.CancelReport
        };
  
        if (JOptionPane.showOptionDialog(null, String.format(Util.ConfirmMessage, subviewName), Util.ConfirmTitle,
                                         JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                                         options[0]) == JOptionPane.NO_OPTION)
        {
          Util.log("User Cancelled Operation");
          return false;
        }
      }
                                                          // Choose the file save location (Only Dirs)
     
      String outputFile = prefs.getDefaultFolder();

      if(outputFile.length() == 0)
      {
        final JFileChooser fc = new JFileChooser();
  
        fc.setDialogTitle(Util.ChooseDir);
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
  
        int returnVal = fc.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
          File file = fc.getSelectedFile();
          outputFile = file.getAbsolutePath();
        }
        else
        {
          return false;
        }
      }

      outputFile = String.format("%s%s%s%s.%s", outputFile, System.getProperty("file.separator"), subviewName, 
                                Util.kFilename, Util.kFileFilter);

                                                          // At this stage, we have the list of entities and file name.

														  // Create counters for position.
	  int rowNum = 0;
	  int cellNumber = 0;
														  // Create the Excel Workbook.
														  // HSSF is for xsl files
														  // Change to XSSF for xslx file (you'll all need to import the
														  // .. jar and change the file filter to xslx)
	  HSSFWorkbook workbook = new HSSFWorkbook();
														  // Create the sheet (named with the EBO Name).
	  HSSFSheet sheet = workbook.createSheet(subviewName);
									
														  // Create the header row.
	  Row row = sheet.createRow(rowNum++);
  
	  row.createCell(cellNumber++).setCellValue("Subview Name");
	  row.createCell(cellNumber++).setCellValue("Entity Name");
	  row.createCell(cellNumber++).setCellValue("Entity Definition");
	  row.createCell(cellNumber++).setCellValue("Attribute Name");
	  row.createCell(cellNumber++).setCellValue("Attribute Datatype");
	  row.createCell(cellNumber++).setCellValue("Attribute Definition");
	  row.createCell(cellNumber++).setCellValue("Attribute Is PK");
	  row.createCell(cellNumber++).setCellValue("Attribute Is FK");
	  row.createCell(cellNumber++).setCellValue("Attribute Required");  
														// For every entity
	  for (int counter = 0; counter < entities.size(); counter++)
	  {
		  Entity entity = (Entity) entities.get(counter);
	  
		  String entityName = entity.getName();
		  
		  List attributes = entity.getAttributes();

		  if(attributes == null)
			  continue;
															//  .. and every attribute
		  for (int i = 0; i < attributes.size(); i++)
		  {
			  Util.log("5-" + i);
	  
			  row = sheet.createRow(rowNum++);				// Create a new row

			  cellNumber = 0;								// Reset the cell counter.		

			  row.createCell(cellNumber++).setCellValue(subviewName);
			  row.createCell(cellNumber++).setCellValue(entityName);
			  
			  row.createCell(cellNumber++).setCellValue(entity.getComment());

			  Attribute attribute = (Attribute) attributes.get(i);
			  row.createCell(cellNumber++).setCellValue(attribute.getName());

															// If it's an integer, don't include the size.
			  if (attribute.getDataType().getName().equals("INTEGER"))
				row.createCell(cellNumber++).setCellValue(attribute.getDataType().getName());
			  else
				row.createCell(cellNumber++).setCellValue(attribute.getDataType().getName() + "(" + attribute.getDataType().getSize() + ")");

			  row.createCell(cellNumber++).setCellValue(attribute.getComment());
	  
			 row.createCell(cellNumber++).setCellValue(attribute.isPKElement() == true? "Yes": "No");
	  
			  row.createCell(cellNumber++).setCellValue(attribute.isPKElement() == true? "Yes": "No");
			  row.createCell(cellNumber++).setCellValue(attribute.isFKAttribute() == true? "Yes": "No");
			  row.createCell(cellNumber++).setCellValue(attribute.isMandatory() == true? "Yes": "No");

	  
		  }
	  }
															// Write it to a file.
	  try
	  {
		FileOutputStream out = 
			new FileOutputStream(new File(outputFile));
		
		workbook.write(out);
	
		out.close();
		
		Util.log(subviewName + " written to " + outputFile);
	  }
	  catch (FileNotFoundException e)
	  {
		Util.log(e.toString());
	  }
	  catch (IOException e)
	  {
		Util.log(e.toString());
	  }	  
  
    }
    catch (Throwable e)
    {
      Util.log(e.toString());
    }

    return true;
  }
}
