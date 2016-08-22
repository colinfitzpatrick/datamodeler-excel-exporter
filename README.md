# datamodeler-excel-exporter
An extension for Oracle SQL*Developer Data Modeler to export subviews to excel.

Exports a flat excel report containing details of the entities and attributes in a logical subview.  The fields are currently hardcoded to

1. Subview Name
1. Entity Name
1. Entity Definition
1. Attribute Name
1. Attribute Datatype
1. Attribute Definition
1. Attribute Is PK
1. Attribute Is FK
1. Attribute Required

The inbuilt reports in DataModeler don't allow for a flat export. 
