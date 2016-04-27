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
import org.apache.commons.io.FilenameUtils;
import org.pentaho.platform.api.repository2.unified.IUnifiedRepository;
import org.pentaho.platform.api.repository2.unified.RepositoryFile;
import org.pentaho.platform.api.repository2.unified.data.simple.SimpleRepositoryFileData;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * REST endpoints' base URI is <webapp>/osgi/cxf/<ADDRESS>/<ENDPOINT_TERMINATION>
 * @see org.pentaho.platform.web.servlet.JAXRSPluginServlet
 */
@Path( "/" )
public class BasicPluginRestApi {

  protected static Logger logger = LoggerFactory.getLogger( BasicPluginRestApi.class );

  private IBasicPluginSettings settings;
  private IUnifiedRepository repository;

  public BasicPluginRestApi( IBasicPluginSettings settings, IUnifiedRepository repository ){
    setSettings( settings );
    setRepository( safeSetUnifiedRepository ( repository ) );
  }

  @GET
  @Path( "/hello" )
  @Produces( MediaType.TEXT_PLAIN )
  public String ready() {
    logger.info( "/hello endpoint" );
    return getSettings().getSaySomething();
  }

  @GET
  @Path( "/view" )
  public Response content( @QueryParam( "path" ) String path ) {
    logger.info( "/view endpoint" );

    RepositoryFile file;
    InputStream content = null;

    try {

      if( !getSettings().getExtension().equalsIgnoreCase( FilenameUtils.getExtension( path ) ) ) {
        logger.warn( "File extension not supported: " + FilenameUtils.getExtension( path ) );
        return Response.status( Response.Status.FORBIDDEN ).build();

      } else if( ( file = getRepository().getFile( path ) ) == null ) {
        logger.warn( "File not found in path " + path );
        return Response.status( Response.Status.NOT_FOUND ).build();
      }

      SimpleRepositoryFileData data = getRepository().getDataForRead( file.getId(), SimpleRepositoryFileData.class );
      content = data.getInputStream();

      return Response.ok( IOUtils.toString( content ), getSettings().getMimeType() ).build();

    } catch ( Exception e ) {
      logger.error( e.getLocalizedMessage(), e );
      return Response.serverError().build();

    } finally {
      IOUtils.closeQuietly( content );
    }
  }

  public IBasicPluginSettings getSettings() {
    return settings;
  }

  public void setSettings( IBasicPluginSettings settings ) {
    this.settings = settings;
  }

  public IUnifiedRepository getRepository() {
    return repository;
  }

  public void setRepository( IUnifiedRepository repository ) {
    this.repository = repository;
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
