/*!
* Copyright 2002 - 2016 Webdetails, a Pentaho company.  All rights reserved.
*
* This software was developed by Webdetails and is provided under the terms
* of the Mozilla Public License, Version 2.0, or any later version. You may not use
* this file except in compliance with the license. If you need a copy of the license,
* please go to  http://mozilla.org/MPL/2.0/. The Initial Developer is Webdetails.
*
* Software distributed under the Mozilla Public License is distributed on an "AS IS"
* basis, WITHOUT WARRANTY OF ANY KIND, either express or  implied. Please refer to
* the license for the specific language governing your rights and limitations.
*/
package pt.webdetails.basic.plugin.platform.api.impl;

import org.pentaho.platform.api.engine.IContentInfo;
import org.pentaho.platform.api.engine.IPluginOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * IContentInfo implementation that will carry the file extension, mimeType and respective configured actions.
 * Similar to declaring it via <content-type> tag in plugin.xml, in non-OSGi platform plugins
 */
public class BasicPluginContentInfo implements IContentInfo {

  private String description;

  private String extension;

  private String mimeType;

  private String title;

  private List<IPluginOperation> operations = new ArrayList<IPluginOperation>();

  private String iconUrl;

  private boolean canImport;

  private boolean canExport;

  @Override public String getExtension() {
    return extension;
  }

  @Override public String getTitle() {
    return title;
  }

  @Override public String getDescription() {
    return description;
  }

  @Override public String getMimeType() {
    return mimeType;
  }

  @Override public List<IPluginOperation> getOperations() {
    return operations;
  }

  @Override public String getIconUrl() {
    return iconUrl;
  }

  @Override public boolean canImport() {
    return canImport;
  }

  @Override public boolean canExport() {
    return canExport;
  }

  public void setDescription( String description ) {
    this.description = description;
  }

  public void setExtension( String extension ) {
    this.extension = extension;
  }

  public void setMimeType( String mimeType ) {
    this.mimeType = mimeType;
  }

  public void setTitle( String title ) {
    this.title = title;
  }

  public void setOperations( List<IPluginOperation> operations ) {
    this.operations = operations;
  }

  public void setIconUrl( String iconUrl ) {
    this.iconUrl = iconUrl;
  }

  public void setCanImport( boolean canImport ) {
    this.canImport = canImport;
  }

  public void setCanExport( boolean canExport ) {
    this.canExport = canExport;
  }
}
