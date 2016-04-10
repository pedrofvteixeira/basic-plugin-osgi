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
package pt.webdetails.basic.plugin.api;

public interface IBasicPluginSettings {

  String PLUGIN_ID = "basic-plugin";

  /**
   * The file extension this plugin will become responsible for
   * @return file extension
   */
  String getExtension();

  /**
   * The file's mimeType
   * @return the file's mimeType
   */
  String getMimeType();

  /**
   * A one-liner this plugin will return when asked for
   * @return a one-liner this plugin we'll return when asked for
   */
  String getSaySomething();

}
