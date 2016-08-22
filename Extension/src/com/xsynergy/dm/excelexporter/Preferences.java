package com.xsynergy.dm.excelexporter;

import oracle.javatools.data.HashStructure;
import oracle.javatools.data.HashStructureAdapter;
import oracle.javatools.data.PropertyStorage;

public final class Preferences
  extends HashStructureAdapter
{
  private static final String DATA_KEY = "com.xsynergy.dm.excelexporter.Preferences";
  
  private Preferences(HashStructure hashStructure)
  {
    super(hashStructure);
  }
  
  public static Preferences getInstance(PropertyStorage prefs)
  {
    return new Preferences(findOrCreate(prefs, DATA_KEY));
  }
  
  private static final String DEFAULT_FOLDER = "DEFAULT_FOLDER";   
  private static final String DEFAULT_DEFAULT_FOLDER = ""; 

  public String getDefaultFolder()
  {
    return _hash.getString(DEFAULT_FOLDER, DEFAULT_DEFAULT_FOLDER);
  }
  
  public void setDefaultFolder(String folder)
  {
    _hash.putString(DEFAULT_FOLDER, folder);
  }  

  private static final String CONFIRMATION = "CONFIRMATION";   
  private static final boolean DEFAULT_CONFIRMATION = true; 

  public boolean getConfirmation()
  {
    return _hash.getBoolean(CONFIRMATION, DEFAULT_CONFIRMATION);
  }
  
  public void setConfirmation(boolean confirmation)
  {
    _hash.putBoolean(CONFIRMATION, confirmation);
  }  
  
}
