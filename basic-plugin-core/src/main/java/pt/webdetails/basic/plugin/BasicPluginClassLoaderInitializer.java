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

import org.pentaho.platform.api.engine.IPentahoObjectReference;
import org.pentaho.platform.api.engine.IPentahoObjectRegistration;
import org.pentaho.platform.engine.core.system.PentahoSystem;
import org.pentaho.platform.engine.core.system.objfac.references.SingletonPentahoObjectReference;
import org.pentaho.platform.plugin.services.pluginmgr.PluginClassLoader;

import pt.webdetails.basic.plugin.api.IBasicPluginSettings;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Collections;

/**
 * Initializes a classLoader for this jar and registers it in PentahoSystem for this plugin
 */
public class BasicPluginClassLoaderInitializer {

  public static final String PLUGIN_ID_KEY = "plugin-id";

  private Logger logger = LoggerFactory.getLogger( getClass() );

  private IPentahoObjectRegistration objRegistration;

  public void init() {

    logger.info( "init()" );

    if( PentahoSystem.getInitializedOK() ) {

      PluginClassLoader pluginClassLoader = new PluginClassLoader( new File(""), this.getClass().getClassLoader() );

      // build a PentahoObjectReference to it with attribute map that relates it to 'basic-plugin' pluginId
      IPentahoObjectReference<ClassLoader> objRef =
          new SingletonPentahoObjectReference.Builder<ClassLoader>( ClassLoader.class )
              .object( pluginClassLoader )
              .attributes( Collections.<String, Object>singletonMap( PLUGIN_ID_KEY, IBasicPluginSettings.PLUGIN_ID ) ).build();


      // Register the classloader with PentahoSystem
      objRegistration = PentahoSystem.registerReference( objRef, ClassLoader.class );
    }

  }

  public void destroy() {

    logger.info( "destroy()" );

    if( getObjRegistration() != null ) {
      getObjRegistration().remove();
    }
  }

  public IPentahoObjectRegistration getObjRegistration() {
    return objRegistration;
  }

  protected void setObjRegistration( IPentahoObjectRegistration objRegistration ) {
    this.objRegistration = objRegistration;
  }
}
