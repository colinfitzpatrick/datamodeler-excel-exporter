package  com.xsynergy.dm.excelexporter;

import oracle.ide.log.LogManager;

/**
 *
 */
public final class Util {
    
    public static final String NL = System.getProperty("line.separator");
    
    public static final void log(String message) {
        
        LogManager.getIdeLogWindow().log("[Excel Exporter] " + message + Util.NL);
        
    }
   
    /* -- Consts -- */
    public static final String kEBOFilter = "";
    
    public static final String kFileFilter = "xls";
    public static final String kFileFilterName = "Excel";
    
    public static final String kFilename = "-Flat_Report";
    
    /* -- Strings -- */
    public static final String InvalidState = "A Subview must be selected to generate a EBO Flat Report";
    
    public static final String ShortName = "Excel Exporter";

    public static final String ConfirmTitle = "Confirm";
    public static final String ConfirmReport = "Create Flat EBO Report";
    public static final String CancelReport = "Cancel";
    public static final String ConfirmMessage = "Create Excel Export for %s.  This will overwrite previous reports.";
 
    public static final String ChooseDir = "Choose the destination directory";
    
    public static final String PreferenceDirText = "Default Output Folder";
    public static final String PreferenceDirButtonText = "Choose Folder";
    public static final String PreferenceConfirmationText = "Confirm Report";
    
}
