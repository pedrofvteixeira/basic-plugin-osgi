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
package pt.webdetails.basic.plugin.dedicated;

import org.apache.commons.lang.StringUtils;
import org.pentaho.platform.api.mimetype.IMimeType;
import org.pentaho.platform.api.mimetype.IPlatformMimeResolver;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.plugin.services.importer.IPlatformImportHandler;
import org.pentaho.platform.plugin.services.importer.IPlatformImporter;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.webdetails.basic.plugin.BasicPluginContentGenerator;

public class BasicPluginContentTypeRegister {

  private static Logger logger = LoggerFactory.getLogger( BasicPluginContentGenerator.class );

  // The platform's IPlatformImporter and IPlatformMimeResolver
  private IPlatformImporter importResolver = PentahoSystem.get( IPlatformImporter.class );
  private IPlatformMimeResolver mimeResolver = PentahoSystem.get( IPlatformMimeResolver.class );

  // The IPlatformImportHandler and IMimeType lists we want to register in the platform
  private List<IPlatformImportHandler> importHandlers;
  private List<IMimeType> mimeTypeResolvers;
  
  public void start() {

    // file import handlers
    if( getImportHandlers() != null ) {

      for( IPlatformImportHandler handler : getImportHandlers() ) {
        importResolver.addHandler( handler );
        logger.info( "Registered '" + handler.getClass().getSimpleName() + "' in the platform" );
      }
    }

    // mimeType resolvers
    if( getMimeTypeResolvers() != null ) {

      for( IMimeType mime : getMimeTypeResolvers() ) {
        mimeResolver.addMimeType( mime );
        logger.info( "Registered '" + mime.getName() + "' in the platform" );
      }
    }
  }

  public List<IPlatformImportHandler> getImportHandlers() {
    return importHandlers;
  }

  public void setImportHandlers( List<IPlatformImportHandler> importHandlers ) {
    this.importHandlers = importHandlers;
  }

  public List<IMimeType> getMimeTypeResolvers() {
    return mimeTypeResolvers;
  }

  public void setMimeTypeResolvers( List<IMimeType> mimeTypeResolvers ) {
    this.mimeTypeResolvers = mimeTypeResolvers;
  }

}
