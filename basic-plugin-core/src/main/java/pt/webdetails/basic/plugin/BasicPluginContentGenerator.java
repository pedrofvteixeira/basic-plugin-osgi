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
package pt.webdetails.basic.plugin;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.pentaho.platform.api.engine.*;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.util.messages.LocaleHelper;

import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicPluginContentGenerator implements IContentGenerator {

  private static Logger logger = LoggerFactory.getLogger( BasicPluginContentGenerator.class );
  private static final String PATH_PARAMETER_ID = "path";

  protected String instanceId;

  protected Map<String, IParameterProvider> parameterProviders;

  protected IPentahoSession userSession;

  protected List<Object> callbacks;

  protected IPentahoUrlFactory urlFactory;

  protected List<String> messages;

  protected IOutputHandler outputHandler;

  protected String itemName;

  protected int loggingLevel = PentahoSystem.loggingLevel;

  private IBasicPluginSettings settings;
  private IUnifiedRepository repository;

  private boolean editMode; // set to true for 'perspective.edit' action

  public BasicPluginContentGenerator( IBasicPluginSettings settings, IUnifiedRepository repository ){
    setSettings( settings );
    setRepository( safeSetUnifiedRepository( repository ) );
  }

  @Override public void createContent() throws Exception {
    info( "createContent() called in " + ( isEditMode() ? "'edit'" : "'open'" ) + " mode" );

    IParameterProvider pathParams = parameterProviders.get( PATH_PARAMETER_ID );

    String urlEncodedFilePath;

    if( pathParams != null
        && !StringUtils.isEmpty( ( urlEncodedFilePath = pathParams.getStringParameter( PATH_PARAMETER_ID, null ) ) ) ) {

      String filePath = URLDecoder.decode( urlEncodedFilePath, LocaleHelper.UTF_8 );

      info( "createContent() called for file: " + filePath );

      getResponse().setContentType( getSettings().getMimeType() );
      getResponse().setHeader( "Cache-Control", "no-cache" );

      InputStream content = null;

      try {

        RepositoryFile file = getRepository().getFile( filePath );

        SimpleRepositoryFileData data = getRepository().getDataForRead( file.getId(), SimpleRepositoryFileData.class );

        writeOutAndFlush( getResponse().getOutputStream(), data.getInputStream() );

      } finally {
        IOUtils.closeQuietly( content );
      }
    }
  }

  @Override public void setOutputHandler( IOutputHandler outputHandler ) {
    this.outputHandler = outputHandler;
  }

  @Override public void setMessagesList( List<String> messages ) {
    this.messages = messages;
  }

  @Override public void setParameterProviders( Map<String, IParameterProvider> parameterProviders ) {
    this.parameterProviders = parameterProviders;
  }

  @Override public void setSession( IPentahoSession userSession ) {
    this.userSession = userSession;
  }

  @Override public void setUrlFactory( IPentahoUrlFactory urlFactory ) {
    this.urlFactory = urlFactory;
  }

  @Override public void setCallbacks( List<Object> callbacks ) {
    this.callbacks = callbacks;
  }

  @Override public void setInstanceId( String instanceId ) {
    this.instanceId = instanceId;
  }

  @Override public String getItemName() {
    return itemName;
  }

  @Override public void setItemName( String itemName ) {
    this.itemName = itemName;
  }

  @Override public int getLoggingLevel() {
    return loggingLevel;
  }

  @Override public void setLoggingLevel( int loggingLevel ) {
    this.loggingLevel = loggingLevel;
  }

  @Override public void trace( String message ) {
    logger.trace( message );
  }

  @Override public void debug( String message ) {
    logger.debug( message );
  }

  @Override public void info( String message ) {
    logger.info( message );
  }

  @Override public void warn( String message ) {
    logger.warn( message );
  }

  @Override public void error( String message ) {
    logger.error( message );
  }

  @Override public void fatal( String message ) {
    logger.error( message );

  }

  @Override public void trace( String message, Throwable error ) {
    logger.trace( message, error );
  }

  @Override public void debug( String message, Throwable error ) {
    logger.debug( message, error );
  }

  @Override public void info( String message, Throwable error ) {
    logger.info( message, error );
  }

  @Override public void warn( String message, Throwable error ) {
    logger.warn( message, error );
  }

  @Override public void error( String message, Throwable error ) {
    logger.error( message, error );
  }

  @Override public void fatal( String message, Throwable error ) {
    logger.error( message, error );
  }

  public IUnifiedRepository getRepository() {
    return repository;
  }

  public void setRepository( IUnifiedRepository repository ) {
    this.repository = repository;
  }

  public IBasicPluginSettings getSettings() {
    return settings;
  }

  public void setSettings( IBasicPluginSettings settings ) {
    this.settings = settings;
  }

  public boolean isEditMode() {
    return editMode;
  }

  public void setEditMode( boolean editMode ) {
    this.editMode = editMode;
  }

  protected HttpServletResponse getResponse() {
    return ( HttpServletResponse ) parameterProviders.get( PATH_PARAMETER_ID ).getParameter( "httpresponse" );
  }

  private void writeOutAndFlush( OutputStream out, InputStream data ) {
    try {
      IOUtils.copy( data, out );
      out.flush();
    } catch (IOException ex){
      logger.error( ex.getLocalizedMessage(), ex );
    }
  }

  private IUnifiedRepository safeSetUnifiedRepository( IUnifiedRepository repository ) {

    try {

      if( repository != null && repository.toString() != null ){
        return repository;
      }

    } catch ( Throwable t ) {
      /* do nothing, use fallback */
    }

    // fallback
    return PentahoSystem.get( IUnifiedRepository.class );
  }
}
