package com.xsynergy.dm.excelexporter;

import com.xsynergy.dm.excelexporter.Util;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;

import oracle.ide.panels.DefaultTraversablePanel;
import oracle.ide.panels.TraversableContext;
import oracle.ide.panels.TraversalException;

/**
 * Preference page implementation.
 */
final class PreferencePanel
  extends DefaultTraversablePanel
{
  
  private final JLabel label = new JLabel(Util.PreferenceDirText);
  private final JTextField folder = new JTextField();
  private final JButton chooser = new JButton();
  private final JCheckBox confirmation = new JCheckBox(Util.PreferenceConfirmationText);
  
  public PreferencePanel()
  {
    layoutControls();
  }

  private void layoutControls()
  { 
    try{
      GroupLayout layout = new GroupLayout(this);
    
      chooser.addActionListener(new ActionListener() {
     
                public void actionPerformed(ActionEvent e)
                {
                  final JFileChooser fc = new JFileChooser();

                  fc.setDialogTitle(Util.ChooseDir);
                  fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                  int returnVal = fc.showOpenDialog(null);
                  if (returnVal == JFileChooser.APPROVE_OPTION)
                  {
                    File file = fc.getSelectedFile();
                    folder.setText(file.getAbsolutePath());
                  }
                }
            });      
      
      chooser.setText(Util.PreferenceDirButtonText);
      folder.setPreferredSize(new Dimension( 200, 24 ));
 
      layout.setHorizontalGroup( layout.createSequentialGroup()
        .addGroup( layout.createParallelGroup( GroupLayout.Alignment.LEADING )
          .addComponent( label )
          .addComponent( folder ) )
        .addGroup( layout.createParallelGroup( GroupLayout.Alignment.LEADING )
          .addComponent( chooser )
          .addComponent( confirmation ) )
      );

    }
    catch(Throwable e)
    {
      e.toString();
    }
  
  }

  @Override
  public void onEntry(TraversableContext context)
  {
    final Preferences prefs = getPreferences(context);
    
    folder.setText(prefs.getDefaultFolder() );
    confirmation.setSelected(prefs.getConfirmation());
    
    super.onEntry(context);
  }

  @Override
  public void onExit(TraversableContext context)
    throws TraversalException
  {
    final Preferences prefs = getPreferences(context);
    
    prefs.setDefaultFolder(folder.getText().trim());
    prefs.setConfirmation(confirmation.isSelected());
    
    super.onExit(context);
  }
  
  private static Preferences getPreferences(TraversableContext tc)
  {
    return Preferences.getInstance(tc.getPropertyStorage());
  }

}
