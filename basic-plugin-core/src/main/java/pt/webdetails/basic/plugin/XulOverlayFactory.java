package pt.webdetails.basic.plugin;

import org.pentaho.ui.xul.XulOverlay;
import org.pentaho.ui.xul.impl.DefaultXulOverlay;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class XulOverlayFactory {

  private static Logger logger = LoggerFactory.getLogger( XulOverlayFactory.class );

  public enum FactoryType { BUTTON_CREATE_NEW, MENU_ITEM }

  private FactoryType factoryType;
  private String overlayId;
  private String itemId;
  private String commandId;
  private String commandLabel;
  private String commandUri;
  private String resourceBundleUri;

  public XulOverlayFactory( String factoryType ) throws EnumConstantNotPresentException {
    if( FactoryType.BUTTON_CREATE_NEW.toString().equalsIgnoreCase( factoryType ) ) {
      setFactoryType( FactoryType.BUTTON_CREATE_NEW );
    } else if ( FactoryType.MENU_ITEM.toString().equalsIgnoreCase( factoryType ) ) {
      setFactoryType( FactoryType.MENU_ITEM );
    } else {
      throw new EnumConstantNotPresentException( null, "factoryType attribute required; "
          + "one-of 'BUTTON_CREATE_NEW', 'MENU_ITEM' " );
    }
  }

  public XulOverlay build() {

    logger.info( "build() called for a xul overlay of type '" + getFactoryType().toString() + "' with ID '" + getOverlayId() );

    StringBuffer source = new StringBuffer();
    source.append( "<overlay id=\"" ).append( getOverlayId() ).append( "\" resourcebundle=\"" ).append( getResourceBundleUri() ).append( "\">" );

    switch ( getFactoryType() ) {

      case BUTTON_CREATE_NEW:
        buildButtonCreateNew( source );
        break;
      case MENU_ITEM:
        buildMenuItem( source );
        break;
      default:
        /* do nothing */
    }

    source.append( "</overlay>" );
    return new DefaultXulOverlay( getOverlayId(), getOverlayId(), source.toString(), getResourceBundleUri() );
  }

  protected void buildButtonCreateNew( StringBuffer source ) {

      source.append( "<button id=\"" ).append( getItemId() )
          .append( "\" label=\"" ).append( getCommandId() )
          .append( "\" command=\"Home.openFile('").append( getCommandId() ).append( "','")
          .append( getCommandLabel() ).append( "','" ).append( getCommandUri() )
          .append( "');$('#btnCreateNew').popover('hide');\" />" );
  }

  protected void buildMenuItem( StringBuffer source ) {

      source.append( "<menubar id=\"" ).append( getItemId() ).append( "\">" );
        source.append( "<menuitem id=\"" ).append( getCommandId().replaceAll( " ", "_" ) )
            .append( "\" label=\"" ).append( getCommandId() )
            .append( "\" command=\"mantleXulHandler.openUrl('").append( getCommandId().replaceAll( " ", "_" ) ).append( "','")
            .append( getCommandLabel() ).append( "','" ).append( getCommandUri() ).append( "')\" />" );
      source.append( "</menubar>" );
  }

  public FactoryType getFactoryType() {
    return factoryType;
  }

  public void setFactoryType( FactoryType factoryType ) {
    this.factoryType = factoryType;
  }

  public String getOverlayId() {
    return overlayId;
  }

  public void setOverlayId( String overlayId ) {
    this.overlayId = overlayId;
  }

  public String getItemId() {
    return itemId;
  }

  public void setItemId( String itemId ) {
    this.itemId = itemId;
  }

  public String getCommandId() {
    return commandId;
  }

  public void setCommandId( String commandId ) {
    this.commandId = commandId;
  }

  public String getCommandLabel() {
    return commandLabel;
  }

  public void setCommandLabel( String commandLabel ) {
    this.commandLabel = commandLabel;
  }

  public String getCommandUri() {
    return commandUri;
  }

  public void setCommandUri( String commandUri ) {
    this.commandUri = commandUri;
  }

  public String getResourceBundleUri() {
    return resourceBundleUri;
  }

  public void setResourceBundleUri( String resourceBundleUri ) {
    this.resourceBundleUri = resourceBundleUri;
  }
}
