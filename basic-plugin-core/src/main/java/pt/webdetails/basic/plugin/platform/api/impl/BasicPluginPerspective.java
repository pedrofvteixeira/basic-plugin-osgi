package pt.webdetails.basic.plugin.platform.api.impl;

import org.pentaho.platform.api.engine.perspective.pojo.IPluginPerspective;
import org.pentaho.ui.xul.XulOverlay;

import java.util.ArrayList;

/**
 * IPluginPerspective implementation that will carry the id, title, contentUrl, ...
 */
public class BasicPluginPerspective implements IPluginPerspective {

  private String id;
  private String title;
  private String contentUrl;
  private String resourceBundleUri;

  private ArrayList<XulOverlay> overlays;
  private int layoutPriority;
  private ArrayList<String> securityActions;

  @Override public String getId() {
    return id;
  }

  @Override public void setId( String id ) {
    this.id = id;
  }

  @Override public String getTitle() {
    return title;
  }

  @Override public void setTitle( String title ) {
    this.title = title;
  }

  @Override public String getContentUrl() {
    return contentUrl;
  }

  @Override public void setContentUrl( String contentUrl ) {
    this.contentUrl = contentUrl;
  }

  @Override public String getResourceBundleUri() {
    return resourceBundleUri;
  }

  @Override public void setResourceBundleUri( String uri ) {
    this.resourceBundleUri = uri;
  }

  @Override public ArrayList<XulOverlay> getOverlays() {
    return overlays;
  }

  @Override public void setOverlays( ArrayList<XulOverlay> overlays ) {
    this.overlays = overlays;
  }

  @Override public int getLayoutPriority() {
    return layoutPriority;
  }

  @Override public void setLayoutPriority( int layoutPriority ) {
    this.layoutPriority = layoutPriority;
  }

  @Override public ArrayList<String> getRequiredSecurityActions() {
    return securityActions;
  }

  @Override public void setRequiredSecurityActions( ArrayList<String> actions ) {
    this.securityActions = actions;
  }
}
